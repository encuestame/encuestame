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
package org.encuestame.core.test.util;

import org.encuestame.utils.ValidationUtils;
import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * Validation Utils Test Cases.
 * 
 * @author Picado, Juan juanATencuestame.org
 * @since May 26, 2012 11:29:20 PM
 */
public class ValidationUtilsTestCases extends TestCase {

	@Test
	public void testCalculateSizeTag() {	
		Assert.assertEquals(ValidationUtils.removeNonAlphanumericCharacters("$$$%#@$%@#^%$%#$$#@#@"), "");
		Assert.assertEquals( ValidationUtils.removeNonAlphanumericCharacters("$$$2%#@$%@#^%$%#$$#@#@"), "2");
		Assert.assertEquals( ValidationUtils.removeNonAlphanumericCharacters("$$$2%#@$%@#^%$%2#$$#@#@"), "22");
		Assert.assertEquals( ValidationUtils.removeNonAlphanumericCharacters("abc$%@#^%$%#$$#@#@"), "abc");
		Assert.assertEquals( ValidationUtils.removeNonAlphanumericCharacters("3232323232@#@"), "3232323232");
		Assert.assertEquals( ValidationUtils.removeNonAlphanumericCharacters("'323'&*422"), "323422");
	}
}
