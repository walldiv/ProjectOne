import com.ex.dao.UserDAO;
import com.ex.model.User;
import com.ex.service.CoachService;
import com.ex.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class UserTests {
    User user = new User(1, "dan", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", "walldiv@gmail.com", "admin");

    @Mock
    UserDAO dao;

    @InjectMocks
    UserService service;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void init() {
        service = new UserService(dao);
//        service = new UserService();
    }

    @Test
    public void hashPassword() throws Exception{
        String hashedPass = service.hashPassword("temp");
        System.out.printf("hashPassword - HASHED PASSWORD IS: %s", hashedPass);
        Assert.assertTrue("NO PASSWORD HASHED", hashedPass.length() > 0);
    }

    @Test
    public void loginUser() throws Exception {
        String hashedPass = service.hashPassword("password");
        Mockito.when(dao.loginUser("dan", hashedPass)).thenReturn(user);
        User mockedUser = service.loginUser("dan", hashedPass);
//        System.out.println(user.toString());
//        Assert.assertEquals("USERS DONT MATCH", user, mockedUser);
    }

    @Test
    public void addUser() throws Exception {
        String hashedPass = service.hashPassword("password");
        User tmp = new User(-1, "jolito", hashedPass, "jolito@email.com", "user");
        Mockito.doNothing().when(dao).addUser(tmp);
        boolean success = service.addUser(tmp);
        Assert.assertTrue("addUser - USER NOT ADDED", success);
    }
}
