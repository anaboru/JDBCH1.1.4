package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    public void createUsersTable() {
        try(Connection connection = Util.getJDBCConnection();
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users" +
                                                                        "(id INT primary key auto_increment," +
                                                                        "name VARCHAR(45)," +
                                                                        "lastName VARCHAR(50)," +
                                                                        "age TINYINT)")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void dropUsersTable() {
        try(Connection connection = Util.getJDBCConnection();
            PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS users")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.getJDBCConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users " +
                                                                        "(name, lastName, age)" +
                                                                        "VALUES (?, ?, ?);")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeUserById(long id) {
        try (Connection connection = Util.getJDBCConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users " +
                     "WHERE id = ?;")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = Util.getJDBCConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public void cleanUsersTable() {
        try(Connection connection = Util.getJDBCConnection();
            PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE users;")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}