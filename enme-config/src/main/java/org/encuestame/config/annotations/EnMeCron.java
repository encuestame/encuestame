package org.encuestame.config.annotations;

import java.util.ArrayList;

import org.encuestame.business.cron.PublishScheduled;
import org.encuestame.business.cron.RemoveSpamCommentsJob;
import org.encuestame.business.cron.RemoveUnconfirmedAccountJob;
import org.encuestame.business.cron.SendNotificationsJob;
import org.encuestame.business.search.IndexFSDirectory;
import org.encuestame.business.search.IndexWriterManager;
import org.encuestame.business.search.IndexerManager;
import org.encuestame.business.search.ReIndexAttachmentsJob;
import org.encuestame.business.search.SearchAttachmentManager;
import org.encuestame.core.cron.CalculateHashTagSize;
import org.encuestame.core.cron.CalculateRelevance;
import org.encuestame.core.cron.IndexRebuilder;
import org.encuestame.core.cron.ReIndexJob;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
@EnableAsync
public class EnMeCron {
	
	/**
	 * 
	 */
	@Autowired
	private IndexFSDirectory indexFSDirectory;
	
	/**
	 * 
	 * @return
	 * @throws EnMeExpcetion
	 */
	@Scope("singleton")
	@Bean(name = "indexWriter")
	public  IndexWriterManager indexWriterManager() throws EnMeExpcetion {
		final IndexWriterManager searchAttachmentManager = new IndexWriterManager();
		searchAttachmentManager.setDirectoryStore(this.indexFSDirectory);
		return new IndexWriterManager();
	}
	
	/**
	 * 
	 * @return
	 * @throws EnMeExpcetion
	 */
	@Scope("singleton")
	@Bean(name = "searchManager")
	public  SearchAttachmentManager searchAttachmentManager() throws EnMeExpcetion {
		final SearchAttachmentManager searchAttachmentManager = new SearchAttachmentManager();
		searchAttachmentManager.setDirectoryIndexStore(this.indexFSDirectory);
		return searchAttachmentManager;
	}
	
	/**
	 * 
	 * @return
	 * @throws EnMeExpcetion
	 */
	@Scope("singleton")
	public @Bean(name = "indexerManager") IndexerManager indexerManager() throws EnMeExpcetion {
		final IndexerManager indexerManager = new IndexerManager();
		final java.util.List<String> list = new ArrayList<String>();
		list.add("docx");
		list.add("pdf");
		list.add("xls");
		list.add("txt");
		indexerManager.setExtensionFilesAllowed(list);
		return indexerManager;
	}	
	
	
	/**
	 * 
	 * @return
	 * @throws EnMeExpcetion
	 */
	public @Bean(name = "reindexAttachmentJob")
	ReIndexAttachmentsJob reIndexAttachmentsJob() throws EnMeExpcetion {
		return new ReIndexAttachmentsJob();
	}

	/**
	 * 
	 * @return
	 * @throws EnMeExpcetion
	 */
	public @Bean(name = "sendNotificationsJob")
	SendNotificationsJob sendNotificationsJob() throws EnMeExpcetion {
		return new SendNotificationsJob();
	}

	/**
	 * 
	 * @return
	 * @throws EnMeExpcetion
	 */
	public @Bean(name = "calculateHashTagSize")
	CalculateHashTagSize calculateHashTagSize() throws EnMeExpcetion {
		return new CalculateHashTagSize();
	}

	/**
	 * 
	 * @return
	 * @throws EnMeExpcetion
	 */
	public @Bean(name = "calculateRelevance")
	CalculateRelevance calculateRelevance() throws EnMeExpcetion {
		return new CalculateRelevance();
	}

	/**
	 * 
	 * @return
	 * @throws EnMeExpcetion
	 */
	public @Bean(name = "removeAccountJob")
	RemoveUnconfirmedAccountJob removeUnconfirmedAccountJob()
			throws EnMeExpcetion {
		return new RemoveUnconfirmedAccountJob();
	}

	/**
	 * 
	 * @return
	 * @throws EnMeExpcetion
	 */
	public @Bean(name = "removeSpamCommentJob")
	RemoveSpamCommentsJob removeSpamCommentsJob() throws EnMeExpcetion {
		return new RemoveSpamCommentsJob();
	}

	/**
	 * 
	 * @return
	 * @throws EnMeExpcetion
	 */
	public @Bean(name = "publishScheduled")
	PublishScheduled publishScheduled() throws EnMeExpcetion {
		return new PublishScheduled();
	}

	/**
	 * 
	 * @return
	 * @throws EnMeExpcetion
	 */
	public @Bean(name = "reindexJob")
	ReIndexJob reIndexJob() throws EnMeExpcetion {
		ReIndexJob indexJob = new ReIndexJob();
		indexJob.setIndexRebuilder(new IndexRebuilder());
		return indexJob;
	}
}
