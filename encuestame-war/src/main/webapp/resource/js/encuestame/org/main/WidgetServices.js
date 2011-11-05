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
dojo.provide("encuestame.org.main.WidgetServices");
dojo.declare("encuestame.org.main.WidgetServices", null, {

    // constructor
    constructor: function(){},

    /*
     *
     */
    _delay_messages : 5000,

    /*
     *
     */
    successMesage : function() {
        console.info("Successfull message");
        encuestame.messages.pubish(encuestame.constants.messageCodes["023"], "message", this._delay_messages);
    },

    /*
     *
     */
    warningMesage : function() {
        encuestame.messages.pubish(encuestame.constants.warningCodes["001"], "warning", this._delay_messages);
    },

    /*
     *
     */
    errorMesage : function() {
        encuestame.messages.pubish(encuestame.constants.errorCodes["023"], "error", this._delay_messages);
    },

    /*
     *
     */
    fatalMesage : function() {
        encuestame.messages.pubish(encuestame.constants.errorCodes["023"], "fatal", this._delay_messages);
    }

});
