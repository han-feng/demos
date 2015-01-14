package org.demos.drools.cep;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class ClassCastBugTest {

    private static final Logger LOG = LogManager
            .getLogger(ClassCastBugTest.class);

    @Test
    public void testClassCastBug() throws IOException {
        LOG.info("starting ......");

        // 初始化读入规则
        InputStream in = DefaultRuleEngine.class
                .getClassLoader()
                .getResourceAsStream(
                        "org/demos/drools/cep/classcastbug/testEventFact_java.drl");

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
        engine.removeRule("org.demos.drools.cep", "test1");

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
