CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    amount NUMERIC(19,2) NOT NULL,
    description VARCHAR(255),
    target_account_id BIGINT,
    created_at TIMESTAMP NOT NULL
); 