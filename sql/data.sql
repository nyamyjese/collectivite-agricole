
-- Collectivités (avec quelques-unes déjà actives)
INSERT INTO collectivite (id, location, official_number, official_name, specialite, statut, federation_approval, montant_cotisations_annuelles) VALUES
        ('col-001', 'Ambatondrazaka', 'COL-NAT-2026-001', 'Collectivité Rizicole du Lac Alaotra', 'RIZICULTURE', 'ACTIVE', true, 200000),
        ('col-002', 'Antsirabe', 'COL-NAT-2026-002', 'Apiculteurs du Vakinankaratra', 'APICULTURE', 'ACTIVE', true, 150000),
        ('col-003', 'Antananarivo', NULL, NULL, 'MARAICHAGE', 'EN_ATTENTE', false, 180000),
        ('col-004', 'Morondava', NULL, NULL, 'ELEVAGE', 'AUTORISEE', true, 220000),
        ('col-005', 'Sambava', 'COL-NAT-2026-005', 'Vanille de la SAVA', 'VANILLE', 'ACTIVE', true, 300000);


-- Membres (répartis dans les collectivités)
-- Membres de col-001 (Ambatondrazaka)
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, status, date_adhesion, collectivity_id) VALUES
        ('mbr-001', 'Jean', 'Rakoto', '1985-04-12', 'MALE', 'Lot 12 Ambatondrazaka', 'Agriculteur', '+261341112233', 'jean.rakoto@mail.mg', 'PRESIDENT', 'ACTIVE', '2024-01-10', 'col-001'),
        ('mbr-002', 'Marie', 'Rabe', '1990-08-23', 'FEMALE', 'Lot 45 Ambatondrazaka', 'Agricultrice', '+261342223344', 'marie.rabe@mail.mg', 'VICE_PRESIDENT', 'ACTIVE', '2024-02-15', 'col-001'),
        ('mbr-003', 'Paul', 'Razafy', '1982-11-02', 'MALE', 'Lot 78 Ambatondrazaka', 'Technicien', '+261343334455', 'paul.razafy@mail.mg', 'TREASURER', 'ACTIVE', '2024-03-20', 'col-001'),
        ('mbr-004', 'Jeanne', 'Randria', '1979-06-17', 'FEMALE', 'Lot 9 Ambatondrazaka', 'Secrétaire', '+261344445566', 'jeanne.randria@mail.mg', 'SECRETARY', 'ACTIVE', '2024-04-05', 'col-001'),
        ('mbr-005', 'Aina', 'Rakoto', '1992-12-05', 'FEMALE', 'Lot 3 Ambatondrazaka', 'Agricultrice', '+261346667788', 'aina.rakoto@mail.mg', 'SENIOR', 'ACTIVE', '2024-06-01', 'col-001'),
        ('mbr-006', 'Henri', 'Rasoa', '1980-03-14', 'MALE', 'Lot 7 Ambatondrazaka', 'Technicien agricole', '+261347778899', 'henri.rasoa@mail.mg', 'SENIOR', 'ACTIVE', '2024-07-10', 'col-001'),
        ('mbr-007', 'Kely', 'Rakoto', '2000-05-05', 'MALE', 'Lot 44 Ambatondrazaka', 'Aide agricole', '+261342112233', 'kely.r@mail.mg', 'JUNIOR', 'ACTIVE', '2025-11-01', 'col-001'),
        ('mbr-008', 'Lala', 'Rasoavina', '1998-08-18', 'FEMALE', 'Lot 55 Ambatondrazaka', 'Aide agricole', '+261343223344', 'lala.r@mail.mg', 'JUNIOR', 'ACTIVE', '2025-12-01', 'col-001');

