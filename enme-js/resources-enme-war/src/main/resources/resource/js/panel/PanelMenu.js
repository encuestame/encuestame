/*
 * Copyright (c) 2013 Juan Picado, @jotadeveloper

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

define([
  "dojo",
  "dojo/_base/declare",
  "dojo/on",
  "dojo/_base/array",
  "dojo/dom-class",
  "dojo/dom-style",
  "dojo/_base/lang",
  "dojo/dom-attr",
  "dojo/dom-construct",
  "dojo/NodeList-manipulate",
  "dojo/query",
  "hammer/hammer" 
  ], function(
  dojo,
  declare,
  on,
  array,
  domClass,
  domStyle,
  lang,
  domAttr,
  domConstruct,
  nodeList,
  query,
  Hammer) {

  return declare(null, {

      _options: {
      
        menu: '#menu',
        trigger: '.menu-trigger',
        excludedPanelContent: 'style, script',

        direction: 'left',
        openPosition: '200px',
        animated: true,
        closeOnContentClick: true,

        keyboardShortcuts: [{
          code: 27,
          open: false,
          close: true
        }, {
          code: 37,
          open: false,
          close: true
        }, {
          code: 39,
          open: true,
          close: true
        }, {
          code: 77,
          open: true,
          close: true
        }],

        duration: 150,
        openDuration: 150,
        closeDuration:  150,

        easing: 'ease-in-out',
        openEasing: 'ease-in-out',
        closeEasing: 'ease-in-out',

        before: function() {},
        beforeOpen: function() {},
        beforeClose: function() {},

        after: function() {},
        afterOpen: function() {},
        afterClose: function() {},

        beforeOn: function() {},
        afterOn: function() {},

        beforeOff: function() {}

    },

    settings: {
      transitionsSupported: 'WebkitTransition' in document.body.style || 'MozTransition' in document.body.style || 'msTransition' in document.body.style || 'OTransition' in document.body.style || 'Transition' in document.body.style,
      shiftFixedChildren: false,
      panelPosition: 'relative',
      positionUnits: 'px'
    },

    menu: '#jPanelMenu-menu',

    css_html_class : 'dojoPanelMenu',

    panel: '.jPanelMenu-panel',

    fixedChildren: [],

    timeouts: {},

    _body: null,

    _html: null,

    /**
     *
     * @method constructor
     */
    constructor: function(options) {
      this._body = query('body')[0];
      this._html = query('html')[0];
      lang.mixin(this._options, options || {});
      this.init();
    },


    /**
     *
     * @method
     */
    init: function() {        
        this._options.beforeOn();

        this.initiateClickListeners();
        if ( Object.prototype.toString.call(this._options.keyboardShortcuts) === '[object Array]' ) {
            this.initiateKeyboardListeners();
        }

        this.setjPanelMenuStyles();

        this.setMenuState(false);
        this.setupMarkup();

        this.setMenuStyle({
            width: this._options.openPosition
        });
       
        this.checkFixedChildren();
        this.setPositionUnits();

        this.closeMenu();

        this._options.afterOn();

        var node = query(this.menu)[0];
        on(node, "click", function(){
          return false;
        });

        var content = query('.jPanelMenu-panel')[0];
        console.log("jPanelMenu-panel", content);
        var hammertime = Hammer(content).on("swiperight", dojo.hitch(this, function(event) {
             this.triggerMenu('left');
         }));
    },

    /**
     *
     * @method
     */
    clearTimeouts: function() {
        clearTimeout(this.timeouts.open);
        clearTimeout(this.timeouts.afterOpen);
        clearTimeout(this.timeouts.afterClose);
    },

    /**
     *
     * @method
     */
    setPositionUnits: function() {
      var foundUnit = false,
          allowedUnits = ['%','px','em'];
        for ( unitID in allowedUnits ) {
          var unit = allowedUnits[unitID];
          if ( this._options.openPosition.toString().substr( - unit.length) == unit ) {
            foundUnit = true;
            this.settings.positionUnits = unit;
          }
        }
        if (!foundUnit) {
          this._options.openPosition = parseInt(this._options.openPosition) + this.settings.positionUnits;
        }
    },

    /**
     *
     * @method
     */
    checkFixedChildren: function() {
        this.disableTransitions();
        var panel = query(this.panel);
        domClass.add(panel, 'position');
        var defaultPanelStyle = {
          position: domClass.add(panel, 'position')
        };

        defaultPanelStyle[this._options.direction] = dojo.hasClass(panel, 'position') == 'auto' ? 0 : dojo.hasClass(panel, this._options.direction);

        query(this.panel + '> *').forEach(dojo.hitch(this, function(node) {
          if (dojo.hasClass(node, 'position') == 'fixed' && dojo.hasClass(node, this._options.direction) == 'auto' ) {
             this.fixedChildren.push(this);
          }
        }));

        if (this.fixedChildren.length > 0) {
          var newPanelStyle = {
            position: 'relative'
          };
          newPanelStyle[this._options.direction] = '1px';
          this.setPanelStyle(newPanelStyle);

          if (parseInt(dojo.position(query(this.fixedChildren[0]), true).x) === 0 ) {
             this.settings.shiftFixedChildren = true;
          }
        }

        this.setPanelStyle(defaultPanelStyle);
    },

    /**
     *
     * @method
     */
    setjPanelMenuStyles: function() {
        var bgColor = '#fff';
        var body = this._body;
        var htmlBG = bgColor;
        var bodyBG = bgColor;
        if (bodyBG != 'transparent' && bodyBG != "rgba(0, 0, 0, 0)") {
            bgColor = bodyBG;
        } else if( htmlBG != 'transparent' && htmlBG != "rgba(0, 0, 0, 0)") {
          bgColor = htmlBG;
        } else {
          bgColor = '#fff';
        }

        if (query('#jPanelMenu-style-master').length == 0) {
          var node = 'body{width:100%}.jPanelMenu,body{overflow-x:hidden}#jPanelMenu-menu{display:block;position:fixed;top:0;' +
              this._options.direction +
              ':0;height:100%;z-index:-1;overflow-x:hidden;overflow-y:scroll;-webkit-overflow-scrolling:touch}.jPanelMenu-panel{position:static;' +
              this._options.direction +
              ':0;top:0;z-index:2;width:100%;min-height:100%;background:' +
              bgColor +
              '}';
          var style = domConstruct.create("style" , {
            innerHTML : node,
            id: 'jPanelMenu-style-master'
          });
          body.appendChild(style);
        }
    },

    /**
     *
     * @method
     */
    setMenuState: function(open) {
        var position = (open) ?'open':'closed';
        domAttr.set(this._body, 'data-menu-position', position);
    },

    /**
     *
     * @method
     */
    getMenuState: function() {
      return domAttr.get(this._body, "data-menu-position");
    },

    /**
     *
     * @method
     */
    getMenuSide: function() {
      return domAttr.get(this._body, "data-menu-side");
    },

    /**
     *
     * @method
     */
    setMenuStyle: function(styles) {
      var node = query(this.menu)[0];
      if (node) {
        domStyle.set(node, styles);
      }
    },

    /**
     *
     * @method
     */
    menuIsOpen: function() {
        if ( this.getMenuState() === 'open' ) {
           return true;
        } else {
          return false;
        }
    },

    /**
     *
     * @method
     */
    setPanelStyle: function(styles) {
      var node = query(this.panel)[0];
      domStyle.set(node, styles);
    },

    /**
     *
     * @method
     */
    showMenu: function() {
      this.setMenuStyle({
          display: 'block'
        });
      this.setMenuStyle({
          'z-index': '1'
        });
    },

    /**
     *
     * @method
     */
    closeMenu: function() {
        if (typeof(animated) === "undefined" || animated === null ) {
          animated = this._options.animated;
        };      

        this.clearTimeouts();

        this._options.before();
        this._options.beforeClose();

        this.setMenuState(false, '');

        var animationChecks = {
          transitions: this.settings.transitionsSupported
        };

        if ( animationChecks.transitions) {
          if ( animationChecks.none ) {
             this.disableTransitions();
          }
          if ( animationChecks.transitions ) {
            this.enableTransitions(this._options.closeDuration, this._options.closeEasing);
          }

          var newPanelStyle = {},
          parent = this;
          newPanelStyle[this._options.direction] = 0 + this.settings.positionUnits;
          this.setPanelStyle(newPanelStyle);

          if (this.settings.shiftFixedChildren) {
            for (var i = 0; i < this.fixedChildren.length; i++) {
              var item = this.fixedChildren[i];
              var _id = item.tagName.toLowerCase() + ' ' + item.className,
                selector = _id.replace(' ','.'),
                _id = _id.replace(' ','-');

              if (animationChecks.transitions) {
                  this.enableFixedTransitions(selector, _id, this._options.openDuration, this._options.openEasing);
              }

              var newChildrenStyle = {};
              newChildrenStyle[id] = 0 + this.settings.positionUnits;
              domStyle.set(item, newChildrenStyle);
            }
          }

          this.timeouts.afterClose = setTimeout(dojo.hitch(this, function() {
            this.setPanelStyle({
              position: this.settings.panelPosition
            });

            this.disableTransitions();

            if (this.settings.shiftFixedChildren) {
              for (var i = 0; i < this.fixedChildren.length; i++) {
                var _id = item.tagName.toLowerCase() + ' ' + item.className,
                selector = _id.replace(' ','.'),
                _id = _id.replace(' ','-');
                this.disableFixedTransitions(_id);
              }
            }

            //this.hideMenu(id === 'left' ? 'right' : 'left');
            this._options.after();
            this._options.afterClose();
            this.destroyContentClickListeners();
          }), this._options.closeDuration);
        }
    },

    /**
     *
     * @method
     */
    // hideMenu: function(id) {
    //     this.setMenuStyle({
    //       'z-index': '-1'
    //     }, id);
    //     this.setMenuStyle({
    //       display: 'none'
    //     }, id);
    // },

    /**
     *
     * @method
     */
    enableTransitions: function(duration, easing) {
      var formattedDuration = duration/1000;
      var formattedEasing = this.getCSSEasingFunction(easing);
      this.disableTransitions();
      var node = '.jPanelMenu-panel{-webkit-transition: all ' + formattedDuration + 's ' + formattedEasing + '; -moz-transition: all ' + formattedDuration + 's ' + formattedEasing + '; -o-transition: all ' + formattedDuration + 's ' + formattedEasing + '; transition: all ' + formattedDuration + 's ' + formattedEasing + ';}';
      var style = domConstruct.create("style" , {
            innerHTML : node,
            id: 'PanelMenu-style-transitions'
          });
      this._body.appendChild(style);
    },

    /**
     *
     * @method
     */
    disableTransitions: function() {
      var node = dojo.byId('PanelMenu-style-transitions');
      if (node) {
        domConstruct.destroy(node);
      }
    },

    /**
     *
     * @method
     */
    enableFixedTransitions: function(selector, id, duration, easing) {
        var formattedDuration = duration / 1000;
        var formattedEasing = this.getCSSEasingFunction(easing);
        this.disableFixedTransitions(id);
        var node = selector + '{-webkit-transition: all ' + formattedDuration + 's ' + formattedEasing + '; -moz-transition: all ' + formattedDuration + 's ' + formattedEasing + '; -o-transition: all ' + formattedDuration + 's ' + formattedEasing + '; transition: all ' + formattedDuration + 's ' + formattedEasing + ';}';
        var style = domConstruct.create("style" , {
            innerHTML : node,
            id: 'jPanelMenu-style-fixed-' + id
          });
        this._body.appendChild(style);
    },

    /**
     *
     * @method
     */
    disableFixedTransitions: function(id) {
      var node = query('#jPanelMenu-style-fixed-' + id);
      domConstruct.destroy(node);
    },

    /**
     *
     * @method
     */
    getCSSEasingFunction: function(name) {
      switch (name) {
          case 'linear':
            return name;
          case 'ease':
            return name;
          case 'ease-in':
            return name;
          case 'ease-out':
            return name;
          case 'ease-in-out':
            return name;
          default:
            return 'ease-in-out';
        }
    },

    /**
     *
     * @method
     */
    openMenu: function(id) {
        this.clearTimeouts();

        this._options.before();
        this._options.beforeOpen();

        this.setMenuState(true, id);

        this.setPanelStyle({ position: 'relative' });

        this.showMenu(id);

        var animationChecks = {
          transitions: this.settings.transitionsSupported
        };

        if ( animationChecks.transitions ) {
          if (animationChecks.none) {
            this.disableTransitions();
          }
          if ( animationChecks.transitions ) {
            this.enableTransitions(this._options.openDuration, this._options.openEasing);
          }

          var newPanelStyle = {};          
          newPanelStyle[id] = this._options.openPosition;
          newPanelStyle['right'] = '';
          this.setPanelStyle(newPanelStyle);

          if ( this.settings.shiftFixedChildren ) {
            for (var i = 0; i < this.fixedChildren.length; i++) {
              var item = this.fixedChildren[i];
              var _id = item.tagName.toLowerCase() + ' ' + item.className,
                selector = _id.replace(' ','.'),
                _id = _id.replace(' ','-');

              if (animationChecks.transitions) {
                  this.enableFixedTransitions(selector, _id, this._options.openDuration, this._options.openEasing);
              }

              var newChildrenStyle = {};
              newChildrenStyle[id] = this._options.openPosition;
              domStyle.set(item, newChildrenStyle);
            }
          }

          this.timeouts.afterOpen = setTimeout(dojo.hitch(this, function(){
            this.disableTransitions();
            if ( this.settings.shiftFixedChildren ) {
              for (var i = 0; i < this.fixedChildren.length; i++) {
                var item = this.fixedChildren[i];
                var _id = item.tagName.toLowerCase() + ' ' + item.className,
                  selector = _id.replace(' ','.'),
                  _id = _id.replace(' ','-');
                  this.disableFixedTransitions(_id);
              };
            }

            this._options.after();
            this._options.afterOpen();
            this.initiateContentClickListeners();
          }), this._options.openDuration);
        }
    },

    /**
     *
     * @method
     */
    destroy: function() {
        this._options.beforeOff();
        this.closeMenu();
        this.destroyClickListeners();
        if ( Object.prototype.toString.call(this._options.keyboardShortcuts) === '[object Array]' ) {
          this.destroyKeyboardListeners();
        }
        this.resetMarkup();
        var childrenStyles = {};
        childrenStyles[this._options.direction] = 'auto';
        query(this.fixedChildren).forEach(function(node) {
          domStyle.set(node, childrenStyles);
        });
        this.fixedChildren = [];
        this._options.afterOff();
    },

    /**
     *
     * @method
     */
    resetMarkup: function() {
        domClass.remove(this._html, this.css_html_class);
        var node = query('body > ' + this.panel)[0];
        dojo.unwrap(node);
        domConstruct.destroy(query(this.menu)[0]);
    },

    /**
     *
     * @method
     */
    setupMarkup: function() {
        domClass.add(this._html, this.css_html_class);
        var _query = query('body:not(style) > *'),
        set = dojo.hitch(this, function(_menu, _menu_id) {
          _query.wrapAll('<div class="' + this.panel.replace('.','') + '"/>');
          var menu = lang.clone(query(_menu))[0];
          domClass.add(menu, 'menu-background');
          domAttr.set(menu, 'id', (_menu_id).replace('#',''));
          query(menu).insertAfter('body > ' + this.panel);
        });
        set(this._options.menu, this.menu);
    },

    /**
     *
     * @method
     */
    triggerMenu: function(id) {
      if (this.menuIsOpen()) {
        this.closeMenu(id);
      } else  {
        this.openMenu(id);
      }
    },

    /**
     *
     * @method
     */
    closeAllMenu: function () {
      this.closeMenu(this.getMenuSide());
    },

    /**
     *
     * @method
     */
    initiateClickListeners: function() {
        this._e_initiateClickListeners = query(this._options.trigger).on('click',dojo.hitch(this, function(e) {
          this.triggerMenu('left');
          return false;
        }));
    },

    /**
     *
     * @method
     */
    destroyClickListeners: function() {
       this._e_initiateClickListeners.forEach(function(handle) {
         handle.remove();
      });
       this._e_right_initiateClickListeners.forEach(function(handle) {
         handle.remove();
      });
    },



    /**
     *
     * @method
     */
    initiateContentClickListeners: function() {
        if ( !this._options.closeOnContentClick ) {
           return false;
        }

        // query(document).on('click', this.panel, dojo.hitch(this, function(e){
        //   if ( this.menuIsOpen() ){
        //      this.closeMenu(this._options.animated);
        //   }
        // }));

        // query(document).on('touchend', this.panel, dojo.hitch(this, function(e){
        //   if ( this.menuIsOpen() ) {
        //      this.closeMenu(this._options.animated);
        //   }
        // }));
    },

    /**
     *
     * @method
     */
    destroyContentClickListeners: function() {
      //
      if (this._e_initiateContentClickListeners_click) {
        this._e_initiateContentClickListeners_click.forEach(function(handle) {
           handle.remove();
        });
      }
      //
      if (this._e_initiateContentClickListeners_touch) {
        this._e_initiateContentClickListeners_touch.forEach(function(handle) {
           handle.remove();
        });
      }
    },    

    destroyKeyboardListeners: function() {
      this._initiateKeyboardListeners.forEach(function(handle) {
         handle.remove();
      });
    },

    /**
     *
     * @method
     */
    initiateKeyboardListeners: function() {
        var preventKeyListeners = ['input', 'textarea'];

        this._initiateKeyboardListeners = query(document).on('keydown',dojo.hitch(this, function(e) {
          var target = e.target,
          prevent = false;

          array.forEach(preventKeyListeners, function(item) {
            if (target === item) {
              prevent = true;
            }
          });

          if ( prevent ) {
            return true;
          }

          for ( var mapping in this._options.keyboardShortcuts ) {
            if ( e.which == this._options.keyboardShortcuts[mapping].code ) {
              var key = this._options.keyboardShortcuts[mapping];

              if ( key.open && key.close ) {
                this.triggerMenu(this._options.animated);
              } else if ( (key.open && !key.close) && !this.menuIsOpen() ) {
                this.openMenu(this._options.animated);
              } else if ( (!key.open && key.close) && this.menuIsOpen() ) {
                this.closeMenu(this._options.animated);
              }
              return false;
            }
          }
        }));
    }

  });
});