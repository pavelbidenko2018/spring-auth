package com.pbidenko.springauth.component;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.pbidenko.springauth.entity.Role;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String redirectUrl = null;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		authorities.forEach(n->System.out.println(n.getClass() + ": " + n.getAuthority()));
		
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals(Role.ADMIN.toString())) {
				redirectUrl = "/my_profile";
				break;
			} else if (grantedAuthority.getAuthority().equals(Role.USER.toString())) {
				redirectUrl = "/my_profile";
				break;
			}
		}
				
		if (redirectUrl == null) {
			throw new IllegalStateException();
		}
		
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);

	}

}
