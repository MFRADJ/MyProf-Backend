package com.MyProf.MyProf_Backend.user;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUserById(Long id);
    Optional<User> findByEmail(String email);


}
