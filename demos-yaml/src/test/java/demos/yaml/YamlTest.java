package demos.yaml;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.snakeyaml.engine.v2.api.Load;
import org.snakeyaml.engine.v2.api.LoadSettings;
import org.yaml.snakeyaml.Yaml;

public class YamlTest {

	@Test
	public void testLoad12() {
		LoadSettings settings = LoadSettings.builder().build();
		Load load = new Load(settings);
		Object obj = load.loadFromInputStream(this.getClass().getClassLoader().getResourceAsStream("test.yaml"));

		Map<String, Object> result = new LinkedHashMap<>();
		flat("", obj, result);
		System.out.println(result);

		Map<String, Object> result2 = new LinkedHashMap<>();

		fillParamsValue(result, result2);

		System.out.println(result2);
	}

	@Test
	public void testLoad11() {
		Yaml yaml = new Yaml();
		Object obj = yaml.load(this.getClass().getClassLoader().getResourceAsStream("test.yaml"));

		Map<String, Object> result = new LinkedHashMap<>();
		flat("", obj, result);
		System.out.println(result);

		Map<String, Object> result2 = new LinkedHashMap<>();

		fillParamsValue(result, result2);

		System.out.println(result2);
	}

	/**
	 * 参数扁平化
	 *
	 * @param prefix
	 * @param in
	 * @param out
	 */
	private void flat(final String prefix, Object in, Map<String, Object> out) {
		if (in instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) in;
			map.forEach((k, v) -> {
				String key = prefix.equals("") ? k : prefix + "." + k;
				flat(key, v, out);
			});
		} else {
			out.put(prefix, in);
		}
	}

	/**
	 * 变量替换
	 *
	 * @param in
	 * @param out
	 */
	private void fillParamsValue(Map<String, Object> in, Map<String, Object> out) {
		final Pattern p = Pattern.compile("(\\$\\{\\s*)([\\w\\.]+)(\\s*\\})");
		in.forEach((k, v) -> {
			Matcher m = p.matcher(v.toString());
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String group = m.group(2);
				Object value = out.get(group);
				if (value != null) {
					m.appendReplacement(sb, value.toString());
				}
			}
			m.appendTail(sb);
			out.put(k, sb.toString());
		});
	}

}
