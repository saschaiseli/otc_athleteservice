package ch.opentrainingcenter.otc.athlete.web.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.opentrainingcenter.otc.athlete.domain.User;
import ch.opentrainingcenter.otc.athlete.service.UserService;
import ch.opentrainingcenter.otc.athlete.web.controllers.UserController;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@Mock
	private UserService service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		final UserController controller = new UserController(service);
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void getAll() throws Exception {
		final List<User> users = new ArrayList<>();
		final User user = new User();
		user.setId(0);
		user.setFirstname("Sascha");
		user.setLastname("Iseli");
		users.add(user);
		when(service.findAll()).thenReturn(users);

		mvc.perform(get("/api/user/getAll").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string("[{\"id\":0,\"firstname\":\"Sascha\",\"lastname\":\"Iseli\"}]"))
				.andExpect(jsonPath("$", hasSize(1))).andDo(print()).andExpect(jsonPath("$[0].id", is(0)))
				.andExpect(jsonPath("$[0].firstname", is(user.getFirstname())))
				.andExpect(jsonPath("$[0].lastname", is(user.getLastname())));

		verify(service, times(1)).findAll();
	}

	@Test
	void getByIdFound() throws Exception {
		final User user = new User();
		user.setId(0);
		user.setFirstname("Sascha");
		user.setLastname("Iseli");
		when(service.findById(0L)).thenReturn(Optional.of(user));

		mvc.perform(get("/api/user/0")).andExpect(status().isOk())
				.andExpect(content().string("{\"id\":0,\"firstname\":\"Sascha\",\"lastname\":\"Iseli\"}"))
				.andExpect(jsonPath("$.id", is(0))).andExpect(jsonPath("$.firstname", is(user.getFirstname())))
				.andExpect(jsonPath("$.lastname", is(user.getLastname())));

		verify(service, times(1)).findById(0L);
	}

	@Test
	void getByIdNotFound() throws Exception {
		when(service.findById(1L)).thenReturn(Optional.empty());

		mvc.perform(get("/api/user/1")).andExpect(status().is4xxClientError());

		verify(service, times(1)).findById(1L);
	}

	@Ignore
	void createUser() throws Exception {
		final User user = new User();
		user.setId(0);
		user.setFirstname("Sascha");
		user.setLastname("Iseli");

		mvc.perform(post("/api/user/{id}", String.valueOf(user.getId())).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(user))).andExpect(status().isOk());

		verify(service, times(1)).createUser(user);
	}

	public String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}