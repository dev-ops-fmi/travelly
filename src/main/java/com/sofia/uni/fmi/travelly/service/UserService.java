package com.sofia.uni.fmi.travelly.service;

import com.sofia.uni.fmi.travelly.dto.TripCreateDto;
import com.sofia.uni.fmi.travelly.exception.ResourceNotFoundException;
import com.sofia.uni.fmi.travelly.mapper.UserMapper;
import com.sofia.uni.fmi.travelly.model.Trip;
import com.sofia.uni.fmi.travelly.model.User;
import com.sofia.uni.fmi.travelly.dto.UserDto;
import com.sofia.uni.fmi.travelly.repository.TripRepository;
import com.sofia.uni.fmi.travelly.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private TripService tripService;
    private TripRepository tripRepository;

    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException("No user present");
        }

        return userOptional.get();
    }

    public Long authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user.getPassword().equals(password)) {
            return user.getId();
        }

        return null;
    }

    public Long addUser(User user) {
        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    public Long updateUser(User user) {
        List<Trip> trips = userRepository.findById(user.getId()).get().getTrips();
        user.setTrips(trips);
        return userRepository.save(user).getId();
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<Trip> getTripsByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException("No user present");
        }

        return userOptional.get().getTrips();
    }

    public Long addTrip(Long userId, TripCreateDto tripCreateDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException("No user present");
        }

        User user = userOptional.get();
        Trip trip = tripService.constructTripEntityBy(tripCreateDto, user);

        tripRepository.save(trip);

        user.getTrips().add(trip);
        userRepository.save(user);

        return trip.getId();
    }

    public User constructUserEntityBy(UserDto userDto, Trip trip) {
        User user = userMapper.toEntity(userDto);
        user.getTrips().add(trip);

        return user;
    }
}
