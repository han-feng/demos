package org.demos.drools.cep;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class CEPTest {

	@Test
	public void testEventFact() {

		KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
		KieBaseConfiguration config = KieServices.Factory.get()
				.newKieBaseConfiguration();
		config.setOption(EventProcessingOption.STREAM);
		KieBase kb = kc.newKieBase("CEPTest", config);
		KieSession ksession = kb.newKieSession();

		Map<String, Object> result = new HashMap<String, Object>();
		ksession.setGlobal("result", result);

		SubjectType subjectType;
		Subject subject;
		// 插入三种类型共9个subject
		for (int i = 0; i < 3; i++) {
			subjectType = new SubjectType();
			subjectType.setName("ST" + (i + 1));
			for (int j = 0; j < 3; j++) {
				subject = new Subject();
				subject.setId("S" + (3 * j + i + 1));
				subject.setSubjectType(subjectType);
				ksession.insert(subject);
			}
		}

		Assert.assertEquals(9, ksession.getFactCount());

		EventFact event;
		for (int i = 0; i < 81; i++) {
			event = new EventFact();
			event.setSubjectId("S" + (i % 9 + 1));
			event.setSubjectTypeId("ST" + (i % 3 + 1));
			event.setName("cpu");
			Map<String, Object> indexMap = new HashMap<String, Object>();
			indexMap.put("CPU", 0.1 * i);
			indexMap.put("Mem", 0.2 * i);
			event.setIndexMap(indexMap);
			event.setValue(0.1 * i);
			ksession.insert(event);
			ksession.fireAllRules();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}

		final double delta = 0.000001;

		Assert.assertEquals(7.2, (Double) result.get("test1"), delta);
		Assert.assertEquals(7.8, (Double) result.get("test2"), delta);
		Assert.assertEquals(7.1, (Double) result.get("test3"), delta);
		Set<?> set = (Set<?>) result.get("test4");

		Assert.assertEquals(10, set.size());
		Assert.assertEquals(29, ksession.getFactCount());

	}
}
