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
dojo.provide("encuestame.org.core.shared.utils.ContextSupport");

/**
 *
 */
dojo.declare("encuestame.org.core.shared.utils.ContextSupport",
        null,
    {
        /*
         * context.
         */
        _context : ["tweetpoll", "poll", "survey"],

        /*
         *
         */
        context : null,

        /*
         *
         */
        constructor: function(){
            this.context = "";
        },

        /*
         * get action.
         */
        getContext : function(context){
            var position = dojo.indexOf(this._context, context);
            //console.info("getAction", position);
            if (position == -1) {
                console.error("invalid context");
            } else {
                return position;
            }
        }
});
