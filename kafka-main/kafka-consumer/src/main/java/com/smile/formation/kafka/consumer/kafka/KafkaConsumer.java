package com.smile.formation.kafka.consumer.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.smile.formation.kafka.consumer.logger.OrdreService;
import com.smile.formation.kafka.schema.OrdreAbonnementAvro;
import com.smile.formation.kafka.schema.OrdreReservationAvro;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumer {

    @Autowired
    private OrdreService ordreService;

    @KafkaListener(topics = "#{formationKafkaProperties.topic.abonnement.name}", containerFactory = "abonnementContainerFactory")
    public void consume(OrdreAbonnementAvro ordre) {
        doConsumeAbonnement(ordre,0);
    }

    @KafkaListener(topics = "#{formationKafkaProperties.topic.reservation.name}", containerFactory = "reservationContainerFactory")
    public void consume(OrdreReservationAvro reservationFullAvro) {
        doConsumeReservation(reservationFullAvro,0);

    }

    private void doConsumeReservation(OrdreReservationAvro ordre, Integer nbAttempts) {
        String typeOperation = ordre.getTypeOperation();
        
        switch (TypeOperation.valueOf(typeOperation)) {
		case CREATE: {
			ordreService.create(ordre);
			break;
		}
		case UPDATE: {
			ordreService.update(ordre);
			break;
		}
		case DELETE: {
			ordreService.delete(ordre);
			break;
		}
		case UPDATE_CODE_ACCES:
			log.error("Operation non supportée sur réservation : {}", typeOperation);
			break;
		}
    }

    private void doConsumeAbonnement(OrdreAbonnementAvro ordre, Integer nbAttempts) {
        String typeOperation = ordre.getTypeOperation();
        
        switch (TypeOperation.valueOf(typeOperation)) {
		case CREATE: {
			ordreService.create(ordre);
			break;
		}
		case UPDATE: {
			ordreService.update(ordre);
			break;
		}
		case DELETE: {
			ordreService.delete(ordre);
			break;
		}
		case UPDATE_CODE_ACCES:
			log.error("Operation non supportée sur abonnement : {}", typeOperation);
			break;
		}
    }
}
