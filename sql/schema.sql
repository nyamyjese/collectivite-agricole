
CREATE TYPE specialite_agricole AS ENUM (
    'MARAICHAGE',
    'CEREALES',
    'ELEVAGE_BOVIN',
    'ELEVAGE_AVICOLE',
    'APICULTURE',
    'ARBORICULTURE',
    'RIZICULTURE',
    'HORTICULTURE',
    'PISCICULTURE',
    'CULTURES_INDUSTRIELLES'
    );

CREATE TYPE statut_collectivite AS ENUM (
    'EN_ATTENTE',
    'EN_COURS_DE_VALIDATION',
    'AUTORISEE',
    'REFUSEE',
    'ACTIVE',
    'SUSPENDUE'
    );

CREATE TYPE genre AS ENUM (
    'MASCULIN',
    'FEMININ'
    );

CREATE TYPE statut_membre AS ENUM (
    'ACTIF',
    'DEMISSIONNE',
    'SUSPENDU'
    );

CREATE TYPE poste AS ENUM (
    'PRESIDENT',
    'PRESIDENT_ADJOINT',
    'TRESORIER',
    'SECRETAIRE',
    'MEMBRE_CONFIRME',
    'MEMBRE_JUNIOR'
    );

CREATE TYPE type_cotisation AS ENUM (
    'PERIODIQUE_MENSUELLE',
    'PERIODIQUE_ANNUELLE',
    'PONCTUELLE'
    );

CREATE TYPE mode_paiement AS ENUM (
    'ESPECE',
    'VIREMENT_BANCAIRE',
    'MOBILE_MONEY'
    );

CREATE TYPE type_compte AS ENUM (
    'CAISSE',
    'BANCAIRE',
    'MOBILE_MONEY'
    );

CREATE TYPE banque AS ENUM (
    'BRED',
    'MCB',
    'BMOI',
    'BOA',
    'BGFI',
    'AFG',
    'ACCES_BANQUE',
    'BAOBAB',
    'SIPEM'
    );

CREATE TYPE service_mobile_money AS ENUM (
    'ORANGE_MONEY',
    'MVOLA',
    'AIRTEL_MONEY'
    );

CREATE TYPE type_activite AS ENUM (
    'AG_MENSUELLE',
    'FORMATION_JUNIORS',
    'EXCEPTIONNELLE'
    );

CREATE TYPE public_cible AS ENUM (
    'TOUS',
    'JUNIORS_SEULEMENT',
    'MEMBRES_CONFIRMES',
    'SUR_INVITATION'
    );

CREATE TYPE statut_presence AS ENUM (
    'PRESENT',
    'EXCUSE',
    'ABSENT'
    );

CREATE TYPE decision_autorisation AS ENUM (
    'APPROUVEE',
    'REFUSEE'
    );



CREATE TABLE collectivite (
                                         id                  SERIAL          PRIMARY KEY,
                                         numero              VARCHAR(20)     NOT NULL UNIQUE,
                                         nom                 VARCHAR(150)    NOT NULL UNIQUE,
                                         localite            VARCHAR(100)    NOT NULL,
                                         specialite_agricole specialite_agricole NOT NULL,
                                         date_creation       DATE            NOT NULL DEFAULT CURRENT_DATE,
                                         statut              statut_collectivite NOT NULL DEFAULT 'EN_ATTENTE',
                                         cotisation_annuelle_obligatoire NUMERIC(12,2) NOT NULL DEFAULT 0
);


-- 2.2 MEMBRE

CREATE TABLE membre (
                                   id              SERIAL          PRIMARY KEY,
                                   nom             VARCHAR(100)    NOT NULL,
                                   prenoms         VARCHAR(150)    NOT NULL,
                                   date_naissance  DATE            NOT NULL,
                                   genre           genre NOT NULL,
                                   adresse         TEXT            NOT NULL,
                                   metier          VARCHAR(150)    NOT NULL,
                                   telephone       VARCHAR(20)     NOT NULL UNIQUE,
                                   email           VARCHAR(200)    NOT NULL UNIQUE,
                                   date_adhesion   DATE            NOT NULL DEFAULT CURRENT_DATE,
                                   statut          statut_membre NOT NULL DEFAULT 'ACTIF'
);

