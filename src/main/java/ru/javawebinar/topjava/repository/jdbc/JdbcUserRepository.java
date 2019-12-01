package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;

@Transactional(readOnly = true)
@Repository
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final TransactionForJdbcRepository transactionForJdbcRepository;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, TransactionForJdbcRepository transactionForJdbcRepository) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.transactionForJdbcRepository = transactionForJdbcRepository;
    }

    @Override
    public User save(User user) {
        return transactionForJdbcRepository.<User>transactionMethod(() -> {
            BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
            if (user.isNew()) {
                Number newKey = insertUser.executeAndReturnKey(parameterSource);
                user.setId(newKey.intValue());
                jdbcTemplate.batchUpdate("INSERT INTO user_roles (role,user_id) VALUES (?,?)",
                        user.getRoles(),
                        user.getRoles().size(), (ps, role) -> {
                            ps.setString(1, role.name());
                            ps.setInt(2, user.getId());
                        });
            } else if (namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0 && namedParameterJdbcTemplate.update("UPDATE user_roles SET role=:role WHERE user_id=:id", parameterSource) == 0) {
                return null;
            } else {
                jdbcTemplate.batchUpdate("UPDATE user_roles SET role=? WHERE user_id=?",
                        user.getRoles(),
                        user.getRoles().size(), (ps, role) -> {
                            ps.setString(1, role.name());
                            ps.setInt(2, user.getId());
                        });
            }
            return user;
        });
    }

    @Override
    public boolean delete(int id) {
        return transactionForJdbcRepository.<Boolean>transactionMethod(() -> jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0);
    }

    @Override
    public User get(int id) {
        Map<Integer, User> userMap = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM users u INNER JOIN user_roles r ON r.user_id = u.id WHERE u.id = ?", getResultSet(userMap), id);
        return DataAccessUtils.singleResult(userMap.values());
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        Map<Integer, User> userMap = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM users u INNER JOIN user_roles r ON r.user_id = u.id WHERE email=?", getResultSet(userMap), email);
        return DataAccessUtils.singleResult(userMap.values());
    }

    @Override
    public List<User> getAll() {
        Map<Integer, User> userMap = new LinkedHashMap<>();
        jdbcTemplate.query("SELECT * FROM users u INNER JOIN user_roles r ON u.id = r.user_id ORDER BY name, email", getResultSet(userMap));
        return new ArrayList<>(userMap.values());
    }

    private RowCallbackHandler getResultSet(Map<Integer, User> userMap) {
        return rs -> {
            do {
                int userId = rs.getInt("id");
                User user;
                final String role = rs.getString("role");
                if (role != null && (user = userMap.get(userId)) != null) {
                    user.getRoles().add(Role.valueOf(role));
                } else {
                    user = new User(
                            userId,
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getInt("calories_per_day"),
                            rs.getBoolean("enabled"),
                            rs.getDate("registered"),
                            role != null ? Collections.singletonList(Role.valueOf(role)) : EnumSet.noneOf(Role.class));
                    userMap.put(userId, user);
                }
            } while (rs.next());
        };
    }
}
