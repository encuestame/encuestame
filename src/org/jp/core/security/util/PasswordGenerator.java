package org.jp.core.security.util;
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
 * Id: PasswordGenerator.java Date: 21/05/2009 13:41:09
 * @author juanpicado
 * package: org.jp.core.security.util
 * @version 1.0
 */
public class PasswordGenerator {

	public static String NUMBERS = "0123456789";
	public static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
	public static String ESPECIALES = "Ò—%&@%=[]?ø{}#~,.*+$!";

	public static String getPinNumber() {
		return getPassword(NUMBERS, 4);
	}

	public static String getEspecialPassword() {
		return getPassword(NUMBERS + MAYUSCULAS + MINUSCULAS + ESPECIALES, 12);
	}

	public static String getEspecialPassword(Integer e) {
		return getPassword(NUMBERS + MAYUSCULAS + MINUSCULAS + ESPECIALES, e);
	}

	public static String getPassword() {
		return getPassword(8);
	}

	public static String getPassword(int length) {
		return getPassword(NUMBERS + MAYUSCULAS + MINUSCULAS, length);
	}

	public static String getPassword(String key, int length) {
		String pswd = "";

		for (int i = 0; i < length; i++) {
			pswd += (key.charAt((int) (Math.random() * key.length())));
		}

		return pswd;
	}

	public static String getNumericPassword(int lenght) {
		return getPassword(NUMBERS, lenght);
	}
}
