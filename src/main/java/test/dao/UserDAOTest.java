package test.dao;

import static org.junit.Assert.*;
import org.junit.Test;
import model.dao.UserDAO;
import model.entity.UserBean;

public class UserDAOTest {

    @Test
    public void 正しいIDとパスワードならユーザが取得できる() {
        UserDAO dao = new UserDAO();

        UserBean user = dao.login("admin", "1234");

        assertNotNull(user);
        assertEquals("admin", user.getUsername());
    }

    @Test
    public void 間違ったパスワードならnullになる() {
        UserDAO dao = new UserDAO();

        UserBean user = dao.login("admin", "9999");

        assertNull(user);
    }
}
