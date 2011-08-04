dojo.provide("encuestame.org.core.commons.dashboard.DashboardLayout");

dojo.require("dojo.dnd.Source");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.require("dijit.layout.ContentPane");
dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.layout.TabContainer");
dojo.require("encuestame.org.core.shared.utils.Avatar");

dojo.require("dojo.dnd.Manager");
dojo.require("dojo.dnd.Container");
dojo.require("dojo.dnd.Selector");
dojo.require("dijit.TitlePane");

dojo.require("encuestame.org.core.commons.dashboard.Gadget");

dojo.declare(
    "encuestame.org.core.commons.dashboard.DashboardLayout",
    [dijit._Widget, dijit._Templated],{
        /*
         * template path url.
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard", "template/dashboardLayout.html"),

        _type : ["AAA", "BB", "B", "AB", "BA"],

        dashboardWidget : null,

        /*
         * enable widgets on template.
         */
        widgetsInTemplate: true,

        /*
         *
         */
        _nodes : [],

        /*
         * post create.
         */
        postCreate : function() {
            //dojo.subscribe("/encuestame/dashboard/layout/change", this, "_change");
            //console.debug("postCreate", this._layout);
            this._layout.appendChild(this._createLayoutAAA());
        },

        /*
         *
         */
        _change : function(/** format **/ typeLayOut) {

        },

        _createGadget : function(data) {
            var gatget = new encuestame.org.core.commons.dashboard.Gadget({data:data});
            return gatget;
        },


        /*
         * create layout AAA.
         */
        _createLayoutAAA : function() {
            var wrapper = document.createElement("div");
            dojo.addClass(wrapper, "aaa");
            var a1 = document.createElement("ul");
            a1.id = "a1_"+this.id;
            a1.setAttribute("dndType", "gadget");
            //this._addEmtpyContent(a1);
            dojo.addClass(a1, "column a1");
            var a2 = document.createElement("ul");
            a2.id = "a2_"+this.id;
            a2.setAttribute("dndType", "gadget");
            //this._addEmtpyContent(a2);
            dojo.addClass(a2, "column a2");
            var a3 = document.createElement("ul");
            a3.id = "a3_"+this.id;
            a3.setAttribute("dndType", "gadget");
            //this._addEmtpyContent(a3);
            dojo.addClass(a3, "column a3");
            wrapper.appendChild(a1);
            wrapper.appendChild(a2);
            wrapper.appendChild(a3);
            //console.debug("_createLayoutAAA", wrapper);
            this._addDragSupport(a1);
            this._addDragSupport(a2);
            this._addDragSupport(a3);
            return wrapper;
        },

        _addEmtpyContent : function(node){
            var li = document.createElement("div");
            dojo.addClass(li, "empty-text");
            li.innerHTML = "Drag your gadgets here or add a new gadget.";
            node.appendChild(li);
        },

        /*
         *
         */
        _addDragSupport : function(node){
              var source  = new dojo.dnd.Source(node, {
              accept: ['gadget'],
              copyOnly: false,
              selfCopy : false,
              selfAccept: true,
              withHandles : true,
              autoSync : true,
              isSource : true
              //creator: this.dndNodeCreator
              });
              source.onDndSourceOver = function(source) {
                  //console.debug("onDndSourceOver this.targetAnchor SOURCE!!", this.source);
                  if(this.source != null){
                      //console.debug("onDndSourceOver this.source.node", this.source.node);
                  }
                  // summary:
                  //			topic event processor for /dnd/source/over, called when detected a current source
                  // source: Object
                  //		the source which has the mouse over it
                  if (this != source ){
                      if(this.source != null) {
                          //console.debug("onDndSourceOver NO ES NULO", this.source.node);
                      }
                      this.mouseDown = false;
                      if (this.targetAnchor) {
                          this._unmarkTargetAnchor();
                      }
                  } else if(this.isDragging) {
                      var m = dojo.dnd.manager();
                      m.canDrop(this.targetState != "Disabled" && (!this.current || m.source != this || !(this.current.id in this.selection)));
                  }
              };
              source.checkAcceptance =  function(source, nodes){
                  // summary:
                  //		checks if the target can accept nodes from this source
                  // source: Object
                  //		the source which provides items
                  // nodes: Array
                  //		the list of transferred items
                  if(this == source){
                      return !this.copyOnly || this.selfAccept;
                  }
                  for(var i = 0; i < nodes.length; ++i){
                      var type = source.getItem(nodes[i].id).type;
                      // type instanceof Array
                      var flag = false;
                      for(var j = 0; j < type.length; ++j){
                          if(type[j] in this.accept){
                              flag = true;
                              break;
                          }
                      }
                      if(!flag){
                          return false;	// Boolean
                      }
                  }
                  return true;	// Boolean
              };
              source.emtpy = null;
              source.createEmpty = function() {
                  if (this.emtpy == null) {
                      this.emtpy = document.createElement("div");
                      this.emtpy.id = "emtpy-dnd";
                      dojo.addClass(this.emtpy, "empty-text");
                      this.emtpy.innerHTML = encuestame.constants.messageCodes["021"];
                  }
              };
              source.destroyEmpty = function() {
                  console.debug("destroyEmpty", dojo.byId("emtpy-dnd"));
                  dojo.destroy(dojo.byId("emtpy-dnd"));
              };
              //var id = "li_"+source.node.id;
              source._markTargetAnchor = function(before){
                 console.debug("_markTargetAnchor targetAnchor!!", this.targetAnchor);
                  // summary:
                  //		assigns a class to the current target anchor based on "before" status
                  // before: Boolean
                  //		insert before, if true, after otherwise
                  if(this.current == this.targetAnchor && this.before == before){
                      return;
                  }
                  //console.debug("_markTargetAnchor this.targetAnchor !!", this.targetAnchor);
                  //console.debug("_markTargetAnchor this.this.current !!", this.current);
                  if (this.targetAnchor) {
                      //dojo.destroy(dojo.byId(id));
                      this._removeItemClass(this.targetAnchor, this.before ? "Before" : "After");
                  }
                  this.targetAnchor = this.current;
                  this.targetBox = null;
                  this.before = before;
                  if(this.targetAnchor) {
                      this._addItemClass(this.targetAnchor, this.before ? "Before" : "After");
                  }
              };
              source.onDndStart = function(source, nodes, copy){
                  console.debug("onDndStart source", source);
                  console.debug("onDndStart nodes", nodes);
                  //console.debug("onDndStart copy", copy);
                  this.createEmpty();
                  source.previous = null;
                  // summary:
                  //		topic event processor for /dnd/start, called to initiate the DnD operation
                  // source: Object
                  //		the source which provides items
                  // nodes: Array
                  //		the list of transferred items
                  // copy: Boolean
                  //		copy items, if true, move items otherwise
                  if(this.autoSync){ this.sync(); }
                  if(this.isSource){
                      this._changeState("Source", this == source ? (copy ? "Copied" : "Moved") : "");
                  }
                  var accepted = this.accept && this.checkAcceptance(source, nodes);
                  this._changeState("Target", accepted ? "" : "Disabled");
                  if(this == source){
                      dojo.dnd.manager().overSource(this);
                  }
                  this.isDragging = true;
              };
              source.onDndCancel = function() {
                  // summary:
                  //		topic event processor for /dnd/cancel, called to cancel the DnD operation
                  if(this.targetAnchor){
                      this._unmarkTargetAnchor();
                      this.targetAnchor = null;
                  }
                  this.before = true;
                  this.isDragging = false;
                  this.mouseDown = false;
                  this._changeState("Source", "");
                  this._changeState("Target", "");
                  this.destroyEmpty();
              };
              source.onMouseMove =  function(e){

                  // summary:
                  //		event processor for onmousemove
                  // e: Event
                  //		mouse event
                  if(this.isDragging && this.targetState == "Disabled"){ return; }
                  dojo.dnd.Source.superclass.onMouseMove.call(this, e);
                  var m = dojo.dnd.manager();
                  if(!this.isDragging){
                      if(this.mouseDown && this.isSource &&
                              (Math.abs(e.pageX - this._lastX) > this.delay || Math.abs(e.pageY - this._lastY) > this.delay)){
                          var nodes = this.getSelectedNodes();
                          if (nodes.length) {
                              //console.debug("source.onMouseMove startDrag");
                              m.startDrag(this, nodes, this.copyState(dojo.isCopyKey(e), true));
                          }
                      }
                  }
                  if(this.isDragging){
                      // calculate before/after
                      var before = false;
                      if (this.current) {
                          if(!this.targetBox || this.targetAnchor != this.current){
                              this.targetBox = dojo.position(this.current, true);
                          }
                          if(this.horizontal){
                              before = (e.pageX - this.targetBox.x) < (this.targetBox.w / 2);
                          }else{
                              before = (e.pageY - this.targetBox.y) < (this.targetBox.h / 2);
                          }
                      }
                      if(this.current != this.targetAnchor || before != this.before){
                          //console.debug("market source.previous", source.previous);
                          //console.debug("market this.current", this.current);
                          if(source.previous != this.targetAnchor) {
                              //console.debug("market moving empty");
                              source.node.insertBefore(source.emtpy, this.targetAnchor);
                              source.previous = this.targetAnchor;
                          }

                          this._markTargetAnchor(before);
                          //console.debug("market anchor", before);
                          m.canDrop(!this.current || m.source != this || !(this.current.id in this.selection));
                      }
                  }
              };
              source._unmarkTargetAnchor = function(){
                  //console.debug("_unmarkTargetAnchor !! ************************************* ");
                  //dojo.destroy(dojo.byId(id));
                  // summary:
                  //		removes a class of the current target anchor based on "before" status
                  if(!this.targetAnchor){ return; }
                  //console.debug("remove emtpy", li);

                  this._removeItemClass(this.targetAnchor, this.before ? "Before" : "After");
                  this.targetAnchor = null;
                  this.targetBox = null;
                  this.before = true;
              };
              dojo.connect(source, "onDndDrop", dojo.hitch(this, this.onDndColumn));
              var itemArray = [];
              dojo.forEach(this.dashboardWidget._test_gadgets, dojo.hitch(this, function(item) {
                  //console.debug("item", item);
                  itemArray.push(this._createGadget(item).domNode);
              }));
            source.insertNodes(false, itemArray);
        },


        dndNodeCreator : function (item, hint) {
            //console.debug("hint", hint);
            //console.debug("item", item);
            var tr = document.createElement("div");
            tr.innerHTML = "Item Dropped...";
            return {node: tr, data: item, type: "tweetpoll"};
        },


        /*
         * on drop on folder.
         */
        onDndColumn : function(source, nodes, copy, target) {
                //console.debug("onDndColumn", nodes);
                dojo.forEach(dojo.query(".dojoDndItemSelected"), function(item){
                    dojo.removeClass(item, "dojoDndItemSelected");
                });
                dojo.forEach(dojo.query(".dojoDndItemAnchor"), function(item){
                    dojo.removeClass(item, "dojoDndItemAnchor");
                });
                if(dojo.dnd.manager().target !== this._folderSourceWidget){
                    return;
                }
                if(dojo.dnd.manager().target == dojo.dnd.manager().source){
                    console.debug("same");
                } else {
                    dojo.forEach(this._folderSourceWidget.getSelectedNodes(), dojo.hitch(this, function(item) {
                       // console.debug("item", item);
//                        var tweetPollId = item.getAttribute('tweetpollId');
//                        var type = item.getAttribute('dndtype');
//                        console.debug("tweetpollId", tweetPollId);
//                        console.debug("type", type);
//                        this._addItem(parseInt(tweetPollId));
//                        dojo.destroy(item);
                    }));
                }
        }
    }
);