-- Membres de col-002 (Antsirabe)
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, status, date_adhesion, collectivity_id) VALUES
        ('mbr-101', 'Bema', 'Razafindrakoto', '1985-02-02', 'MALE', 'Lot 1 Antsirabe', 'Apiculteur', '+261344334455', 'bema.r@mail.mg', 'PRESIDENT', 'ACTIVE', '2024-01-01', 'col-002'),
        ('mbr-102', 'Miora', 'Rasoanirina', '1990-03-03', 'FEMALE', 'Lot 2 Antsirabe', 'Apicultrice', '+261345445566', 'miora.r@mail.mg', 'VICE_PRESIDENT', 'ACTIVE', '2024-02-01', 'col-002'),
        ('mbr-103', 'Fidy', 'Randrianasolo', '1993-04-04', 'MALE', 'Lot 3 Antsirabe', 'Apiculteur', '+261346556677', 'fidy.r@mail.mg', 'SENIOR', 'ACTIVE', '2024-03-01', 'col-002'),
        ('mbr-104', 'Voahangy', 'Rakotomalala', '1980-01-01', 'FEMALE', 'Lot 4 Antsirabe', 'Apicultrice', '+261347667788', 'voahangy.r@mail.mg', 'TREASURER', 'ACTIVE', '2024-01-01', 'col-002');

-- Membres de col-003 (Tana) – collectivité en attente
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, status, date_adhesion, collectivity_id) VALUES
        ('mbr-201', 'Lanto', 'Rakotonirina', '1981-02-02', 'MALE', 'Lot 1 Tana', 'Maraîcher', '+261348778899', 'lanto.r@mail.mg', 'PRESIDENT', 'ACTIVE', '2024-02-01', 'col-003'),
        ('mbr-202', 'Tiana', 'Ravaonirina', '1982-03-03', 'FEMALE', 'Lot 2 Tana', 'Maraîchère', '+261349889900', 'tiana.r@mail.mg', 'VICE_PRESIDENT', 'ACTIVE', '2024-03-01', 'col-003'),
        ('mbr-203', 'Haja', 'Randrianarisoa', '1983-04-04', 'MALE', 'Lot 3 Tana', 'Maraîcher', '+261341990011', 'haja.r@mail.mg', 'TREASURER', 'ACTIVE', '2024-04-01', 'col-003'),
        ('mbr-204', 'Mamy', 'Razafimanantsoa', '1984-05-05', 'MALE', 'Lot 4 Tana', 'Maraîcher', '+261342001122', 'mamy.r@mail.mg', 'SECRETARY', 'ACTIVE', '2024-05-01', 'col-003'),
        ('mbr-205', 'Nirina', 'Rakotovao', '1985-06-06', 'FEMALE', 'Lot 5 Tana', 'Maraîchère', '+261343112233', 'nirina.r@mail.mg', 'SENIOR', 'ACTIVE', '2024-06-01', 'col-003'),
        ('mbr-206', 'Tsiry', 'Razafimandimby', '1986-07-07', 'MALE', 'Lot 6 Tana', 'Maraîcher', '+261344223344', 'tsiry.r@mail.mg', 'SENIOR', 'ACTIVE', '2024-07-01', 'col-003'),
        ('mbr-207', 'Sitraka', 'Andrianarivelo', '1987-08-08', 'MALE', 'Lot 7 Tana', 'Maraîcher', '+261345334455', 'sitraka.r@mail.mg', 'JUNIOR', 'ACTIVE', '2024-08-01', 'col-003'),
        ('mbr-208', 'Njara', 'Rasolofonirina', '1990-01-01', 'MALE', 'Lot 8 Tana', 'Maraîcher', '+261346445566', 'njara.r@mail.mg', 'SENIOR', 'ACTIVE', '2024-09-01', 'col-003'),
        ('mbr-209', 'Mendrika', 'Rakotondramanana', '1991-02-02', 'FEMALE', 'Lot 9 Tana', 'Maraîchère', '+261347556677', 'mendrika.r@mail.mg', 'SENIOR', 'ACTIVE', '2024-10-01', 'col-003'),
        ('mbr-210', 'Faniry', 'Razafinjatovo', '1992-03-03', 'MALE', 'Lot 10 Tana', 'Maraîcher', '+261348667788', 'faniry.r@mail.mg', 'JUNIOR', 'ACTIVE', '2024-11-01', 'col-003'),
        ('mbr-211', 'Hasina', 'Rasamimanana', '1993-04-04', 'FEMALE', 'Lot 11 Tana', 'Maraîchère', '+261349778899', 'hasina.r@mail.mg', 'JUNIOR', 'ACTIVE', '2024-12-01', 'col-003');

