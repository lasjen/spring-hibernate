package no.eritec.demo.jpahibernate.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class WaitRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Transactional(readOnly=true)
    public List<Integer> getIdWithWait(int rows, int sec_wait) {
        return jdbcTemplate.query("select id from table(get_id(?,?))", 
        		new Object[]{rows,sec_wait}, new IntegerRowMapper());
    } 
}

class IntegerRowMapper implements RowMapper<Integer>
{
    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Integer(rs.getInt("ID"));
    }
}

