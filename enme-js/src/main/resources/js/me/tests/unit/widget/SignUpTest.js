define( [
    "intern!object",
    "intern/chai!assert",
	"../Helper",
    "me/web/widget/Signup/LoginDialog",
    "me/web/widget/Signup/Signup",
    "me/web/widget/validator/AbstractValidatorWidget",
    "me/web/widget/validator/EmailValidator",
    "me/web/widget/validator/PasswordValidator",
    "me/web/widget/validator/RealNameValidator",
    "me/web/widget/validator/UsernameValidator"
], function(
    registerSuite,
    assert,
    Helper,
    LoginDialog,
    Signup,
    AbstractValidatorWidget,
    EmailValidator,
    PasswordValidator,
    RealNameValidator,
    UsernameValidator ) {
    registerSuite({
        name: "Sign Up Widgets",

	    setup: function() {
		    Helper.init();
		    Helper.addCss( 1, "../../tests/resources/resources/css/dev/home.css");
	    },

        "Signup data": function() {
	        var a1 = Helper.createElement( "mainWrapper" );
	        var wrapper = Helper.createElement( "signupForm" );
	        var rm = Helper.createElement( "rm" );
	        var pssw = Helper.createElement( "pssw" );
	        var em = Helper.createElement( "em" );
	        var usrva = Helper.createElement( "usrva" );
	        var context = Helper.createElement( "context" );
	        Helper.place( rm, "signupForm" );
	        Helper.place( pssw, "signupForm" );
	        Helper.place( usrva, "signupForm" );
	        Helper.place( em, "signupForm" );
	        Helper.place( context, "signupForm" );
	        Helper.place( wrapper, "mainWrapper" );
            var signup = new Signup({

            });
	        Helper.removeElement( a1 );
	        Helper.removeElement( context );
	        Helper.removeElement( usrva );
	        Helper.removeElement( pssw );
	        Helper.removeElement( rm );
	        Helper.removeElement( wrapper );
        }
    });
});
