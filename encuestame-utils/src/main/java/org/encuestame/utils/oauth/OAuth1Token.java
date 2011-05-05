package org.encuestame.utils.oauth;

/**
 * Holds an OAuth token and secret.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 22, 2011
 */
public final class OAuth1Token {

    /**
     * Value.
     */
    private final String value;

    /**
     * Secret.
     */
    private final String secret;

    /**
     * Create a new OAuth token with a token value and null secret.
     * Use this for OAuth 2.
     */
    public OAuth1Token(String value) {
        this(value, null);
    }

    /**
     * Create a new OAuth token with a token value and secret.
     * Use this for OAuth 1.
     */
    public OAuth1Token(final String value, final String secret) {
        this.value = value;
        this.secret = secret;
    }

    public String getValue() {
        return value;
    }

    public String getSecret() {
        return secret;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OAuthToken [value=").append(value).append(", secret=")
                .append(secret).append("]");
        return builder.toString();
    }
}