-- 2.3 APPARTENANCE
CREATE TABLE appartenance (
                                         id                  SERIAL          PRIMARY KEY,
                                         membre_id           INT             NOT NULL REFERENCES membre(id),
                                         collectivite_id     INT             NOT NULL REFERENCES collectivite(id),
                                         poste               poste NOT NULL,
                                         date_debut          DATE            NOT NULL DEFAULT CURRENT_DATE,
                                         date_fin            DATE            NULL,


                                         CONSTRAINT uq_appartenance_active
                                             EXCLUDE USING gist (
                                             membre_id WITH =,
                                             daterange(date_debut, COALESCE(date_fin, '9999-12-31')) WITH &&
                                             ) WHERE (date_fin IS NULL),
                                         CONSTRAINT chk_poste_unique_actif CHECK (
                                             poste IN ('MEMBRE_CONFIRME', 'MEMBRE_JUNIOR')
                                                 OR date_fin IS NOT NULL
                                             )
);

CREATE INDEX idx_appartenance_active
    ON appartenance(membre_id)
    WHERE date_fin IS NULL;


CREATE INDEX idx_appartenance_collectivite
    ON appartenance(collectivite_id)
    WHERE date_fin IS NULL;


-- 2.4 MANDAT

CREATE TABLE mandat (
                                   id                  SERIAL      PRIMARY KEY,
                                   membre_id           INT         NOT NULL REFERENCES membre(id),
                                   collectivite_id     INT         NULL REFERENCES collectivite(id),
                                   est_federation      BOOLEAN     NOT NULL DEFAULT FALSE,
                                   poste               poste NOT NULL,
                                   annee_debut         INT         NOT NULL,
                                   annee_fin           INT         NOT NULL,

                                   CONSTRAINT chk_mandat_entite CHECK (
                                       (collectivite_id IS NOT NULL AND est_federation = FALSE)
                                           OR (collectivite_id IS NULL AND est_federation = TRUE)
                                       ),
                                   CONSTRAINT chk_poste_mandat CHECK (
                                       poste IN ('PRESIDENT','PRESIDENT_ADJOINT','TRESORIER','SECRETAIRE')
                                       ),
                                   CONSTRAINT uq_mandat_annee UNIQUE (collectivite_id, poste, annee_debut)
);


CREATE VIEW federation.v_nb_mandats_par_membre AS
SELECT membre_id, collectivite_id, est_federation, poste,
       COUNT(*) AS nb_mandats
FROM mandat
GROUP BY membre_id, collectivite_id, est_federation, poste;


-- 2.5 PARRAINAGE (B-2 : au moins 2 parrains, regle collectivite)
CREATE TABLE parrainage (
                                       id                      SERIAL      PRIMARY KEY,
                                       candidat_id             INT         NOT NULL REFERENCES membre(id),
                                       parrain_id              INT         NOT NULL REFERENCES membre(id),
                                       collectivite_cible_id   INT         NOT NULL REFERENCES collectivite(id),
                                       collectivite_parrain_id INT         NOT NULL REFERENCES collectivite(id),
                                       relation                VARCHAR(100) NOT NULL,
                                       date_parrainage         DATE        NOT NULL DEFAULT CURRENT_DATE,

                                       CONSTRAINT uq_parrainage UNIQUE (candidat_id, parrain_id),
                                       CONSTRAINT chk_pas_autoparrainage CHECK (candidat_id <> parrain_id)
);


-- 2.6 AUTORISATION OUVERTURE
CREATE TABLE federation.autorisation_ouverture (
                                                   id                      SERIAL      PRIMARY KEY,
                                                   collectivite_id         INT         NOT NULL UNIQUE
                                                       REFERENCES collectivite(id),
                                                   decision                decision_autorisation NOT NULL,
                                                   date_decision           TIMESTAMP   NOT NULL DEFAULT NOW(),
                                                   agent_federation_id     INT         NOT NULL REFERENCES membre(id),
                                                   commentaire             TEXT        NULL,
                                                   numero_autorisation     VARCHAR(50) NULL UNIQUE
);