-- Membres pour col-004 (Morondava) – quelques membres
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, status, date_adhesion, collectivity_id) VALUES
        ('mbr-301', 'Soa', 'Rakotoarisoa', '1975-05-10', 'FEMALE', 'Lot 1 Morondava', 'Éleveur', '+261340001001', 'soa.r@mail.mg', 'PRESIDENT', 'ACTIVE', '2024-01-10', 'col-004'),
        ('mbr-302', 'Hery', 'Randriamanana', '1978-07-20', 'MALE', 'Lot 2 Morondava', 'Éleveur', '+261340001002', 'hery.r@mail.mg', 'VICE_PRESIDENT', 'ACTIVE', '2024-02-15', 'col-004'),
        ('mbr-303', 'Mbola', 'Razafimahaleo', '1982-09-05', 'MALE', 'Lot 3 Morondava', 'Éleveur', '+261340001003', 'mbola.r@mail.mg', 'TREASURER', 'ACTIVE', '2024-03-20', 'col-004');

-- Membres pour col-005 (Sambava)
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, status, date_adhesion, collectivity_id) VALUES
        ('mbr-401', 'Lova', 'Rakotoarivelo', '1988-11-11', 'MALE', 'Lot 1 Sambava', 'Planteur', '+261350001001', 'lova.r@mail.mg', 'PRESIDENT', 'ACTIVE', '2024-05-01', 'col-005'),
        ('mbr-402', 'Tantely', 'Ranaivo', '1990-12-12', 'FEMALE', 'Lot 2 Sambava', 'Planteur', '+261350001002', 'tantely.r@mail.mg', 'VICE_PRESIDENT', 'ACTIVE', '2024-06-01', 'col-005'),
        ('mbr-403', 'Miora', 'Andriamahenina', '1992-02-02', 'FEMALE', 'Lot 3 Sambava', 'Planteur', '+261350001003', 'miora.a@mail.mg', 'TREASURER', 'ACTIVE', '2024-07-01', 'col-005'),
        ('mbr-404', 'Faneva', 'Rakotomalala', '1995-03-03', 'MALE', 'Lot 4 Sambava', 'Planteur', '+261350001004', 'faneva.r@mail.mg', 'SECRETARY', 'ACTIVE', '2024-08-01', 'col-005'),
        ('mbr-405', 'Rindra', 'Rasoanirina', '1997-04-04', 'FEMALE', 'Lot 5 Sambava', 'Planteur', '+261350001005', 'rindra.r@mail.mg', 'SENIOR', 'ACTIVE', '2024-09-01', 'col-005'),
        ('mbr-406', 'Hajasoa', 'Randrianasolo', '1999-05-05', 'FEMALE', 'Lot 6 Sambava', 'Planteur', '+261350001006', 'hajasoa.r@mail.mg', 'JUNIOR', 'ACTIVE', '2025-01-01', 'col-005'),
        ('mbr-407', 'Kanto', 'Razafindrakoto', '2001-06-06', 'MALE', 'Lot 7 Sambava', 'Planteur', '+261350001007', 'kanto.r@mail.mg', 'JUNIOR', 'ACTIVE', '2025-02-01', 'col-005');


