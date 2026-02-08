package org.springframework.samples.petclinic.feature;

import org.springframework.stereotype.Component;

@Component
public class FeatureFlagEvaluator {

    private final FeatureFlagService service;

    public FeatureFlagEvaluator(FeatureFlagService service) {
        this.service = service;
    }

    public boolean isEnabled(String name) {
        return service.isEnabled(name, null);
    }

    public boolean isEnabled(String name, String principal) {
        return service.isEnabled(name, principal);
    }

}
