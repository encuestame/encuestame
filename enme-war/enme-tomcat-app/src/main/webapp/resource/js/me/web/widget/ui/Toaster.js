/*
 * Copyright 2013 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/***
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.146
 *  @module UI
 *  @namespace Widget
 *  @class Toaster
 */
define([
         "dojo/_base/declare",
   		 "dojox/widget/Toaster",
   		 "me/web/widget/ui/Message",
     	  "me/core/enme"],
    function(
    declare,
    Toaster,
    Message,
    _ENME) {

  return declare([Toaster], {

     /**
      * template string.
      * @property messageTopic
      */
 	 messageTopic : "/encuestame/message/publish",

 	/**
 	 *
 	 * @method
 	 */
	postCreate: function() {
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
	 * @param method
	 */
	_setContent: function(message) {
		var widget = new Message({
			message : message,
			descriptiom : "",
			type : this.messageCurrentType || 'message'
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
	 * @param message
	 */
	_handleMessage: function(/*String|Object*/message) {
		if (dojo.isString(message)) {
			this.setContent(message);
		} else {
			var currentMessage = message.message;
			if (message.description) {
				var content = dojo.create("div");
				content.innerHTML = currentMessage;
				var desc = dojo.create("p");
				desc.innerHTML = message.description;
				content.appendChild(desc);
				currentMessage = content;
			}
			this.messageCurrentType = message.type;
			this.setContent(currentMessage, message.type, message.duration);
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
	}

  });
});