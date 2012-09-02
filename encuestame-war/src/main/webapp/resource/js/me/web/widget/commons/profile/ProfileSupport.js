dojo.provide("encuestame.org.core.commons.profile.ProfileSupport");

dojo.declare("encuestame.org.core.commons.profile.ProfileSupport", null, {

        userAccount : null,

        /**
         * Constructor.
         */
        constructor: function(){

        },

        /**
         *
         * @returns
         */
        getMyProfile :function(){
            var load = dojo.hitch(this, function(response){
                if (response.success) {
                    this.userAccount = response.success.account;
                }
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(encuestame.service.list.profile.my, {}, load, error);
            return this.userAccount;
         }
});
