package com.smile.formation.kafka.producer.config.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reservation {

    private String name;

    private int nbPartition;

    private int replicas;

    private String retentionMode;

    private long retention;

    private int minInsyncReplicas;

    private String acks;
}
