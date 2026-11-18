package com.frauddetect.fraud_rule_engine.decision;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class RuleEngine {

    public record Registration(Rule rule, int weight, boolean shadow) {
    }

    private final List<Registration> registrations;
    private final DecisionPolicy policy;

    public RuleEngine(List<Registration> registrations, DecisionPolicy policy) {
        this.registrations = List.copyOf(registrations);
        this.policy = Objects.requireNonNull(policy, "policy");
    }

    public DecisionResult decide(Transaction transaction) {
        int score = 0;
        List<RuleHit> hits = new ArrayList<>();

        for (Registration registration : registrations) {
            RuleVerdict verdict = registration.rule().evaluate(transaction);
            if (!verdict.triggered()) {
                continue;
            }

            hits.add(new RuleHit(
                    registration.rule().code(),
                    registration.weight(),
                    verdict.reason(),
                    registration.shadow()));

            if (!registration.shadow()) {
                score += registration.weight();
            }
        }
        return new DecisionResult(policy.outcomeFor(score), score, hits);
    }
}
