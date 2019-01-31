package no.eritec.demo.jpahibernate.config;

import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;

public class OracleMetricDataSource implements DataSource, InitializingBean, BeanNameAware {
	private final Logger log = LoggerFactory.getLogger(OracleMetricDataSource.class);

	private DataSource dataSource;
	private String beanName;
	private List<String> statements;
	
	private String module;
	private String action;
	private String ecid;
	
	public OracleMetricDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.module = "ModuleDefault";
		this.action = "ActionDefault";
		this.ecid = "EcidDefault";
	}
	
	public synchronized void setStatements(List<String> statements) {
		if (log.isDebugEnabled()) {
			log.debug("Statements set to " + statements + " on " + this.beanName);
		}
		if (statements != null && statements.size() > 0) {
			this.statements = new ArrayList<String>(statements);
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
	@Override
	public Connection getConnection() throws SQLException {
		//StubWebApplicationContext
		Connection c = dataSource.getConnection();
		setMetrics(c);
		log.info("==> OracleMetricDatasource.getConnection completed.");
		//if (!this.module.equals("ModuleDefault")) beginOperation(c,this.action);
		return c;
	}
	
	public void setContext(String mod, String act, String ecid) {
		this.module = mod;
		this.action = act;
		this.ecid = ecid;
	}
	
	public void setMetrics(Connection c) throws SQLException {
		Properties p = new Properties();
		p.setProperty("OCSID.MODULE", this.module);
		p.setProperty("OCSID.ACTION", this.action);
		p.setProperty("OCSID.ECID", this.ecid);
		p.setProperty("OCSID.DBOP", this.action);
		p.setProperty("OCSID.CLIENTID", UUID.randomUUID().toString());
		c.setClientInfo(p);
	}
	
	public void setMetrics(Connection c, String mod, String act) throws SQLException {
		Properties p = new Properties();
		p.setProperty("OCSID.MODULE", mod);
		p.setProperty("OCSID.ACTION", act);
		p.setProperty("OCSID.CLIENTID", UUID.randomUUID().toString());
		p.setProperty("OCSID.ECID", "TestECID");
		p.setProperty("OCSID.DBOP", act);
		c.setClientInfo(p);
	}
	
	public void setMetrics(String mod, String act, String cli) throws SQLException {
		Connection c = dataSource.getConnection();
		Properties p = new Properties();
		p.setProperty("OCSID.MODULE", mod);
		p.setProperty("OCSID.ACTION", act);
		p.setProperty("OCSID.CLIENTID", UUID.randomUUID().toString());
		p.setProperty("OCSID.ECID", "TestECID");
		p.setProperty("OCSID.DBOP", act);
		c.setClientInfo(p);
	}

	private void executeStatements(Connection c) throws SQLException {
		List<String> s = statements;
		if (s == null) return;
		
		if (log.isDebugEnabled()) {
			log.debug("Executing statements " + s);
		}
		Statement statement = c.createStatement();
		try {
			for (String stmt : s) {
				log.info("executeStatment: " + stmt);
				statement.execute(stmt);
			}
		} finally {
			JdbcUtils.closeStatement(statement);
		}
		
	}
	
	private void beginOperation(Connection c, String dbop_name) throws SQLException {
		int ret = 0;
		String sql = "BEGIN :dbop_id := DBMS_SQL_MONITOR.begin_operation ( \n" +
	                 "  dbop_name       => :l_dbop_name, \n" +
		            // "  dbop_eid        => NULL" + //dbop_seq.nextval, \n" +
		             "  forced_tracking => DBMS_SQL_MONITOR.force_tracking); \n" +
		             "END;";
		
		CallableStatement stmt = c.prepareCall(sql);		
		
		try {
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, dbop_name);
			stmt.execute();
			ret = stmt.getInt(1);
			//log.info("==> OracleMetricDatasource.beginOperation dbop_name set.");
		} finally {
			JdbcUtils.closeStatement(stmt);
		}
		log.info("==> OracleMetricDatasource.beginOperation completed.");
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		Connection c = dataSource.getConnection(username, password);
		executeStatements(c);
		return c;
	}

	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return dataSource.isWrapperFor(iface);
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		dataSource.setLogWriter(out);
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		dataSource.setLoginTimeout(seconds);
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return dataSource.unwrap(iface);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.dataSource, "dataSource must be set on bean " + this.beanName + " of type " + getClass().getName());
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}
