package com.frauddetect.fraud_rule_engine.decision;

public record RuleVerdict(boolean triggered, String reason) {
    public static RuleVerdict noMatch() {
        return new RuleVerdict(false, "");
    }
    public static RuleVerdict match(String reason) {
        return new RuleVerdict(true, reason);
    }
}
