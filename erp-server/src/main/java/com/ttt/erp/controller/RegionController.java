package com.ttt.erp.controller;

import com.ttt.erp.model.Region;
import com.ttt.erp.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    @Autowired
    RegionRepository regionRepository;

    @GetMapping("")
    public List<Region> getAll() {
        return regionRepository.findAll();
    }

    @GetMapping("/{id}")
    public List<Region> getRegion(@PathVariable("id") final Long id) {
        return regionRepository.findById(id);
    }
}
