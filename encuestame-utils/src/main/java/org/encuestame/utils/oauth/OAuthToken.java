package org.encuestame.utils.oauth;

public final class OAuthToken {

    private final String value;

    private final String secret;

    /**
     * Create a new OAuth token with a token value and null secret.
     * Use this for OAuth 2.
     */
    public OAuthToken(String value) {
        this(value, null);
    }

    /**
     * Create a new OAuth token with a token value and secret.
     * Use this for OAuth 1.
     */
    public OAuthToken(String value, String secret) {
        this.value = value;
        this.secret = secret;
    }

    public String getValue() {
        return value;
    }

    public String getSecret() {
        return secret;
    }

}
