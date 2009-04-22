package org.jp.developer.web.beans.security;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LoginBean {
	//managed properties for the login page

	// This is the action method called when the user clicks the "login" button
	public String doLogin() throws IOException, ServletException {
		System.out.printf("TESSSSSSSSSSSTTTTTTTTT");
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/j_spring_security_check");
		System.out.printf("TESSSSSSSSSSSTTTTdTTTTT");
		dispatcher.forward((ServletRequest) context.getRequest(),
				(ServletResponse) context.getResponse());

		FacesContext.getCurrentInstance().responseComplete();
		// It's OK to return null here because Faces is just going to exit.
		System.out.printf("TESSSSSSSSSSSTTTTTTTTT");
		return null;
	}
	/*
	@PostConstruct
    private void handleErrorMessage()
    {
        Exception e = (Exception) FacesUtils
                .getSessionAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);
        if (e instanceof BadCredentialsException)
        {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
                    AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY, null);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username or password not valid.", null));
        }
    }
*/
}
