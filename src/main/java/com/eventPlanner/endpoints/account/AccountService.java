package com.eventPlanner.endpoints.account;

import com.eventPlanner.dataAccess.userEvents.ParticipantsRepository;
import com.eventPlanner.dataAccess.userEvents.UserRepository;
import com.eventPlanner.dataAccess.sessions.SessionManager;
import com.eventPlanner.models.schemas.User;
import com.eventPlanner.models.serviceResponse.ServiceResponse;
import com.eventPlanner.models.serviceResponse.providers.ResponseProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final UserRepository usersRepo;
    private final ParticipantsRepository participantsRepository;

    private final SessionManager sessionManager;
    private final ResponseProvider responseProvider;

    @Autowired
    public AccountService(UserRepository usersRepo, ParticipantsRepository participantsRepository,
                          SessionManager sessionManager, ResponseProvider responseProvider) {
        this.usersRepo = usersRepo;
        this.participantsRepository = participantsRepository;
        this.sessionManager = sessionManager;
        this.responseProvider = responseProvider;
    }

    public ServiceResponse createAccount(String name, String password, String email) {
        if (usersRepo.existsUserByName(name)) {
            return responseProvider.account().usernameTaken();
        }

        if (usersRepo.existsUserByEmail(email)) {
            return responseProvider.account().emailTaken();
        }

        this.usersRepo.save(new User(name, password, email));
        return responseProvider.account().userCreated();
    }

    @Transactional
    public ServiceResponse loginAccount(String name, String password) {
        if (!usersRepo.existsByNameAndPassword(name, password)) {
            return responseProvider.account().wrongUsernameOrPassword();
        }

        Long userId = usersRepo.findUserByName(name).getId();
        String sessionId = sessionManager.createSession(userId);
        return responseProvider.session().sessionCreated(sessionId);
    }

    public ServiceResponse logoutAccount(String sessionId) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        this.sessionManager.endSession(sessionId);
        return responseProvider.session().sessionEnded();
    }

    @Transactional
    public ServiceResponse deleteAccount(String sessionId) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        participantsRepository.deleteAllByUserId(userId);
        usersRepo.deleteById(userId);
        sessionManager.endSession(sessionId);
        return responseProvider.account().userDeleted();
    }
}
