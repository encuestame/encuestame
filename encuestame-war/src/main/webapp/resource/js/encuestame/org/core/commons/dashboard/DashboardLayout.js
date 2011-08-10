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

        /*
         * list of layouts.
         */
        _type : ["AAA", "BB", "B", "AB", "BA"],

        /*
         * dashboard widget.
         */
        dashboardWidget : null,

        /*
         * enable widgets on template.
         */
        widgetsInTemplate : true,

        /*
         * dashboard info.
         */
        dashboard : null,

        /*
         * gadgets.
         */
        gadgets: null,


        _listColumns : [],

        /*
         * post create.
         */
        postCreate : function() {
            dojo.subscribe("/encuestame/dashboard/layout/change", this, "_change");
            dojo.subscribe("/encuestame/dashboard/gadget/add", this, "_addGadget");
            if (this.dashboard) {
                this.loadLayout(this.dashboard.layout);
            } else {
                //error
            }
        },

        /**
         * Load layout
         * @param layout layout to load.
         */
        loadLayout : function(layout /*string layout*/){
            var gadgets = [];
            var node = null;
            if (layout == this._type[0]) {
                this._layout.appendChild(this._createLayoutAAA(this.gadgets));
            } else if (layout == this._type[1]) {
                this._layout.appendChild(this._createLayoutAAA(this.gadgets));
            } else if (layout == this._type[2]) {
                this._layout.appendChild(this._createLayoutAAA(this.gadgets));
            } else if (layout == this._type[3]) {
                this._layout.appendChild(this._createLayoutAAA(this.gadgets));
            } else if (layout == this._type[4]) {
                this._layout.appendChild(this._createLayoutAAA(this.gadgets));
            } else {
                //error.
            }
            if(node != null){
                this._layout.appendChild(node);
            }
        },

        _addGadget : function(name){
            var params = {boardId: this.dashboard.id, gadgetId: name};
            console.debug("add gadget", name);
            console.debug("_addDndGadgetItem", params);
            var load = dojo.hitch(this, function(data) {
                console.debug("_addGadget", data);
                });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(encuestame.service.gadget.add, params, load, error);
        },

        /*
         *
         */
        _change : function(/** format **/ typeLayOut) {

        },

        /*
         * create layout AAA.
         */
        _createLayoutAAA : function(gadgets) {
            this._listColumns = [1, 2, 3];
            var wrapper = document.createElement("div");
            wrapper.appendChild(this._createColumn("1", "a1_"+this.id, gadgets).domNode);
            wrapper.appendChild(this._createColumn("2", "a2_"+this.id, gadgets).domNode);
            wrapper.appendChild(this._createColumn("3", "a3_"+this.id, gadgets).domNode);
            //console.debug("_createLayoutAAA", wrapper);
            return wrapper;
        },

        /*
         * create layout A.
         */
        _createLayoutA : function() {
            var wrapper = document.createElement("div");
            wrapper.appendChild(this._createColumn("1", "a1_"+this.id));
            console.debug("_createLayoutA", wrapper);
            return wrapper;
        },


        /*
         *
         */
        _createColumn : function(i, id, gadgets){
            var widget = new encuestame.org.core.commons.dashboard.LayoutColumn({ id: id, column : i, gadgets : gadgets, dashboard: this.dashboard}, "ul");
            this._listColumns.push(widget);
            return widget;
        }
    }
);

/**
 *
 */
