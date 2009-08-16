package org.encuestame.core.security.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Id: EmailUtils.java Date: 21/05/2009 13:41:09
 * 
 * @author juanpicado package: org.encuestame.core.security.util
 * @version 1.0
 */
public class EmailUtils {

	/**
	 * Validate Email
	 * @param email
	 * @return
	 */
	public static boolean validateEmail(String email) {
		// Set the email pattern string
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		// Match the given string with the pattern
		Matcher m = p.matcher(email);
		// check whether match is found
		boolean matchFound = m.matches();
		if (matchFound)
			return true;
		else
			return false;
	}

}
