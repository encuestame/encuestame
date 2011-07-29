package org.encuestame.social.connect.service;


import org.encuestame.core.exception.EnMeExistPreviousConnectionException;
import org.encuestame.core.service.ServiceOperations;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.social.connect.SocialSignInOperations;
import org.encuestame.utils.oauth.AccessGrant;

public interface ConnectOperations extends ServiceOperations{

    /**
    *
    * @param social
    * @return
    * @throws EnMeExistPreviousConnectionException
    * @throws Exception
    */
   String connectSignInAccount(final SocialSignInOperations social)
                     throws EnMeExistPreviousConnectionException, Exception;

   /**
    *
    * @param social
    */
   void disconnectSignInAccount(final SocialSignInOperations social);

   /**
    *
    * @param provider
    * @param socialProfileId
    * @return
    */
   SocialAccount findAccountConnectionBySocialProfileId(
           final SocialProvider provider,
           final String socialProfileId);

   /**
   *
   * @param accessGrant
   * @param socialAccountId
   * @param userAccount
   * @param providerProfileUrl
   * @param currentSocialAccount
   * @return
   */
  SocialAccount updateSocialAccountConnection(
          final AccessGrant accessGrant,
          final String socialAccountId,
          final SocialAccount currentSocialAccount);
}
