dojo.provide("encuestame.org.core.shared.utils.TimeRangeDropDownMenu");

dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.DropDownMenuSelect');

dojo.declare("encuestame.org.core.shared.utils.TimeRangeDropDownMenu",
    [ encuestame.org.main.EnmeMainLayoutWidget ], {

      /**
       * Default publish channel.
       */
      channel : "/encuestame/hashtag/time/range/refresh/graph",

      defaultDateRange : ENME.CONST.YEAR,

      /**
       * position default.
       */
      _open : false,

      /**
       * Default template.
       */
      templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils",
          "template/timeDropDownTemplate.html"),

      /**
       * post create.
       */
      postCreate : function() {
        this.inherited(arguments);
        if (this.range_actions) {
          this.buildMenu(this.range_actions);
        }
        dojo.connect(this._menu, "onclick", dojo.hitch(this, function(event) {
                  this._expandMenu();
              }));
      },

      /**
       * Build the menu.
       * @param arrayList {Object}
       */
      buildMenu : function (arrayList) {
        var innerText = dojo.hitch(this, function(value) {
          this._menu.innerHTML  = value;
        });
        var setItem = dojo.hitch(this, function (item) {
          innerText(item.period);
          this._expandMenu(item);
        });
        dojo.forEach(arrayList, dojo.hitch(this,function(menuItem) {
                   var item = dojo.create('li');
                   dojo.addClass(item, "dropdown-item");
                   item.innerHTML = menuItem.period;
                   if (this.defaultDateRange && this.defaultDateRange === menuItem.value) {
                     innerText(menuItem.period);
                   }
                   dojo.connect(item, "onclick", dojo.hitch(this, function(event) {
                     menuItem.action(this.channel);
                     setItem(menuItem);
                    }));
                   this.append(item, this._items);
                }));
      },

      /**
       *
       * @param item
       */


      /**
       * Expand the menu when the user click in the button.
       */
      _expandMenu : function () {
        if (this._open) {
          dojo.removeClass(this._menu, "menu-expand");
          dojo.addClass(this._items, "hidden");
        } else {
          dojo.addClass(this._menu, "menu-expand");
          dojo.removeClass(this._items, "hidden");
        }
        this._open = !this._open;
      }

    });
