dojo.provide("encuestame.org.core.commons.profile.ProfileMenu");

dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.commons.support.ToggleMenu");
dojo.require('encuestame.org.core.commons');

/**
 * Widget to define the profile of menu.
 */
dojo.declare(
    "encuestame.org.core.commons.profile.ProfileMenu",
    [encuestame.org.core.commons.support.ToggleMenu],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.profile", "templates/profileMenu.html"),

        /*
         * the complete username
         */
        completeName : "",

        /*
         * the logged username
         */
        username : "",

        /*
         * class to replace on toggle event is triggered
         */
        _classReplace : "profileOpenPanel",
        
        /*
         * post create life cycle.
         */
       postCreate : function() {
           // add the menus, find the nodes before render the template
           // to create all menu nodes.
           var append_node = this._nodes;
           dojo.query("div.profile-menu", this.srcNodeRef).forEach(
                   dojo.hitch(this, function(node) {
                      var li = dojo.create('li'),
                      a = dojo.create('a'),
                      url = node.getAttribute('data-url'),
                      label = node.innerHTML;
                      a.href = url;
                      a.innerHTML = label;
                      li.appendChild(a);
                      append_node.appendChild(li);
                   })
            );
    	   //set the toggle menu support
           this.addMenuSupport(this._image);
           this.addMenuSupport(this._name);

       }
});
