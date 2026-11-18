package com.frauddetect.fraud_rule_engine.state;

import java.time.Duration;
import java.time.Instant;

public interface VelocityStore {
    long recordAndCount(String cardToken, Instant timestamp, Duration window);
}
