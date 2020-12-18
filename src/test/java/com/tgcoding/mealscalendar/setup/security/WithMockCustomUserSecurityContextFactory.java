package com.tgcoding.mealscalendar.setup.security;

import com.tgcoding.mealscalendar.model.User;
import com.tgcoding.mealscalendar.security.AuthProvider;
import com.tgcoding.mealscalendar.security.UserPrincipal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    public static final Long USER_ID = 12345L;

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser mockCustomUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = new User();
        user.setId(USER_ID);
        user.setEmail("test@gmail.com");
        user.setEmailVerified(false);
        user.setPassword(null);
        user.setProvider(AuthProvider.google);
        user.setProviderId("123");

        UserDetails userDetails = UserPrincipal.create(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        context.setAuthentication(authentication);

        return context;
    }
}