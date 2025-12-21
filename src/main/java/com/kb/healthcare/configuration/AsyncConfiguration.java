package com.kb.healthcare.configuration;

import com.kb.healthcare.exception.DefaultAsyncExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static java.util.concurrent.Executors.newFixedThreadPool;

@Slf4j
@Configuration
@EnableAsync
class AsyncConfiguration implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        return newFixedThreadPool(1);
    }

    @Bean
    public ThreadPoolTaskExecutor activityDomainEventExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(20);
        executor.setThreadNamePrefix("ACTIVITY-DOMAIN-EVENT-EXECUTOR_");
        executor.initialize();

        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new DefaultAsyncExceptionHandler();
    }

}
