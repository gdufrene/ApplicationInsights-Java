package com.microsoft.applicationinsights.common;

public class Preconditions {

	public static void checkArgument(boolean check, String message) {
		if ( !check ) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void checkNotNull(Object obj, String message) {
		if ( obj == null ) {
			throw new NullPointerException(message);
		}
	}

	public static void checkState(boolean state, String message) {
		if ( !state ) {
			throw new IllegalStateException(message);
		}
	}

}
