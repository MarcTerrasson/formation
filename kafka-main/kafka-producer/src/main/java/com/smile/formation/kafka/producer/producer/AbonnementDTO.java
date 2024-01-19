package com.smile.formation.kafka.producer.producer;

import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.With;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class AbonnementDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Clé primaire de l'objet dans le SMA")
    private Long idAbonnement;

    private String idContratCrm;

    private String idAboCrm;

    private String idAboEffia;

    private Integer nbPlace;

    private String statut;

    private OffsetDateTime dateDebutValidite;

    private OffsetDateTime dateFinValidite;

    private String codePoche;

    private String codeProduit;

    private Long idClient;

    private String entiteComptable;
}
