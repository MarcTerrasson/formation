package com.smile.formation.kafka.producer.config;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.smile.formation.kafka.producer.config.properties.Abonnement;
import com.smile.formation.kafka.producer.config.properties.Reservation;
import com.smile.formation.kafka.producer.config.properties.FormationKafkaProperties;
import com.smile.formation.kafka.schema.OrdreAbonnementAvro;
import com.smile.formation.kafka.schema.OrdreReservationAvro;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    public static final String SCHEMA_REGISTRY_URL_NAME = "schema.registry.url";

    @Autowired
    private FormationKafkaProperties smartAccessKafkaProperties;

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, smartAccessKafkaProperties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

    @Bean
    public ProducerFactory<String, OrdreReservationAvro> reservationProducerFactory() {

        Reservation reservation = smartAccessKafkaProperties.getTopic().getReservation();

        HashMap<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, smartAccessKafkaProperties.getBootstrapServers());
        configProps.put(SCHEMA_REGISTRY_URL_NAME, smartAccessKafkaProperties.getSchemaRegistryUrl());
        configProps.put(ProducerConfig.ACKS_CONFIG, reservation.getAcks());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ProducerFactory<String, OrdreAbonnementAvro> abonnementProducerFactory() {

        Abonnement abonnement = smartAccessKafkaProperties.getTopic().getAbonnement();

        HashMap<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, smartAccessKafkaProperties.getBootstrapServers());
        configProps.put(SCHEMA_REGISTRY_URL_NAME, smartAccessKafkaProperties.getSchemaRegistryUrl());
        configProps.put(ProducerConfig.ACKS_CONFIG, abonnement.getAcks());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, OrdreAbonnementAvro> abonnementKafkaTemplate() {
        return new KafkaTemplate<>(abonnementProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, OrdreReservationAvro> reservationKafkaTemplate() {
        return new KafkaTemplate<>(reservationProducerFactory());
    }

    @Bean
    public NewTopic reservation() {
        Reservation reservation = smartAccessKafkaProperties.getTopic().getReservation();

        return TopicBuilder.name(reservation.getName())
            .partitions(reservation.getNbPartition())
            .replicas(reservation.getReplicas())
            .config(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, Integer.toString(reservation.getMinInsyncReplicas()))
            .config(TopicConfig.CLEANUP_POLICY_CONFIG, reservation.getRetentionMode())
            .config(TopicConfig.RETENTION_MS_CONFIG, Long.toString(reservation.getRetention()))
            .build();
    }

    @Bean
    public NewTopic abonnement() {
        Abonnement abonnement = smartAccessKafkaProperties.getTopic().getAbonnement();

        return TopicBuilder.name(abonnement.getName())
            .partitions(abonnement.getNbPartition())
            .replicas(abonnement.getReplicas())
            .config(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, Integer.toString(abonnement.getMinInsyncReplicas()))
            .config(TopicConfig.CLEANUP_POLICY_CONFIG, abonnement.getRetentionMode())
            .config(TopicConfig.RETENTION_MS_CONFIG, Long.toString(abonnement.getRetention()))
            .build();
    }
}
