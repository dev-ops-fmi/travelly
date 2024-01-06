package com.sofia.uni.fmi.travelly.controller;

import com.sofia.uni.fmi.travelly.dto.TripCreateDto;
import com.sofia.uni.fmi.travelly.dto.TripListDto;
import com.sofia.uni.fmi.travelly.dto.UserDto;
import com.sofia.uni.fmi.travelly.dto.UserLoginDto;
import com.sofia.uni.fmi.travelly.dto.UserRegistrationDto;
import com.sofia.uni.fmi.travelly.mapper.TripMapper;
import com.sofia.uni.fmi.travelly.mapper.UserMapper;
import com.sofia.uni.fmi.travelly.mapper.UserRegistrationMapper;
import com.sofia.uni.fmi.travelly.model.Trip;
import com.sofia.uni.fmi.travelly.model.User;
import com.sofia.uni.fmi.travelly.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService service;
    private TripMapper tripMapper;
    private UserMapper userMapper;
    private UserRegistrationMapper userRegistrationMapper;

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        User user = service.getUserById(userId);
        return userMapper.toDto(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Long>> login(@RequestBody UserLoginDto userLoginDto) {
        Long userId = service.authenticateUser(userLoginDto.getUsername(), userLoginDto.getPassword());

        if (userId != null) {
            Map<String, Long> response = new HashMap<>();
            response.put("userId", userId);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> addUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        User user = userRegistrationMapper.toEntity(userRegistrationDto);
        Long userId = service.addUser(user);
        user.setId(userId);
        UserDto addedUserDto = userMapper.toDto(user);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(addedUserDto);
    }

    @PatchMapping
    public Long updateUserById(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return service.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        service.deleteUserById(userId);
    }

    @GetMapping("/{userId}/trips")
    public List<TripListDto> getTrips(@PathVariable Long userId) {
        return service.getTripsByUserId(userId)
                .stream()
                .map(trip -> tripMapper.toListDto(trip))
                .collect(Collectors.toList());
    }
  
    public List<Trip> getTripsByUserId(@PathVariable Long userId) {
        return service.getTripsByUserId(userId);
    }

    @PostMapping("{userId}/trips")
    public Long addTrip(@PathVariable Long userId, @RequestBody TripCreateDto tripCreateDto) {
        return service.addTrip(userId, tripCreateDto);
    }
}
