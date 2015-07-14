package ru.bingo.dao;

import org.hibernate.HibernateException;
import ru.bingo.model.User;

public interface UserDAO {

    User getById(int id);

    User getByUserName(String userName);

    User getByUserNameAndPass(String userName, String pass);

}
