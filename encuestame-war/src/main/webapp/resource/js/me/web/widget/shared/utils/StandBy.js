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
dojo.provide("encuestame.org.core.shared.utils.StandBy");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dojox.widget.Standby");
dojo.require('encuestame.org.core.commons');

dojo.declare("encuestame.org.core.shared.utils.StandBy", [ dijit._Widget,
        dijit._Templated ], {

    templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils",
            "template/standBy.html"),

    /** Allow other widgets in the template. * */
    widgetsInTemplate : true,

    size : "medium",

    target : "",

    postCreate : function() {
       this.init();
    },

    init : function() {

    },

    start : function() {
        console.debug("STAND BY START", standById);
        standById.show();
    },

    stop : function() {
        console.debug("STAND BY START", standById);
        standById.hide();
    }

});
