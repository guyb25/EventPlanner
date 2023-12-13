package com.eventPlanner.dataAccess;

import com.eventPlanner.models.schemas.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    boolean existsUserByName(String name);
    boolean existsUserByEmail(String email);
}
