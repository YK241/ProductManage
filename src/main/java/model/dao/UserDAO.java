package model.dao;

import model.entity.UserBean;

public class UserDAO {

    public UserBean login(String username, String password) {

        if ("admin".equals(username) && "1234".equals(password)) {
            UserBean user = new UserBean();
            user.setUsername(username);
            return user;
        }

        return null;
    }
}
