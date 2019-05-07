package com.cvicse.refactor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefactorDemo {

	public void forEachDemo() {
		// Array
		String[] stringArray = new String[100];
		Arrays.fill(stringArray, "test");

		for (int i = 0, len = stringArray.length; i < len; i++) {
			assert "test".equals(stringArray[i]);
		}

		for (String str : stringArray) {
			assert "test".equals(str);
		}

		Arrays.asList(stringArray).forEach(str -> {
			assert "test".equals(str);
		});

		// List
		List<String> stringList = new ArrayList<>();
		Collections.fill(stringList, "test");

		for (int i = 0, len = stringList.size(); i < len; i++) {
			assert "test".equals(stringList.get(i));
		}

		for (String str : stringList) {
			assert "test".equals(str);
		}

		stringList.forEach(str -> {
			assert "test".equals(str);
		});

		// Map
		Map<String, String> stringMap = new HashMap<>();
		for (int i = 0; i < 100; i++) {
			stringMap.put("" + i, "test");
		}

		for (Map.Entry<String, String> entry : stringMap.entrySet()) {
			assert "test".equals(entry.getValue());
		}

		stringMap.forEach((k, v) -> {
			assert "test".equals(v);
		});
	}

	public static void main(String[] args) {
		new RefactorDemo().forEachDemo();
	}

}
