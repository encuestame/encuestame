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
dojo.provide("encuestame.org.core.shared.options.CommentsOptions");

dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.options.AbstractOptionSelect');

/**
 * Represents a option to repeate votes.
 */
dojo.declare(
    "encuestame.org.core.shared.options.CommentsOptions",
    [encuestame.org.core.shared.options.AbstractOptionSelect],{

     /*
      * Allow other widgets in the template.
      */
     widgetsInTemplate: true,

     option_value : "comments",

     _default_selected_item : 1,

     option_name : "comments_",

     options_label : [
                      ENME.getMessage('widget_comments_allow'), 
                      ENME.getMessage('widget_comments_moderated'), 
                      ENME.getMessage('widget_comments_no_comments')
                      ]


});
