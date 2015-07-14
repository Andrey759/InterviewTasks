package ru.bingo.dao;

import org.springframework.stereotype.Repository;
import ru.bingo.model.User;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.bingo.utils.HibernateUtil;

@Repository("usersDAO")
public class UserDAOImpl implements UserDAO {

    private static Logger log = Logger.getLogger(UserDAOImpl.class);

    @Override
    public User getById(int id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            tx.commit();
            return user;
        } catch (Throwable ex) {
            if (tx != null) tx.rollback();
            log.error("UsersDAOImpl: can't get user by id", ex);
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public User getByUserName(String userName) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from User u where u.username = :userName");
            query.setString("userName", userName);
            User user = (User) query.uniqueResult();
            tx.commit();
            return user;
        } catch (Throwable ex) {
            if (tx != null) tx.rollback();
            log.error("UsersDAOImpl: can't get user by userName", ex);
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public User getByUserNameAndPass(String userName, String pass) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("from User u where u.username = :userName and u.pass = SHA(:pass)");
            query.setString("userName", userName);
            query.setString("pass", pass);
            User user = (User) query.uniqueResult();
            tx.commit();
            return user;
        } catch (Throwable ex) {
            if (tx != null) tx.rollback();
            log.error("UsersDAOImpl: can't get user by userName", ex);
        } finally {
            session.close();
        }
        return null;
    }

}