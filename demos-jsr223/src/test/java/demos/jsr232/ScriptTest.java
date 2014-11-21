package demos.jsr232;

import java.math.BigDecimal;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 脚本引擎测试
 * 
 * @author han_feng
 * 
 */
public class ScriptTest {

	private final static Logger logger = LogManager.getLogger(ScriptTest.class);

	private final static ScriptEngineManager manager = new ScriptEngineManager();

	@Test
	public void testJavaScript() throws ScriptException {
		Bindings bindings = new SimpleBindings();
		bindings.put("单价", 10.0);
		bindings.put("数量", 100);
		double ret = (Double) test("js", "单价*数量", bindings);
		Assert.assertEquals(1000, ret, 0.1);
	}

	@Test
	public void testGroovy() throws ScriptException {
		Bindings bindings = new SimpleBindings();
		bindings.put("单价", BigDecimal.valueOf(10.0));
		bindings.put("数量", 100);
		BigDecimal ret = (BigDecimal) test("groovy", "单价*数量", bindings);
		Assert.assertEquals(BigDecimal.valueOf(1000.0), ret);
	}

	@Test
	public void testClojure() throws ScriptException {
		Bindings bindings = new SimpleBindings();
		bindings.put("单价", 10.0);
		bindings.put("数量", 100);
		double ret = (Double) test("Clojure", "(* 单价 数量)", bindings);
		Assert.assertEquals(1000, ret, 0.1);
	}

	@Test
	public void testPython() throws ScriptException {
		// python不支持汉字做变量名
		Bindings bindings = new SimpleBindings();
		bindings.put("price", 10.0);
		bindings.put("num", 100);
		double ret = (Double) test("python", "price*num", bindings);
		Assert.assertEquals(1000, ret, 0.1);
	}

	@Test
	public void testRuby() throws ScriptException {
		// Ruby不支持汉字做变量名
		Bindings bindings = new SimpleBindings();
		bindings.put("price", 10.0);
		bindings.put("num", 100);
		double ret = (Double) test("ruby", "$price*$num", bindings);
		Assert.assertEquals(1000, ret, 0.1);
	}

	private Object test(String scriptName, String script, Bindings bindings)
			throws ScriptException {
		ScriptEngine scriptEngine = manager.getEngineByName(scriptName);
		return scriptEngine.eval(script, bindings);
	}

	@BeforeClass
	public static void init() {
		// JavaFX Script, Groovy, JRuby, Jython, JavaScript, Scala, Clojure
		logger.entry();
		logger.info("当前支持的脚本引擎有：");
		for (ScriptEngineFactory factory : manager.getEngineFactories()) {
			logger.info("\t{} ({}):", factory.getEngineName(),
					factory.getEngineVersion());

			logger.info("\t\tLanguage: {} ({})", factory.getLanguageName(),
					factory.getLanguageVersion());

			logger.info("\t\tNames: {}", factory.getNames());
			logger.info("\t\tMimeTypes: {}", factory.getMimeTypes());
			logger.info("\t\tExtensions: {}", factory.getExtensions());

			if (factory.getScriptEngine() instanceof Compilable) {
				logger.info("\t\tSupport compilation.");
			}

			if (factory.getScriptEngine() instanceof Invocable) {
				logger.info("\t\tSupport invocation.");
			}
		}
		logger.exit();
	}
}
