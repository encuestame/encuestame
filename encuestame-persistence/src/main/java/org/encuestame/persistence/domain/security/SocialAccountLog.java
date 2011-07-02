package org.encuestame.persistence.domain.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "social_account_log")
public class SocialAccountLog {

    private Long id;

    /**
     * Reference to User Account.
     */
    private UserAccount userAccount;

    /**
     *
     */
    private SocialAccount socialAccount;

    /**
     *
     */
    private Date dateLogged;

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    /**
     * @return the userAccount
     */
    @ManyToOne()
    public UserAccount getUserAccount() {
        return userAccount;
    }


    /**
     * @param userAccount the userAccount to set
     */
    public void setUserAccount(final UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * @return the dateLogged
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_logged", nullable = false)
    public Date getDateLogged() {
        return dateLogged;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param dateLogged the dateLogged to set
     */
    public void setDateLogged(final Date dateLogged) {
        this.dateLogged = dateLogged;
    }

    /**
     * @return the socialAccount
     */
    @ManyToOne()
    public SocialAccount getSocialAccount() {
        return socialAccount;
    }

    /**
     * @param socialAccount the socialAccount to set
     */
    public void setSocialAccount(final SocialAccount socialAccount) {
        this.socialAccount = socialAccount;
    }
}
