
-- Insertion de collectivités
INSERT INTO collectivite (id, nom, localite, specialite, date_creation, statut, montant_cotisations_annuelles) VALUES
    ('COL001', 'Collectivité Rizicole Alaotra', 'Ambatondrazaka', 'RIZICULTURE', '2025-01-15', 'ACTIVE', 200000),
    ('COL002', 'Apiculteurs du Vakinankaratra', 'Antsirabe', 'APICULTURE', '2025-03-10', 'ACTIVE', 150000),
    ('COL003', 'Maraîchers Analamanga', 'Antananarivo', 'MARAICHAGE', '2025-06-01', 'ACTIVE', 180000),
    ('COL004', 'Éleveurs du Menabe', 'Morondava', 'ELEVAGE', '2026-01-20', 'ACTIVE', 220000),
    ('COL005', 'Vanille de la SAVA', 'Sambava', 'VANILLE', '2024-11-05', 'ACTIVE', 300000),
    ('COL006', 'Nouvelle Collectivité en attente', 'Fianarantsoa', 'ARBORICULTURE', '2026-04-01', 'EN_ATTENTE', 190000);

-- Insertion de membres (environ 40 pour permettre des tests variés)
INSERT INTO membre (id, nom, prenoms, date_naissance, genre, adresse, metier, telephone, email, date_adhesion_federation, poste, statut, collectivite_id) VALUES
    ('MBR001', 'Rakoto', 'Jean', '1985-04-12', 'MASCULIN', 'Lot 12 Ambatondrazaka', 'Agriculteur', '+261341112233', 'jean.rakoto@mail.mg', '2024-01-10', 'PRESIDENT', 'ACTIF', 'COL001'),
    ('MBR002', 'Rabe', 'Marie', '1990-08-23', 'FEMININ', 'Lot 45 Antsirabe', 'Apicultrice', '+261342223344', 'marie.rabe@mail.mg', '2024-02-15', 'PRESIDENT', 'ACTIF', 'COL002'),
    ('MBR003', 'Razafy', 'Paul', '1982-11-02', 'MASCULIN', 'Lot 78 Tana', 'Maraîcher', '+261343334455', 'paul.razafy@mail.mg', '2024-03-20', 'PRESIDENT', 'ACTIF', 'COL003'),
    ('MBR004', 'Randria', 'Jeanne', '1979-06-17', 'FEMININ', 'Lot 9 Morondava', 'Éleveur', '+261344445566', 'jeanne.randria@mail.mg', '2024-04-05', 'PRESIDENT', 'ACTIF', 'COL004'),
    ('MBR005', 'Andria', 'Luc', '1988-09-30', 'MASCULIN', 'Lot 56 Sambava', 'Planteur', '+261345556677', 'luc.andria@mail.mg', '2024-05-12', 'PRESIDENT', 'ACTIF', 'COL005'),

-- Membres confirmés (ancienneté > 90 jours) pour COL001
    ('MBR006', 'Rakoto', 'Aina', '1992-12-05', 'FEMININ', 'Lot 3 Ambatondrazaka', 'Agricultrice', '+261346667788', 'aina.rakoto@mail.mg', '2024-06-01', 'MEMBRE_CONFIRME', 'ACTIF', 'COL001'),
    ('MBR007', 'Rasoa', 'Henri', '1980-03-14', 'MASCULIN', 'Lot 7 Ambatondrazaka', 'Technicien agricole', '+261347778899', 'henri.rasoa@mail.mg', '2024-07-10', 'MEMBRE_CONFIRME', 'ACTIF', 'COL001'),
    ('MBR008', 'Ravo', 'Claire', '1995-01-20', 'FEMININ', 'Lot 11 Ambatondrazaka', 'Commerçante', '+261348889900', 'claire.ravo@mail.mg', '2024-08-01', 'MEMBRE_CONFIRME', 'ACTIF', 'COL001'),
    ('MBR009', 'Rakotoarison', 'Michel', '1987-07-19', 'MASCULIN', 'Lot 22 Ambatondrazaka', 'Agriculteur', '+261349990011', 'michel.r@mail.mg', '2024-09-01', 'MEMBRE_CONFIRME', 'ACTIF', 'COL001'),
    ('MBR010', 'Ranaivo', 'Sylvie', '1991-11-11', 'FEMININ', 'Lot 33 Ambatondrazaka', 'Agricultrice', '+261341001122', 'sylvie.r@mail.mg', '2024-10-01', 'MEMBRE_CONFIRME', 'ACTIF', 'COL001'),

