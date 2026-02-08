package org.springframework.samples.petclinic.feature;

/**
 * Static helper to check flags from anywhere in the codebase.
 */
public final class FeatureFlags {

    private FeatureFlags() {}

    public static boolean isEnabled(String name) {
        FeatureFlagEvaluator evaluator = SpringContext.getBean(FeatureFlagEvaluator.class);
        return evaluator.isEnabled(name);
    }

    public static boolean isEnabled(String name, String principal) {
        FeatureFlagEvaluator evaluator = SpringContext.getBean(FeatureFlagEvaluator.class);
        return evaluator.isEnabled(name, principal);
    }

}
