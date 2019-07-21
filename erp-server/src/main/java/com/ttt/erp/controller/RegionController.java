package com.ttt.erp.controller;

import com.ttt.erp.model.Region;
import com.ttt.erp.repository.RegionRepository;
import com.ttt.erp.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    @Autowired
    RegionRepository repository;

    @Autowired
    RegionService service;

    @GetMapping("/{id}")
    public Region getRegion(@PathVariable("id") final Long id) {
        return repository.findById(id);
    }

    // TODO: get actual userId from cookie
    @PostMapping
    public Optional<Region> addRegion (
        @CookieValue(value = "userId", defaultValue = "1") String modifiedById,
        @RequestBody Region newRegion) {
        return this.service.createRegion(Long.parseLong(modifiedById), newRegion);
    }

    // TODO: get actual userId from cookie
    @PutMapping("/{id}")
    public Optional<Region> updateRegionById (
        @CookieValue(value = "userId", defaultValue = "1") String modifiedById,
        @PathVariable("id") Long regionId,
        @RequestBody Region modified) {
        return this.service.updateRegion(Long.parseLong(modifiedById), regionId, modified);
    }

    // TODO: get actual userId from cookie
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegionById (
        @CookieValue(value = "userId", defaultValue = "1") String modifiedById,
        @PathVariable("id") Long regionId
    ) {
        return this.service.deleteRegion(Long.parseLong(modifiedById), regionId);
    }

    @GetMapping
    public List<Region> getAll() {
        return repository.findAll();
    }
}
