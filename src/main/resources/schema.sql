CREATE TABLE AUTHORITIES (
                                    id bigint NOT NULL,
                                    description varchar(50) NOT NULL,
                                    comments varchar(4000) NULL,
                                    record_date timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                    CONSTRAINT authorities__pk PRIMARY KEY (id)
);

CREATE TABLE users (
                              id int NOT NULL AUTO_INCREMENT,
                              first_name varchar(100) NOT NULL,
                              last_name varchar(100) NOT NULL,
                              username varchar(100) NOT NULL,
                              email varchar(100) NOT NULL,
                              password varchar(100) NOT NULL,
                              active int4 NOT NULL,
                              comments varchar(4000) NULL,
                              record_date timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT users__pk PRIMARY KEY (id),
                              CONSTRAINT users_em UNIQUE (email),
                              CONSTRAINT users_un UNIQUE (username)
);

CREATE TABLE friends (
                         id INT PRIMARY KEY,
                         user_id INT NOT NULL,
                         friend_id INT NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES users(id),
                         FOREIGN KEY (friend_id) REFERENCES users(id),
                         UNIQUE (user_id, friend_id)
);

CREATE TABLE groups (
                                id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                group_name VARCHAR(100) NOT NULL,
                                created_by INT NOT NULL,
                                FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE user_groups (
                                id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                user_id INT NOT NULL,
                                group_id INT NOT NULL,
                                FOREIGN KEY (user_id) REFERENCES users(id),
                                FOREIGN KEY (group_id) REFERENCES groups(id)
);


CREATE TABLE messages (
                                 id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                                 sender_id INT NOT NULL,
                                 group_id INT NOT NULL,
                                 content varchar NULL,
                                 recorddate timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                 CONSTRAINT fksender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
                                 CONSTRAINT fkgroup FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE
);


CREATE TABLE user_authorities (
                                  id bigint NOT NULL,
                                  user_id int4 NOT NULL,
                                  authority_id int4 NOT NULL,
                                  comments varchar(131072) NULL,
                                  recorddate timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                  CONSTRAINT pkusers_authorities PRIMARY KEY (id),
                                  CONSTRAINT user_authorities_un UNIQUE (user_id, authority_id),
                                  CONSTRAINT fkauthority FOREIGN KEY (authority_id) REFERENCES authorities(id) ON DELETE CASCADE,
                                  CONSTRAINT fkuser FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);