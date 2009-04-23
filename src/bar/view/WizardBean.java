package bar.view;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.hibernate.validator.Email;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.Pattern;
import bar.service.Service;
import bar.utils.JSFUtils;
/**
 * encuestame:  system online surveys
 * Copyright (C) 2005-2008 encuestame Development Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * Id: WizardBean.java Date: 22/04/2009 
 * @author Juan
 * package: bar.view
 * @version 1.0
 */
public class WizardBean {

	private static final long serialVersionUID = 1L;
	
	    @NotEmpty(message = "Name must not be empty")
	    @Pattern(regex = ".*[^\\s].*", message = "This string contain only spaces")
	   private String name;
	 
	    @NotEmpty
	    @Email(message = "Invalid email format")
	   private String email;
	
	    private String drink;
	  private String comments;
	   private String drinkCategorySelected;
	    
	   private Service orderService;
	
	   private String startPage;
	    
	    private List<SelectItem> drinkCategory;
	   private List<SelectItem> drinkList;
	
	    // init all drinks
	    private static final String [] CATEGORY = {"Brandy", "Rum", "Tequila", "Whiskey", "Wine", "Beer"};
	    private static final String [] BRANDY = {"XO", "VSOP", "VS"};
	    private static final String [] RUM = {"Medium", "Full-bodied", "Aromatic"};
	    private static final String [] TEQUILA = {"Reposado", "Añejo", "Blanco"};
	    private static final String [] WHISKEY = {"Malt", "Grain", "Single Malt", };
	   private static final String [] WINE = {"Red", "White", "Pink"};
	    private static final String [] BEER = {"Ales", "Lager", "Specialty Beer", };
	   
	   public void changeDrink(ValueChangeEvent event) {
	        String newValue = (String) event.getNewValue();
	       
	        if (newValue.equals("Brandy")) {drinkList = JSFUtils.createList(BRANDY); drink=BRANDY[0];}
	       else if (newValue.equals("Rum")) {drinkList = JSFUtils.createList(RUM); drink=RUM[0];}
	        else if (newValue.equals("Tequila")) {drinkList = JSFUtils.createList(TEQUILA);drink=TEQUILA[0];}
	        else if (newValue.equals("Whiskey")) {drinkList = JSFUtils.createList(WHISKEY);drink=WHISKEY[0];}
	        else if (newValue.equals("Wine")) {drinkList = JSFUtils.createList(WINE);drink=WINE[0];}
	       else if (newValue.equals("Beer")) {drinkList = JSFUtils.createList(BEER);drink=BEER[0];}
	   }
	    
	   @PostConstruct
	    public void create() {
	        drinkCategorySelected = CATEGORY[0];
	       drinkCategory = JSFUtils.createList(CATEGORY);
	        drinkList = JSFUtils.createList(BRANDY);
	      drink = BRANDY[0];
	    }
	    public void save() {
	        orderService.addOrder(name, email, drink, comments);
	        this.startPage = "/page1.xhtml";
	
	        FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
	             .remove("wizardBean");
	    }
	
}
