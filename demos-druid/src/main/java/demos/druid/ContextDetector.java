package demos.druid;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextDetector implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		if (ctx != null)
			printApplicationContext(ctx);
	}

	private static void printApplicationContext(ApplicationContext ctx) {
		ApplicationContext pctx = ctx.getParent();
		if (pctx != null)
			printApplicationContext(pctx);

		System.out.println(ctx.getId() + "\t" + ctx.getDisplayName() + "\t"
				+ ctx.getApplicationName());
		for (String name : ctx.getBeanDefinitionNames()) {
			System.out.println("\t" + name);
		}
	}

}
