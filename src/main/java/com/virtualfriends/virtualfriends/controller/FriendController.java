package com.virtualfriends.virtualfriends.controller;

import com.virtualfriends.virtualfriends.model.Friend;
import com.virtualfriends.virtualfriends.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class FriendController {

    @Autowired
    FriendRepository repo;

    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @PostMapping("/friends")
    public ResponseEntity<Friend> addFriend(@RequestBody Friend friend) {
        try {

            // if the put request comes without url fields
            if(friend.getAvatarUrl() == null || friend.getIconUrl() == null) {
                friend = new Friend(friend.getName(), friend.getAdjective());
            }

            Friend _friend = repo.save(friend);
            return new ResponseEntity<>(_friend, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/friends")
    public ResponseEntity<List<Friend>> getAllFriends(@RequestParam(required = false) String name) {
        try {
            List<Friend> friends = new ArrayList<>();

            if(name != null) {
                repo.findByNameContaining(name).forEach(friends::add);
            } else {
                repo.findAll().forEach(friends::add);
            }

            if (friends.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(friends, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/friends/{id}")
    public ResponseEntity<Friend> getFriendById(@PathVariable String id) {
        Optional<Friend> friend = repo.findById(id);

        if (friend.isPresent()) {
            return new ResponseEntity<>(friend.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/friends/{id}")
    public ResponseEntity<Friend> updateFriend(@PathVariable String id, @RequestBody Friend friend) {
        Optional<Friend> friendToUpdate = repo.findById(id);

        if (friendToUpdate.isPresent()) {
            Friend _friend = friendToUpdate.get();

            // if the put request comes without url fields
            if(friend.getAvatarUrl() == null || friend.getIconUrl() == null) {
                friend = new Friend(friend.getName(), friend.getAdjective());
            }

            _friend.setName(friend.getName());
            _friend.setAdjective(friend.getAdjective());
            _friend.setAvatarUrl(friend.getAvatarUrl());
            _friend.setIconUrl(friend.getIconUrl());
            return new ResponseEntity<>(repo.save(_friend), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/friends/{id}")
    public ResponseEntity<HttpStatus> deleteFriend(@PathVariable String id){
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