dojo.extend(dojo.dnd.Container, {
    onMouseOver: function(e){
        // summary:
        //		event processor for onmouseover
        // e: Event
        //		mouse event
        var n = e.relatedTarget;
        while(n){
            if(n == this.node){ break; }
            try{
                n = n.parentNode;
            }catch(x){
                n = null;
            }
        }
        if(!n){
            this._changeState("Container", "Over");
            this.onOverEvent();
        }
        n = this._getChildByEvent(e);
        if(this.current == n){ return; }
        if(this.current){ this._removeItemClass(this.current, "Over"); }
        if(n){ this._addItemClass(n, "Over"); }
        //console.debug("CONTAINER ", n);
        this.current = n;
    }
});

dojo.extend(dojo.dnd.Manager, {
    makeAvatar: function() {
           return new encuestame.org.core.shared.utils.Avatar(this);
    },
    updateAvatar: function() {
         this.avatar.update();
    },
    // avatar's offset from the mouse
    OFFSET_X: 0,
    OFFSET_Y: 0,
    canDrop: function(flag){
        //console.debug("canDrop flag", flag);
        // summary:
        //		called to notify if the current target can accept items
        var canDropFlag = Boolean(this.target && flag);
        //console.debug("canDrop canDropFlag", canDropFlag);
        if(this.canDropFlag != canDropFlag){
            this.canDropFlag = canDropFlag;
            this.avatar.update();
        }
    },
    overSource: function(source){
        //console.debug("overSource source", source.node);
        // summary:
        //		called when a source detected a mouse-over condition
        // source: Object
        //		the reporter
        if (this.avatar) {
            this.target = (source && source.targetState != "Disabled") ? source : null;
            this.canDropFlag = Boolean(this.target);
            this.avatar.update();
        }
        dojo.publish("/dnd/source/over", [source]);
    }
});