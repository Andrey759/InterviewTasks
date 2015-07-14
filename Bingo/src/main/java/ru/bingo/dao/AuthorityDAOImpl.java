package ru.bingo.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import ru.bingo.model.Authority;
import ru.bingo.utils.HibernateUtil;

@Service
public class AuthorityDAOImpl implements AuthorityDAO {

    private static Logger log = Logger.getLogger(AuthorityDAOImpl.class);

    @Override
    public Authority getById(int id) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Authority authority = (Authority) session.get(Authority.class, id);
            tx.commit();
            return authority;
        } catch (Throwable ex) {
            if (tx != null) tx.rollback();
            log.error("UsersDAOImpl: can't get user by id", ex);
        } finally {
            session.close();
        }
        return null;
    }

}
