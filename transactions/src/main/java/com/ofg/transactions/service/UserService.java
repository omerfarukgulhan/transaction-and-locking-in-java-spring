package com.ofg.transactions.service;

import com.ofg.transactions.model.User;
import com.ofg.transactions.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createUserWithRollback(User user) throws Exception {
        userRepository.save(user);

        if (!user.getEmail().contains("@")) {
            throw new Exception("Simulated failure: Email does not contain '@'.");
        }
    }

    @Transactional(rollbackFor = Exception.class, timeout = 5)
    public void createUserWithTimeout(User user) throws Exception {
        Random random = new Random();
        int randomNumber = random.nextInt(2) + 1;

        userRepository.save(user);

        if (randomNumber == 1) {
            Thread.sleep(6000);
        }
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        // This method is optimized for read-only operations
        return userRepository.findByEmail(email).orElse(null);
    }

    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public void createUserWithNoRollback(User user) throws Exception {
        userRepository.save(user);

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty or null.");
        }

        if (!user.getEmail().contains("@")) {
            throw new Exception("Simulated failure: Email does not contain '@'.");
        }
    }
}