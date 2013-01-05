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
 *  @module Services
 *  @namespace Widget
 *  @class EnmeMainLayoutWidget
 *  @requires WidgetServices
 */

define([ "dojo/parser",
         "dojo/_base/declare",
     "me/core/URLServices",
     "me/core/main_widgets/WidgetServices",
     "me/core/enme"], function(
    parser,
    declare,
    _URL,
    _WIDGET,
    _ENME) {

  return declare([_WIDGET], {

      /**
       *
       * @property defaultNoResults
       */
       defaultNoResults : "Nothing find with ",

       /**
        * default context path.
        * @property contextDefaultPath
        */
       contextDefaultPath : _ENME.config('contextPath'),

      /**
       * add item on drop down menu.
       * @property append
       */
       append : function(node, place) {
         dojo.place(node, place);
       },

       /**
        * Range Actions
        * @property range_actions
        */
       range_actions : [ {
         period : "All",
         value  : "all",
         action : dojo.hitch(this, function(channel) {
           dojo.publish(channel, [ "all" ]);
         })
       }, {
         period : "Last Year",
         value  :  "365",
         action : dojo.hitch(this, function(channel) {
           dojo.publish(channel, [ "365" ]);
         })
       }, {
         period : "Last Month",
         value  : "30",
         action : dojo.hitch(this, function(channel) {
           dojo.publish(channel, [ "30" ]);
         })
       }, {
         period : "Last Week",
         value  : "7",
         action : dojo.hitch(this, function(channel) {
           dojo.publish(channel, [ "7" ]);
         })
       }, {
         period : "Last Day",
         value  : "24",
         action : dojo.hitch(this, function(channel) {
           dojo.publish(channel, [ "24" ]);
         })
       }]
  });
});