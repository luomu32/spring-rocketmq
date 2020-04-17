package io.ray.rocketmq.spring;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by luomu32 on 2017/4/18.
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
@Import(RocketMQRegistrar.class)
public @interface EnableRocketMQ {

    String consumerGroup() default "";

    String nameSrvAddr();
}
