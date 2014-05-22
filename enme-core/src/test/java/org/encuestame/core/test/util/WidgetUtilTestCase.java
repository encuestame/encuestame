package org.encuestame.core.test.util;

import org.encuestame.core.security.util.WidgetUtil;
import org.encuestame.utils.AbstractBaseUtils;
import org.encuestame.utils.categories.test.DefaultTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Created by jpicado on 22/05/14.
 */
@Category(DefaultTest.class)
public class WidgetUtilTestCase extends AbstractBaseUtils {

    /**
     * Slug test.
     */
    @Test
    public void testValidateLocale() {
        final String language1 = "en_US";
        final String lang = WidgetUtil.validateLocale(language1);
        System.out.println(lang);
        Assert.assertEquals(lang, "en_US");
        Assert.assertEquals(WidgetUtil.validateLocale("us"), "en_US");
        Assert.assertEquals(WidgetUtil.validateLocale("en"), "en_US");
    }
}
