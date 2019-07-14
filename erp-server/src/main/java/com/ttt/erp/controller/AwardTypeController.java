package com.ttt.erp.controller;

import com.ttt.erp.model.AwardType;
import com.ttt.erp.repository.AwardTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/awardTypes")
public class AwardTypeController {

    @Autowired
    AwardTypeRepository awardTypeRepository;

    @GetMapping("")
    public List<AwardType> getAll() {
        return awardTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    public List<AwardType> getAwardType(@PathVariable("id") final Long id) {
        return awardTypeRepository.findById(id);
    }
}
