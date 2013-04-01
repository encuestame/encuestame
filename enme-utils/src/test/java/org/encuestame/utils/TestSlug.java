package org.encuestame.utils;

import junit.framework.Assert;

import org.encuestame.utils.categories.test.DefaultTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(DefaultTest.class)
public class TestSlug extends AbstractBaseUtils{

    /**
     * Slug test.
     */
    @Test
    public void testSlugs() {
           // test 1
           final String value1 = "___ +++ \" ????? ##@!#@!$#@ dsdsa ds$#@$#@a dsa dsa dsa$#$#@dsads  321321DS EDWRe432SFDS#!@#@!#@!E#SA ,.,.,. ..   .. ";
           final String restult1 = RestFullUtil.slugify(value1);
           Assert.assertEquals(restult1, "dsdsa-ds-a-dsa-dsa-dsa-dsads-ds-edwre-sfds-e-sa");
           //
           final String value2 = "Who was the first woman to drive in the Indy 500?";
           final String restult2 = RestFullUtil.slugify(value2);
           Assert.assertEquals(restult2, "who-was-the-first-woman-to-drive-in-the-indy");
           //
           final String value3 = "Where did the Chevrolet Corvette get its name?";
           final String restult3 = RestFullUtil.slugify(value3);
           Assert.assertEquals(restult3, "where-did-the-chevrolet-corvette-get-its-name");
           //
           final String value4 = "What color was Luke's lightsaber in the first Star Wars film? ";
           final String restult4 = RestFullUtil.slugify(value4);
           Assert.assertEquals(restult4, "what-color-was-luke-s-lightsaber-in-the-first-star-wars-film");
           //
           final String value5 = "Is @japita your twitter nickname?";
           final String restult5 = RestFullUtil.slugify(value5);
           Assert.assertEquals(restult5, "is-japita-your-twitter-nickname");
           //
           final String value6 = "Are you fan of the 20 of 50 twenty fox?";
           final String restult6 = RestFullUtil.slugify(value6);
           Assert.assertEquals(restult6, "are-you-fan-of-the-of-twenty-fox");
    }
}