-- Contributions (cotisations) pour tester la fonctionnalité C
INSERT INTO contribution (id, member_id, collectivity_id, type, amount, payment_mode, transaction_reference, collection_date, federation_reversed_amount, status) VALUES
-- Cotisations mensuelles pour col-001
('cont-001', 'mbr-001', 'col-001', 'PERIODIQUE_MENSUELLE', 20000, 'MOBILE_MONEY', 'OM-2026-001', '2026-04-01', 2000, 'ENREGISTRE'),
('cont-002', 'mbr-005', 'col-001', 'PERIODIQUE_MENSUELLE', 20000, 'VIREMENT_BANCAIRE', 'VIR-2026-001', '2026-04-02', 2000, 'ENREGISTRE'),
('cont-003', 'mbr-006', 'col-001', 'PERIODIQUE_MENSUELLE', 20000, 'MOBILE_MONEY', 'OM-2026-002', '2026-04-03', 2000, 'ENREGISTRE'),
('cont-004', 'mbr-007', 'col-001', 'PERIODIQUE_MENSUELLE', 20000, 'ESPECE', NULL, '2026-04-05', 2000, 'ENREGISTRE'),
('cont-005', 'mbr-001', 'col-001', 'PERIODIQUE_ANNUELLE', 200000, 'MOBILE_MONEY', 'OM-2026-ANN-001', '2026-01-15', 20000, 'ENREGISTRE'),

-- Cotisations pour col-002
('cont-101', 'mbr-101', 'col-002', 'PERIODIQUE_MENSUELLE', 15000, 'MOBILE_MONEY', 'OM-2026-101', '2026-04-01', 1500, 'ENREGISTRE'),
('cont-102', 'mbr-102', 'col-002', 'PERIODIQUE_MENSUELLE', 15000, 'ESPECE', NULL, '2026-04-02', 1500, 'ENREGISTRE'),
('cont-103', 'mbr-103', 'col-002', 'PERIODIQUE_ANNUELLE', 150000, 'VIREMENT_BANCAIRE', 'VIR-2026-002', '2026-02-20', 15000, 'ENREGISTRE'),

-- Cotisations pour col-005 (Sambava)
('cont-401', 'mbr-401', 'col-005', 'PERIODIQUE_MENSUELLE', 30000, 'MOBILE_MONEY', 'OM-2026-401', '2026-04-10', 3000, 'ENREGISTRE'),
('cont-402', 'mbr-402', 'col-005', 'PERIODIQUE_MENSUELLE', 30000, 'MOBILE_MONEY', 'OM-2026-402', '2026-04-11', 3000, 'ENREGISTRE'),
('cont-403', 'mbr-405', 'col-005', 'PONCTUELLE', 50000, 'ESPECE', NULL, '2026-03-15', 0, 'ENREGISTRE'),   -- ponctuelle : 0 reversé
('cont-404', 'mbr-406', 'col-005', 'PERIODIQUE_MENSUELLE', 30000, 'MOBILE_MONEY', 'OM-2026-403', '2026-04-12', 3000, 'ENREGISTRE');


-- Comptes (trésorerie) – quelques exemples pour tester la fonctionnalité D
INSERT INTO compte (id, entite_type, entite_id, type_compte, solde, nom_titulaire, banque, numero_compte, service_mobile_money, numero_telephone) VALUES
    ('cpt-001', 'COLLECTIVITE', 'col-001', 'CAISSE', 150000, NULL, NULL, NULL, NULL, NULL),
    ('cpt-002', 'COLLECTIVITE', 'col-001', 'BANCAIRE', 500000, 'Collectivité Rizicole', 'BOA', '00005000010000000012345', NULL, NULL),
    ('cpt-003', 'COLLECTIVITE', 'col-001', 'MOBILE_MONEY', 75000, 'Jean Rakoto', NULL, NULL, 'ORANGE_MONEY', '+261341112233'),
    ('cpt-004', 'COLLECTIVITE', 'col-002', 'CAISSE', 80000, NULL, NULL, NULL, NULL, NULL),
    ('cpt-005', 'FEDERATION', 'FEDERATION', 'CAISSE', 1000000, NULL, NULL, NULL, NULL, NULL);