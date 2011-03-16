package org.encuestame.search.main;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class CreateSpellCheckerIndex {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
        System.out.println("Usage: java lia.tools.SpellCheckerTest " +
        "SpellCheckerIndexDir IndexDir IndexField");
        System.exit(1);
        }
        String spellCheckDir = args[0];
        String indexDir = args[1];
        String indexField = args[2];
        System.out.println("Now build SpellChecker index...");
        Directory dir = FSDirectory.open(new File(spellCheckDir));
        SpellChecker spell = new SpellChecker(dir);
        long startTime = System.currentTimeMillis();

        Directory dir2 = FSDirectory.open(new File(indexDir));

        IndexReader r = IndexReader.open(dir2,true);
        try {
        spell.indexDictionary(new LuceneDictionary(r, indexField));
        } finally {
        r.close();
        }
        dir.close();
        dir2.close();
        long endTime = System.currentTimeMillis();
        System.out.println(" took " + (endTime-startTime) + " milliseconds");
        }
        }


