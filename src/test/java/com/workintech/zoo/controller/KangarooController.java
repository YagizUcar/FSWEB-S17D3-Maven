package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workintech/kangaroos")
public class KangarooController {

    private Map<Long, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public List<Kangaroo> findAll() {
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Kangaroo findById(@PathVariable long id) {
        checkId(id);
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.get(id);
    }

    @PostMapping
    public Kangaroo save(@RequestBody Kangaroo kangaroo) {
        checkId(kangaroo.getId());
        if (kangaroos.containsKey(kangaroo.getId())) {
            throw new ZooException("Kangaroo already exist with id: " + kangaroo.getId(), HttpStatus.BAD_REQUEST);
        }
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    @PutMapping("/{id}")
    public Kangaroo update(@PathVariable long id, @RequestBody Kangaroo kangaroo) {
        checkId(id);
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id not exist: " + id, HttpStatus.NOT_FOUND);
        }
        kangaroo.setId(id);
        kangaroos.put(id, kangaroo);
        return kangaroo;
    }

    @DeleteMapping("/{id}")
    public Kangaroo delete(@PathVariable long id) {
        checkId(id);
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given id not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.remove(id);
    }

    private void checkId(long id) {
        if (id <= 0) {
            throw new ZooException("Id must be greater than zero: " + id, HttpStatus.BAD_REQUEST);
        }
    }
}