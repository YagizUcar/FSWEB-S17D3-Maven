package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workintech/koalas")
public class KoalaController {

    private Map<Long, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> findAll() {
        return koalas.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Koala findById(@PathVariable long id) {
        checkId(id);
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with given id not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return koalas.get(id);
    }

    @PostMapping
    public Koala save(@RequestBody Koala koala) {
        checkId(koala.getId());
        if (koalas.containsKey(koala.getId())) {
            throw new ZooException("Koala already exist with id: " + koala.getId(), HttpStatus.BAD_REQUEST);
        }
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/{id}")
    public Koala update(@PathVariable long id, @RequestBody Koala koala) {
        checkId(id);
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with given id not exist: " + id, HttpStatus.NOT_FOUND);
        }
        koala.setId(id);
        koalas.put(id, koala);
        return koala;
    }

    @DeleteMapping("/{id}")
    public Koala delete(@PathVariable long id) {
        checkId(id);
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with given id not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return koalas.remove(id);
    }

    private void checkId(long id) {
        if (id <= 0) {
            throw new ZooException("Id must be greater than zero: " + id, HttpStatus.BAD_REQUEST);
        }
    }
}