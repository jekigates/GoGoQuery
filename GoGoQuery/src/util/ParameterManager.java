package util;

import java.util.HashMap;
import java.util.Map;

public class ParameterManager {
	private static final Map<String, Object> parameters = new HashMap<String, Object>();

	public static void put(String key, Object value) {
		parameters.put(key, value);
	}

	public static Object get(String key) {
		return parameters.get(key);
	}

	public static void clear() {
		parameters.clear();
	}
}
