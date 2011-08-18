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
dojo.provide("encuestame.org.core.gadget.Activity");

dojo.require("encuestame.org.core.gadget.Gadget");
dojo.require("encuestame.org.core.commons.notifications.NotificationItem");

dojo.declare(
    "encuestame.org.core.gadget.Activity",
    [encuestame.org.core.gadget.Gadget],{
        templatePath: dojo.moduleUrl("encuestame.org.core.gadget", "template/activity.html"),

        /*
         *
         */
        postCreate : function(){
            this.inherited(arguments);
        },

        /*
         *
         */
        getUrl : function(){
            return '/service/stream/get';
        },

        /*
         * override.
         */
        _updateStream : function(message){
            var obj = dojo.fromJson(message.data.stream);
            console.info("activity update ...", obj);
            this._printStream(obj);
        },

        createItem : function(item){
            return new encuestame.org.core.gadget.ActivityItem({item: item});
        },

        getNode : function(){
            return this._list;
        }
});

/**
 *
 */
dojo.declare(
        "encuestame.org.core.gadget.ActivityItem",
        [encuestame.org.core.commons.notifications.NotificationItem],{
            templatePath: dojo.moduleUrl("encuestame.org.core.gadget", "template/activityItem.html"),

});