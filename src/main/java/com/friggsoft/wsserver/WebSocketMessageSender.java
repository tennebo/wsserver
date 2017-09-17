package com.friggsoft.wsserver;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.friggsoft.wsserver.config.WebSocketProperties;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Sends messages to registered subscribers.
 */
@Slf4j
@Service
@EnableScheduling
public final class WebSocketMessageSender {

    @Getter
    @ToString
    private static final class PulseMessage {
        private final String message;

        PulseMessage(String message) {
            this.message = message;
        }
    }

    /**
     * Prefix for pub-sub destinations that target the message broker.
     */
    private final String topicDestinationPrefix;

    private static final String HEARTBEAT_DESTINATION = "/pulse";

    private final SimpMessagingTemplate msgTemplate;

    WebSocketMessageSender(WebSocketProperties properties, SimpMessagingTemplate msgTemplate) {
        this.topicDestinationPrefix = properties.getTopicPrefix();
        this.msgTemplate = msgTemplate;
    }

    /**
     * Send details of an event.
     */
    private void send(String destination, Object payload) {
        String eventDestination = topicDestinationPrefix + destination;
        log.info("Sending {} to {}", payload.toString(), eventDestination);
        msgTemplate.convertAndSend(eventDestination, payload);
    }

    /**
     * Send a heartbeat every few seconds.
     */
    @Scheduled(fixedRate = 11_000)
    public void pulse() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        String message = "Heartbeat pulse at: " + now;
        PulseMessage msg = new PulseMessage(message);
        send(HEARTBEAT_DESTINATION, msg);
    }
}
