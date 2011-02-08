package org.encuestame.search;

import junit.framework.TestCase;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.encuestame.search.main.TestUtil;

public class PrefixQueryTest extends TestCase{
	public void testPrefix() throws Exception {
		Directory dir = TestUtil.getBookIndexDirectory();
		IndexSearcher searcher = new IndexSearcher(dir);
		Term term = new Term("category", "/technology/computers/programming");
		PrefixQuery query = new PrefixQuery(term);
	 	TopDocs matches = searcher.search(query, 10);
		int programmingAndBelow = matches.totalHits;
		matches = searcher.search(new TermQuery(term), 10);
		int justProgramming = matches.totalHits;
		assertTrue(programmingAndBelow > justProgramming);
		searcher.close();
		dir.close();
		}


public void testAnd() throws Exception {
	TermQuery searchingBooks = new TermQuery(new Term("subject", "search"));
 	Query books2010 = NumericRangeQuery.newIntRange("pubmonth", 201001,	201012, true, true);
	BooleanQuery searchingBooks2010 = new BooleanQuery();
	searchingBooks2010.add(searchingBooks, BooleanClause.Occur.MUST);
	searchingBooks2010.add(books2010, BooleanClause.Occur.MUST);

	Directory dir = TestUtil.getBookIndexDirectory();
	IndexSearcher searcher = new IndexSearcher(dir);
	TopDocs matches = searcher.search(searchingBooks2010, 10);
	assertTrue(TestUtil.hitsIncludeTitle(searcher, matches, "Lucene in Action, Second Edition"));
	searcher.close();
	dir.close();
	}


public void testOr() throws Exception {
	TermQuery methodologyBooks = new TermQuery(new Term("category", "/technology/computers/programming/methodology"));
	TermQuery easternPhilosophyBooks = new TermQuery(
	new Term("category", "/philosophy/eastern"));
	BooleanQuery enlightenmentBooks = new BooleanQuery();
	enlightenmentBooks.add(methodologyBooks, BooleanClause.Occur.SHOULD);
	enlightenmentBooks.add(easternPhilosophyBooks , BooleanClause.Occur.SHOULD);
 	Directory dir = TestUtil.getBookIndexDirectory();
	IndexSearcher searcher = new IndexSearcher(dir);
	TopDocs matches = searcher.search(enlightenmentBooks, 10);
	System.out.println("or = " + enlightenmentBooks);
	assertTrue(TestUtil.hitsIncludeTitle(searcher, matches, "Extreme Programming Explained"));
	assertTrue(TestUtil.hitsIncludeTitle(searcher, matches, "Tao Te Ching \u9053\u5FB7\u7D93"));
	searcher.close();
	dir.close();
	}
 }