-- Membres juniors (ancienneté < 6 mois)
     ('MBR011', 'Rakoto', 'Kely', '2000-05-05', 'MASCULIN', 'Lot 44 Ambatondrazaka', 'Aide agricole', '+261342112233', 'kely.r@mail.mg', '2025-11-01', 'MEMBRE_JUNIOR', 'ACTIF', 'COL001'),
     ('MBR012', 'Rasoavina', 'Lala', '1998-08-18', 'FEMININ', 'Lot 55 Ambatondrazaka', 'Aide agricole', '+261343223344', 'lala.r@mail.mg', '2025-12-01', 'MEMBRE_JUNIOR', 'ACTIF', 'COL001'),

-- Autres membres pour COL002 (Apiculteurs)
     ('MBR013', 'Razafindrakoto', 'Bema', '1985-02-02', 'MASCULIN', 'Lot 1 Antsirabe', 'Apiculteur', '+261344334455', 'bema.r@mail.mg', '2024-01-01', 'MEMBRE_CONFIRME', 'ACTIF', 'COL002'),
     ('MBR014', 'Rasoanirina', 'Miora', '1990-03-03', 'FEMININ', 'Lot 2 Antsirabe', 'Apicultrice', '+261345445566', 'miora.r@mail.mg', '2024-02-01', 'MEMBRE_CONFIRME', 'ACTIF', 'COL002'),
     ('MBR015', 'Randrianasolo', 'Fidy', '1993-04-04', 'MASCULIN', 'Lot 3 Antsirabe', 'Apiculteur', '+261346556677', 'fidy.r@mail.mg', '2024-03-01', 'MEMBRE_JUNIOR', 'ACTIF', 'COL002'),

-- Ajout de membres avec postes spécifiques pour chaque collectivité
     ('MBR016', 'Rakotomalala', 'Voahangy', '1980-01-01', 'FEMININ', 'Lot 1 Tana', 'Maraîchère', '+261347667788', 'voahangy.r@mail.mg', '2024-01-01', 'SECRETAIRE', 'ACTIF', 'COL003'),
     ('MBR017', 'Rakotonirina', 'Lanto', '1981-02-02', 'MASCULIN', 'Lot 2 Tana', 'Maraîcher', '+261348778899', 'lanto.r@mail.mg', '2024-02-01', 'TRESORIER', 'ACTIF', 'COL003'),
     ('MBR018', 'Ravaonirina', 'Tiana', '1982-03-03', 'FEMININ', 'Lot 3 Tana', 'Maraîchère', '+261349889900', 'tiana.r@mail.mg', '2024-03-01', 'PRESIDENT_ADJOINT', 'ACTIF', 'COL003'),

-- Pour COL004
     ('MBR019', 'Randrianarisoa', 'Haja', '1983-04-04', 'MASCULIN', 'Lot 1 Morondava', 'Éleveur', '+261341990011', 'haja.r@mail.mg', '2024-04-01', 'SECRETAIRE', 'ACTIF', 'COL004'),
     ('MBR020', 'Razafimanantsoa', 'Mamy', '1984-05-05', 'MASCULIN', 'Lot 2 Morondava', 'Éleveur', '+261342001122', 'mamy.r@mail.mg', '2024-05-01', 'TRESORIER', 'ACTIF', 'COL004'),

-- Pour COL005
     ('MBR021', 'Rakotovao', 'Nirina', '1985-06-06', 'FEMININ', 'Lot 1 Sambava', 'Planteur', '+261343112233', 'nirina.r@mail.mg', '2024-06-01', 'SECRETAIRE', 'ACTIF', 'COL005'),
     ('MBR022', 'Razafimandimby', 'Tsiry', '1986-07-07', 'MASCULIN', 'Lot 2 Sambava', 'Planteur', '+261344223344', 'tsiry.r@mail.mg', '2024-07-01', 'TRESORIER', 'ACTIF', 'COL005'),
     ('MBR023', 'Andrianarivelo', 'Sitraka', '1987-08-08', 'MASCULIN', 'Lot 3 Sambava', 'Planteur', '+261345334455', 'sitraka.r@mail.mg', '2024-08-01', 'PRESIDENT_ADJOINT', 'ACTIF', 'COL005'),

