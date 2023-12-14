package com.eventPlanner.endpoints.account;

import com.eventPlanner.dataAccess.userEvents.ParticipantsRepository;
import com.eventPlanner.dataAccess.userEvents.UserRepository;
import com.eventPlanner.dataAccess.sessions.SessionManager;
import com.eventPlanner.models.schemas.User;
import com.eventPlanner.models.serviceResponse.serviceResponse;
import com.eventPlanner.models.serviceResponse.factories.ResponseFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final UserRepository usersRepo;
    private final ParticipantsRepository participantsRepository;

    private final SessionManager sessionManager;
    private final ResponseFactory responseFactory;

    @Autowired
    public AccountService(UserRepository usersRepo, ParticipantsRepository participantsRepository,
                          SessionManager sessionManager, ResponseFactory responseFactory) {
        this.usersRepo = usersRepo;
        this.participantsRepository = participantsRepository;
        this.sessionManager = sessionManager;
        this.responseFactory = responseFactory;
    }

    public serviceResponse createAccount(String name, String password, String email) {
        if (this.usersRepo.existsUserByName(name)) {
            return responseFactory.account().usernameTaken();
        }

        if (this.usersRepo.existsUserByEmail(email)) {
            return responseFactory.account().emailTaken();
        }

        this.usersRepo.save(new User(name, password, email));
        return responseFactory.account().userCreated();
    }

    public serviceResponse loginAccount(String name, String password) {
        if (!this.usersRepo.existsByNameAndPassword(name, password)) {
            return responseFactory.account().wrongUsernameOrPassword();
        }

        Long userId = this.usersRepo.findUserByName(name).getId();
        String sessionId = this.sessionManager.createSession(userId);
        return responseFactory.session().sessionCreated(sessionId);
    }

    public serviceResponse logoutAccount(String sessionId) {
        if (this.sessionManager.missing(sessionId)) {
            return responseFactory.session().invalidSession();
        }

        this.sessionManager.endSession(sessionId);
        return responseFactory.session().sessionEnded();
    }

    @Transactional
    public serviceResponse deleteAccount(String sessionId) {
        if (this.sessionManager.missing(sessionId)) {
            return responseFactory.session().invalidSession();
        }

        Long userId = this.sessionManager.getUserIdFromSession(sessionId);
        this.participantsRepository.deleteAllByUserId(userId);
        this.usersRepo.deleteById(userId);
        this.sessionManager.endSession(sessionId);
        return responseFactory.account().userDeleted();
    }
}
