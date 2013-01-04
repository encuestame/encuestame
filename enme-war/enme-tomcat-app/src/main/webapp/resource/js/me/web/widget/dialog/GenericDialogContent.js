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
// dojo.provide("encuestame.org.core.shared.utils.GenericDialogContent");

// dojo.require('dijit.form.Button');

// dojo.require('encuestame.org.core.commons');
// dojo.require('encuestame.org.main.EnmeMainLayoutWidget');


// dojo.declare(
//     "encuestame.org.core.shared.utils.GenericDialogContent",
//     [encuestame.org.main.EnmeMainLayoutWidget],{

//       /*
//        *
//        */
//       templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/genericDialogContent.html"),

//       /*
//        * default content.
//        */
//       content : "",

//       /*
//        *
//        */
//       postCreate : function() {
//           if (this._button) {
//               this._button.onClick = dojo.hitch(this, function(event){
//                   dojo.stopEvent(event);
//                   console.debug("dialog", "click");
//                   dojo.publish("/encuestame/dialog/close");
//               });
//           }

//       },


// });