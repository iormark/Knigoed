package info.knigoed.dao;

import info.knigoed.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserDao {

    @Autowired
    @Qualifier("mysqlJdbc")
    private JdbcTemplate mysqlJdbc;

    public User getByEmail(String email) throws EmptyResultDataAccessException {
        String sql = "SELECT userId, password, role FROM User WHERE email = ?";
        return (User) mysqlJdbc.queryForObject(sql, new Object[]{email}, new BeanPropertyRowMapper(User.class));
    }

    public User getByUserId(int userId) throws EmptyResultDataAccessException {
        String sql = "SELECT userId, email, password, name, hash, role, lastLogin FROM User WHERE userId = ?";
        return (User) mysqlJdbc.queryForObject(sql, new Object[]{userId}, new BeanPropertyRowMapper(User.class));
    }


    public void setUser(String name, String email, String password, String hash) {
        String sql = "INSERT INTO User (name, email, password, hash) VALUES (?, ?, ?, ?, NOW())";
        mysqlJdbc.update(sql, new Object[]{name, email, password, hash});
    }
}
