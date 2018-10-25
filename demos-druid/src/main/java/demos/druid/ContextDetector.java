package demos.druid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextDetector implements ApplicationContextAware {

    private static final Logger LOG = LogManager
            .getLogger(ContextDetector.class);

    @Override
    public void setApplicationContext(ApplicationContext ctx)
            throws BeansException {
        if (ctx != null) {
            printApplicationContext(ctx);
        }
    }

    private static void printApplicationContext(ApplicationContext ctx) {

        ApplicationContext pctx = ctx.getParent();
        if (pctx != null) {
            printApplicationContext(pctx);
        }

        LOG.info("{}\t{}\t{}", ctx.getId(), ctx.getDisplayName(),
                ctx.getApplicationName());
        for (String name : ctx.getBeanDefinitionNames()) {
            LOG.info("\t{}", name);
        }
    }

}
