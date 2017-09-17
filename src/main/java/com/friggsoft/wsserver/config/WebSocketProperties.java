package com.friggsoft.wsserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuration properties for STOMP over Websocket support.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "friggsoft.websocket", ignoreUnknownFields = false)
public class WebSocketProperties {

    /**
     * Whether to enable SockJS fallback options.
     */
    private boolean useSockJs;

    /**
     * Mapping path for the WebSocket endpoint.
     */
    private String websocketEndpoint;

    /**
     * Prefix for publish/subscribe message destinations.
     */
    private String topicPrefix;

    /**
     * Prefix for queue (point-to-point) message destinations.
     */
    private String queuePrefix;

    /**
     * Prefixes for destinations that target the message broker.
     */
    private String[] brokerDestinationPrefixes;

    /**
     * Prefixes for destinations that target application annotated methods
     * (i.e. not the message broker).
     */
    private String[] appDestinationPrefixes;
}
