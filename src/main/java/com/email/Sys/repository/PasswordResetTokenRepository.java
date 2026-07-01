package com.email.Sys.repository;

import com.email.Sys.model.PasswordResetToken;
import com.email.Sys.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByUserAndCodeAndIsUsedFalse(User user, String code);
    void deleteByUser(User user);
}
