package org.demos.drools.cep;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.conf.MBeansOption;
import org.kie.api.definition.rule.Rule;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

// @SuppressWarnings("deprecation")
public class DefaultRuleEngine {
    private static final Logger LOG = LogManager
            .getLogger(DefaultRuleEngine.class);
    private KnowledgeBase knowledgeBase = null;
    private StatefulKnowledgeSession ksession = null;
    private static DefaultRuleEngine ruleEngine;
    private final Semaphore sempLock = new Semaphore(1);

    private DefaultRuleEngine() {

    }

    public static DefaultRuleEngine getInstance() {
        if (ruleEngine == null) {
            ruleEngine = new DefaultRuleEngine();
        }
        return ruleEngine;
    }

    /**
     *
     * 
     * @param rules
     * @throws RuleException
     * @throws
     */
    public void init(List<String> rules) {

        long initStart = System.currentTimeMillis();
        LOG.info("DefaultRuleEngine------init ");
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
                .newKnowledgeBuilder();

        for (int i = 0; i < rules.size(); i++) {
            // System.out.println("kbuilder-------------");
            kbuilder.add(ResourceFactory.newByteArrayResource(rules.get(i)
                    .getBytes()), ResourceType.DRL);
        }
        long initEnd1 = System.currentTimeMillis();
        LOG.info("DefaultRuleEngine------init " + rules.size() + " "
                + (initEnd1 - initStart));

        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        StringBuffer stringBuffer = new StringBuffer();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error : errors) {
                stringBuffer.append(error.toString());
                stringBuffer.append("\n");
            }
        }
        if (stringBuffer.length() != 0) {
            throw new RuntimeException("" + stringBuffer.toString());
        }
        long checkTime = System.currentTimeMillis();
        LOG.info("DefaultRuleEngine------" + (checkTime - initEnd1));

        Collection<KnowledgePackage> kpackage = kbuilder.getKnowledgePackages();
        KieBaseConfiguration conf = KnowledgeBaseFactory
                .newKnowledgeBaseConfiguration();

        conf.setOption(EventProcessingOption.STREAM);
        conf.setOption(MBeansOption.ENABLED);
        try {
            sempLock.acquire();

            if (knowledgeBase == null) {
                knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase(
                        "utmpBase", conf);
            }
            knowledgeBase.addKnowledgePackages(kpackage);
            if (ksession == null) {
                ksession = knowledgeBase.newStatefulKnowledgeSession();
                Map<String, Object> result = new HashMap<String, Object>();
                ksession.setGlobal("result", result);

            }
        } catch (InterruptedException e) {
        } finally {
            sempLock.release();
        }
        LOG.info("" + (System.currentTimeMillis() - checkTime));
    }

    /**
     * 鍒犻櫎瑙勫垯
     * 
     * @param rule
     */
    public void removeRule(String packName, String ruleName) {
        Rule rule = knowledgeBase.getRule(packName, ruleName);
        if (rule == null) {
            throw new RuntimeException("rule " + packName + "." + ruleName
                    + " not found!");
        }
        knowledgeBase.removeRule(packName, ruleName);
    }

    public void insertFact(Object fact) {
        ksession.insert(fact);
    }

    public void fireAllRules() {
        ksession.fireAllRules();
    }

    public static void main(String[] args) throws UnsupportedEncodingException,
            IOException {

        LOG.info("starting ......");

        // 初始化读入规则
        InputStream in = DefaultRuleEngine.class.getClassLoader()
                .getResourceAsStream(
                        "drools/osgi/engine/testEventFact_java.drl");

        if (in == null) {
            LOG.error("testEventFact.drl not found");
            return;
        }

        byte[] buff = new byte[8196];
        StringBuilder rule = new StringBuilder();
        int l;
        while ((l = in.read(buff)) > -1) {
            rule.append(new String(buff, 0, l, "UTF-8"));
        }

        List<String> rules = new ArrayList<String>();
        rules.add(rule.toString());

        DefaultRuleEngine engine = DefaultRuleEngine.getInstance();
        engine.init(rules);

        // 插入事实
        SubjectType subjectType;
        Subject subject;
        // 插入三种类型共9个subject
        for (int i = 0; i < 3; i++) {
            subjectType = new SubjectType();
            subjectType.setName("ST" + (i + 1));
            engine.insertFact(subjectType);
            for (int j = 0; j < 3; j++) {
                subject = new Subject();
                subject.setId("S" + (3 * j + i + 1));
                subject.setSubjectType(subjectType);
                engine.insertFact(subject);
            }
        }

        engine.fireAllRules();

        EventFact event;
        event = new EventFact();
        event.setSubjectId("S" + 1);
        event.setSubjectTypeId("ST" + 1);
        event.setName("cpu");
        Map<String, Object> indexMap0 = new HashMap<String, Object>();
        indexMap0.put("CPU", 0.1);
        indexMap0.put("Mem", 0.2);
        event.setIndexMap(indexMap0);
        event.setValue(0.1);
        engine.insertFact(event);
        engine.fireAllRules();

        LOG.info("Remove test1");
        engine.removeRule("drools.osgi.engine", "test1");

        event = new EventFact();
        event.setSubjectId("S" + 2);
        event.setSubjectTypeId("ST" + 2);
        event.setName("cpu");
        indexMap0 = new HashMap<String, Object>();
        indexMap0.put("CPU", 0.1);
        indexMap0.put("Mem", 0.2);
        event.setIndexMap(indexMap0);
        event.setValue(0.1);
        engine.insertFact(event);
        engine.fireAllRules();

        LOG.info("started!");
    }
}
