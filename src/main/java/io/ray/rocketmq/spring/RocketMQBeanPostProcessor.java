//package io.ray.rocketmq.spring;
//
//import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
//import com.alibaba.rocketmq.client.consumer.listener.MessageListener;
//import com.alibaba.rocketmq.client.exception.MQClientException;
//import org.springframework.aop.support.AopUtils;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//
//import java.lang.reflect.Method;
//
///**
// * Created by luomu32 on 2017/3/26.
// */
//public class RocketMQBeanPostProcessor implements BeanPostProcessor {
//
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//
//        Class<?> targetClass=AopUtils.getTargetClass(bean);
//        Method[] methods=targetClass.getDeclaredMethods();
//
//        for(Method method:methods){
//            if(method.isAnnotationPresent(RocketMQListener.class)){
//                DefaultMQPushConsumer consumer=new DefaultMQPushConsumer();
//
//                consumer.registerMessageListener(new MessageListener() {
//
//                });
//                try {
//                    consumer.start();
//                } catch (MQClientException e) {
//                    throw new IllegalStateException("无法连接",e);
//                }
//            }
//        }
//
//        return bean;
//    }
//}
