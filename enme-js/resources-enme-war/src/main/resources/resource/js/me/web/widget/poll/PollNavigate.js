define([
         "dojo/_base/declare",
         "dojo/Deferred",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/menu/DropDownMenuSelect",
         "me/web/widget/menu/DropDownMenuItem",
         "me/web/widget/poll/PollNavigateItem",
         "me/web/widget/data/FilterList",
         "me/core/enme",
         "dojo/text!me/web/widget/poll/templates/pollNavigate.html" ],
        function(
                declare,
                Deferred,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                DropDownMenuSelect,
                DropDownMenuItem,
                PollNavigateItem,
                FilterList,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, FilterList, _WidgetsInTemplateMixin], {

          // template string.
           templateString : template,

          /*
           *
           */
          _rows : [],

          /**
           * Override property field.
           */
          property : "poll",

          /**
           * Override  folder scope field.
           */
          folder_scope : "poll",

          /*
           * i18n message for this widget.
           */
          i18nMessage : {
            detail_manage_by_account : _ENME.getMessage("detail_manage_by_account"),
            detail_manage_today : ENME.getMessage("detail_manage_today"),
            detail_manage_last_week : _ENME.getMessage("detail_manage_last_week"),
            detail_manage_favorites : _ENME.getMessage("detail_manage_favorites"),
            detail_manage_scheduled : _ENME.getMessage("detail_manage_scheduled"),
            detail_manage_all : _ENME.getMessage("detail_manage_all"),
            detail_manage_published : _ENME.getMessage("detail_manage_published"),
            detail_manage_unpublished : _ENME.getMessage("detail_manage_unpublished"),
            detail_manage_only_completed : _ENME.getMessage("detail_manage_only_completed"),
            detail_manage_poll_title : _ENME.getMessage("detail_manage_poll_title"),
            detail_manage_filters : _ENME.getMessage("detail_manage_filters"),
            detail_manage_filters : _ENME.getMessage("detail_manage_filters"),
            detail_manage_poll_dropdown_title : _ENME.getMessage("detail_manage_poll_dropdown_title")
          },

          /*
           *
           */
          _cache_items : [],

          /**
           * Poll Navigate default parameters.
           */
          _params : { typeSearch : "BYOWNER", keyword : null, max : 10, start : 0},

          /**
           * Post Create Cycle Life.
           */
          postCreate : function() {
              //required subscribe to filter support.
              //should be in the parent class??
              dojo.subscribe("/encuestame/filter/list/call", this, "_callFilterList");

//              var def = new Deferred();
//              try {
//                  def.then(dojo.hitch(this, this._callServiceSearch));
//                  def.then(dojo.hitch(this,this._printRows));
//                  def.callback(true);
//              } catch(e) {
//                 def.errback(new Error("load poll failed."));
//              }

              var deferred = new Deferred(function(reason){
                  // do something when the Deferred is cancelled
                });

                // do something asynchronously

                // provide an update on progress:
                deferred.progress(function(e){
                    _ENME.log('progress', e);
                });

                // when the process finishes:
                deferred.resolve(function(e){
                    _ENME.log('resolve', e);
                });

                // performing "callbacks" with the process:
                deferred.then(dojo.hitch(this, this._callServiceSearch), function(err){
                  // Do something when the process errors out
                }, function(update){
                  // Do something when the process provides progress information
                });

                deferred.then(dojo.hitch(this,this._printRows), function(err){
                    // Do something when the process errors out
                  }, function(update){
                    // Do something when the process provides progress information
                  });

                // to cancel the asynchronous process:
                //deferred.cancel(reason);

              //enable folder support.
              if (this.folder_support && this._folder) {
                 this.enableFolderSupport();
              }
              //enable more support.
              if (this.enable_more_support) {
                  this.enableMoreSupport(this._params.start, this._params.max, this._more);
              }

              //dojo.addOnLoad(function() {
              //    dojo.connect(dojo.byId('strapline'), 'onclick', function(event) {
              //        dojo.publish('myMessages', [{ message: 'Qwerty', type: "error", duration: 0}]);
              //    });
              //});

              var menu_widget = this._dropdownmenu;
              var newPoll = new DropDownMenuItem({
                  label : _ENME.getMessage("poll_admon_poll_new"),
                  url : "/user/poll/new"
              });
              menu_widget._appendItem(newPoll);
          },

          /*
           * @override
           */
          displayEmptyMessage : function() {
              var node = dojo.create("div");
              dojo.addClass(node, "web-items-no-results");
              node.innerHTML = _ENME.getMessage("commons_no_results");
              dojo.place(node, this._items);
          },

          /**
           * Function to clean _items node.
           */
          _empty : function() {
              dojo.empty(this._items);
          },

          /**
           * Subscribe function on filter search
           * @param typeSearch set the type of search
           */
          _callFilterList : function(typeSearch) {
              this._params.typeSearch = typeSearch;
              this._callServiceSearch();
          },

          /*
           *
           */
          _afterEach : function() {
            //TODO future.
              //var more = new encuestame.org.core.shared.utils.More();
          },

          /**
           * Call a service to retrieve a list of poll based on a previous filter parameters.
           */
          _callServiceSearch : function() {
              dojo.hitch(this, this.loadItems('encuestame.service.list.listPoll'));
          },


          /**
           * customize service params.
           */
          getParams : function() {
              return this._params;
          },

          /**
           * The url json service.
           * @returns
           */
          getUrl : function() {
              return 'encuestame.service.list.listPoll';
          },


          /**
           * Create a new PollNavigateItem.
           */
          processItem : function(/** poll data**/  data, /** position **/ index) {
              var row = new PollNavigateItem({ data: data});
              this._rows.push(row);
              dojo.place(row.domNode, this._items);
          },

          /**
           *
           */
          _printRows : function() {
               dojo.forEach(this._rows,
                    dojo.hitch(this, function(data, index) {
                        this._cache_items.push(data);
               }));
          }
    });
});