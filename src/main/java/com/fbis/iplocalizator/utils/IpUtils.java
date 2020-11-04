package com.fbis.iplocalizator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtils {

	// 25[0-5] = 250-255
	// (2[0-4])[0-9] = 200-249
	// (1[0-9])[0-9] = 100-199
	// ([1-9])[0-9] = 10-99
	// [0-9] = 0-9
	// (\.(?!$)) = can't end with a dot
	private static final String IPV4_PATTERN = "^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\\.(?!$)|$)){4}$";

	private static final Pattern pattern = Pattern.compile(IPV4_PATTERN);

	//Valida que sea una dirección IPv4
	public static boolean isValidIP(final String ip) {
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

}
