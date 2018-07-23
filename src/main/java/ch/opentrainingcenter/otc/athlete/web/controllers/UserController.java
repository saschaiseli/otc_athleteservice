package ch.opentrainingcenter.otc.athlete.web.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ch.opentrainingcenter.otc.athlete.domain.User;
import ch.opentrainingcenter.otc.athlete.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/user")
@Slf4j
public class UserController {

	private final UserService service;

	public UserController(@Autowired final UserService service) {
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
	public ResponseEntity<User> getById(@PathVariable final Long id) {
		log.info("getById");
		final Optional<User> byId = service.findById(id);
		if (!byId.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(byId.get(), HttpStatus.OK);
		}
	}

	@PostMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<User> createUser(@RequestBody final User user) {
		final User createdUser = service.createUser(user);

		final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdUser.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
}
