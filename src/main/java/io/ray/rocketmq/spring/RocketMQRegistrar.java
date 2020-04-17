package io.ray.rocketmq.spring;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by luomu32 on 2017/4/18.
 */
@Configuration
public class RocketMQRegistrar implements ImportBeanDefinitionRegistrar,BeanPostProcessor {

    private String nameSrvAddr;
    private String defaultConsumerGroup;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

       AnnotationAttributes enableRocketMQAttr=  AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableRocketMQ.class.getName(), false));
       nameSrvAddr= enableRocketMQAttr.getString("nameSrvAddr");
        defaultConsumerGroup=enableRocketMQAttr.getString("consumerGroup");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {

        Class<?> targetClass= AopUtils.getTargetClass(bean);
        Method[] methods=targetClass.getDeclaredMethods();
        try {
        for(final Method method:methods){
            if(method.isAnnotationPresent(RocketMQListener.class)){
                RocketMQListener listenerAnnotation=method.getAnnotation(RocketMQListener.class);
                if(StringUtils.isEmpty(defaultConsumerGroup)&&StringUtils.isEmpty(listenerAnnotation.group()))
                    throw new IllegalStateException("Can not find RocketMQ Consumer Group Name");

                DefaultMQPushConsumer consumer=new DefaultMQPushConsumer(StringUtils.isEmpty(listenerAnnotation.group())?defaultConsumerGroup:listenerAnnotation.group());
                consumer.subscribe(listenerAnnotation.topic(),listenerAnnotation.tags());
                consumer.registerMessageListener(new MessageListenerConcurrently() {
                    @Override
                    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                        Object[] args=new Object[method.getParameterTypes().length];
                        try {
                            method.invoke(bean,args);
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }catch (Exception e){
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                    }
                });

                    consumer.start();

            }
        }} catch (MQClientException e) {

            throw new IllegalStateException("Can not connect to "+nameSrvAddr);
        }

        return bean;
    }
}
