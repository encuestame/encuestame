package org.encuestame.utils.json;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Profile json user account.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 5, 2011 4:44:10 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileUserAccount {

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "complete_name")
    private String name;

    @JsonProperty(value = "private")
    private Boolean privateProfile;

    @JsonProperty(value = "language")
    private String language;


    /**
     * @return the username
     */
    @JsonIgnore
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the email
     */
    @JsonIgnore
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the privateProfile
     */
    @JsonIgnore
    public Boolean getPrivateProfile() {
        return privateProfile;
    }

    /**
     * @param privateProfile the privateProfile to set
     */
    public void setPrivateProfile(final Boolean privateProfile) {
        this.privateProfile = privateProfile;
    }

    /**
     * @return the langgetProfileUserInfouage
     */
    @JsonIgnore
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(final String language) {
        this.language = language;
    }

    /**
     * @return the name
     */
    @JsonIgnore
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }
}