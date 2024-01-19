package com.smile.formation.kafka.consumer.logger;

import org.springframework.stereotype.Service;

import com.smile.formation.kafka.schema.OrdreAbonnementAvro;
import com.smile.formation.kafka.schema.OrdreReservationAvro;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrdreServiceImpl implements OrdreService {

	@Override
	public void create(OrdreAbonnementAvro ordre) {
		log.info("{}", ordre);
	}

	@Override
	public void update(OrdreAbonnementAvro ordre) {
		log.info("{}", ordre);
	}

	@Override
	public void create(OrdreReservationAvro ordre) {
		log.info("{}", ordre);
	}

	@Override
	public void delete(OrdreAbonnementAvro ordre) {
		log.info("{}", ordre);
	}

	@Override
	public void update(OrdreReservationAvro ordre) {
		log.info("{}", ordre);
	}

	@Override
	public void delete(OrdreReservationAvro ordre) {
		log.info("{}", ordre);
	}
}
