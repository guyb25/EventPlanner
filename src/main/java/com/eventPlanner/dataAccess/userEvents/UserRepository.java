package com.eventPlanner.dataAccess.userEvents;

import com.eventPlanner.models.schemas.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByName(String name);
    boolean existsUserByEmail(String email);
    boolean existsByNameAndPassword(String name, String password);
    User findUserByName(String name);
    User findUserById(Long id);
}
