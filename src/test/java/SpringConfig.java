import io.ray.rocketmq.spring.EnableRocketMQ;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by luomu32 on 2017/4/18.
 */
@Configuration
@EnableRocketMQ(nameSrvAddr = "127.0.0.1:9876")

public class SpringConfig {

    @Bean
    public UserServiceImpl userService(){
        return new UserServiceImpl();
    }
}
