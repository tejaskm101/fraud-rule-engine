package com.frauddetect.fraud_rule_engine.decision;

public interface Rule {
    String code();
    RuleVerdict evaluate(Transaction transaction);
}
