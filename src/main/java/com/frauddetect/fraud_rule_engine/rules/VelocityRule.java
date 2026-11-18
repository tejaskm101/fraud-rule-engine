package com.frauddetect.fraud_rule_engine.rules;

import com.frauddetect.fraud_rule_engine.decision.Rule;
import com.frauddetect.fraud_rule_engine.decision.RuleVerdict;
import com.frauddetect.fraud_rule_engine.decision.Transaction;
import com.frauddetect.fraud_rule_engine.state.VelocityStore;

import java.time.Duration;

public class VelocityRule implements Rule {
    public static final String CODE = "VELOCITY_EXCEEDED";

    private final VelocityStore store;
    private final int maxAttempts;
    private final Duration window;

    public VelocityRule(VelocityStore store, int maxAttempts, Duration window) {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be at least 1");
        }
        this.store = store;
        this.maxAttempts = maxAttempts;
        this.window = window;
    }
    @Override
    public String code() {
        return CODE;
    }
    @Override
    public RuleVerdict evaluate(Transaction transaction) {
        long count = store.recordAndCount(transaction.cardToken(), transaction.timestamp(), window);

        if (count > maxAttempts) {
            return RuleVerdict.match(
                    count + " attempts in " + window.toSeconds() + "s, limit " + maxAttempts);
        }
        return RuleVerdict.noMatch();
    }
}
