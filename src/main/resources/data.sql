INSERT INTO AUTHORITIES
(id, description, comments, record_date)
VALUES(1, 'ROLE_USER', NULL, '2023-01-28 13:21:20.210');
INSERT INTO AUTHORITIES
(id, description, comments, record_date)
VALUES(2, 'ROLE_PROFESSIONAL', NULL, '2023-01-28 13:21:39.800');
INSERT INTO AUTHORITIES
(id, description, comments, record_date)
VALUES(3, 'ROLE_ADMIN', NULL, '2023-01-28 13:21:47.692');
INSERT INTO AUTHORITIES
(id, description, comments, record_date)
VALUES(4, 'ROLE_PREMIUM_PROFESSIONAL', NULL, '2023-01-28 13:21:39.800');


INSERT INTO users
(id, first_name, last_name, username, email, password, active, comments, record_date)
VALUES(8, 'Sotiris', 'Patsimas','Sotiris', 'sotirinio@hotmail.com', '$2a$10$IuHe0CeAnMUNqMoLUvB7UOiIty7lWslBM7MCHV8yH/lnZ0i6wJzDa', 1, 'pwd: sotii', '2023-03-21 10:34:30.043');
INSERT INTO users
(id, first_name, last_name, username, email, password, active, comments, record_date)
VALUES(11, 'Giannis', 'Sevaslidis','Giannis', 'sevas@hotmail.com', '$2a$10$TCRAhVtDWq8KAArODSkPGOkmBX9FEMTWiVVkpzr7myqsICB/2Y9mC', 1, 'pwd: tot', '2023-03-21 10:42:10.505');
INSERT INTO users
(id, first_name, last_name, username, email, password, active, comments, record_date)
VALUES(9, 'Tasos', 'Bolosis','Tasos', 'tasos@hotmail.com', '$2a$10$98I75E2tecubE1jaMZwPVec5ofq7q4xPKeMrBtKEtmeY1msFjAUr2', 1, 'pwd: sportsman', '2023-03-21 10:36:55.733');
INSERT INTO users
(id, first_name, last_name, username, email, password, active, comments, record_date)
VALUES(10, 'Andriana', 'Bolosi','Andriana', 'andri@hotmail.com', '$2a$10$bh/zlnssrpfMbhWOBht6.ePlzoo64wa8VnipRnR0heOjSLaWoXu..', 1, 'pwd: andrianaki', '2023-03-21 10:38:54.233');
INSERT INTO users
(id, first_name, last_name, username, email, password, active, comments, record_date)
VALUES(7, 'Chris', 'Bolosis','Chris', 'bolositsious@gmail.com', '$2a$10$P1mVw5xzuWNsQk0mHu..t.2fszBWw6yT0nXvwT75Qa/K8EV4YQrRa', 1, 'pwd: bolo7', '2023-03-21 10:31:39.421');


INSERT INTO user_authorities
(id, user_id, authority_id, comments, recorddate)
VALUES(40, 8, 2, NULL, '2023-04-11 15:49:11.405');
INSERT INTO user_authorities
(id, user_id, authority_id, comments, recorddate)
VALUES(41, 8, 1, NULL, '2023-04-11 15:49:11.405');
INSERT INTO user_authorities
(id, user_id, authority_id, comments, recorddate)
VALUES(15, 10, 2, NULL, '2023-03-21 10:38:54.233');
INSERT INTO user_authorities
(id, user_id, authority_id, comments, recorddate)
VALUES(16, 11, 2, NULL, '2023-03-21 10:42:10.505');
INSERT INTO user_authorities
(id, user_id, authority_id, comments, recorddate)
VALUES(21, 7, 1, NULL, '2023-04-06 12:55:24.328');
INSERT INTO user_authorities
(id, user_id, authority_id, comments, recorddate)
VALUES(14, 9, 1, NULL, '2023-03-21 10:36:55.733');
