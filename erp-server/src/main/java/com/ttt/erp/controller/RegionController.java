package com.ttt.erp.controller;

import com.ttt.erp.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    @Autowired
    RegionRepository regionRepository;

}
