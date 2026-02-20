CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users
        FOREIGN KEY(username)
        REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username
ON authorities(username, authority);

INSERT INTO users (username, password, enabled)
VALUES (
    'user',
    '$2a$12$pe9sN8OMX.wCrcuZPfY/kulaTy0uTB4vYfVxwsuz95RhyAE6fNAl6',
    true
);

INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_USER');