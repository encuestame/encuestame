package org.encuestame.business.service.social.api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.encuestame.business.service.social.AbstractSocialAPISupport;
import org.encuestame.core.social.IdentiCaProfile;
import org.encuestame.core.social.IdenticaAPIOperations;
import org.encuestame.core.social.IdenticaStatusDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class IdenticaAPITemplate extends AbstractSocialAPISupport implements IdenticaAPIOperations {


    static final int DEFAULT_RESULTS_PER_PAGE = 50;
    static final String API_URL_BASE = "http://identi.ca/api/";
    static final String VERIFY_CREDENTIALS_URL = API_URL_BASE + "account/verify_credentials.json";
    static final String USER_PROFILE_URL = API_URL_BASE + "users/show.json";
    static final String FRIENDS_STATUSES_URL = API_URL_BASE + "statuses/friends.json?screen_name={screen_name}";
    static final String TWEET_URL = API_URL_BASE + "statuses/update.json";
    static final String RETWEET_URL = API_URL_BASE + "/statuses/retweet/{tweet_id}.json";
    static final String MENTIONS_URL = API_URL_BASE + "statuses/mentions.json";
    static final String DIRECT_MESSAGES_URL = API_URL_BASE + "direct_messages.json";
    static final String SEND_DIRECT_MESSAGE_URL = API_URL_BASE + "direct_messages/new.json";
    static final String PUBLIC_TIMELINE_URL = API_URL_BASE + "statuses/public_timeline.json";
    static final String HOME_TIMELINE_URL = API_URL_BASE + "statuses/home_timeline.json";
    static final String FRIENDS_TIMELINE_URL = API_URL_BASE + "statuses/friends_timeline.json";
    static final String USER_TIMELINE_URL = API_URL_BASE + "statuses/user_timeline.json";

    private DateFormat searchDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    private DateFormat timelineDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);


    public IdenticaAPITemplate(String apiKey, String apiSecret,
            String accessToken, String accessTokenSecret) {
        setRestTemplate(org.encuestame.core.social.oauth1.ProtectedResourceClientFactory
                .create(apiKey, apiSecret, accessToken, accessTokenSecret));
    }

    public String getProfileName() {
        Map<?, ?> response = getRestTemplate().getForObject(VERIFY_CREDENTIALS_URL, Map.class);
        return (String) response.get("screen_name");
    }

    public Long getProfileId() {
        Map<?, ?> response = getRestTemplate().getForObject(VERIFY_CREDENTIALS_URL, Map.class);
        return Long.valueOf(response.get("id").toString());
    }

    public IdentiCaProfile getUserProfile(long userId) {
        Map<?, ?> response = getRestTemplate().getForObject(USER_PROFILE_URL + "?user_id={userId}", Map.class, userId);
        return getProfileFromResponseMap(response);
    }

    public void updateStatus(String message) {
        updateStatus(message, new IdenticaStatusDetails());
    }

    public void updateStatus(String message, IdenticaStatusDetails details) {
        MultiValueMap<String, Object> tweetParams = new LinkedMultiValueMap<String, Object>();
        tweetParams.add("status", message);
        tweetParams.setAll(details.toParameterMap());
        ResponseEntity<Map> response = getRestTemplate().postForEntity(TWEET_URL, tweetParams, Map.class);
        this.handleIdentiCaResponseErrors(response);

    }

    private void handleIdentiCaResponseErrors(ResponseEntity<Map> response) {
       //FUTURE: translate identica errors.
    }

    private Date toDate(String dateString, DateFormat dateFormat) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    private IdentiCaProfile getProfileFromResponseMap(Map<?, ?> response) {
        IdentiCaProfile profile = new IdentiCaProfile();
        profile.setId(Long.valueOf(String.valueOf(response.get("id"))).longValue());
        profile.setScreenName(String.valueOf(response.get("screen_name")));
        profile.setName(String.valueOf(response.get("name")));
        profile.setDescription(String.valueOf(response.get("description")));
        profile.setLocation(String.valueOf(response.get("location")));
        profile.setUrl(String.valueOf(response.get("url")));
        profile.setProfileImageUrl(String.valueOf(response.get("profile_image_url")));
        profile.setCreatedDate(toDate(String.valueOf(response.get("created_at")), timelineDateFormat));
        return profile;
    }
}
