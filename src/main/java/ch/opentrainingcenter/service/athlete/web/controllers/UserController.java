package ch.opentrainingcenter.service.athlete.web.controllers;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ch.opentrainingcenter.service.athlete.domain.User;
import ch.opentrainingcenter.service.athlete.service.UserService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/user")
@Slf4j
public class UserController {

    private UserService service;

    public UserController(@Autowired UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/getAll")
    @ResponseBody
    public List<User> getAll() {
        log.info("getAll");
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<User> getById(@PathVariable Long id) {
        log.info("getById");
        final Optional<User> byId = service.findById(id);
        if (!byId.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(byId.get(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser=  service.saveUser(user);

        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
