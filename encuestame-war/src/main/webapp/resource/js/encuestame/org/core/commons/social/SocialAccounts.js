dojo.provide("encuestame.org.core.commons.social.SocialAccounts");

dojo.declare(
    "encuestame.org.core.commons.social.SocialAccounts",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.social", "templates/socialAccounts.inc"),

        widgetsInTemplate: true,

        domain : "",

        socialList : ["Twitter", "Facebook", "LinkedIn"],

        postCreate : function(){
              console.debug("domain", this.domain);
        }
});
