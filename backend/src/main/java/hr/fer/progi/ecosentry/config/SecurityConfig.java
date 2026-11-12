package hr.fer.progi.ecosentry.config;

import hr.fer.progi.ecosentry.auth.KorisnikOidcUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,
                                    KorisnikOidcUserService oidcUserService,
                                    OAuth2AuthorizationRequestResolver resolver) throws Exception {

        var csrfHandler = new CsrfTokenRequestAttributeHandler();
        csrfHandler.setCsrfRequestAttributeName(null);

        http
            .authorizeHttpRequests(a -> a
                .requestMatchers("/api/public/**", "/error").permitAll()
                .requestMatchers("/api/redar/**").hasRole("KOMUNALNI_REDAR")
                .requestMatchers("/api/udruga/**").hasRole("EKO_UDRUGA")
                .anyRequest().authenticated())

            .oauth2Login(o -> o
                .authorizationEndpoint(e -> e.authorizationRequestResolver(resolver))
                .userInfoEndpoint(u -> u.oidcUserService(oidcUserService))
                .defaultSuccessUrl(frontendUrl + "/", true)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler(frontendUrl + "/login?error")))

            .logout(l -> l
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()))

            // za /api/** vrati 401 umjesto preusmjeravanja na Google
            .exceptionHandling(e -> e.defaultAuthenticationEntryPointFor(
                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                request -> request.getRequestURI().startsWith("/api/")))

            // CSRF token u cookieju XSRF-TOKEN; axios ga automatski šalje u headeru
            .csrf(c -> c
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(csrfHandler));

        return http.build();
    }

    // access_type=offline -> Google vraća refresh token (treba za Google Calendar)
    @Bean
    OAuth2AuthorizationRequestResolver authorizationRequestResolver(ClientRegistrationRepository repo) {
        var resolver = new DefaultOAuth2AuthorizationRequestResolver(repo, "/oauth2/authorization");
        resolver.setAuthorizationRequestCustomizer(c -> c.additionalParameters(p -> {
            p.put("access_type", "offline");
            p.put("prompt", "consent");
        }));
        return resolver;
    }
}