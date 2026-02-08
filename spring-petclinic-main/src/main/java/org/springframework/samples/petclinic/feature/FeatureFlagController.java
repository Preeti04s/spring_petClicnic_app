package org.springframework.samples.petclinic.feature;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feature-flags")
public class FeatureFlagController {

    private final FeatureFlagService service;

    public FeatureFlagController(FeatureFlagService service) {
        this.service = service;
    }

    @GetMapping
    public List<FeatureFlag> list() {
        return service.findAll();
    }

    @GetMapping("/{name}")
    public FeatureFlag getByName(@PathVariable String name) {
        Optional<FeatureFlag> maybe = service.findByName(name);
        return maybe.orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, "Feature flag not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FeatureFlag create(@RequestBody FeatureFlag flag) {
        flag.setId(null);
        return service.save(flag);
    }

    @PutMapping("/{id}")
    public FeatureFlag update(@PathVariable Integer id, @RequestBody FeatureFlag flag) {
        flag.setId(id);
        return service.save(flag);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        this.service.deleteById(id);
    }

}
