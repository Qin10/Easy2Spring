import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import cn.zjutleo.pojo.User;
import cn.zjutleo.service.UserService;

/**
 * @author : Qin Zhenghan
 * @date : Created in 2021/5/17
 * @description: 测试类
 */
@Slf4j
public class UserTest {

    @Test
    public void testUserService() {
        // 加载xml配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 通过反射机制获取userService对象
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.doSomething();
    }

    @Test
    public void testAssemble() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) applicationContext.getBean("user");
        log.info(user.toString());
    }
}
