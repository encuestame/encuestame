/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.test.security.utils;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.encuestame.core.security.util.HTMLInputFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link HTMLInputFilter} Test Case.
 * http://josephoconnell.com/java/xss-html-filter/
 * @author Joseph O'Connell <joe.oconnell at gmail dot com>
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 17, 2010 9:39:54 PM
 * @version $Id:$
 */
public class HTMLInputFilterTestCase extends TestCase {

        protected HTMLInputFilter vFilter = new HTMLInputFilter(true);

        /**
         *
         * @param input
         * @param result
         */
        private void verifyTest(String input, String result) {
            Assert.assertEquals(result, vFilter.filter(input));
        }

        @Test
        public void test_basics() {
            verifyTest("", "");
            verifyTest("hello", "hello");
        }

        @Test
        public void test_balancing_tags() {
            verifyTest("<b>hello", "<b>hello</b>");
            verifyTest("<b>hello", "<b>hello</b>");
            verifyTest("hello<b>", "hello");
            verifyTest("hello</b>", "hello");
            verifyTest("hello<b/>", "hello");
            verifyTest("<b><b><b>hello", "<b><b><b>hello</b></b></b>");
            verifyTest("</b><b>", "");
        }

        @Test
        public void test_end_slashes() {
            verifyTest("<img>", "<img />");
            verifyTest("<img/>", "<img />");
            verifyTest("<b/></b>", "");
        }

        @Test
        public void test_balancing_angle_brackets() {
            if (HTMLInputFilter.ALWAYS_MAKE_TAGS) {
                verifyTest("<img src=\"foo\"", "<img src=\"foo\" />");
                verifyTest("i>", "");
                verifyTest("<img src=\"foo\"/", "<img src=\"foo\" />");
                verifyTest(">", "");
                verifyTest("foo<b", "foo");
                verifyTest("b>foo", "<b>foo</b>");
                verifyTest("><b", "");
                verifyTest("b><", "");
                verifyTest("><b>", "");
            } else {
                verifyTest("<img src=\"foo\"", "&lt;img src=\"foo\"");
                verifyTest("b>", "b&gt;");
                verifyTest("<img src=\"foo\"/", "&lt;img src=\"foo\"/");
                verifyTest(">", "&gt;");
                verifyTest("foo<b", "foo&lt;b");
                verifyTest("b>foo", "b&gt;foo");
                verifyTest("><b", "&gt;&lt;b");
                verifyTest("b><", "b&gt;&lt;");
                verifyTest("><b>", "&gt;");
            }
        }

        @Test
        public void test_attributes() {
            verifyTest("<img src=foo>", "<img src=\"foo\" />");
            verifyTest("<img asrc=foo>", "<img />");
            verifyTest("<img src=test test>", "<img src=\"test\" />");
        }

        @Test
        public void test_disallow_script_tags() {
            verifyTest("<script>", "");
            if (HTMLInputFilter.ALWAYS_MAKE_TAGS) {
                verifyTest("<script", "");
            } else {
                verifyTest("<script", "&lt;script");
            }
            verifyTest("<script/>", "");
            verifyTest("</script>", "");
            verifyTest("<script woo=yay>", "");
            verifyTest("<script woo=\"yay\">", "");
            verifyTest("<script woo=\"yay>", "");
            verifyTest("<script woo=\"yay<b>", "");
            verifyTest("<script<script>>", "");
            verifyTest("<<script>script<script>>", "script");
            verifyTest("<<script><script>>", "");
            verifyTest("<<script>script>>", "");
            verifyTest("<<script<script>>", "");
        }

        @Test
        public void test_protocols() {
            verifyTest("<a href=\"http://foo\">bar</a>",
                    "<a href=\"http://foo\">bar</a>");
            // we don't allow ftp. t("<a href=\"ftp://foo\">bar</a>",
            // "<a href=\"ftp://foo\">bar</a>");
            verifyTest("<a href=\"mailto:foo\">bar</a>",
                    "<a href=\"mailto:foo\">bar</a>");
            verifyTest("<a href=\"javascript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
            verifyTest("<a href=\"java script:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
            verifyTest("<a href=\"java\tscript:foo\">bar</a>",
                    "<a href=\"#foo\">bar</a>");
            verifyTest("<a href=\"java\nscript:foo\">bar</a>",
                    "<a href=\"#foo\">bar</a>");
            verifyTest("<a href=\"java" + HTMLInputFilter.chr(1) + "script:foo\">bar</a>",
                    "<a href=\"#foo\">bar</a>");
            verifyTest("<a href=\"jscript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
            verifyTest("<a href=\"vbscript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
            verifyTest("<a href=\"view-source:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
        }

        @Test
        public void test_self_closing_tags() {
            verifyTest("<img src=\"a\">", "<img src=\"a\" />");
            verifyTest("<img src=\"a\">foo</img>", "<img src=\"a\" />foo");
            verifyTest("</img>", "");
        }

        @Test
        public void test_comments() {
            if (HTMLInputFilter.STRIP_COMMENTS) {
                verifyTest("<!-- a<b --->", "");
            } else {
                verifyTest("<!-- a<b --->", "<!-- a&lt;b --->");
            }
        }
}
