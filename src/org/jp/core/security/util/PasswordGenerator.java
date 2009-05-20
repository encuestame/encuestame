package org.jp.core.security.util;

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
