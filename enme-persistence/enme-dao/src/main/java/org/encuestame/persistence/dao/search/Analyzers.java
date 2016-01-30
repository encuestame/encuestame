package org.encuestame.persistence.dao.search;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

/**
 * Created by jpicado on 29/01/16.
 */
public class Analyzers {

    public static SimpleAnalyzer simpleAnalyzer() {
        return new SimpleAnalyzer();
    }

    public static StandardAnalyzer standardAnalyzer() {
        return new StandardAnalyzer();
    }
}
