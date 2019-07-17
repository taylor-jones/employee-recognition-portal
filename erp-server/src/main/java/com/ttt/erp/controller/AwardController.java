package com.ttt.erp.controller;

import com.ttt.erp.model.Award;
import com.ttt.erp.repository.AwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/awards")
public class AwardController {

    @Autowired
    AwardRepository awardRepository;

    @GetMapping("")
    public List<Award> getAll() {
        return awardRepository.findAll();
    }

    @GetMapping("/{id}")
    public List<Award> getAward(@PathVariable("id") final Long id) {
        return awardRepository.findById(id);
    }
}
