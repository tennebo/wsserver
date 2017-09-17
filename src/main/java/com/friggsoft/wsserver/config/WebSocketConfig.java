package com.friggsoft.wsserver.config;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * Configure STOMP over WebSockets.
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /** Spring Boot application properties. */
    private final WebSocketProperties properties;

    /** Constructor for injecting application properties. */
    WebSocketConfig(WebSocketProperties properties) {
        this.properties = properties;
    }

    // TODO: This should be a bean
    private static TaskScheduler getTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

        taskScheduler.setPoolSize(2);
        taskScheduler.setWaitForTasksToCompleteOnShutdown(false);
        taskScheduler.afterPropertiesSet();

        return taskScheduler;
    }

    /**
     * Configure an in-memory broker with a task scheduler.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        log.info(
                "Registering application destination prefixes: {}",
                Arrays.toString(properties.getAppDestinationPrefixes()));
        registry.setApplicationDestinationPrefixes(properties.getAppDestinationPrefixes());

        log.info(
                "Registering broker destination prefixes: {}",
                Arrays.toString(properties.getBrokerDestinationPrefixes()));
        registry.enableSimpleBroker(properties.getBrokerDestinationPrefixes())
                .setTaskScheduler(getTaskScheduler());
    }

    /**
     * Register an endpoint using the STOMP messaging protocol as a sub-protocol.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        log.info(
                "Registering STOMP endpoint '{}' {} SockJS",
                properties.getWebsocketEndpoint(),
                properties.isUseSockJs()? "with" : "without");

        val registration = registry.addEndpoint(properties.getWebsocketEndpoint())
                .setAllowedOrigins("*");

        // Support SockJS?
        if (properties.isUseSockJs()) {
            registration.withSockJS();
        }
    }
}
