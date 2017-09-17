package info.knigoed.dao;

import info.knigoed.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserDao {

    @Autowired
    @Qualifier("mysqlJdbc")
    private JdbcTemplate mysqlJdbc;

    public User getByEmail(String email) {
        String sql = "SELECT * FROM User WHERE email = ?";
        return DataAccessUtils.singleResult(mysqlJdbc.queryForList(sql, new Object[]{email}, User.class));
        //return (User) mysqlJdbc.queryForObject(sql, new Object[]{email}, new BeanPropertyRowMapper(User.class));
    }


    public void setUser(String name, String email, String password, String hash) {
        String sql = "INSERT INTO User (name, email, password, hash) VALUES (?, ?, ?, ?, NOW())";
        mysqlJdbc.update(sql, new Object[]{name, email, password, hash});
    }
}
