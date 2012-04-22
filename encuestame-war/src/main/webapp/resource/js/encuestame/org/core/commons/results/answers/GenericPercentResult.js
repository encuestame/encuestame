dojo
		.provide("encuestame.org.core.commons.results.answers.GenericPercentResult");

dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.core.commons.results.answers.ResultSupport");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

/**
 * Search menu widget. This widget only return suggest float window with list of
 * results based on keyword.
 */
dojo.declare(
		"encuestame.org.core.commons.results.answers.GenericPercentResult", [
				encuestame.org.main.EnmeMainLayoutWidget,
				encuestame.org.core.commons.results.answers.ResultSupport ], {

			/**
			 * Represent the unique item id of the result.
			 */
			itemId : null,

			/**
			 * Represent the label of the result / answer.
			 */
			labelResponse : "",

			/**
			 * Represent the color of the result.
			 */
			color : "",

			/**
			 * The current total of votes.
			 */
			votes : "",

			/**
			 * The question id.
			 */
			questionId : null,
			
			/**
			 * The percent result.
			 */
			percent : "0%",

			/*
			 * template.
			 */
			templatePath : dojo.moduleUrl(
					"encuestame.org.core.commons.results.answers",
					"templates/generic_result.html"),

		});