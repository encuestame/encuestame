dojo.provide("encuestame.org.core.commons.dashboard.DashboardGridLayoutWrapper");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.ComboBox");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.DropDownButton");
dojo.require("dijit.TooltipDialog");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("dojo.dnd.Source");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("encuestame.org.core.commons.dashboard.GadgetDirectory");
dojo.require("encuestame.org.core.commons.dialog.Info");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("dojox.layout.GridContainer");
dojo.require("dojox.widget.Portlet");
dojo.require("dijit.form.DropDownButton");
dojo.require("dijit.TooltipDialog");

dojo.require("encuestame.org.core.commons.dashboard.GridContainer");

/**
 *
 */
dojo.declare("encuestame.org.core.commons.dashboard.DashboardGridLayoutWrapper",
             [ encuestame.org.main.EnmeMainLayoutWidget ], {
                    templatePath : dojo.moduleUrl("encuestame.org.core.commons.dashboard",
                            "template/dashboardGridLayoutWrapper.html"),

                    /*
                     *
                     */
                    postCreate : function() {
                      //  dojo.addOnLoad(function() {
                                    // create a new GridContainer:
                     var d = new encuestame.org.core.commons.dashboard.GridContainer(this._layout);
                        //        });
                    }

                });