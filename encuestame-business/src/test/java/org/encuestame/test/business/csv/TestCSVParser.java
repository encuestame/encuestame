package org.encuestame.test.business.csv;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.encuestame.business.setup.install.demo.CSVParser;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestCSVParser extends AbstractSpringSecurityContext{

	@Autowired
	CSVParser csvParser;
	
	/**
	 * Test csv parser.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	 @Test
	 public void testCSVParser() throws FileNotFoundException, IOException {
		 Assert.assertNotNull(this.csvParser);
		 this.csvParser.executeCSVDemoInstall(2, 2, 2);
		 Assert.assertEquals("Questions should be", 2, getHibernateTemplate().find("from Question").size());
		 Assert.assertEquals("Users should be", 11, getHibernateTemplate().find("from UserAccount").size());
		 Assert.assertEquals("TweetPoll should be", 1, getHibernateTemplate().find("from TweetPoll").size());
		 Assert.assertEquals("Poll should be", 1, getHibernateTemplate().find("from Poll").size());
		 
	 }

	/**
	 * @return the csvParser
	 */
	public CSVParser getCsvParser() {
		return csvParser;
	}

	/**
	 * @param csvParser the csvParser to set
	 */
	public void setCsvParser(CSVParser csvParser) {
		this.csvParser = csvParser;
	}
}
