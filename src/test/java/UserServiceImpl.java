import io.ray.rocketmq.spring.RocketMQListener;

/**
 * Created by luomu32 on 2017/4/18.
 */

public class UserServiceImpl {



    @RocketMQListener(topic = "USER")
    public void userBuySomething(){
        System.out.println("用户购买了某些东西");
    }
}
