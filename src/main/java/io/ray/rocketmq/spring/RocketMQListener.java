package io.ray.rocketmq.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by luomu32 on 2017/4/18.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface RocketMQListener {


    String topic();
    String tags() default "*";
    String group() default "";
}
