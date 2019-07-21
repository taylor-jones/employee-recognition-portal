package com.ttt.erp.controller;

import com.ttt.erp.model.Log;
import com.ttt.erp.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
*  TR: For obvious reasons we don't want to expose POST/PUT/DELETE endpoints here. When
*  we're logging on an entity, I think that should happen with the LogRepository. We
*  probably also want to think about creating a LoggedEntityRepository or
*  something similar that other repositories can extend.
*/ 
@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    LogRepository repo;

    @GetMapping("")
    public List<Log> getAll() {
        return this.repo.findAll();
    }

    @GetMapping("/{id}")
    public List<Log> getLog(@PathVariable("id") final Long id) {
        return this.repo.findById(id);
    }
}