-- 3. COTISATIONS
CREATE TABLE cotisation (
                                       id                  SERIAL      PRIMARY KEY,
                                       membre_id           INT         NOT NULL REFERENCES membre(id),
                                       collectivite_id     INT         NOT NULL REFERENCES collectivite(id),
                                       type_cotisation     type_cotisation NOT NULL,
                                       montant             NUMERIC(12,2) NOT NULL CHECK (montant > 0),
                                       date_encaissement   DATE        NOT NULL,
                                       mode_paiement       mode_paiement NOT NULL,
                                       reference_transaction VARCHAR(100) NULL,
                                       periode_concernee   VARCHAR(7)  NULL,
                                       tresorier_id        INT         NOT NULL REFERENCES membre(id),

                                       CONSTRAINT chk_reference_paiement CHECK (
                                           mode_paiement = 'ESPECE'
                                               OR reference_transaction IS NOT NULL
                                           )
);

CREATE INDEX idx_cotisation_membre    ON cotisation(membre_id);
CREATE INDEX idx_cotisation_collectivite ON cotisation(collectivite_id);
CREATE INDEX idx_cotisation_date      ON cotisation(date_encaissement);


CREATE TABLE compte (
                                   id                  SERIAL      PRIMARY KEY,
                                   collectivite_id     INT         NULL REFERENCES collectivite(id),
                                   est_federation      BOOLEAN     NOT NULL DEFAULT FALSE,
                                   type_compte         type_compte NOT NULL,
                                   titulaire           VARCHAR(200) NOT NULL,
                                   solde               NUMERIC(15,2) NOT NULL DEFAULT 0,
                                   devise              VARCHAR(10) NOT NULL DEFAULT 'MGA',
                                   date_creation       DATE        NOT NULL DEFAULT CURRENT_DATE,

                                   CONSTRAINT chk_compte_entite CHECK (
                                       (collectivite_id IS NOT NULL AND est_federation = FALSE)
                                           OR (collectivite_id IS NULL AND est_federation = TRUE)
                                       ),
                                   CONSTRAINT uq_caisse_collectivite UNIQUE (collectivite_id, type_compte)
                                       DEFERRABLE INITIALLY DEFERRED
);


CREATE UNIQUE INDEX uq_caisse_par_collectivite
    ON compte(collectivite_id)
    WHERE type_compte = 'CAISSE' AND est_federation = FALSE;

CREATE UNIQUE INDEX uq_caisse_federation
    ON compte(est_federation)
    WHERE type_compte = 'CAISSE' AND est_federation = TRUE;


-- 4.2 COMPTE BANCAIRE
CREATE TABLE compte_bancaire (
                                            compte_id       INT         PRIMARY KEY REFERENCES compte(id)
                                                ON DELETE CASCADE,
                                            banque          banque NOT NULL,
                                            numero_compte   CHAR(23)    NOT NULL UNIQUE,

                                            CONSTRAINT chk_numero_compte_format CHECK (
                                                numero_compte ~ '^\d{23}$'
                                                )
);

-- 4.3 COMPTE MOBILE MONEY (details specifiques)
CREATE TABLE compte_mobile_money (
                                                compte_id           INT         PRIMARY KEY REFERENCES compte(id)
                                                    ON DELETE CASCADE,
                                                service            service_mobile_money NOT NULL,
                                                numero_telephone    VARCHAR(20) NOT NULL UNIQUE
);



-- 5. ACTIVITES
CREATE TABLE activite (
                                     id                  SERIAL      PRIMARY KEY,
                                     collectivite_id     INT         NULL REFERENCES collectivite(id),
                                     est_federation      BOOLEAN     NOT NULL DEFAULT FALSE,
                                     titre               VARCHAR(250) NOT NULL,
                                     type_activite       type_activite NOT NULL,
                                     date_activite       DATE        NOT NULL,
                                     heure_debut         TIME        NULL,
                                     heure_fin           TIME        NULL,
                                     lieu                VARCHAR(250) NULL,
                                     presence_obligatoire BOOLEAN    NOT NULL DEFAULT TRUE,
                                     public_cible        public_cible NOT NULL DEFAULT 'TOUS',

                                     CONSTRAINT chk_activite_entite CHECK (
                                         (collectivite_id IS NOT NULL AND est_federation = FALSE)
                                             OR (collectivite_id IS NULL AND est_federation = TRUE)
                                         )
);


CREATE TABLE config_recurrence (
                                              id                  SERIAL      PRIMARY KEY,
                                              collectivite_id     INT         NOT NULL REFERENCES collectivite(id),
                                              type_activite       type_activite NOT NULL,
                                              numero_semaine      INT         NOT NULL CHECK (numero_semaine BETWEEN 1 AND 5),
                                              jour_semaine        VARCHAR(10) NOT NULL
                                                  CHECK (jour_semaine IN
                                                         ('LUNDI','MARDI','MERCREDI','JEUDI',
                                                          'VENDREDI','SAMEDI','DIMANCHE')),

                                              CONSTRAINT uq_config_recurrence UNIQUE (collectivite_id, type_activite),
                                              CONSTRAINT chk_type_recurrent CHECK (
                                                  type_activite IN ('AG_MENSUELLE', 'FORMATION_JUNIORS')
                                                  )
);


