dojo.provide("encuestame.org.core.shared.utils.TimeRangeDropDownMenu");

dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.DropDownMenuSelect');

dojo.declare("encuestame.org.core.shared.utils.TimeRangeDropDownMenu",
		[ encuestame.org.main.EnmeMainLayoutWidget ], {
	
			channel : "/encuestame/hashtag/time/range/refresh/graph",
			
			_open : false,

			templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils",
					"template/timeDropDownTemplate.html"),								

			/*
			 * post create.
			 */
			postCreate : function() {
				this.inherited(arguments);
				if (this.range_actions) {
					this.buildMenu(this.range_actions);
				}
				dojo.connect(this._menu, "onclick", dojo.hitch(this, function(event) {
	                this._expandMenu();
	            }));
			},
			
			/**
			 * 
			 */
			buildMenu : function (arrayList) {
				dojo.forEach(arrayList,
                        dojo.hitch(this,function(menuItem) {
                   var item = dojo.create('li');
                   dojo.addClass(item, "dropdown-item");
                   item.innerHTML = menuItem.period;         
                   dojo.connect(item, "onclick", dojo.hitch(this, function(event) {
                	   menuItem.action(this.channel);
                	   this.setItem(menuItem);
   	            }));
                   this.append(item, this._items);      	
                }));				
			},
			
			setItem : function (item) {
				this._menu.innerHTML  = item.period; 
				this._expandMenu();
			},
 			
			/**
			 * 
			 */
			_expandMenu : function () {
				if (this._open) {
					dojo.removeClass(this._menu, "menu-expand");
					dojo.addClass(this._items, "hidden");
				} else {
					dojo.addClass(this._menu, "menu-expand");
					dojo.removeClass(this._items, "hidden");
				}
				this._open = !this._open;
			}
			
		});
