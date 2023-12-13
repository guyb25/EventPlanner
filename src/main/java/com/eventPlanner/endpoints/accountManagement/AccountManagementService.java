package com.eventPlanner.endpoints.accountManagement;

import com.eventPlanner.dataAccess.UsersRepository;
import com.eventPlanner.models.schemas.User;
import com.eventPlanner.models.serviceResult.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountManagementService {
    private final UsersRepository usersRepo;


    @Autowired
    public AccountManagementService(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    public ServiceResult CreateUser(String name, String password, String email) {
        if (this.usersRepo.existsUserByName(name)) {
            return ServiceResult.USERNAME_TAKEN;
        }

        if (this.usersRepo.existsUserByEmail(email)) {
            return ServiceResult.EMAIL_TAKEN;
        }

        this.usersRepo.save(new User(name, password, email));
        return ServiceResult.USER_CREATED;
    }

}
