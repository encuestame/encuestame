define([
	"delite/register",
	"delite/Widget",
	"delite/handlebars!./SearchlistElement/SearchlistElement.html",
    "requirejs-dplugins/i18n!./SearchlistElement/nls/messages",
    "requirejs-dplugins/css!./SearchlistElement/css/SearchlistElement.css"
], function (register, Widget, template, messages) {
	return register("searchlist-element", [HTMLElement, Widget], {
		baseClass: "searchlist-element",
		nls: messages,
		value: "",
		template: template
	});
});