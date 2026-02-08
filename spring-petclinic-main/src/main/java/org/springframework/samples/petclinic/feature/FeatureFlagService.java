package org.springframework.samples.petclinic.feature;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class FeatureFlagService {

    private final FeatureFlagRepository repository;

    public FeatureFlagService(FeatureFlagRepository repository) {
        this.repository = repository;
    }

    public List<FeatureFlag> findAll() {
        return repository.findAll();
    }

    public Optional<FeatureFlag> findByName(String name) {
        return repository.findByName(name);
    }

    public FeatureFlag save(FeatureFlag flag) {
        return repository.save(flag);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public boolean isEnabled(String name, String principal) {
        Optional<FeatureFlag> maybe = repository.findByName(name);
        if (maybe.isEmpty()) {
            return false;
        }
        FeatureFlag flag = maybe.get();
        if (flag.getFlagType() == null) {
            return Boolean.TRUE.equals(flag.getEnabled());
        }

        switch (flag.getFlagType()) {
            case GLOBAL_DISABLE:
                return false;
            case BOOLEAN:
                return Boolean.TRUE.equals(flag.getEnabled());
            case WHITELIST:
                if (principal == null) return false;
                return listContains(flag.getWhitelist(), principal);
            case BLACKLIST:
                if (principal == null) return Boolean.TRUE.equals(flag.getEnabled());
                if (listContains(flag.getBlacklist(), principal)) return false;
                return Boolean.TRUE.equals(flag.getEnabled());
            case PERCENTAGE:
                int pct = flag.getPercentage() == null ? 0 : flag.getPercentage();
                if (pct <= 0) return false;
                if (pct >= 100) return true;
                int bucket = computeBucket(principal);
                return bucket < pct;
            default:
                return Boolean.TRUE.equals(flag.getEnabled());
        }
    }

    private boolean listContains(String listCsv, String value) {
        if (listCsv == null || listCsv.isBlank()) return false;
        return Arrays.stream(listCsv.split(","))
                .map(String::trim)
                .anyMatch(s -> s.equalsIgnoreCase(value));
    }

    private int computeBucket(String principal) {
        if (principal == null || principal.isBlank()) {
            // random for anonymous
            return (int) (Math.random() * 100);
        }
        byte[] bytes = principal.getBytes(StandardCharsets.UTF_8);
        int hash = 0;
        for (byte b : bytes) {
            hash = (hash * 31) + (b & 0xff);
        }
        int positive = Math.abs(hash);
        return positive % 100;
    }

}
