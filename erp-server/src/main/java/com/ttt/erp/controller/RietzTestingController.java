package com.ttt.erp.controller;

import com.ttt.erp.model.RietzTesting;
import com.ttt.erp.repository.RietzTestingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class RietzTestingController {

    @Autowired
    private RietzTestingRepository rietzTestingRepository;

    @GetMapping("/testing")
    public List<RietzTesting> getRietzTestings() {
        return rietzTestingRepository.findAll();
    }

    @PostMapping("/testing")
    public RietzTesting newRietzTesting(@RequestBody RietzTesting newRietzTesting) {
        return rietzTestingRepository.save(newRietzTesting);
    }

}