-- Membres supplémentaires pour atteindre au moins 10 par collectivité pour les tests d'ouverture
     ('MBR024', 'Rasolofonirina', 'Njara', '1990-01-01', 'MASCULIN', 'Lot 100 Ambatondrazaka', 'Agriculteur', '+261346445566', 'njara.r@mail.mg', '2024-09-01', 'MEMBRE_CONFIRME', 'ACTIF', 'COL001'),
     ('MBR025', 'Rakotondramanana', 'Mendrika', '1991-02-02', 'FEMININ', 'Lot 101 Ambatondrazaka', 'Agricultrice', '+261347556677', 'mendrika.r@mail.mg', '2024-10-01', 'MEMBRE_CONFIRME', 'ACTIF', 'COL001'),
     ('MBR026', 'Razafinjatovo', 'Faniry', '1992-03-03', 'MASCULIN', 'Lot 102 Ambatondrazaka', 'Agriculteur', '+261348667788', 'faniry.r@mail.mg', '2024-11-01', 'MEMBRE_JUNIOR', 'ACTIF', 'COL001'),
     ('MBR027', 'Rasamimanana', 'Hasina', '1993-04-04', 'FEMININ', 'Lot 103 Ambatondrazaka', 'Agricultrice', '+261349778899', 'hasina.r@mail.mg', '2024-12-01', 'MEMBRE_JUNIOR', 'ACTIF', 'COL001'),

-- Membres pour COL006 (en attente) : 10 membres nécessaires pour test d'éligibilité
     ('MBR028', 'Rakoto', 'Test1', '1990-01-01', 'MASCULIN', 'Adresse test', 'Métier test', '+261340000001', 'test1@mail.mg', '2025-01-01', 'PRESIDENT', 'ACTIF', 'COL006'),
     ('MBR029', 'Rakoto', 'Test2', '1990-01-02', 'FEMININ', 'Adresse test', 'Métier test', '+261340000002', 'test2@mail.mg', '2025-01-02', 'PRESIDENT_ADJOINT', 'ACTIF', 'COL006'),
     ('MBR030', 'Rakoto', 'Test3', '1990-01-03', 'MASCULIN', 'Adresse test', 'Métier test', '+261340000003', 'test3@mail.mg', '2025-01-03', 'TRESORIER', 'ACTIF', 'COL006'),
     ('MBR031', 'Rakoto', 'Test4', '1990-01-04', 'FEMININ', 'Adresse test', 'Métier test', '+261340000004', 'test4@mail.mg', '2025-01-04', 'SECRETAIRE', 'ACTIF', 'COL006'),
     ('MBR032', 'Rakoto', 'Test5', '1990-01-05', 'MASCULIN', 'Adresse test', 'Métier test', '+261340000005', 'test5@mail.mg', '2025-06-01', 'MEMBRE_CONFIRME', 'ACTIF', 'COL006'),
     ('MBR033', 'Rakoto', 'Test6', '1990-01-06', 'FEMININ', 'Adresse test', 'Métier test', '+261340000006', 'test6@mail.mg', '2025-06-02', 'MEMBRE_CONFIRME', 'ACTIF', 'COL006'),
     ('MBR034', 'Rakoto', 'Test7', '1990-01-07', 'MASCULIN', 'Adresse test', 'Métier test', '+261340000007', 'test7@mail.mg', '2025-06-03', 'MEMBRE_CONFIRME', 'ACTIF', 'COL006'),
     ('MBR035', 'Rakoto', 'Test8', '1990-01-08', 'FEMININ', 'Adresse test', 'Métier test', '+261340000008', 'test8@mail.mg', '2025-06-04', 'MEMBRE_CONFIRME', 'ACTIF', 'COL006'),
     ('MBR036', 'Rakoto', 'Test9', '1990-01-09', 'MASCULIN', 'Adresse test', 'Métier test', '+261340000009', 'test9@mail.mg', '2025-06-05', 'MEMBRE_CONFIRME', 'ACTIF', 'COL006'),
     ('MBR037', 'Rakoto', 'Test10', '1990-01-10', 'FEMININ', 'Adresse test', 'Métier test', '+261340000010', 'test10@mail.mg', '2025-06-06', 'MEMBRE_JUNIOR', 'ACTIF', 'COL006');

-- Insertion de mandats pour les présidents actuels
INSERT INTO mandat (membre_id, collectivite_id, poste, annee) VALUES
     ('MBR001', 'COL001', 'PRESIDENT', 2025),
     ('MBR001', 'COL001', 'PRESIDENT', 2026),
     ('MBR002', 'COL002', 'PRESIDENT', 2025),
     ('MBR002', 'COL002', 'PRESIDENT', 2026),
     ('MBR003', 'COL003', 'PRESIDENT', 2025),
     ('MBR003', 'COL003', 'PRESIDENT', 2026),
     ('MBR004', 'COL004', 'PRESIDENT', 2025),
     ('MBR004', 'COL004', 'PRESIDENT', 2026),
     ('MBR005', 'COL005', 'PRESIDENT', 2025),
     ('MBR005', 'COL005', 'PRESIDENT', 2026);

