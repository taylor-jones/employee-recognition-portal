package com.ttt.erp.controller;

import com.ttt.erp.model.Award;
import com.ttt.erp.model.Employee;
import com.ttt.erp.model.Region;
import com.ttt.erp.repository.RegionRepository;
import com.ttt.erp.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    /**
     * Get all the employees for a particular region
     * @param id - the region.id
     * @return JSON array of employee objects, empty array if none found
     */
    @GetMapping("/{id}/employees")
    public Set<Employee> getEmployees(@PathVariable("id") final Long id) {
        Region region = this.repository.findById(id);
        return this.service.findEmployees(region);
    }


    /**
     * Get the total number of awards give to each region
     * @return JSON array
     */
    @GetMapping("/awards/totals")
    public List<Object[]> getAwardCounts() {
        return this.service.getAwardCountsForRegions();
    }



    /**
     * Get all the awards for a particular region
     * @param id - the region.id
     * @return JSON array of award objects, empty array if none found
     */
    @GetMapping("/{id}/awards")
    public List<Award> getAwards(@PathVariable("id") final Long id) {
        Region region = this.repository.findById(id);
        return this.service.findAwards(region);
    }


    @PostMapping
    public Optional<Region> addRegion (
        @CookieValue(value = "userId") String modifiedById,
        @RequestBody Region newRegion) {
        return this.service.createRegion(Long.parseLong(modifiedById), newRegion);
    }


    @PutMapping("/{id}")
    public Optional<Region> updateRegionById (
        @CookieValue(value = "userId") String modifiedById,
        @PathVariable("id") Long regionId,
        @RequestBody Region modified) {
        return this.service.updateRegion(Long.parseLong(modifiedById), regionId, modified);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegionById (
        @CookieValue(value = "userId") String modifiedById,
        @PathVariable("id") Long regionId
    ) {
        return this.service.deleteRegion(Long.parseLong(modifiedById), regionId);
    }

    @GetMapping
    public List<Region> getAll() {
        return repository.findAll();
    }
}
