
-- Types énumérés
CREATE TYPE gender AS ENUM ('MALE', 'FEMALE');
CREATE TYPE member_occupation AS ENUM ('JUNIOR', 'SENIOR', 'SECRETARY', 'TREASURER', 'VICE_PRESIDENT', 'PRESIDENT');
CREATE TYPE member_status AS ENUM ('ACTIVE', 'RESIGNED');
CREATE TYPE specialite_agricole AS ENUM ('RIZICULTURE', 'APICULTURE', 'MARAICHAGE', 'ARBORICULTURE', 'ELEVAGE', 'PISCICULTURE', 'VANILLE', 'GIROFLE', 'CACAO', 'AUTRE');
CREATE TYPE statut_collectivite AS ENUM ('EN_ATTENTE', 'AUTORISEE', 'REFUSEE', 'ACTIVE', 'DISSOUTE');
CREATE TYPE type_contribution AS ENUM ('PERIODIQUE_MENSUELLE', 'PERIODIQUE_ANNUELLE', 'PONCTUELLE');
CREATE TYPE mode_paiement AS ENUM ('ESPECE', 'VIREMENT_BANCAIRE', 'MOBILE_MONEY');
CREATE TYPE type_compte AS ENUM ('CAISSE', 'BANCAIRE', 'MOBILE_MONEY');


-- Table : collectivite
CREATE TABLE collectivite (
                              id VARCHAR(36) PRIMARY KEY,
                              location VARCHAR(100) NOT NULL,
                              official_number VARCHAR(50) UNIQUE,
                              official_name VARCHAR(100) UNIQUE,
                              specialite specialite_agricole,
                              date_creation DATE DEFAULT CURRENT_DATE,
                              statut statut_collectivite DEFAULT 'EN_ATTENTE',
                              federation_approval BOOLEAN DEFAULT FALSE,
                              montant_cotisations_annuelles INT DEFAULT 0
);


-- Table : member
CREATE TABLE member (
                        id VARCHAR(36) PRIMARY KEY,
                        first_name VARCHAR(50) NOT NULL,
                        last_name VARCHAR(50) NOT NULL,
                        birth_date DATE NOT NULL,
                        gender gender NOT NULL,
                        address TEXT,
                        profession VARCHAR(100),
                        phone_number VARCHAR(20) UNIQUE NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        occupation member_occupation NOT NULL,
                        status member_status DEFAULT 'ACTIVE',
                        date_adhesion DATE DEFAULT CURRENT_DATE,
                        collectivity_id VARCHAR(36) NOT NULL,
                        FOREIGN KEY (collectivity_id) REFERENCES collectivite(id) ON DELETE RESTRICT
);


-- Table : contribution (cotisations)
CREATE TABLE contribution (
                              id VARCHAR(30) PRIMARY KEY,
                              member_id VARCHAR(36) NOT NULL,
                              collectivity_id VARCHAR(36) NOT NULL,
                              type type_contribution NOT NULL,
                              amount INT NOT NULL CHECK (amount > 0),
                              payment_mode mode_paiement NOT NULL,
                              transaction_reference VARCHAR(50),
                              collection_date DATE NOT NULL,
                              federation_reversed_amount INT DEFAULT 0,
                              status VARCHAR(30) DEFAULT 'ENREGISTRE',
                              FOREIGN KEY (member_id) REFERENCES member(id),
                              FOREIGN KEY (collectivity_id) REFERENCES collectivite(id)
);


-- Table : compte (trésorerie) – pour fonctionnalité D
CREATE TABLE compte (
                        id VARCHAR(36) PRIMARY KEY,
                        entite_type VARCHAR(20) NOT NULL CHECK (entite_type IN ('COLLECTIVITE', 'FEDERATION')),
                        entite_id VARCHAR(36) NOT NULL,
                        type_compte type_compte NOT NULL,
                        solde INT NOT NULL DEFAULT 0,
                        devise VARCHAR(10) DEFAULT 'MGA',
                        date_mise_a_jour TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        nom_titulaire VARCHAR(100),
                        banque VARCHAR(30),
                        numero_compte VARCHAR(23),
                        service_mobile_money VARCHAR(30),
                        numero_telephone VARCHAR(20),
                        CONSTRAINT ck_compte_numero CHECK (
                            (type_compte = 'BANCAIRE' AND numero_compte IS NOT NULL AND LENGTH(numero_compte)=23) OR
                            (type_compte != 'BANCAIRE')
                            )
);


-- Index pour performances
CREATE INDEX idx_member_collectivity ON member(collectivity_id);
CREATE INDEX idx_contribution_member ON contribution(member_id);
CREATE INDEX idx_contribution_collectivity ON contribution(collectivity_id);
CREATE INDEX idx_contribution_dates ON contribution(collection_date);