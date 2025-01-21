package com.MyProf.MyProf_Backend.repositories;

import com.MyProf.MyProf_Backend.models.Profile;
import com.MyProf.MyProf_Backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);
}
