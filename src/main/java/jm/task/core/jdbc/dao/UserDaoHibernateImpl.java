package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getHibernateSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.createNativeQuery("CREATE TABLE IF NOT EXISTS users" +
                                "(id INT primary key auto_increment," +
                                "name VARCHAR(50)," +
                                "lastName VARCHAR(50)," +
                                "age TINYINT)")
                        .executeUpdate();
                session.getTransaction().commit();
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                if (session != null) {
                    session.getTransaction().rollback();
                }
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.createNativeQuery("DROP TABLE IF EXISTS users")
                        .executeUpdate();
                session.getTransaction().commit();
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                if (session != null)
                    session.getTransaction().rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.save(new User(name, lastName, age));
                session.getTransaction().commit();
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                if (session != null)
                    session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.createQuery("DELETE User WHERE id =:id").setParameter("id", id).executeUpdate();
                session.getTransaction().commit();
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                if (session != null)
                    session.getTransaction().rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            try {
                session.getTransaction();
                userList = session.createQuery("FROM User", User.class).list();
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                if (session != null)
                    session.getTransaction().rollback();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (var session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.createNativeQuery("TRUNCATE TABLE users;").executeUpdate();
                session.getTransaction().commit();
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
                if (session != null)
                    session.getTransaction().rollback();
            }
        }
    }
}
