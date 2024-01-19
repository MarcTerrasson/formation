package com.smile.formation.kafka.consumer.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "formation.kafka")
@Getter
@Setter
public class FormationKafkaProperties {

    private String bootstrapServers;

    private String schemaRegistryUrl;

    @NestedConfigurationProperty
    private Topic topic;
}
