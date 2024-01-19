package com.smile.formation.kafka.producer.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter
@Setter
public class Topic {
    @NestedConfigurationProperty
    private Reservation reservation;

    @NestedConfigurationProperty
    private Abonnement abonnement;
}
