package hr.fer.progi.ecosentry.auth;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "GOOGLE_TOKEN")
public class GoogleToken {

    @Id
    private String principalName;   // = KORISNIK.oidcSub

    private String accessToken;
    private Instant accessTokenIssuedAt;
    private Instant accessTokenExpiresAt;
    private String scopes;
    private String refreshToken;

    public String getPrincipalName() { return principalName; }
    public void setPrincipalName(String v) { principalName = v; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String v) { accessToken = v; }

    public Instant getAccessTokenIssuedAt() { return accessTokenIssuedAt; }
    public void setAccessTokenIssuedAt(Instant v) { accessTokenIssuedAt = v; }

    public Instant getAccessTokenExpiresAt() { return accessTokenExpiresAt; }
    public void setAccessTokenExpiresAt(Instant v) { accessTokenExpiresAt = v; }

    public String getScopes() { return scopes; }
    public void setScopes(String v) { scopes = v; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String v) { refreshToken = v; }
}