-- Insertion de quelques cotisations
INSERT INTO cotisation (id, membre_id, collectivite_id, type, montant, mode_paiement, reference_transaction, date_encaissement) VALUES
     ('COT001', 'MBR001', 'COL001', 'PERIODIQUE_MENSUELLE', 20000, 'MOBILE_MONEY', 'OM-2026-001', '2026-04-01'),
     ('COT002', 'MBR006', 'COL001', 'PERIODIQUE_MENSUELLE', 20000, 'VIREMENT_BANCAIRE', 'VIR-2026-001', '2026-04-02'),
     ('COT003', 'MBR007', 'COL001', 'PERIODIQUE_MENSUELLE', 20000, 'MOBILE_MONEY', 'OM-2026-002', '2026-04-03'),
     ('COT004', 'MBR002', 'COL002', 'PERIODIQUE_MENSUELLE', 15000, 'ESPECE', NULL, '2026-04-01'),
     ('COT005', 'MBR013', 'COL002', 'PERIODIQUE_MENSUELLE', 15000, 'MOBILE_MONEY', 'MV-2026-001', '2026-04-02');

-- Insertion de comptes pour chaque collectivité (caisse + quelques comptes)
INSERT INTO compte (id, entite_type, entite_id, type_compte, solde, nom_titulaire, banque, numero_compte, service_mobile_money, numero_telephone) VALUES
     ('CPT001', 'COLLECTIVITE', 'COL001', 'CAISSE', 150000, NULL, NULL, NULL, NULL, NULL),
     ('CPT002', 'COLLECTIVITE', 'COL001', 'BANCAIRE', 500000, 'Collectivité Rizicole', 'BOA', '00005000010000000012345', NULL, NULL),
     ('CPT003', 'COLLECTIVITE', 'COL001', 'MOBILE_MONEY', 75000, 'Rakoto Jean', NULL, NULL, 'ORANGE_MONEY', '+261341112233'),
     ('CPT004', 'COLLECTIVITE', 'COL002', 'CAISSE', 80000, NULL, NULL, NULL, NULL, NULL),
     ('CPT005', 'FEDERATION', 'FEDERATION', 'CAISSE', 1000000, NULL, NULL, NULL, NULL, NULL),
     ('CPT006', 'FEDERATION', 'FEDERATION', 'BANCAIRE', 2500000, 'Fédération Agricole', 'MCB', '00001000020000000098765', NULL, NULL);

-- Insertion d'activités
INSERT INTO activite (id, entite_type, entite_id, type_activite, date, cible, obligatoire, statut, description) VALUES
     ('ACT001', 'COLLECTIVITE', 'COL001', 'ASSEMBLEE_GENERALE_MENSUELLE', '2026-04-10 09:00:00', 'TOUS', true, 'TERMINE', 'AG Avril 2026'),
     ('ACT002', 'COLLECTIVITE', 'COL001', 'FORMATION_JUNIORS', '2026-04-17 08:00:00', 'JUNIORS', true, 'PLANIFIE', 'Formation irrigation'),
     ('ACT003', 'COLLECTIVITE', 'COL002', 'ASSEMBLEE_GENERALE_MENSUELLE', '2026-04-12 10:00:00', 'TOUS', true, 'TERMINE', 'AG Avril 2026'),
     ('ACT004', 'FEDERATION', 'FEDERATION', 'ASSEMBLEE_FEDERATION', '2026-04-20 09:00:00', 'TOUS', true, 'PLANIFIE', 'Assemblée générale fédérale');

-- Insertion de présences
INSERT INTO presence (activite_id, membre_id, statut, motif_absence, date_notification, est_visiteur) VALUES
     ('ACT001', 'MBR001', 'PRESENT', NULL, NULL, false),
     ('ACT001', 'MBR006', 'PRESENT', NULL, NULL, false),
     ('ACT001', 'MBR007', 'EXCUSE', 'Déplacement', '2026-04-09', false),
     ('ACT001', 'MBR008', 'ABSENT', NULL, NULL, false),
     ('ACT003', 'MBR002', 'PRESENT', NULL, NULL, false),
     ('ACT003', 'MBR013', 'PRESENT', NULL, NULL, false),
     ('ACT003', 'MBR014', 'PRESENT', NULL, NULL, false);
