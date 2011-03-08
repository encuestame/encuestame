
package org.encuestame.business.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.business.service.imp.ITwitterService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.security.SocialAccount.TypeAuth;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.IllegalSocialActionException;
import org.encuestame.utils.security.SocialAccountBean;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.http.AccessToken;

/**
 * Social service layer.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 7, 2011
 */
public class AbstractSocialService extends AbstractConfigurationService {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /** Twitter Service. **/
    private ITwitterService twitterService;

   /**
    * Twitter error code.
    */
   private static int TWITTER_AUTH_ERROR = 401;


    /**
     * Update OAuth Secret Twitter Credentials.
     * @param accountBean {@link SocialAccountBean}
     * @param username username logged
     * @throws EnMeExpcetion exception
     */
    public void updateSecretTwitterCredentials(final SocialAccountBean accountBean,
            final String username) throws EnMeExpcetion{
         //TODO: we should search twitter account filter by username
         final SocialAccount twitterAccount = this.getSocialAccount(accountBean.getAccountId()); //TODO: filter by Username Too
         //twitterAccount.setConsumerKey(accountBean.getKey());
         //twitterAccount.setConsumerSecret(accountBean.getSecret());
         twitterAccount.setType(ConvertDomainBean.convertStringToEnum(accountBean.getType()));
         if(accountBean.getPin() != null && !accountBean.getPin().isEmpty()){
             log.debug("PIN Exists {"+accountBean.getPin());
             //twitterAccount.setTwitterPin(Integer.valueOf(accountBean.getPin()));
            //If exist pin, we can verify credentials
            log.debug("Verify OAuth Credentials");
                if(verifyCredentials(
                        //Token and Secret token should be always from database
                        twitterAccount.getToken(),
                        twitterAccount.getSecretToken(),
                        //consumer key's
                        accountBean.getKey(),
                        accountBean.getSecret(),
                        //pin, update by the user.
                        accountBean.getPin())){
                    twitterAccount.setVerfied(Boolean.TRUE);
                } else {
                    twitterAccount.setVerfied(Boolean.FALSE);
                }
         } else {
             log.info("Account not verified, pin not found");
             //twitterAccount.setTwitterPin(null);
             twitterAccount.setVerfied(Boolean.FALSE);
         }
        log.debug("Update Secret Twitter Credentials");
        getAccountDao().saveOrUpdate(twitterAccount);
        log.info("update Twitter Account");
    }

    /**
     * Change state social account.
     * @param accountId
     * @param username
     * @param action
     * @throws EnMeNoResultsFoundException
     * @throws IllegalSocialActionException
     */
    public void changeStateSocialAccount(
            final Long accountId,
            final String username,
            final String action) throws EnMeNoResultsFoundException, IllegalSocialActionException{
        final UserAccount userAccount = getUserAccount(username);
        final SocialAccount social = getAccountDao().getSocialAccount(accountId, userAccount.getAccount());
        if(social == null){
            throw new EnMeNoResultsFoundException("social accout not found");
        }
        if("default".equals(action)){
           log.info("update social twitter account");
           social.setDefaultSelected(!social.getDefaultSelected());
           getAccountDao().saveOrUpdate(social);
        } else if("remove".equals(action)){
            getAccountDao().delete(social);
        } else if("valid".equals(action)){
            social.setVerfied(!social.getVerfied());
            getAccountDao().saveOrUpdate(social);
        } else {
            throw new IllegalSocialActionException();
        }
    }

    /**
     * Verify OAuth Credentials
     * @param token token stored
     * @param secretToken secret Token
     * @param pin pin
     * @return
     * @throws TwitterException
     */
    public Boolean verifyCredentials(final String token,
                                     final String tokenSecret,
                                     final String consumerKey,
                                     final String consumerSecret,
                final String pin){
        Boolean verified = false;
        log.debug("verifyCredentials OAuth");
        log.debug("Token {"+token);
        log.debug("secretToken {"+tokenSecret);
        log.debug("pin {"+pin);
        log.debug("consumerKey {"+consumerKey);
        log.debug("consumerSecret {"+consumerSecret);
        Twitter twitter = null;
        try {
             twitter = new TwitterFactory().getInstance();
            if(token == null || token.isEmpty()){
                verified = false;
            } else {
                log.debug("Exist Previous Token.");
                final AccessToken accessToken = new AccessToken(token, tokenSecret);
                log.debug("Created Token "+accessToken);
                twitter = new TwitterFactory().getOAuthAuthorizedInstance(consumerKey, consumerSecret, accessToken);
                log.debug("Verifying Credentials");
                final User user = twitter.verifyCredentials();
                log.debug("Verifying Credentials User "+user);
                if (user != null) {
                    log.debug("Verify OAuth User " + user.getId());
                    verified = true;
                }
            }
        } catch (TwitterException te) {
            log.error("Twitter Error "+te.getMessage());
            if (AbstractSocialService.TWITTER_AUTH_ERROR == te.getStatusCode()) {
                log.error("Twitter Error "+te.getStatusCode());
                verified = false;
            } else {
                log.error(te);
            }
            log.error("Verify OAuth Error " + te.getLocalizedMessage());
        }
        log.debug("verified "+verified);
        return verified;
    }


