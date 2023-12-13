package com.eventPlanner.endpoints.accountManagement;

import com.eventPlanner.dataAccess.userEvents.UsersRepository;
import com.eventPlanner.dataAccess.sessions.SessionManager;
import com.eventPlanner.models.schemas.User;
import com.eventPlanner.models.serviceResult.ServiceResult;
import com.eventPlanner.models.serviceResult.ServiceResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountManagementService {
    private final UsersRepository usersRepo;

    private final SessionManager sessionManager;

    @Autowired
    public AccountManagementService(UsersRepository usersRepo, SessionManager sessionManager) {
        this.usersRepo = usersRepo;
        this.sessionManager = sessionManager;
    }

    public ServiceResult CreateUser(String name, String password, String email) {
        if (this.usersRepo.existsUserByName(name)) {
            return ServiceResultFactory.usernameTaken();
        }

        if (this.usersRepo.existsUserByEmail(email)) {
            return ServiceResultFactory.emailTaken();
        }

        this.usersRepo.save(new User(name, password, email));
        return ServiceResultFactory.userCreated();
    }

    public ServiceResult LoginUser(String name, String password) {
        if (!this.usersRepo.existsByNameAndPassword(name, password)) {
            return ServiceResultFactory.wrongUsernameOrPassword();
        }

        String sessionId = this.sessionManager.createSession(name);
        return ServiceResultFactory.sessionCreated(sessionId);
    }

    public ServiceResult LogoutUser(String sessionId) {
        if (!this.sessionManager.exists(sessionId)) {
            return ServiceResultFactory.invalidSession();
        }

        this.sessionManager.endSession(sessionId);
        return ServiceResultFactory.sessionEnded();
    }
}
