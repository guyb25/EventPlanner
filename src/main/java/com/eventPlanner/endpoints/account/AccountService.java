package com.eventPlanner.endpoints.account;

import com.eventPlanner.dataAccess.sessions.SessionManager;
import com.eventPlanner.dataAccess.userEvents.services.ParticipantDataService;
import com.eventPlanner.dataAccess.userEvents.services.UserDataService;
import com.eventPlanner.core.models.dtos.account.CreateAccountDto;
import com.eventPlanner.core.models.dtos.account.DeleteAccountDto;
import com.eventPlanner.core.models.dtos.account.LoginAccountDto;
import com.eventPlanner.core.models.dtos.account.LogoutAccountDto;
import com.eventPlanner.dataAccess.userEvents.schemas.User;
import com.eventPlanner.core.models.responses.ServiceResponse;
import com.eventPlanner.core.models.responses.providers.ResponseProvider;
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

    public ServiceResponse createAccount(CreateAccountDto createAccountDto) {
        var name = createAccountDto.name();
        var email = createAccountDto.email();
        var password = createAccountDto.password();

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
    public ServiceResponse loginAccount(LoginAccountDto loginAccountDto) {
        if (!userDataService.doUsernameAndPasswordMatch(loginAccountDto.name(), loginAccountDto.password())) {
            return responseProvider.account().wrongUsernameOrPassword();
        }

        Long userId = userDataService.tryGetUserIdByName(loginAccountDto.name());

        String sessionId = sessionManager.createSession(userId);
        return responseProvider.session().sessionCreated(sessionId);
    }

    public ServiceResponse logoutAccount(LogoutAccountDto logoutAccountDto) {
        if (sessionManager.missing(logoutAccountDto.sessionId())) {
            return responseProvider.session().invalidSession();
        }

        sessionManager.endSession(logoutAccountDto.sessionId());
        return responseProvider.session().sessionEnded();
    }

    @Transactional
    public ServiceResponse deleteAccount(DeleteAccountDto deleteAccountDto) {
        if (sessionManager.missing(deleteAccountDto.sessionId())) {
            return responseProvider.session().invalidSession();
        }

        Long userId = sessionManager.getUserIdFromSession(deleteAccountDto.sessionId());
        participantDataService.deleteAllByUserId(userId);
        userDataService.deleteUserById(userId);
        sessionManager.endSession(deleteAccountDto.sessionId());
        return responseProvider.account().userDeleted();
    }
}
