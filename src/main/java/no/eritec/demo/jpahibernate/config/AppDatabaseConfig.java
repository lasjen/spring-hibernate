package no.eritec.demo.jpahibernate.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class AppDatabaseConfig implements BeanPostProcessor {
	Logger log = LoggerFactory.getLogger(AppDatabaseConfig.class);
	
	@Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof DataSource) {
        	 log.info("====> DEBUG <===== postProcessBeforeInit found DataSource");
             return new OracleMetricDataSource((DataSource) bean);
        }
        return bean;
    }
	@Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