dojo.declare(
        "encuestame.org.core.commons.dashboard.LayoutColumn",
        [dijit._Widget, dijit._Templated],{

            /*
             * template path url.
             */

            templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard", "template/column.html"),

            column : "1",


            dashboard : null,

            /*
             * source dnd support.
             */
            sourceDndWidget : null,

            /*
             * list of gadgets.
             */
            gadgets: [],

            /*
             * list of widgets gadget.
             */
            _widgetsGadgets: [],

            /*
             *
             */
            accept: ['gadget'],

            /*
             *
             */
            postCreate : function() {
                this._addDndSupport();
                this._addGadgets();
            },


            /*
             * create gadgets.
             */
            _createGadget : function(data /* gadget info*/) {
                var gatget = new encuestame.org.core.commons.dashboard.Gadget({data : data});
                return gatget;
            },


            /*
             *
             */
            _addGadgets : function(){
              var itemArray = [];
              console.debug("this.gadgets", this.gadgets);
              dojo.forEach(this.gadgets, dojo.hitch(this, function(item) {
                  console.debug(item.gadget_column, this.column);
                  if(item.gadget_column == parseInt(this.column)){
                      var widget = this._createGadget(item);
                      this._widgetsGadgets.push(widget);
                      itemArray.push(widget.domNode);
                  }
              }));
              //console.debug("this.sourceDndWidget", this.sourceDndWidget);
              this.sourceDndWidget.insertNodes(false, itemArray);
            },

            /*
             * dnd node creator.
             */
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
                    console.debug("//////////////////////////////////////////////////onDndColumn", nodes);
                    dojo.forEach(dojo.query(".dojoDndItemSelected"), function(item){
                        dojo.removeClass(item, "dojoDndItemSelected");
                    });
                    dojo.forEach(dojo.query(".dojoDndItemAnchor"), function(item){
                        dojo.removeClass(item, "dojoDndItemAnchor");
                    });
                    if(dojo.dnd.manager().target !== this.sourceDndWidget){
                        return;
                    }
                    if(dojo.dnd.manager().target == dojo.dnd.manager().source){
                        console.debug("same");
                    } else {
                        dojo.forEach(this.sourceDndWidget.getSelectedNodes(), dojo.hitch(this, function(item) {
                              console.debug("DND item", item);
                              console.debug("DND item", this.dashboard);
                              var gid = item.getAttribute('gid');
                              if (gid) {
                                  this._addDndGadgetItem({
                                        gadgetId : parseInt(gid),
                                        position : 1,
                                        column   : this.column,
                                        dashboardId : this.dashboard.id,
                                       });
                              }
                        }));
                    }
            },

            /*
             * {
             *  gadgetId : x,
             *  position : x,
             *  column   : x,
             *  dashboardId x,
             *  }
             */
            _addDndGadgetItem : function(params){
                console.debug("_addDndGadgetItem", params);
                var load = dojo.hitch(this, function(data) {
                    console.debug("_addDndGadgetItem", data);
                    });
                var error = function(error) {
                    console.debug("error", error);
                };
                encuestame.service.xhrGet(encuestame.service.gadget.move, params, load, error);
            },

            /*
             *
             */
            _addDndSupport : function() {
                var source  = new dojo.dnd.Source(this._ul, {
                accept: this.accept,
                copyOnly: false,
                selfCopy : false,
                selfAccept: true,
                withHandles : true,
                autoSync : true,
                isSource : true
                //creator: this.dndNodeCreator
                });
                this.sourceDndWidget = source;
                source.onDndSourceOver = function(source) {
                    //console.debug("onDndSourceOver this.targetAnchor SOURCE!!", this.source);
                    if (this.source != null){
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
                    if(this == source) {
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
                    //console.debug("destroyEmpty", dojo.byId("emtpy-dnd"));
                    dojo.destroy(dojo.byId("emtpy-dnd"));
                };
                //var id = "li_"+source.node.id;
                source._markTargetAnchor = function(before){
                   //console.debug("_markTargetAnchor targetAnchor!!", this.targetAnchor);
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
                    //console.debug("onDndStart source", source);
                    //console.debug("onDndStart nodes", nodes);
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
                    if (this.isDragging) {
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
                source._unmarkTargetAnchor = function() {
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
            },


            /*
             *
             */
            _addEmtpyContent : function(node){
                var li = document.createElement("div");
                dojo.addClass(li, "empty-text");
                li.innerHTML = "Drag your gadgets here or add a new gadget.";
                node.appendChild(li);
            }
});

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