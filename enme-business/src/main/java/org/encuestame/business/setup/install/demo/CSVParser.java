package org.encuestame.business.setup.install.demo;

import java.io.IOException;

import org.encuestame.persistence.exception.EnMeNoResultsFoundException;

public interface CSVParser {

	/**
	 * @throws IOException 
	 * @throws EnMeNoResultsFoundException 
	 * 
	 */
	void executeCSVDemoInstall(Integer tpvotes, Integer pollvotes, Integer surveyVotes) throws IOException, EnMeNoResultsFoundException;
}
