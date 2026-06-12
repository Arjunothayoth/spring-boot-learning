package com.codewithmosh.store.controller;

import com.codewithmosh.store.Dtos.ChangePasswordRequest;
import com.codewithmosh.store.Dtos.RegisterUserRequest;
import com.codewithmosh.store.Dtos.UpdateUserRequest;
import com.codewithmosh.store.Dtos.UserDto;
import com.codewithmosh.store.Mapper.UserMapper;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

//    @GetMapping
    //we can remove /user from get mapping and add requestMapping at top with /user
//    public Iterable<User> getAllUsers() {
//        return userRepository.findAll();
//    }

    //TODO:-change user to userDto
//    @GetMapping
//    public Iterable<UserDto> getAllUsers() {

    /// /        return userRepository.findAll().stream()
    /// /                .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail())).toList();
//        //TODO: use with userMapper
//        return userRepository.findAll().stream()
//                .map(userMapper::toDto).toList();
//    }

    //TODO: query parameter
//    @GetMapping
//    public Iterable<UserDto> getAllUsers(
//            @RequestParam(required = false,defaultValue = "",name = "sort") String sortBy) {
//        if (!Set.of("name", "email").contains(sortBy))
//            sortBy = "name";
//        return userRepository.findAll(Sort.by(sortBy))
//                .stream()
//                .map(userMapper::toDto)
//                .toList();
//    }

    //TODO: add hearders
    @GetMapping
    public Iterable<UserDto> getAllUsers(
            @RequestHeader(required = false, name = "x-auth-token") String authToken,
            @RequestParam(required = false, defaultValue = "", name = "sort") String sortBy) {
        if (!Set.of("name", "email").contains(sortBy))
            sortBy = "name";
        System.out.println("auth token :" + authToken);
        return userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }


//    @GetMapping("/{id}")
//    public User getUser(@PathVariable Long id) {
//        return userRepository.findById(id).orElse(null);
//    }

    //HTTP status code handling
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUser(@PathVariable Long id) {
//        var user = userRepository.findById(id).orElse(null);
//        if (user == null) {
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            // new approach
//            return ResponseEntity.notFound().build();
//        }
//

    /// /        return new ResponseEntity<>(user, HttpStatus.OK);
//        //new approach
//        return ResponseEntity.ok(user);
//    }


    //TODO:-change user to userDto
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
//        var userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        //TODO: use with userMapper
        var userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }

    //TODO:-post
//    @PostMapping
//    public UserDto createUser(@RequestBody UserDto data) {
//        return data;
//    }

    //TODO : change to register user request
//    @PostMapping
//    public UserDto createUser(@RequestBody RegisterUserRequest request) {
//        var user = userMapper.toEntity(request);
//        userRepository.save(user);//save to db
//        var userDto = userMapper.toDto(user);
//        System.out.println(user);
//        return userDto;
//    }

    //TODO:- change to Response Entity
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody RegisterUserRequest request,
                                              UriComponentsBuilder uriBuilder) {
        var user = userMapper.toEntity(request);
        userRepository.save(user);
        var userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/user/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    //TODO:-update
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") Long id,
                                              @RequestBody UpdateUserRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userMapper.update(request, user);
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    //TODO:-DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id,
                                               @RequestBody ChangePasswordRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if (!user.getPassword().equals(request.getOldPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return ResponseEntity.noContent().build();

    }


}