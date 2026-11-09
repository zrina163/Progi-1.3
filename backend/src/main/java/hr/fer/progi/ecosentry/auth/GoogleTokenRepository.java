package hr.fer.progi.ecosentry.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleTokenRepository extends JpaRepository<GoogleToken, String> {
}