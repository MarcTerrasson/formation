package com.smile.formation.kafka.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;

import com.smile.formation.kafka.consumer.config.properties.Abonnement;
import com.smile.formation.kafka.consumer.config.properties.FormationKafkaProperties;
import com.smile.formation.kafka.consumer.config.properties.Reservation;
import com.smile.formation.kafka.schema.OrdreAbonnementAvro;
import com.smile.formation.kafka.schema.OrdreReservationAvro;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class KafkaConfig {

    public static final String SCHEMA_REGISTRY_URL_NAME = "schema.registry.url";
    public static final String SPECIFIC_AVRO_READER_NAME = "specific.avro.reader";
    private static final String CLEANUP_POLICY_CONFIG = "delete";
    private static final String AUTO_OFFSET_RESET_CONFIG ="earliest";

    @Autowired
    private FormationKafkaProperties formationKafkaProperties;

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, formationKafkaProperties.getBootstrapServers());

        return new KafkaAdmin(configs);
    }

    @Bean
    public ConsumerFactory<String, OrdreAbonnementAvro> abonnmentConsumerFactory() {
        HashMap<String, Object> configuration = getCommonConsumerConfig();
        configuration.put(ConsumerConfig.GROUP_ID_CONFIG, formationKafkaProperties.getTopic().getAbonnement().getGroupId());

        return new DefaultKafkaConsumerFactory<>(configuration);
    }

    @Bean
    public ConsumerFactory<String, OrdreReservationAvro> reservationConsumerFactory() {
        HashMap<String, Object> configuration = getCommonConsumerConfig();
        configuration.put(ConsumerConfig.GROUP_ID_CONFIG, formationKafkaProperties.getTopic().getReservation().getGroupId());

        return new DefaultKafkaConsumerFactory<>(configuration);
    }

    @Bean(name = "abonnementContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, OrdreAbonnementAvro> abonnementContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrdreAbonnementAvro> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(abonnmentConsumerFactory());

        return factory;
    }

    @Bean(name = "reservationContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, OrdreReservationAvro> reservationContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrdreReservationAvro> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reservationConsumerFactory());

        return factory;
    }

    private HashMap<String, Object> getCommonConsumerConfig() {
        HashMap<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, formationKafkaProperties.getBootstrapServers());
        configProps.put(SCHEMA_REGISTRY_URL_NAME, formationKafkaProperties.getSchemaRegistryUrl());
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_CONFIG);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        configProps.put(SPECIFIC_AVRO_READER_NAME, "true");
        return configProps;
    }
}
