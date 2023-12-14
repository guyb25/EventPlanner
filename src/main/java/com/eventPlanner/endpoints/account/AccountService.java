package com.eventPlanner.endpoints.account;

import com.eventPlanner.dataAccess.userEvents.ParticipantsRepository;
import com.eventPlanner.dataAccess.userEvents.UserRepository;
import com.eventPlanner.dataAccess.sessions.SessionManager;
import com.eventPlanner.models.schemas.User;
import com.eventPlanner.models.serviceResult.ServiceResult;
import com.eventPlanner.models.serviceResult.ServiceResultFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final UserRepository usersRepo;
    private final ParticipantsRepository participantsRepository;

    private final SessionManager sessionManager;

    @Autowired
    public AccountService(UserRepository usersRepo, ParticipantsRepository participantsRepository, SessionManager sessionManager) {
        this.usersRepo = usersRepo;
        this.participantsRepository = participantsRepository;
        this.sessionManager = sessionManager;
    }

    public ServiceResult createAccount(String name, String password, String email) {
        if (this.usersRepo.existsUserByName(name)) {
            return ServiceResultFactory.usernameTaken();
        }

        if (this.usersRepo.existsUserByEmail(email)) {
            return ServiceResultFactory.emailTaken();
        }

        this.usersRepo.save(new User(name, password, email));
        return ServiceResultFactory.userCreated();
    }

    public ServiceResult loginAccount(String name, String password) {
        if (!this.usersRepo.existsByNameAndPassword(name, password)) {
            return ServiceResultFactory.wrongUsernameOrPassword();
        }

        Long userId = this.usersRepo.findUserByName(name).getId();
        String sessionId = this.sessionManager.createSession(userId);
        return ServiceResultFactory.sessionCreated(sessionId);
    }

    public ServiceResult logoutAccount(String sessionId) {
        if (this.sessionManager.missing(sessionId)) {
            return ServiceResultFactory.invalidSession();
        }

        this.sessionManager.endSession(sessionId);
        return ServiceResultFactory.sessionEnded();
    }

    @Transactional
    public ServiceResult deleteAccount(String sessionId) {
        if (this.sessionManager.missing(sessionId)) {
            return ServiceResultFactory.invalidSession();
        }

        Long userId = this.sessionManager.getUserIdFromSession(sessionId);
        this.participantsRepository.deleteAllByUserId(userId);
        this.usersRepo.deleteById(userId);
        this.sessionManager.endSession(sessionId);
        return ServiceResultFactory.userDeleted();
    }
}
