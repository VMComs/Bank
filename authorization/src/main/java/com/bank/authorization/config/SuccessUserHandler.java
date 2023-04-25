package com.bank.authorization.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Handler для переадресации на нужный url в зависимости от прав доступа
 */

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    /** @param authentication - Spring Security использует объект Authentication, пользователя авторизованной сессии*/
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        final Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/api/auth/users");
        } else if (roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/api/auth/userView");
        } else {
            httpServletResponse.sendRedirect("/api/auth");
        }
    }
}
