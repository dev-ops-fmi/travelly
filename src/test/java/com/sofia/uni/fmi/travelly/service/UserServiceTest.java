package com.sofia.uni.fmi.travelly.service;

import com.sofia.uni.fmi.travelly.exception.ResourceNotFoundException;
import com.sofia.uni.fmi.travelly.mapper.UserMapper;
import com.sofia.uni.fmi.travelly.model.User;
import com.sofia.uni.fmi.travelly.repository.TripRepository;
import com.sofia.uni.fmi.travelly.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TripService tripService;

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserById_ExistingUser_ReturnsUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    void getUserById_NonExistingUser_ThrowsResourceNotFoundException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
    }

    // Add more tests for other methods in UserService

}
