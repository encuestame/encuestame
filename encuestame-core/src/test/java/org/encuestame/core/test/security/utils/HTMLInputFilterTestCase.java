/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
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
import org.junit.Test;

/**
 * {@link HTMLInputFilter} XSS Test Case.
 * http://josephoconnell.com/java/xss-html-filter/
 * @author Joseph O'Connell <joe.oconnell at gmail dot com>
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 17, 2010 9:39:54 PM
 * @version $Id:$
 */
public class HTMLInputFilterTestCase extends TestCase {

        protected HTMLInputFilter vFilter = new HTMLInputFilter(true);
        protected HTMLInputFilter vFilter2 = new HTMLInputFilter();

        /**
         *
         * @param input
         * @param result
         */
        private void verifyTest(String input, String result) {
            Assert.assertEquals(result, vFilter.filter(input));
        }

        /**
         * Test Basics.
         */
        @Test
        public void testBasics() {
            verifyTest("", "");
            verifyTest("hello", "hello");
            verifyTest("([a-z0-9]+)", "([a-z0-9]+)");
        }

        /**
         * Balancing Tags.
         */
        @Test
        public void test_balancingTags() {
            verifyTest("<b>hello", "<b>hello</b>");
            verifyTest("<b>hello", "<b>hello</b>");
            verifyTest("hello<b>", "hello");
            verifyTest("hello</b>", "hello");
            verifyTest("hello<b/>", "hello");
            verifyTest("<b><b><b>hello", "<b><b><b>hello</b></b></b>");
            verifyTest("</b><b>", "");
        }

        /**
         * End Slashed.
         */
        @Test
        public void testEndSlashes() {
            verifyTest("<img>", "<img />");
            verifyTest("<img/>", "<img />");
            verifyTest("<b/></b>", "");
        }

        /**
         * Balancing.
         */
        @Test
        public void testBalancingAnglBrackets() {
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

        /**
         * Test Attribites.
         */
        @Test
        public void testAttributes() {
            verifyTest("<img src=foo>", "<img src=\"foo\" />");
            verifyTest("<img asrc=foo>", "<img />");
            verifyTest("<img src=test test>", "<img src=\"test\" />");
        }

        /**
         * Dissalow.
         */
        @Test
        public void testDisallowScriptTags() {
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

        /**
         * Test Protocols.
         */
        @Test
        public void testProtocols() {
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

        /**
         * Test Closing Tags.
         */
        @Test
        public void testSelfClosingTags() {
            verifyTest("<img src=\"a\">", "<img src=\"a\" />");
            verifyTest("<img src=\"a\">foo</img>", "<img src=\"a\" />foo");
            verifyTest("</img>", "");
        }

        /**
         * Test Comment.
         */
        @Test
        public void testComments() {
            if (HTMLInputFilter.STRIP_COMMENTS) {
                verifyTest("<!-- a<b --->", "");
            } else {
                verifyTest("<!-- a<b --->", "<!-- a&lt;b --->");
            }
        }
}
