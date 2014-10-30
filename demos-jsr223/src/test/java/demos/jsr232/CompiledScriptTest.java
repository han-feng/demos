package demos.jsr232;

import java.math.BigDecimal;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;

import org.junit.Assert;
import org.junit.Test;

/**
 * 预编译脚本测试
 * 
 * @author han_feng
 * 
 */
public class CompiledScriptTest {

	private final static ScriptEngineManager manager = new ScriptEngineManager();

	private final static int MAX = 1000;

	@Test
	public void testJavaScript() throws Exception {

		CompiledScript script = compile("js", "单价*数量");

		Bindings bindings = new SimpleBindings();
		for (int i = 0; i < MAX; i++) {
			bindings.put("单价", 10.0 * i);
			bindings.put("数量", 100 * i);

			double ret = (Double) script.eval(bindings);
			bindings.clear();

			Assert.assertEquals(1000.0 * i * i, ret, 0.1);
		}
	}

	@Test
	public void testGroovy() throws Exception {

		CompiledScript script = compile("groovy", "单价*数量");

		Bindings bindings = new SimpleBindings();
		for (int i = 0; i < MAX; i++) {
			bindings.put("单价", BigDecimal.valueOf(10.0 * i));
			bindings.put("数量", 100 * i);

			BigDecimal ret = (BigDecimal) script.eval(bindings);
			bindings.clear();

			Assert.assertEquals(1000.0 * i * i, ret.doubleValue(), 0.1);
		}
	}

	// 测试未通过
	// @Test
	// public void testClojure() throws Exception {
	// CompiledScript script = compile("Clojure", "(* 单价 数量)");
	//
	// Bindings bindings = new SimpleBindings();
	// for (int i = 0; i < MAX; i++) {
	// bindings.put("单价", 10.0 * i);
	// bindings.put("数量", 100 * i);
	//
	// double ret = (Double) script.eval(bindings);
	// bindings.clear();
	//
	// Assert.assertEquals(1000 * i * i, ret, 0.1);
	// }
	// }

	@Test
	public void testPython() throws Exception {
		// Ruby不支持汉字做变量名
		CompiledScript script = compile("python", "price*num");

		Bindings bindings = new SimpleBindings();
		for (int i = 0; i < MAX; i++) {
			bindings.put("price", 10.0 * i);
			bindings.put("num", 100 * i);

			double ret = (Double) script.eval(bindings);
			bindings.clear();

			Assert.assertEquals(1000.0 * i * i, ret, 0.1);
		}
	}

	@Test
	public void testRuby() throws Exception {
		// Ruby不支持汉字做变量名
		CompiledScript script = compile("ruby", "$price*$num");

		Bindings bindings = new SimpleBindings();
		for (int i = 0; i < MAX; i++) {
			bindings.put("price", 10.0 * i);
			bindings.put("num", 100 * i);

			double ret = (Double) script.eval(bindings);
			bindings.clear();

			Assert.assertEquals(1000.0 * i * i, ret, 0.1);
		}
	}

	private CompiledScript compile(String scriptName, String script)
			throws Exception {
		ScriptEngine scriptEngine = manager.getEngineByName(scriptName);

		if (scriptEngine instanceof Compilable) {
			Compilable compilable = (Compilable) scriptEngine;
			return compilable.compile(script);
		}
		throw new Exception("Compilation is not supported");
	}

}
