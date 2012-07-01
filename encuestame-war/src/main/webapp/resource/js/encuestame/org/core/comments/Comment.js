dojo.provide("encuestame.org.core.comments.Comment");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.AccountPicture');

dojo.declare(
        "encuestame.org.core.comments.Comment",
        [dijit._Widget, dijit._Templated],{

          templatePath: dojo.moduleUrl("encuestame.org.core.comments", "templates/comment.html"),

          widgetsInTemplate: true,

          data : null,

          /**
           *
           */
          postCreate : function() {
        	  if (this.data) {
        		  try {
        			  this._fillComment();
        		  } catch (err) {
        			  console.error("error on fill comment", err);
        		  } finally {
        			 //something could be happend.
        		  }
        	  }
          },

          /*
           *
           */
          _fillComment : function() {
        	  //set user link
        	  if (this._commented_by) {
        		  var a = dojo.create("a");
        		  var commentedBy = (this.data.commented_by == null ? this.data.commented_username : this.data.commented_by);
        		  a.innerHTML = commentedBy;
        		  a.href = encuestame.utilities.usernameLink(this.data.commented_username);
        		  //a.target = "_blank";
        		  this._commented_by.appendChild(a);
        	  }
        	  //set content.
        	  /*
        	   * in the future the content could be formated by HTML.
        	   */
        	  if (this._comment_content) {
        		  var p = dojo.create("p");
        		  p.innerHTML = this.data.comment;
        		  this._comment_content.appendChild(p);
        	  }
        	  //set date
        	  if(this._comment_content_date){
        		  var date = dojo.create("a");
        		  date.innerHTML = ENME.fromNow(this.data.created_at, "YYYY-MM-DD");
        		  date.href = "#"; //TODO: future inprovments
        		  this._comment_content_date.appendChild(date);
        	  }
          }
    });