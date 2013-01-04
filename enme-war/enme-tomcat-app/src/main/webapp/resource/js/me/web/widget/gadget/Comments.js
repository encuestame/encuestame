define([
     "dojo/_base/declare",
     "me/core/enme"],
    function(
    declare,
    _ENME) {

  return declare(null, {

   /**
    * PostCreate life cycle.
    */
     postCreate : function() {}
  });
});

// /*
//  ************************************************************************************
//  * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
//  * encuestame Development Team.
//  * Licensed under the Apache Software License version 2.0
//  * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
//  * Unless required by applicable law or agreed to  in writing,  software  distributed
//  * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
//  * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
//  * specific language governing permissions and limitations under the License.
//  ************************************************************************************
//  */
// dojo.provide("encuestame.org.core.gadget.Comments");

// dojo.require("encuestame.org.core.gadget.Gadget");
// dojo.require("encuestame.org.core.comments.Comment");

// dojo.declare(
//     "encuestame.org.core.gadget.Comments",
//     [encuestame.org.core.gadget.Gadget],{

//         templatePath: dojo.moduleUrl("encuestame.org.core.gadget", "template/comments.html"),

//         /*
//          *
//          */
//         postCreate : function(){
//             this.inherited(arguments);
//         },

//         /*
//          *
//          */
//         getUrl : function(){
//             return '/service/comment/get';
//         },

//         /*
//          * override.
//          */
//         _updateStream : function(message) {
//              console.info("comment update ...", message);
//              var obj = dojo.fromJson(message.data.comments);
//              this._printStream(obj);
//         },

//         /*
//          * create item comment.
//          */
//         createItem : function(item) {
//             return new encuestame.org.core.comments.Comment({comment : item , minimizeFormat : true});
//         },

//         /*
//          *
//          * @returns
//          */
//         getNode : function() {
//             return this._comments;
//         }
// });