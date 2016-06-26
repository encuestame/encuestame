package org.encuestame.business.setup.install.demo;

import org.encuestame.util.exception.EnMeNoResultsFoundException;

import java.io.IOException;

public interface CSVParser {

	/**
	 * @throws IOException 
	 * @throws EnMeNoResultsFoundException 
	 * 
	 */
	void executeCSVDemoInstall(Integer tpvotes, Integer pollvotes, Integer surveyVotes) throws IOException, EnMeNoResultsFoundException;
}
