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
dojo.provide("encuestame.org.core.commons.poll.vote.PollVote");

dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.main.EnmeMainLayoutWidget');

dojo.require('encuestame.org.core.commons.questions.patterns.SingleOptionResponse');
dojo.require('encuestame.org.core.commons.questions.patterns.MultipleOptionResponse');

/**
 *
 */
dojo.declare("encuestame.org.core.commons.poll.vote.PollVote", [
        encuestame.org.main.EnmeMainLayoutWidget], {

        /**
         *
         */
        templatePath : dojo.moduleUrl("encuestame.org.core.commons.poll.vote","templates/pollvote.html"),


        /**
         *
         */
        postCreate : function(){
            dojo.query("> [dojoType]", this.srcNodeRef).forEach(
                    dojo.hitch(this, function(node) {
                       console.info(node);
                       this._responses.appendChild(node);
                    })
                );
        }

});