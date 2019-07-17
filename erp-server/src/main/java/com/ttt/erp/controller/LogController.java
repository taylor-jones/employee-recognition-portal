package com.ttt.erp.controller;

import com.ttt.erp.model.Log;
import com.ttt.erp.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    LogRepository logRepository;

    @GetMapping("")
    public List<Log> getAll() {
        return logRepository.findAll();
    }

    @GetMapping("/{id}")
    public List<Log> getLog(@PathVariable("id") final Long id) {
        return logRepository.findById(id);
    }
}
