package com.smile.formation.kafka.producer.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.smile.formation.kafka.producer.config.properties.FormationKafkaProperties;
import com.smile.formation.kafka.schema.OrdreAbonnementAvro;
import com.smile.formation.kafka.schema.OrdreReservationAvro;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaProducer {

    private KafkaTemplate<String, OrdreReservationAvro> reservationKafkaTemplate;

    private KafkaTemplate<String, OrdreAbonnementAvro> abonnementKafkaTemplate;

    @Autowired
    private FormationKafkaProperties smartAccessKafkaProperties;

    public ListenableFuture<SendResult<String,OrdreReservationAvro>> sendMessage(OrdreReservationAvro ordreReservation) {
        log.info(String.format("Produce  -> %s to topic  %s ", ordreReservation, smartAccessKafkaProperties.getTopic().getReservation().getName()));
        return reservationKafkaTemplate.send(smartAccessKafkaProperties.getTopic().getReservation().getName(), ordreReservation.getIdClient().toString(), ordreReservation);
    }

    public ListenableFuture<SendResult<String,OrdreAbonnementAvro>> sendMessage(OrdreAbonnementAvro ordreAbonnement) {
        log.info(String.format("Produce  -> %s to topic  %s ", ordreAbonnement, smartAccessKafkaProperties.getTopic().getAbonnement().getName()));
        return abonnementKafkaTemplate.send(smartAccessKafkaProperties.getTopic().getAbonnement().getName(), ordreAbonnement.getIdClient().toString(), ordreAbonnement);
    }
}
