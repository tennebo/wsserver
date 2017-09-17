// Log config for logback

appender('console', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss} %highlight(%-5level) %cyan([%-9thread]) %logger{20} - %msg%n"
        immediateFlush = false
    }
    // In newer versions, this goes here:
    // immediateFlush = false
}

// Set logging levels
logger("com.friggsoft", INFO)
logger("org.springframework", WARN)
logger("org.springframework.integration.channel.QueueChannel", DEBUG)
logger("org.springframework.batch.integration.partition.MessageChannelPartitionHandler", DEBUG)
logger("org.springframework.jms.support.JmsAccessor", INFO)
logger("org.springframework.integration.jms.ChannelPublishingJmsMessageListener", DEBUG)
logger("org.springframework.integration.context.IntegrationObjectSupport", DEBUG)

logger("org.apache.activemq.transport.WireFormatNegotiator", INFO)
logger("org.apache.activemq.transport.InactivityMonitor", INFO)
logger("org.apache.activemq.transport.AbstractInactivityMonitor", INFO)

logger("org.springframework.web.socket.sockjs.client.SockJsClient", DEBUG)
logger("org.springframework.web.socket.messaging", TRACE)
logger("org.springframework.web.socket", TRACE)

// Setup a console logger
root(INFO, ['console'])
