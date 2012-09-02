dojo.provide("encuestame.org.core.commons.dialog.ModalBox");

dojo.require("dojo.fx");

/**
 * Widget to display a pretty a modal box.
 */
dojo.declare(
    "encuestame.org.core.commons.dialog.ModalBox",
     null,{

    /**
     * Required node to attach the modal box.
     */
     node : null,

    /**
     * Define the mode of modalbox.
     */
     mode : "alert", //could be [alert, decision],

     /**
      * Default behaviour of decision button.
      */
     handler : function(){},

     /**
      * Modal constructor.
      * @param node
      * @param mode
      * @param customHalder
      */
     constructor : function(node, mode, customHandler) {
         if (node == null) {
             throw new Error("node is null");
         }
         this.node = node;
         this.mode = mode == null ? this.mode : mode;
         this.handler = customHandler == null ? this.handler : customHandler;
         //scroll event for IE
         ///document.addEventListener(!dojo.isMozilla ? "onmousewheel" : "DOMMouseScroll", dojo.hitch(this, this.onScroll), false);
         //scroll wheel for
         //window.onscroll = dojo.hitch(this, this.onScroll);

     },

     onScroll : function(){
         //var height = window.height();
         //var scrollTop = window.scrollTop();
         //console.debug("onScroll",height);
         //console.debug("onScroll",scrollTop);
     },

     /**
      * Override the default handler
      */
     addButtonHandler : function(customHandler){
         this.handler = customHandler;
     },

     /**
      * Display the modalbox.
      * @param content
      */
     show : function(content) {
         dojo.addClass(this.node, "modalBoxBackground");
         var node = this._buildModal(content);
         window.document.body.appendChild(node);
     },

     /**
      * Build the modalbox.
      * @param content item to attach.
      */
     _buildModal : function(content) {
         var container = dojo.create("div");
         dojo.addClass(container, "modal-box");
         //var height = window.height();

         var innerWidth = 0;
         if (window.innerWidth) { //if browser supports window.innerWidth
             innerWidth = window.innerWidth;
         } else if (document.all) { //else if browser supports document.all (IE 4+)
             innerWidth = document.body.clientWidth;
         }
         var left = (innerWidth / 2) - (420 / 2);
         //check if is IE.
         var iebody = (document.compatMode && document.compatMode != "BackCompat") ? document.documentElement : document.body;
         //position top scroll
         var dsoctop = document.all ? iebody.scrollTop : pageYOffset;
         //position left scroll
         var dsocleft = document.all? iebody.scrollLeft : pageXOffset;
         //adjust window top.
         dsoctop = dsoctop + 100;
         //adjust left top.
         dsocleft = dsocleft + 100;
         //modal-box-im
         dojo.addClass(container, "modal-box-im");
         dojo.style(container, "top","-10px");
         dojo.style(container, "left", left+"px"); //TODO: issues on webkit browsers.
         var l = dojo.create("div");
         if(typeof content == "string") {
             l.innerHTML = content;
         } else if(typeof content == "object") {
             l.appendChild(content);
         }
         dojo.addClass(l, "wrapper");
         container.appendChild(l);
         var buttons = this._createButtons(container);
         //console.info("buttons",buttons);
         l.appendChild(buttons);
         var slideArgs = {
                 node: container,
                 top: dsoctop,
                 left: left,
                 unit: "px"
             };
          dojo.fx.slideTo(slideArgs).play();
         return container;
     },

     /**
      * Create the buttons of the modal box.
      */
     _createButtons : function(container){
         var buttons = dojo.create("div");
         dojo.addClass(buttons, "msgbox-buttons");
         if (this.mode == 'alert') {
             var close = dojo.create("button", {innerHTML : "Close"});
             dojo.connect(close, "onclick", dojo.hitch(this, function(event) {
                 this._removeModalBox(container);
             }));
             buttons.appendChild(close);
         } else if (this.mode == 'decision') {
             var button1 = dojo.create("button", {innerHTML : "Accept"});
             dojo.connect(button1, "onclick", dojo.hitch(this, function(event) {
                this.handler();
                this._removeModalBox(container);
             }));
             var button2 = dojo.create("a", {innerHTML : "Cancel"});
             dojo.connect(button2, "onclick", dojo.hitch(this, function(event) {
                 this._removeModalBox(container);
              }));
             //append
             buttons.appendChild(button1);
             var separator = dojo.create("span", {innerHTML : " - or - "});
             buttons.appendChild(separator);
             buttons.appendChild(button2);

         } else {

         }
         return buttons;
     },

     /**
      * Destroy the modal box.
      * @param container
      */
     _removeModalBox : function(container){
         dojo.removeClass(this.node, "modalBoxBackground");
         dojo.destroy(container);
     }

});

