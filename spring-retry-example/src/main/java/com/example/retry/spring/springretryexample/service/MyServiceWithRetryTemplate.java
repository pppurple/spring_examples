package com.example.retry.spring.springretryexample.service;

import com.example.retry.spring.springretryexample.service.MyService.User;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import static com.example.retry.spring.springretryexample.service.MyService.*;

@Service
public class MyServiceWithRetryTemplate {
    public void execute() throws Throwable {
        RetryTemplate template = new RetryTemplate();

        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
            System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
            return getUserUnsuccessfully();
        });

        System.out.println(user);
    }

    public void executeWithRetryContext() throws Throwable {
        RetryTemplate template = new RetryTemplate();

        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
            System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
            return getUserUnsuccessfully();
        });

        System.out.println(user);
    }

    public void executeWithRetryPolicy() throws Throwable {
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(5);

        template.setRetryPolicy(retryPolicy);

        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
            System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
            return getUserUnsuccessfully();
        });

        System.out.println(user);
    }

    public void executeWithInitialInterval() throws Throwable {
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(5);

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1_000L);

        template.setRetryPolicy(retryPolicy);
        template.setBackOffPolicy(backOffPolicy);

        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
            System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
            return getUserUnsuccessfully();
        });

        System.out.println(user);
    }

    public void executeWithMultiplier() throws Throwable {
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(5);

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1_000L);
        backOffPolicy.setMultiplier(1.0);

        template.setRetryPolicy(retryPolicy);
        template.setBackOffPolicy(backOffPolicy);

        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
            System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
            return getUserUnsuccessfully();
        });

        System.out.println(user);
    }

    public void executeWithMaxInternal() throws Throwable {
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(5);

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(2_000L);
        backOffPolicy.setMaxInterval(4_000L);

        template.setRetryPolicy(retryPolicy);
        template.setBackOffPolicy(backOffPolicy);

        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
            System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
            return getUserUnsuccessfully();
        });

        System.out.println(user);
    }

    public void executeWithRecovery() throws Throwable {
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(5);

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1_000L);
        backOffPolicy.setMultiplier(1.0);

        template.setRetryPolicy(retryPolicy);
        template.setBackOffPolicy(backOffPolicy);

        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
                    System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
                    return getUserUnsuccessfully();
                },
                retryContext -> {
                    System.out.println("recovered!");
                    return getUserSuccessfully();
                });

        System.out.println(user);
    }

    public void executeWithRetryableExceptions() throws Throwable {
        RetryTemplate template = new RetryTemplate();

        // specify exceptions that are retryable
        Map<Class<? extends Throwable>, Boolean> retryableExceptions =
                Collections.singletonMap(UserNotFoundException.class, true);
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(5, retryableExceptions);

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1_000L);
        backOffPolicy.setMultiplier(1.0);

        template.setRetryPolicy(retryPolicy);
        template.setBackOffPolicy(backOffPolicy);

        // will retry
        System.out.println("will retry");
        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
                    System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
                    throw new UserNotFoundException();
                },
                retryContext -> {
                    System.out.println("recovered!");
                    return getUserSuccessfully();
                });
        System.out.println(user);

        // will not retry
        System.out.println("will not retry");
        template.execute((RetryCallback<User, Throwable>) retryContext -> {
                    System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
                    throw new UnexpectedException();
                },
                retryContext -> {
                    System.out.println("recovered!");
                    return getUserSuccessfully();
                });
    }

    public void executeWithListeners() throws Throwable {
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy policy = new SimpleRetryPolicy(5);

        RetryListener listener = new RetryListener() {
            @Override
            public <T, E extends Throwable> boolean open(RetryContext retryContext, RetryCallback<T, E> retryCallback) {
                System.out.println("open!!");
                return true;
            }

            @Override
            public <T, E extends Throwable> void close(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
                System.out.println("close.");
            }

            @Override
            public <T, E extends Throwable> void onError(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
                System.out.println("error occurred!");
            }
        };

        template.setRetryPolicy(policy);
        template.setListeners(new RetryListener[]{listener});

        User user = template.execute((RetryCallback<User, Throwable>) retryContext -> {
                    System.out.println(LocalDateTime.now() + " retry=" + retryContext.getRetryCount());
                    return getUserUnsuccessfully();
                },
                retryContext -> {
                    System.out.println("recovered!");
                    return getUserSuccessfully();
                });

        System.out.println(user);
    }

    public User getUserSuccessfully() {
        return new User("Bobby", 20);
    }

    public User getUserUnsuccessfully() throws UserNotFoundException {
        throw new UserNotFoundException();
    }
}
