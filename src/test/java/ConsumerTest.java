import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * Created by luomu32 on 2017/4/18.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class ConsumerTest {

    @Autowired
    private UserServiceImpl userService;
    @Test
    public void test() throws InterruptedException {


        userService.userBuySomething();
        TimeUnit.SECONDS.sleep(5);


    }

}
