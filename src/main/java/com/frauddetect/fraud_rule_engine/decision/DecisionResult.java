package com.frauddetect.fraud_rule_engine.decision;

import java.util.List;

public record DecisionResult(Outcome outcome, int score, List<RuleHit> hits) {
    public DecisionResult {
        hits = List.copyOf(hits);
    }
}