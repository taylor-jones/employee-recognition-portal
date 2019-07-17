package com.ttt.erp.controller;

import com.ttt.erp.repository.AwardTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/awardTypes")
public class AwardTypeController {

    @Autowired
    AwardTypeRepository awardTypeRepository;

}
