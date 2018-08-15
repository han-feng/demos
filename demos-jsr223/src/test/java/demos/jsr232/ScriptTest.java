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

    private static final Logger LOG = LogManager.getLogger(ScriptTest.class);

    private static final ScriptEngineManager MANAGER = new ScriptEngineManager();

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

    @Test
    public void testLua() throws ScriptException {
        // lua不支持汉字做变量名
        Bindings bindings = new SimpleBindings();
        bindings.put("price", 10.0);
        bindings.put("num", 100);
        Number ret = (Number) test("lua", "return price*num", bindings);
        Assert.assertEquals(1000, ret.doubleValue(), 0.1);
    }

    private Object test(String scriptName, String script, Bindings bindings)
            throws ScriptException {
        ScriptEngine scriptEngine = MANAGER.getEngineByName(scriptName);
        if (scriptEngine == null) {
            throw new RuntimeException(scriptName + " not supported");
        }
        return scriptEngine.eval(script, bindings);
    }

    @BeforeClass
    public static void init() {
        // JavaFX Script, Groovy, JRuby, Jython, JavaScript, Scala, Clojure, Lua
        LOG.entry();
        LOG.info("当前支持的脚本引擎有：");
        for (ScriptEngineFactory factory : MANAGER.getEngineFactories()) {
            LOG.info("\t{} ({}):", factory.getEngineName(),
                    factory.getEngineVersion());

            LOG.info("\t\tLanguage: {} ({})", factory.getLanguageName(),
                    factory.getLanguageVersion());

            LOG.info("\t\tNames: {}", factory.getNames());
            LOG.info("\t\tMimeTypes: {}", factory.getMimeTypes());
            LOG.info("\t\tExtensions: {}", factory.getExtensions());

            ScriptEngine engine = factory.getScriptEngine();
            if (engine != null) {

                LOG.info("\t\tEngine implementation: {}",
                        engine.getClass().getName());

                if (factory.getScriptEngine() instanceof Compilable) {
                    LOG.info("\t\tSupport compilation.");
                }

                if (factory.getScriptEngine() instanceof Invocable) {
                    LOG.info("\t\tSupport invocation.");
                }
            }
        }
        LOG.exit();
    }
}
