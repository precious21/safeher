package com.tgi.safeher.common.enumeration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum ColorEnum {
	Lilac(1), Violet(2), Blue(3), Mint(4), Green(5), Yellow(6), Grey(7), Dark_Red(
			8), Pink(9), Red(10), Soft_Pink(11), Light_Grey(12) ;
	private int value;

	ColorEnum(int code) {
		this.value = code;
	}

	public int getValue() {

		return value;
	}

	private static final List<ColorEnum> VALUES = Collections
			.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static ColorEnum randomColor() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

}
