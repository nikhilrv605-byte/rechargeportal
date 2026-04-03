package com.example.demo.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.example.demo.reposiotry.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final UserRepository userRepository;

	public CustomOAuth2SuccessHandler(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2AuthenticationToken token =
                (OAuth2AuthenticationToken) authentication;

        OAuth2User oAuth2User = token.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String providerId = oAuth2User.getAttribute("sub");

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isEmpty()) {
            User user = new User();
           
                    user.setEmail(email);
                    user.setName(name);
                    user.setProvider("Google");
                    user.setProviderId(providerId);
                    user.setRole("ROLE_USER");
//                    .name(name)
//                    .provider("GOOGLE")
//                    .providerId(providerId)
//                    .role("ROLE_USER")
//                  

            userRepository.save(user);
        }

        // Redirect to frontend
        response.sendRedirect("http://localhost:8080");
    }
}