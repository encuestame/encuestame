/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.security;

import org.apache.log4j.Logger;
import org.encuestame.core.security.details.EnMeUserDetailsDataAccount;
import org.encuestame.core.security.service.EnMeUserServiceImp;
import org.jasypt.spring.security3.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * An {@link AuthenticationProvider} implementation that retrieves user details
 * from an {@link EnMeUserServiceImp} or Social Accounts.
 * This implementation require {@link PasswordEncoder} from jasypt lib.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 30, 2011
 */
public class EnMeUsernameProvider extends AbstractUserDetailsAuthenticationProvider {

    private Logger log = Logger.getLogger(this.getClass());

    private SaltSource saltSource;

    private PasswordEncoder passwordEncoder = new PasswordEncoder();

    @Autowired
    private UserDetailsService userDetailsService;

    private boolean includeDetailsObject = true;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        log.debug("additionalAuthenticationChecks *********************************************");
        log.debug("additionalAuthenticationChecks *********************************************");
        log.debug("additionalAuthenticationChecks *********************************************");
        log.debug("additionalAuthenticationChecks *********************************************");

        Object salt = null;

        if (this.saltSource != null) {
            salt = this.saltSource.getSalt(userDetails);
        }

        final EnMeUserDetailsDataAccount detailsDataAccount = (EnMeUserDetailsDataAccount) userDetails;

        if (log.isDebugEnabled()) {
            log.debug("detailsDataAccount "+detailsDataAccount.toString());
        }

        if (!detailsDataAccount.isSocialCredentials()) {
            log.debug("SOCIAL CREDENTIALS OFF");
            if (authentication.getCredentials() == null) {
                logger.debug("Authentication failed: no credentials provided");

                throw new BadCredentialsException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.badCredentials",
                        "Bad credentials"), includeDetailsObject ? userDetails
                        : null);
            }
            log.debug("PASOWRDDDDDDDD CHECKKKKKKKS *********************************************");
            log.debug("PASOWRDDDDDDDD CHECKKKKKKKS *********************************************");
            log.debug("PASOWRDDDDDDDD CHECKKKKKKKS *********************************************");
            log.debug("PASOWRDDDDDDDD CHECKKKKKKKS *********************************************");
            log.debug("PASOWRDDDDDDDD CHECKKKKKKKS *********************************************");

            String presentedPassword = authentication.getCredentials().toString();
            log.debug("EnMe: presentedPassword "+presentedPassword);
            log.debug("EnMe: presentedPassword details "+userDetails.getPassword());
            log.debug("EnMe: presentedPassword details "+passwordEncoder.getClass());

            if (!passwordEncoder.isPasswordValid(userDetails.getPassword(),
                    presentedPassword, salt)) {
                logger.debug("Authentication failed: password does not match stored value");

                throw new BadCredentialsException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.badCredentials",
                        "Bad credentials"), includeDetailsObject ? userDetails
                        : null);
            }

            log.debug("PASOWRDDDDDDDD CHECKKKKKKKS *********************************************");
            log.debug("PASOWRDDDDDDDD CHECKKKKKKKS *********************************************");
            log.debug("PASOWRDDDDDDDD CHECKKKKKKKS *********************************************");
            log.debug("PASOWRDDDDDDDD CHECKKKKKKKS *********************************************");
            log.debug("PASOWRDDDDDDDD CHECKKKKKKKS *********************************************");
            log.debug("PASOWRDDDDDDDD CHECKKKKKKKS *********************************************");
        } else {
            if (log.isInfoEnabled()) {
                log.info("SOCIAL CREDENTIALS ON");
            }
        }
    }

    /**
     *
     */
    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService,
                "A UserDetailsService must be set");
    }

    //@Override
    //public Authentication authenticate(Authentication authentication) throws AuthenticationException{
     //   return authentication;
    //}

    /*
     * (non-Javadoc)
     * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#retrieveUser(java.lang.String, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)
     */
    @Override
    protected final UserDetails retrieveUser(
            final String username,
            UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        log.info("retrieve user customized *********************************************************");
        log.info("retrieve user customized *********************************************************");
        log.info("retrieve user customized *********************************************************");
        log.info("retrieve user customized *********************************************************");
        log.info("retrieve user customized *********************************************************");
        log.info("retrieve user customized *********************************************************");

        EnMeUserDetailsDataAccount loadedUser;
        try {
            loadedUser = (EnMeUserDetailsDataAccount) this.getUserDetailsService().loadUserByUsername(
                    username);
        } catch (UsernameNotFoundException notFound) {
            throw notFound;
        } catch (Exception repositoryProblem) {
            throw new AuthenticationServiceException(
                    repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new AuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        log.debug("loaded user "+loadedUser.getUsername());
        log.info("loader user "+loadedUser.toString());
        return loadedUser;
    }

    /**
     * Sets the PasswordEncoder instance to be used to encode and validate
     * passwords. If not set, the password will be compared as plain text.
     * <p>
     * For systems which are already using salted password which are encoded
     * with a previous release, the encoder should be of type
     * {@code org.springframework.security.authentication.encoding.PasswordEncoder}
     * . Otherwise, the recommended approach is to use
     * {@code org.springframework.security.crypto.password.PasswordEncoder}.
     *
     * @param passwordEncoder
     *            must be an instance of one of the {@code PasswordEncoder}
     *            types.
     */
    public void setPasswordEncoder(Object passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        if (passwordEncoder instanceof PasswordEncoder) {
            this.passwordEncoder = (PasswordEncoder) passwordEncoder;
            return;
        } else {
            throw new IllegalArgumentException(
                    "passwordEncoder must be a PasswordEncoder instance");
        }
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }

    protected SaltSource getSaltSource() {
        return saltSource;
    }

    public void setUserDetailsService(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }
}
