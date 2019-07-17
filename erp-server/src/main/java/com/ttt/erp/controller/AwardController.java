package com.ttt.erp.controller;

import com.ttt.erp.repository.AwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/awards")
public class AwardController {

    @Autowired
    AwardRepository awardRepository;

}
