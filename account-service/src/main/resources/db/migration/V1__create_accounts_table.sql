CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    account_number VARCHAR(32) NOT NULL UNIQUE,
    type VARCHAR(20) NOT NULL,
    balance NUMERIC(19,2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
); 