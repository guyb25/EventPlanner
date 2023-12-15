package com.eventPlanner.endpoints.account;

import com.eventPlanner.dataAccess.sessions.SessionManager;
import com.eventPlanner.dataAccess.userEvents.services.ParticipantDataService;
import com.eventPlanner.dataAccess.userEvents.services.UserDataService;
import com.eventPlanner.models.schemas.User;
import com.eventPlanner.models.serviceResponse.ServiceResponse;
import com.eventPlanner.models.serviceResponse.providers.ResponseProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final SessionManager sessionManager;
    private final ResponseProvider responseProvider;
    private final UserDataService userDataService;
    private final ParticipantDataService participantDataService;

    @Autowired
    public AccountService(SessionManager sessionManager, ResponseProvider responseProvider,
                          UserDataService userDataService, ParticipantDataService participantDataService) {
        this.sessionManager = sessionManager;
        this.responseProvider = responseProvider;
        this.userDataService = userDataService;
        this.participantDataService = participantDataService;
    }

    public ServiceResponse createAccount(String name, String password, String email) {
        if (userDataService.isUsernameTaken(name)) {
            return responseProvider.account().usernameTaken();
        }

        if (userDataService.isEmailTaken(email)) {
            return responseProvider.account().emailTaken();
        }

        userDataService.saveUser(new User(name, password, email));
        return responseProvider.account().userCreated();
    }

    @Transactional
    public ServiceResponse loginAccount(String name, String password) {
        if (!userDataService.doUsernameAndPasswordMatch(name, password)) {
            return responseProvider.account().wrongUsernameOrPassword();
        }

        Long userId = userDataService.tryGetUserIdByName(name);

        String sessionId = sessionManager.createSession(userId);
        return responseProvider.session().sessionCreated(sessionId);
    }

    public ServiceResponse logoutAccount(String sessionId) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        sessionManager.endSession(sessionId);
        return responseProvider.session().sessionEnded();
    }

    @Transactional
    public ServiceResponse deleteAccount(String sessionId) {
        if (sessionManager.missing(sessionId)) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(sessionId);
        participantDataService.deleteAllByUserId(userId);
        userDataService.deleteUserById(userId);
        sessionManager.endSession(sessionId);
        return responseProvider.account().userDeleted();
    }
}
