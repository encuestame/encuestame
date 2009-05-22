package org.jp.test;

import java.util.Date;
import java.util.Formatter;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jp.core.security.util.PasswordGenerator;

import sun.security.provider.MD5;

/**
 * encuestame:  system online surveys
 * Copyright (C) 2009  encuestame Development Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * Id: StrongPasswords.java Date: 20/05/2009 11:48:46
 * @author juanpicado
 * package: org.jp.test
 * @version 1.0
 */
public class StrongPasswords {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		org.jasypt.util.password.BasicPasswordEncryptor d = new BasicPasswordEncryptor();
		
		
		String encryptedPassword = passwordEncryptor.encryptPassword("test");
		String encryptedPassword2 = d.encryptPassword(new Date().toString()+"juanpicado19@gmail.com");
		String randomNumber = new Formatter().format("%05d", new Random().nextInt(100000)).toString();
		System.out.print(randomNumber);

		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for(int i = 0; i < 60; i++) {
		int num = random.nextInt(10);
		sb.append(Integer.toString(num));
		}
		String randomnumber = sb.toString();
		System.out.print(randomnumber);
	}

}
