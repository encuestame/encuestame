package org.encuestame.test.business.service;

import java.io.File;
import java.io.IOException;

import org.encuestame.business.search.AttachmentIndex;
import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.encuestame.utils.web.UnitAttachment;
import org.junit.Before;
import org.junit.Test;

public class TestSearchService extends AbstractServiceBase {


    private String filepathUpload = "/home/dmorales/encuestame/profiles/1/Pro Apache Log4j.pdf";

    @Test
    public void testAddAttachment() throws IOException{
        File file = new File(filepathUpload);
        log.debug("Getting filename -->"+ file.getName());
        log.debug("Getting "+file.getCanonicalPath());
        //final UnitAttachment unitAttachment = createUnitAttachment(filename, uploadDate, projectBean);

    }



}
