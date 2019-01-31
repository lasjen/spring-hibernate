package no.eritec.demo.jpahibernate.context;

public interface OracleMetricContext<T> {
	T get(String key);
	void set(String key);
}
