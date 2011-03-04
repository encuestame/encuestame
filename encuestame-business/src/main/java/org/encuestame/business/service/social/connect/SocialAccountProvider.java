
package org.encuestame.business.service.social.connect;

/**
 * Social Account Provider.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 20, 2011 10:45:15 PM
 */
public class SocialAccountProvider {

    private String name;

    private String apiKey;

    private String secret;

    private Long appId;

    private String requestTokenUrl;

    private String authorizeUrl;

    private String accessTokenUrl;

    /**
     * Constructor.
     */
    public SocialAccountProvider() {}

    /**
     * Constructor.
     * @param name
     * @param apiKey
     * @param secret
     * @param appId
     * @param requestTokenUrl
     * @param authorizeUrl
     * @param accessTokenUrl
     */
    public SocialAccountProvider(String name, String apiKey, String secret,
            Long appId, String requestTokenUrl, String authorizeUrl,
            String accessTokenUrl) {
        super();
        this.name = name;
        this.apiKey = apiKey;
        this.secret = secret;
        this.appId = appId;
        this.requestTokenUrl = requestTokenUrl;
        this.authorizeUrl = authorizeUrl;
        this.accessTokenUrl = accessTokenUrl;
    }



    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey the apiKey to set
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret the secret to set
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * @return the appId
     */
    public Long getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(Long appId) {
        this.appId = appId;
    }

    /**
     * @return the requestTokenUrl
     */
    public String getRequestTokenUrl() {
        return requestTokenUrl;
    }

    /**
     * @param requestTokenUrl the requestTokenUrl to set
     */
    public void setRequestTokenUrl(String requestTokenUrl) {
        this.requestTokenUrl = requestTokenUrl;
    }

    /**
     * @return the authorizeUrl
     */
    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    /**
     * @param authorizeUrl the authorizeUrl to set
     */
    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    /**
     * @return the accessTokenUrl
     */
    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    /**
     * @param accessTokenUrl the accessTokenUrl to set
     */
    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }
}
