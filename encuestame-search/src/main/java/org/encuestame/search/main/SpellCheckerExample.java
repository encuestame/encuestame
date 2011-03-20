package org.encuestame.search.main;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.LevensteinDistance;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SpellCheckerExample {
    public static void main(String[] args) throws IOException {

        System.out.println("First Parameter"+args[0]);
        if (args.length != 2) {
        System.out.println("Usage: java lia.tools.SpellCheckerTest " +
        "SpellCheckerIndexDir wordToRespell");
        System.exit(1);
        }
        String spellCheckDir = args[0];
        String wordToRespell = args[1];
        Directory dir = FSDirectory.open(new File(spellCheckDir));
        if (!IndexReader.indexExists(dir)) {
        System.out.println("\nERROR: No spellchecker index at path \"" +
        spellCheckDir + "\"; please run CreateSpellCheckerIndex first\n");
        System.exit(1);
        }
        SpellChecker spell = new SpellChecker(dir);
        spell.setStringDistance(new LevensteinDistance());
        String[] suggestions = spell.suggestSimilar( wordToRespell, 3);
        System.out.println(suggestions.length + " suggestions for '" + wordToRespell + "':");
        //String suggestion;
        for (String suggestion : suggestions) {
            System.out.println(" " + suggestion);
        }

        }
        }


