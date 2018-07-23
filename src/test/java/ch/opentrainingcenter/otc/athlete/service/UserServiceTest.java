package ch.opentrainingcenter.otc.athlete.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ch.opentrainingcenter.otc.athlete.domain.User;
import ch.opentrainingcenter.otc.athlete.repositories.UserRepo;

public class UserServiceTest {

	private UserService service;
	@Mock
	private UserRepo repo;

	@Mock
	User user;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new UserService(repo);
	}

	@Test
	void testSaveUser() {
		service.saveUser(user);

		verify(repo, Mockito.times(1)).save(user);
		verifyNoMoreInteractions(repo);

	}

	@Test
	void testFindAll() {
		service.findAll();

		verify(repo, Mockito.times(1)).findAll();
		verifyNoMoreInteractions(repo);
	}

	@Test
	void testFindById() {

		service.findById(Mockito.anyLong());

		verify(repo, Mockito.times(1)).findById(Mockito.anyLong());
		verifyNoMoreInteractions(repo);
	}

	@Test
	void testCreateUser() {
		service.createUser(user);

		verify(repo, Mockito.times(1)).save(user);
		verifyNoMoreInteractions(repo);
	}

}
