package com.frauddetect.fraud_rule_engine.decision;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public record Transaction(String cardToken, BigDecimal amount, String currency, Instant timestamp) {
    public Transaction {
        Objects.requireNonNull(cardToken, "cardToken");
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(currency, "currency");
        Objects.requireNonNull(timestamp, "timestamp");
    }
}
