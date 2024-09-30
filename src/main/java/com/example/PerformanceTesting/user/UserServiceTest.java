package com.example.PerformanceTesting.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = new User("john_doe");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser("john_doe");

        assertNotNull(createdUser);
        assertEquals("john_doe", createdUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUser_withEmptyUsername_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser("");
        });
    }

    @Test
    public void testFindUserByUsername() {
        User user = new User("john_doe");
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findUserByUsername("john_doe");

        assertTrue(foundUser.isPresent());
        assertEquals("john_doe", foundUser.get().getUsername());
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
