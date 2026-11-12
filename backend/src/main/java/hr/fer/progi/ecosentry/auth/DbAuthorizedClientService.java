package hr.fer.progi.ecosentry.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Sprema Google tokene u tablicu GOOGLE_TOKEN umjesto u memoriju,
 * da ostanu nakon restarta servera (potrebno za Google Calendar).
 */
@Service
public class DbAuthorizedClientService implements OAuth2AuthorizedClientService {

    private final GoogleTokenRepository tokeni;
    private final ClientRegistrationRepository registracije;

    public DbAuthorizedClientService(GoogleTokenRepository tokeni,
                                     ClientRegistrationRepository registracije) {
        this.tokeni = tokeni;
        this.registracije = registracije;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String registrationId,
                                                                     String principalName) {
        ClientRegistration reg = registracije.findByRegistrationId(registrationId);
        if (reg == null) {
            return null;
        }
        return (T) tokeni.findById(principalName).map(t -> new OAuth2AuthorizedClient(
                reg,
                principalName,
                new OAuth2AccessToken(
                        OAuth2AccessToken.TokenType.BEARER,
                        t.getAccessToken(),
                        t.getAccessTokenIssuedAt(),
                        t.getAccessTokenExpiresAt(),
                        t.getScopes() == null ? Set.of() : Set.of(t.getScopes().split(" "))),
                t.getRefreshToken() == null ? null : new OAuth2RefreshToken(t.getRefreshToken(), null)
        )).orElse(null);
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient client, Authentication principal) {
        GoogleToken t = tokeni.findById(principal.getName()).orElseGet(GoogleToken::new);
        t.setPrincipalName(principal.getName());

        OAuth2AccessToken at = client.getAccessToken();
        t.setAccessToken(at.getTokenValue());
        t.setAccessTokenIssuedAt(at.getIssuedAt());
        t.setAccessTokenExpiresAt(at.getExpiresAt());
        t.setScopes(String.join(" ", at.getScopes()));

        // Google ne šalje uvijek novi refresh token -> stari ne brišemo
        if (client.getRefreshToken() != null) {
            t.setRefreshToken(client.getRefreshToken().getTokenValue());
        }
        tokeni.save(t);
    }

    @Override
    public void removeAuthorizedClient(String registrationId, String principalName) {
        tokeni.deleteById(principalName);
    }
}