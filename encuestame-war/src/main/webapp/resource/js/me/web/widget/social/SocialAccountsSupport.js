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
dojo.provide("encuestame.org.core.shared.utils.SocialAccountsSupport");

/**
 *
 */
dojo.declare("encuestame.org.core.shared.utils.SocialAccountsSupport", null, {

    /*
     *
     */
    arrayWidgetAccounts : [],

    /*
     *
     */
    arrayAccounts : [],

    /*
     *
     */
    constructor : function() {},

    /*
     *
     */
    cleanSocialAccounts : function(){},

    /*
     * load all social accounts verified.
     */
    _loadSocialConfirmedAccounts : function() {
        var load = dojo.hitch(this, function(data) {
            this.arrayAccounts = data.success.items;
            this.cleanSocialAccounts();
            //console.debug("social", this.arrayAccounts);
            this.showListAccounts();
        });
        var error = function(error) {
            console.debug("error", error);
        };
        encuestame.service.xhrGet(encuestame.service.list.allSocialAccount, {},
                load, error);
    },


    /*
     * return array of id for each social account selected.
     */
    getSocialAccounts : function() {
        var accountsId = [];
        dojo.forEach(
                this.arrayWidgetAccounts,
                dojo.hitch(this, function(widget, index) {
                    if (widget.selected) {
                        accountsId.push(widget.account.id);
                    }
            }));
        //console.debug("getSocialAccounts", accountsId);
        return accountsId;
    },

    /*
     * return complete data for selected accounts.
     */
    getSocialCompleteAccounts : function(){
        var accounts = [];
        dojo.forEach(
                this.arrayWidgetAccounts,
                dojo.hitch(this, function(widget, index) {
                    if (widget.selected) {
                        accounts.push(widget.account);
                    }
            }));
        //console.debug("getSocialAccounts", accounts);
        return accounts;
    },

    /*
     * is valid check.
     */
    isValid : function() {
        if (this._countSelected() >= this._required) {
            return true;
        } else {
            this._isValidMessage = ENME.getMessage("e_022");
            return false;
        }
    },

    /*
     * return a validator message.
     */
    isValidMessage : function() {
        return this._isValidMessage;
    },



    /*
     *
     */
    _countSelected : function(){
        var counter = 0;
        dojo.forEach(
                this.arrayWidgetAccounts,
                dojo.hitch(this, function(widget, index) {
                if (widget.selected) {
                    counter++;
                }
            }));
        //console.debug("_reloadCounter", counter);
        return counter;
    },

    /*
     * to override.
     */
    createPickSocialAccount : function(data){},

    /*
     * show up list accounts.
     */
    showListAccounts : function(){
        dojo.forEach(
                this.arrayAccounts,
                dojo.hitch(this, function(data, index) {
                      this.createPickSocialAccount(data);
            }));
    }
});
