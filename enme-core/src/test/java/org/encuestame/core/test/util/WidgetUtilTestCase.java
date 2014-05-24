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
     * Test validate locale
     */
    @Test
    public void testValidateLocale() {
        final String language1 = "en_US";
        final String lang = WidgetUtil.validateLocale(language1);
        Assert.assertEquals(lang, "en_US");
        Assert.assertEquals(WidgetUtil.validateLocale("us"), "en_US");
        Assert.assertEquals(WidgetUtil.validateLocale("en"), "en_US");
    }

    @Test
    public void testConvertToDojo(){
        final String lang = WidgetUtil.convertToDojoLocale("en");
        Assert.assertEquals(lang, "en-us");
    }
}
