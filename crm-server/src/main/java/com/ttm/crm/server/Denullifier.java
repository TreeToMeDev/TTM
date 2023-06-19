package com.ttm.crm.server;

import java.lang.reflect.Method;

public class Denullifier {

	/* Pass any object into this method and and String fields that have a value of null will
	 * be set to blanks. Only works for Strings and only for objects that have getters and setters
	 * setup with consistent naming conventions. Anything else will be ignored.
	 */

	public static void denullify(Object object) {
		try {
			for(Method method : object.getClass().getMethods()) {
				if(method.getName().startsWith("get") && method.getReturnType().equals(String.class)) {
					String s = (String) method.invoke(object);
					if(s == null) {
						Method setter = object.getClass().getMethod("set" + method.getName().substring(3), String.class);
						setter.invoke(object, "");
					}
				}
			}
		} catch(Exception e) {
			throw new RuntimeException("denullifier failure");
		}
	}
	
}
