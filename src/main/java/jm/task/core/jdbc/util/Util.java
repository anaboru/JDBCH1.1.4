package jm.task.core.jdbc.util;

import com.mysql.cj.conf.ConnectionPropertiesTransform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import

public class Util {
    private static volatile Util instance;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/birbz";
    private static Connection connect;

    // реализуйте настройку соеденения с БД
    private Util() {
    }
    public static Util getUtil() {
        Util localInstance = instance;
        if (localInstance == null) {
            synchronized (Util.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Util();
                }
            }
        }
        return localInstance;
    }
    public static Connection getUtilConnection() {
        try {
            connect = DriverManager
                .getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }
}
