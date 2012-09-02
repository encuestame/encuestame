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
dojo.provide("encuestame.org.core.commons.support.SearchMenu");

dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit.form.MultiSelect");
dojo.require("dijit.form.Slider");
dojo.require("dijit.form.HorizontalSlider");
dojo.require("dijit.form.HorizontalRule");
dojo.require("dijit.form.HorizontalRuleLabels");
dojo.require("encuestame.org.core.commons.support.AbstractFilterSupport");

dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

/**
 * Search Menu Support.
 * @author Picado, Juan juanATencuestame.org
 * @since 14/01/12
 */
dojo.declare("encuestame.org.core.commons.support.SearchMenu",
        [encuestame.org.main.EnmeMainLayoutWidget,
         encuestame.org.core.commons.support.AbstractFilterSupport], {

    /*
     *
     */
     templatePath: dojo.moduleUrl("encuestame.org.core.commons.support", "templates/search-filters.html"),
     
     /*
      * i18n message for this widget.
      */
     i18nMessage : {
    	 detail_manage_filters_advanced_type_to_search : ENME.getMessage("detail_manage_filters_advanced_type_to_search"),
    	 detail_manage_filters_advanced_title : ENME.getMessage("detail_manage_filters_advanced_title"),
    	 detail_manage_published : ENME.getMessage("detail_manage_published"),
    	 detail_manage_unpublished : ENME.getMessage("detail_manage_unpublished"),
    	 detail_manage_filters_advanced_all_results : ENME.getMessage("detail_manage_filters_advanced_all_results"),
    	 detail_manage_scheduled : ENME.getMessage("detail_manage_scheduled"),
    	 detail_manage_favorites : ENME.getMessage("detail_manage_favorites"),
    	 detail_manage_by_account : ENME.getMessage("detail_manage_by_account"),
    	 detail_manage_filters_advanced_range_days : ENME.getMessage("detail_manage_filters_advanced_range_days"),
    	 detail_manage_search : ENME.getMessage("detail_manage_search"),
    	 detail_manage_only_completed : ENME.getMessage("detail_manage_only_completed"),
     }

});
