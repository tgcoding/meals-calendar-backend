package com.tgcoding.mealscalendar.security;

import com.tgcoding.mealscalendar.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.stream.IntStream;

@Slf4j
@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
    private static final int[] knownPorts = {-1, 80, 443};

    @Autowired
    AppProperties appProperties;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        String referer = request.getHeader(HttpHeaders.REFERER);
        log.info("Referer header: " + referer);

        try {
            URI uri = new URI(referer);
            int port = uri.getPort();
            referer = uri.getScheme() + "://" + uri.getHost();
            if (IntStream.of(knownPorts).noneMatch(x -> x == port)) {
                referer += ":" + port;
            }

            log.info("Referer domain only: " + referer);
        } catch (Exception e) {
            log.error("Bad URI");
        }

        String redirect = referer + appProperties.getOauth2().getLogoutPath();
        log.info("Redirect: " + redirect);

        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect(redirect);
    }
}
