package com.yangbingdong.springboot.common.utils;

/**
 * @author ybd
 * @date 18-4-26
 * @contact yangbingdong1994@gmail.com
 */
public class NumberUtil {
	public static final int MAX_SEED = 62;

	/**
	 * 在进制表示中的字符集合，0-Z分别用于表示最大为62进制的符号表示
	 */
	private static final char[] DIGITS = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
	};

	/**
	 * 将十进制的数字转换为指定进制的字符串
	 *
	 * @param number 十进制的数字
	 * @param seed   指定的进制
	 * @return 指定进制的字符串
	 */
	public static String toOtherNumberSystem(long number, int seed) {
		if (seed > MAX_SEED) {
			throw new IllegalArgumentException("Max seed is " + MAX_SEED);
		}
		if (number < 0) {
			number = ((long) 2 * 0x7fffffff) + number + 2;
		}
		char[] buf = new char[32];
		int charPos = 32;
		while ((number / seed) > 0) {
			buf[--charPos] = DIGITS[(int) (number % seed)];
			number /= seed;
		}
		buf[--charPos] = DIGITS[(int) (number % seed)];
		return new String(buf, charPos, (32 - charPos));
	}

	/**
	 * 将其它进制的数字（字符串形式）转换为十进制的数字
	 *
	 * @param number 其它进制的数字（字符串形式）
	 * @param seed   指定的进制，也就是参数str的原始进制
	 * @return 十进制的数字
	 */
	public static long toDecimalNumber(String number, int seed) {
		if (seed > MAX_SEED) {
			throw new IllegalArgumentException("Max seed is " + MAX_SEED);
		}
		if (seed == 10) {
			return Long.parseLong(number);
		}

		char[] charBuf = number.toCharArray();
		long result = 0, base = 1;

		for (int i = charBuf.length - 1; i >= 0; i--) {
			int index = 0;
			for (int j = 0, length = DIGITS.length; j < length; j++) {
				//找到对应字符的下标，对应的下标才是具体的数值
				if (DIGITS[j] == charBuf[i]) {
					index = j;
				}
			}
			result += index * base;
			base *= seed;
		}
		return result;
	}

}