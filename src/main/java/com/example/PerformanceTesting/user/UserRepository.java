package com.example.PerformanceTesting.user;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUsername(String username);

    void deleteById(Long userId);
}
