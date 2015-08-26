define([
	"delite/register",
	"delite/Widget",
	"delite/handlebars!./MenuLink/MenuLink.html",
], function (register, Widget, template, messages) {
	return register("menu-link", [HTMLElement, Widget], {
		baseClass: "menu-link",
		nls: messages,
		name: "",
    url: "",
		template: template
	});
});