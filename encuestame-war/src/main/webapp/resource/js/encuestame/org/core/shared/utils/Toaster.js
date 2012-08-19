dojo.provide("encuestame.org.core.shared.utils.Toaster");

dojo.require("dojox.widget.Toaster");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

dojo.declare(
    "encuestame.org.core.shared.utils.Toaster",
    [dojox.widget.Toaster],{
    	
		postCreate: function(){
			//this.inherited(arguments);
			this.hide();			
			// place node as a child of body for positioning
			//dojo.body().appendChild(this.domNode);		
			if (this.messageTopic) {
				dojo.subscribe(this.messageTopic, this, "_handleMessage");
			}
		},
		
		
		/**
		 * 
		 * @param message
		 */
		_setContent: function(message) {
			var widget = new encuestame.org.core.shared.utils.Message({
				message : message,
				descriptiom : ""
			});
			
			if (message && this.isVisible) {
				this.contentNode.appendChild(widget.domNode);
			} else {
				dojo.empty(this.contentNode);
				this.contentNode.appendChild(widget.domNode);
			}
			
			
		},		
		
		/**
		 * 
		 */
		_placeClip: function() {
			var view = dojo.window.getBox();
			view.w = 900;
			var nodeSize = dojo.marginBox(this.containerNode);

			var style = this.clipNode.style;
			// sets up the size of the clipping node
			style.height = nodeSize.h+"px";
			style.width = nodeSize.w+"px";

			// sets up the position of the clipping node
			var pd = this.positionDirection;
			if(pd.match(/^t/)){
				style.top = view.t+"px";
			}else if(pd.match(/^b/)){
				style.top = (view.h - nodeSize.h - 2 + view.t)+"px";
			}
//			if(pd.match(/^[tb]r-/)){
//				style.left = (view.w - nodeSize.w - 1 - view.l)+"px";
//			}else if(pd.match(/^[tb]l-/)){
//				style.left = 0 + "px";
//			}
			style.left = "750px";	
			style.clip = "rect(0px, " + nodeSize.w + "px, " + nodeSize.h + "px, 0px)";
			if(dojo.isIE){
				if(!this.bgIframe){
					this.clipNode.id = dijit.getUniqueId("dojox_widget_Toaster_clipNode");
					this.bgIframe = new dijit.BackgroundIframe(this.clipNode);
				}
				var iframe = this.bgIframe.iframe;
				if(iframe){ iframe.style.display="block"; }
			}
		},
});


/**
 *
 */
dojo.declare(
    "encuestame.org.core.shared.utils.Message",
    [encuestame.org.main.EnmeMainLayoutWidget],{
    	
        templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/message.html"),
        
        message : "message test",
        
        description : "test description",
        
        postCreate : function () {
        	
        }   
});