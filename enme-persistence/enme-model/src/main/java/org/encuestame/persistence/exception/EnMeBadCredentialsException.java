package org.encuestame.persistence.exception;

public class EnMeBadCredentialsException extends EnMeOAuthSecurityException {

    /**
     *
     */
    private static final long serialVersionUID = 5042566576006743837L;

    public EnMeBadCredentialsException() {
        super();
    }

    public EnMeBadCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnMeBadCredentialsException(String message) {
        super(message);
    }

    public EnMeBadCredentialsException(Throwable cause) {
        super(cause);
    }
}
