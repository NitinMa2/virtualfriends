package com.virtualfriends.virtualfriends.repository;

import com.virtualfriends.virtualfriends.model.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FriendRepository extends MongoRepository<Friend,String> {

    List<Friend> findByNameContaining(String name);

}
