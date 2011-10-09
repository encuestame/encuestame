/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
dojo.provide("encuestame.org.core.commons.support.ActionDialogHandler");

dojo.require('encuestame.org.core.commons.dialog.Dialog');

dojo.declare("encuestame.org.core.commons.support.ActionDialogHandler", null, {

        /*
         *
         */
        node : null,

        /*
         *
         */
        dialogHandlerWidget : null,

        /*
         *
         */
        hide_header: true,

        /*
         *
         */
        _dragable : false,

        /*
         *
         */
        constructor: function() {
             this.dialogHandlerWidget = new encuestame.org.core.commons.dialog.Dialog({
                 content: this.getMessage(),
                 style: "width: 700px",
                 draggable : this._dragable
             });
             if (this.hide_header) {
                 dojo.addClass(this.dialogHandlerWidget.titleBar,"defaultDisplayHide");
             }
        },

        /*
         *
         */
        getMessage : function(){
            return "";
        },

        /*
         * show error message.
         */
        showErrorMessage : function(errorMessage){
            this.dialogHandlerWidget = new encuestame.org.core.commons.dialog.Dialog({
                content: errorMessage,
                style: "width: 700px",
                draggable : this._dragable
            });
            this.dialogHandlerWidget.show();
        },

        /*
         *
         */
        showSuccessMessage : function(successMessage){
            this.dialogHandlerWidget.content = successMessage;
            this.dialogHandlerWidget.show();
        }
});