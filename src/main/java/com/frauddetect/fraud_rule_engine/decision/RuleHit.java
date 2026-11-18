package com.frauddetect.fraud_rule_engine.decision;

public record RuleHit(String ruleCode, int weight, String reason, boolean shadow) {
}
