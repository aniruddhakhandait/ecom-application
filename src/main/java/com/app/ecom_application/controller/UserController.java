package com.app.ecom_application.controller;

import com.app.ecom_application.dto.UserRequest;
import com.app.ecom_application.dto.UserResponse;
import com.app.ecom_application.service.UserService;
import com.app.ecom_application.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor

// This is base url
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping

    // request mapping at method level
//    @RequestMapping (value = "/api/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponse>> getAllUsers (){
        // Better controller and customize response

      return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
        //        return ResponseEntity.ok(userService.fetchAllUsers());
    }


     // Dynamically get user
    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUser (@PathVariable Long id){
        return userService.fetchUser(id).map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }



    @PostMapping
    public ResponseEntity< String >createUser (@RequestBody UserRequest userRequest){
        userService.addUsers(userRequest);
        return ResponseEntity.ok("User added successfully");
    }

    @PutMapping("{id}")
    public ResponseEntity< String >updateUser (@PathVariable Long id , @RequestBody UserRequest updateUserRequest){
         boolean updated = userService.updateUser(id, updateUserRequest);
           if (updated)
        return ResponseEntity.ok("User updated successfully");
           return ResponseEntity.notFound().build();
    }



}
