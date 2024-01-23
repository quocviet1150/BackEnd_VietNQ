package com.example.cafe.Component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class LoginCounter implements ApplicationContextAware {
    private int loginCount = 0;
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void incrementLoginCount() {
        loginCount++;
    }


    public static LoginCounter getLoginCounterBean() {
        return context.getBean(LoginCounter.class);
    }
}
