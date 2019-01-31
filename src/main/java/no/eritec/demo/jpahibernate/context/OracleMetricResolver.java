package no.eritec.demo.jpahibernate.context;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Provider
public class OracleMetricResolver implements ContextResolver<OracleMetricContext> {
    private OracleMetricContext context = new OracleMetricContextImpl();
    
    Logger log = LoggerFactory.getLogger(OracleMetricResolver.class);

    @Override
    public OracleMetricContext getContext(Class<?> type) {
    	log.info("====> DEBUG <==== called OraceMetricContext()");
        if (type == String.class) {
            return context;
        }
        return null;
    }

    private static class OracleMetricContextImpl implements OracleMetricContext<String> {
    	private String key="DEFAULT";
        
    	@Override
        public String get(String key) {
            return "KEY:" + key;
        }
        @Override 
        public void set(String key) {
        	this.key = key;
        }
    }
}
