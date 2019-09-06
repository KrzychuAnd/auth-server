-- The encrypted client_secret it `secret`
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity)
  VALUES ('clientId', '{bcrypt}$2a$10$vCXMWCn7fDZWOcLnIEhmK.74dvK1Eh8ae2WrWlhr2ETPLoxQctN4.', 'read,write', 'password,refresh_token,client_credentials', 'ROLE_CLIENT', 300);

-- The encrypted password is `pass`
INSERT INTO users (username, password, enabled) VALUES ('user', '{bcrypt}$2a$10$cyf5NfobcruKQ8XGjUJkEegr9ZWFqaea6vjpXWEaSqTa2xL9wjgQC', 1);
INSERT INTO users (username, password, enabled) VALUES ('guest', '{bcrypt}$2a$10$cyf5NfobcruKQ8XGjUJkEegr9ZWFqaea6vjpXWEaSqTa2xL9wjgQC', 1);

INSERT INTO authorities (authority) VALUES ('ROLE_USER');
INSERT INTO authorities (authority) VALUES ('ROLE_GUEST');

INSERT INTO users_authorities (user_id, authority_id)
    SELECT id, (SELECT id FROM authorities WHERE authority = 'ROLE_USER')
        FROM users WHERE username = 'user';

INSERT INTO users_authorities (user_id, authority_id)
    SELECT id, (SELECT id FROM authorities WHERE authority = 'ROLE_GUEST')
        FROM users WHERE username = 'guest';
