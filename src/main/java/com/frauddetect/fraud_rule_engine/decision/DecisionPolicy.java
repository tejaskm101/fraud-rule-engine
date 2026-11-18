package com.frauddetect.fraud_rule_engine.decision;

public final class DecisionPolicy {

    private final int flagThreshold;
    private final int blockThreshold;

    public DecisionPolicy(int flagThreshold, int blockThreshold) {
        if (flagThreshold >= blockThreshold) {
            throw new IllegalArgumentException("flagThreshold must be below blockThreshold");
        }
        this.flagThreshold = flagThreshold;
        this.blockThreshold = blockThreshold;
    }

    public static DecisionPolicy defaults() {
        return new DecisionPolicy(40, 80);
    }

    public Outcome outcomeFor(int score) {
        if (score >= blockThreshold) {
            return Outcome.BLOCK;
        }
        if (score >= flagThreshold) {
            return Outcome.FLAG;
        }
        return Outcome.ALLOW;
    }
}
