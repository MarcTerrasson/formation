package com.smile.formation.kafka.producer.producer;

import org.springframework.stereotype.Service;

import com.smile.formation.kafka.producer.kafka.KafkaProducer;
import com.smile.formation.kafka.producer.kafka.TypeOperation;
import com.smile.formation.kafka.schema.OrdreAbonnementAvro;
import com.smile.formation.kafka.schema.OrdreReservationAvro;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProducerServiceImpl implements ProducerService {

	private KafkaProducer service;

	@Override
	public void create(ReservationDTO dto) {
		OrdreReservationAvro ordre = new OrdreReservationAvro();
		ordre.setTypeOperation(TypeOperation.CREATE.name());
		service.sendMessage(ordre);
	}

	@Override
	public void update(ReservationDTO dto) {
		OrdreReservationAvro ordre = new OrdreReservationAvro();
		ordre.setTypeOperation(TypeOperation.UPDATE.name());
		service.sendMessage(ordre);
	}

	@Override
	public void create(AbonnementDTO dto) {
		OrdreAbonnementAvro ordre = new OrdreAbonnementAvro();
		ordre.setTypeOperation(TypeOperation.CREATE.name());
		service.sendMessage(ordre);
	}

}
