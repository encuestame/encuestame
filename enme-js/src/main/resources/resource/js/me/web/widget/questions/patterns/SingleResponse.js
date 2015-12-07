define( [
  "dojo/_base/declare",
  "dojo/topic",
  "dojo/_base/lang",
  "dojo/dom-class",
  "dijit/_WidgetBase",
  "dijit/_TemplatedMixin",
  "dijit/form/ValidationTextBox",
  "dijit/_WidgetsInTemplateMixin",
  "dijit/form/TextBox",
  "me/core/main_widgets/EnmeMainLayoutWidget",
  "dijit/registry",
  "me/web/widget/questions/patterns/AbstractPattern",
  "me/web/widget/pictures/Icon",
  "me/core/enme",
  "dojo/text!me/web/widget/questions/patterns/templates/single.html" ],
  function(
  declare,
  topic,
  lang,
  domClass,
  _WidgetBase,
  _TemplatedMixin,
  ValidationTextBox,
  _WidgetsInTemplateMixin,
  TextBox,
  main_widget,
  registry,
  AbstractPattern,
  Icon,
  _ENME,
  template ) {

  //Var

  //Lang.extend(ValidationTextBox, {
  // validator : function(value, constraints) {
  //    if (false) {
  //      return true;
  //    } else {
  //      return false;
  //    }
  //  }
  //});

 return declare( [ _WidgetBase, _TemplatedMixin, main_widget, AbstractPattern, _WidgetsInTemplateMixin ], {

  templateString: template,
  dndEnabled: false,
  group: "A",

  /**
  * I18n message for this widget.
  */
  i18nMessage: {
    pattern_question_single: _ENME.getMessage("pattern_question_single"),
    pattern_single_response_error_m:  _ENME.getMessage("pattern_single_response_error_m")
  },

  /**
  * Max length for the answer.
  */
  max_length: 100,

  /**
  * Trim the the text.
  */
  trim: true,

   /**
    * Define if the textbox is valid or not
    * @private
    */
  _validate: true,

  postCreate: function() {

    if ( this.dndEnabled && !this.isMobile ) {
      domClass.add( this._handle, "dojoDndHandle");
    }

    this._single.onBlur = lang.hitch( this, function() {
      topic.publish("/encuestame/survey/singleresponse/" + this.group, this.id, this._single.get( "value" ) );
    });

    this._single.validator = lang.hitch( this, function( value ) {
      if ( this._validate ) {
        return true;
      } else {
        return false;
      }
    });

    topic.subscribe("/encuestame/survey/singleresponse/" + this.group, lang.hitch( this, function( id, answer_text ) {
      if ( this.id != id && answer_text !== "" ) {
          if ( answer_text === this._single.get( "value" ) ) {
            this._validate = false;
          } else {
            this._validate = true;
          }
          this._single.validate( false );
        }
    }) );

   },

   /**
    *
    * @returns {*}
    */
    isValid: function() {
      return this._single.isValid();
    },

   /**
    * Response.
    */
    getResponse: function() {
     if ( this._single ) {
        return this._single.get( "value" );
     }
  }
 });
});