    /**
     * Update OAuth Token/Secret Social Account.
     * @param socialAccountId
     * @param token
     * @param tokenSecret
     * @param username
     * @param account
     * @param socialProvider
     * @throws EnMeExpcetion
     */
    public void addOrUpdateOAuthTokenSocialAccount(
            final Long socialAccountId,
            final String token,
            final String tokenSecret,
            final String username,
            final UserAccount account,
            final SocialProvider socialProvider) throws EnMeExpcetion{
            SocialAccount socialAccount = getAccountDao().getSocialAccount(socialProvider, socialAccountId);
            if (socialAccount == null) {
                log.info("adding new social account");
                socialAccount = new SocialAccount();
            }
            log.debug("Updating  Token to {"+token);
            log.debug("Updating Secret Token to {"+tokenSecret);
            socialAccount.setToken(token);
            socialAccount.setVerfied(Boolean.TRUE);
            socialAccount.setAccounType(socialProvider);
            socialAccount.setSecUsers(account.getAccount());
            socialAccount.setSocialAccountName(username);
            socialAccount.setType(TypeAuth.OAUTH);
            socialAccount.setSecretToken(tokenSecret);
            socialAccount.setSocialUserId(socialAccountId);
            getAccountDao().saveOrUpdate(socialAccount);
            log.debug("Updated Token");
    }


    /**
     * Get Twitter Account.
     * @param twitterAccountId
     * @return
     */
    public SocialAccountBean getTwitterAccount(final Long twitterAccountId){
        return ConvertDomainBean.convertSocialAccountToBean(getAccountDao().getTwitterAccount(twitterAccountId));
    }


    /**
     * Get User Logged Scocial Accounts.
     * @param username
     * @param provider
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<SocialAccountBean> getUserLoggedSocialAccount(final String username, final SocialProvider provider)
           throws EnMeNoResultsFoundException{
         return ConvertDomainBean.convertListSocialAccountsToBean(getAccountDao()
                                 .getTwitterAccountByUser(getUserAccount(username).getAccount(), provider));
    }

    /**
     * Get User Logged Verified Social Accounts.
     * @param username username
     * @return list of social accounts.
     * @throws EnMeNoResultsFoundException exception
     */
    public List<SocialAccountBean> getUserLoggedVerifiedTwitterAccount(final String username, final SocialProvider provider)
             throws EnMeNoResultsFoundException{
        final List<SocialAccountBean> comfirmedSocialAccounts = new ArrayList<SocialAccountBean>();
        final List<SocialAccount> socialAccounts = getAccountDao()
                .getTwitterVerifiedAccountByUser(getUserAccount(username).getAccount(), provider);
        for (SocialAccount socialAccount : socialAccounts) {
            if(getTwitterService().verifyCredentials(socialAccount)){
                log.debug("Confirmed Account  -- "+socialAccount.getSocialAccountName());
                comfirmedSocialAccounts.add(ConvertDomainBean.convertSocialAccountToBean(socialAccount));
            }
        }
        log.debug("social provider verified "+comfirmedSocialAccounts.size());
        return comfirmedSocialAccounts;
   }

   /**
    * Get social account by id.
    * @param accountId
    * @return
    */
   protected SocialAccount getSocialAccount(final Long accountId){
        return  getAccountDao().getTwitterAccount(accountId); //TODO: ENCUESTAME-113
   }

   /**
    * Update Twitter Account.
    * @param accountBean account
    * @param password password
    * TODO: this method is close to be deprecated, twitter don't allow password login.
    */
   @Deprecated
   public void updateTwitterAccount(
           final SocialAccountBean accountBean,
           final String password,
           final Boolean verify){
       if(accountBean.getAccountId() != null){
           final SocialAccount twitterAccount = getAccountDao().getTwitterAccount(accountBean.getAccountId());
           if(twitterAccount != null){
               //twitterAccount.setTwitterPassword(password);
               twitterAccount.setVerfied(verify);
               log.debug("Updating twitter password account");
               getAccountDao().saveOrUpdate(twitterAccount);
           }
       }
       log.info("update Twitter Account");
   }

   /**
    * @return the twitterService
    */
   public ITwitterService getTwitterService() {
       return twitterService;
   }

   /**
    * @param twitterService the twitterService to set
    */
   public void setTwitterService(final ITwitterService twitterService) {
       this.twitterService = twitterService;
   }
}