CREATE TABLE presence (
                                     id                      SERIAL      PRIMARY KEY,
                                     activite_id             INT         NOT NULL REFERENCES activite(id),
                                     membre_id               INT         NOT NULL REFERENCES membre(id),
                                     statut_presence         statut_presence NOT NULL,
                                     motif_absence           TEXT        NULL,
                                     est_visiteur            BOOLEAN     NOT NULL DEFAULT FALSE,
                                     collectivite_origine_id INT         NULL REFERENCES collectivite(id),
                                     date_saisie             TIMESTAMP   NOT NULL DEFAULT NOW(),
                                     secretaire_id           INT         NOT NULL REFERENCES membre(id),

                                     CONSTRAINT uq_presence UNIQUE (activite_id, membre_id),
                                     CONSTRAINT chk_excuse_motif CHECK (
                                         statut_presence <> 'EXCUSE' OR motif_absence IS NOT NULL
                                         ),
                                     CONSTRAINT chk_visiteur_origine CHECK (
                                         est_visiteur = FALSE OR collectivite_origine_id IS NOT NULL
                                         )
);

CREATE INDEX idx_presence_activite ON presence(activite_id);
CREATE INDEX idx_presence_membre   ON presence(membre_id);


-- 6. RAPPORTS ET STATISTIQUES
CREATE TABLE rapport_mensuel (
                                            id                      SERIAL          PRIMARY KEY,
                                            collectivite_id         INT             NOT NULL REFERENCES collectivite(id),
                                            periode                 CHAR(7)         NOT NULL,  -- format YYYY-MM
                                            president_id            INT             NOT NULL REFERENCES membre(id),
                                            taux_assiduite_global   NUMERIC(5,2)    NOT NULL CHECK (
                                                taux_assiduite_global BETWEEN 0 AND 100),
                                            nombre_membres_inscrits INT             NOT NULL CHECK (nombre_membres_inscrits >= 0),
                                            commentaire             TEXT            NULL,
                                            date_soumission         TIMESTAMP       NOT NULL DEFAULT NOW(),

                                            CONSTRAINT uq_rapport_mensuel UNIQUE (collectivite_id, periode),
                                            CONSTRAINT chk_periode_format CHECK (
                                                periode ~ '^\d{4}-(0[1-9]|1[0-2])$'
                                                )
);


-- 6.2 RAPPORT FEDERATION
CREATE TABLE rapport_federation (
                                               id                  SERIAL          PRIMARY KEY,
                                               periode             CHAR(7)         NOT NULL,  -- YYYY-MM
                                               secretaire_id       INT             NOT NULL REFERENCES membre(id),
                                               date_generation     TIMESTAMP       NOT NULL DEFAULT NOW(),
                                               commentaire         TEXT            NULL,

                                               CONSTRAINT uq_rapport_federation UNIQUE (periode),
                                               CONSTRAINT chk_periode_federation CHECK (
                                                   periode ~ '^\d{4}-(0[1-9]|1[0-2])$'
                                                   )
);

CREATE TABLE rapport_federation_ligne (
                                                     id                          SERIAL          PRIMARY KEY,
                                                     rapport_federation_id       INT             NOT NULL
                                                         REFERENCES rapport_federation(id)
                                                             ON DELETE CASCADE,
                                                     collectivite_id             INT             NOT NULL
                                                         REFERENCES collectivite(id),
                                                     taux_assiduite_global       NUMERIC(5,2)    NOT NULL
                                                         CHECK (taux_assiduite_global BETWEEN 0 AND 100),
                                                     pourcentage_membres_a_jour  NUMERIC(5,2)    NOT NULL
                                                         CHECK (pourcentage_membres_a_jour BETWEEN 0 AND 100),
                                                     nombre_nouveaux_adherents   INT             NOT NULL DEFAULT 0,

                                                     CONSTRAINT uq_rapport_federation_ligne UNIQUE (rapport_federation_id, collectivite_id)
);

