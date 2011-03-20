CREATE TABLE account (
    uid bigint NOT NULL,
    twitter_consumer_key character varying(255),
    twitter_consumer_secret character varying(255),
    twitter_account character varying(18),
    twitter_password character varying(18),
    twitter_pin integer
);

CREATE TABLE application (
    application_id bigint NOT NULL,
    api_key character varying(255),
    callback_url character varying(255),
    description character varying(255),
    icon_url character varying(255),
    name character varying(255),
    secret character varying(255),
    slug character varying(255),
    account_uid bigint
);

CREATE TABLE application_connection (
    connection_id bigint NOT NULL,
    access_token character varying(255),
    api_key character varying(255),
    secret character varying(255),
    account_uid bigint,
    application_application_id bigint
);


CREATE TABLE client (
    client_id bigint NOT NULL,
    description character varying(255),
    email character varying(255),
    facebook character varying(255),
    fax character varying(255),
    name character varying(255) NOT NULL,
    telephone character varying(255),
    twitter character varying(255),
    url character varying(255),
    project_id bigint
);

CREATE TABLE email (
    email_id bigint NOT NULL,
    created_at timestamp without time zone,
    email character varying(255) NOT NULL,
    emailaccount character varying(255),
    subscribed boolean NOT NULL,
    id_list bigint
);

CREATE TABLE emaillist (
    id_list bigint NOT NULL,
    createdat timestamp without time zone,
    descripcionlist character varying(255),
    list_name character varying(255),
    liststate character varying(255),
    uid bigint NOT NULL
);

CREATE TABLE emailsubscribe (
    id_subscribe bigint NOT NULL,
    hashcode character varying(255) NOT NULL,
    email_id bigint NOT NULL,
    id_list bigint NOT NULL
);

CREATE TABLE geopoint (
    locate_id bigint NOT NULL,
    accuracy integer,
    address character varying(255),
    country_code character varying(255),
    country_name character varying(255),
    description character varying(255),
    lat real,
    lng real,
    location_status character varying(255),
    account_uid bigint,
    geopointfolder_locate_folder_id bigint,
    loc_id_type bigint
);

CREATE TABLE geopoint_folder (
    locate_folder_id bigint NOT NULL,
    type character varying(255),
    name character varying(255) NOT NULL,
    account_uid bigint,
    sublocationfolder_locate_folder_id bigint
);

ALTER TABLE public.geopoint_folder OWNER TO postgres;

CREATE TABLE geopoint_type (
    loc_id_type bigint NOT NULL,
    description character varying(255),
    level integer,
    users_uid bigint
);

CREATE TABLE group_permission (
    sec_id_permission bigint NOT NULL,
    sec_id_group bigint NOT NULL
);

CREATE TABLE groups (
    group_id bigint NOT NULL,
    des_info character varying(255),
    name character varying(50),
    type character varying(255),
    id_state bigint,
    account_uid bigint
);

CREATE TABLE groups_permission (
    sec_id_group bigint NOT NULL,
    sec_id_permission bigint NOT NULL
);

CREATE TABLE hash_tags (
    hash_tag_id bigint NOT NULL,
    tag character varying(255)
);

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE TABLE notification (
    notification_id bigint NOT NULL,
    additional_description character varying(255) NOT NULL,
    created timestamp without time zone NOT NULL,
    description character varying(255) NOT NULL,
    readed boolean NOT NULL,
    uid bigint NOT NULL
);

CREATE TABLE oauth_account_connection (
    account_connection_id bigint NOT NULL,
    access_token character varying(255),
    profile_url character varying(255),
    secret character varying(255),
    socialaccountid character varying(255),
    accountprovider_social_provider_id bigint,
    useraccout_uid bigint,
    account_provider character varying(255)
);

CREATE TABLE oauth_account_social_provider (
    social_provider_id bigint NOT NULL,
    access_token_url character varying(255),
    api_key character varying(255),
    app_id bigint,
    authorize_url character varying(255),
    implementation character varying(255),
    name character varying(255),
    request_token_url character varying(255),
    secret character varying(255)
);

CREATE TABLE permission (
    id_permission bigint NOT NULL,
    permission character varying(255),
    description character varying(255)
);

CREATE TABLE poll (
    poll_id bigint NOT NULL,
    additionalinfo character varying(255),
    closeafterdate boolean,
    close_after_quota boolean,
    close_date timestamp without time zone,
    closed_quota integer,
    custom_final_message character varying(255),
    custom_message boolean,
    custom_start_message character varying(255),
    hits integer,
    ip_protection character varying(255),
    ip_restrictions boolean,
    multiple_response character varying(255),
    name character varying(255),
    notifications boolean,
    numbervotes integer,
    optional_title character varying(255),
    password_protection character varying(255),
    password_restrictions boolean,
    showadditionalinfo boolean,
    showcomments integer,
    show_progress_bar boolean,
    showresults boolean,
    close_notification boolean,
    created_at timestamp without time zone NOT NULL,
    end_date timestamp without time zone,
    completed boolean NOT NULL,
    poll_hash character varying(255) NOT NULL,
    publish_poll boolean,
    show_results boolean,
    editor bigint,
    poll_folder bigint,
    uid bigint NOT NULL,
    qid bigint NOT NULL
);


CREATE TABLE poll_folder (
    pollfolderid bigint NOT NULL,
    created_at date,
    foldername character varying(255) NOT NULL,
    uid bigint NOT NULL
);

CREATE TABLE poll_result (
    poll_resultid bigint NOT NULL,
    ip_address character varying(255) NOT NULL,
    votation_date timestamp without time zone NOT NULL,
    q_answer_id bigint NOT NULL,
    poll_id bigint NOT NULL
);

CREATE TABLE project (
    project_id bigint NOT NULL,
    hide_project boolean,
    notify_members boolean,
    priority character varying(255),
    date_finish timestamp without time zone NOT NULL,
    date_start timestamp without time zone NOT NULL,
    description character varying(600),
    project_info text,
    name character varying(255) NOT NULL,
    project_status character varying(255),
    published boolean,
    lead_uid bigint,
    users_uid bigint
);

CREATE TABLE project_geopoint (
    cat_id_project bigint NOT NULL,
    cat_id_loc bigint NOT NULL
);

CREATE TABLE project_group (
    sec_id_group bigint NOT NULL,
    cat_id_project bigint NOT NULL
);

CREATE TABLE project_locations (
    cat_id_loc bigint NOT NULL,
    cat_id_project bigint NOT NULL
);

CREATE TABLE question_category (
    qcategory bigint NOT NULL,
    category character varying(18)
);

CREATE TABLE question_category_questions (
    question_category_qcategory bigint NOT NULL,
    questionlibrary_qid bigint NOT NULL
);

CREATE TABLE question_collection (
    id_q_colection bigint NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    des_coleccion character varying(255) NOT NULL,
    uid bigint NOT NULL
);

CREATE TABLE question_dependence_survey (
    question_dependence_survey bigint NOT NULL,
    sid bigint NOT NULL
);

CREATE TABLE question_relations (
    question_id bigint NOT NULL,
    id_q_colection bigint NOT NULL
);


CREATE TABLE questions (
    qid bigint NOT NULL,
    qid_key character varying(255),
    question character varying(255),
    shared_question boolean,
    uid bigint NOT NULL,
    id_question_pattern bigint
);

CREATE TABLE questions_answers (
    q_answer_id bigint NOT NULL,
    answer character varying(255),
    answertype integer,
    answer_hash character varying(255),
    answer_url character varying(255),
    id_question_answer bigint NOT NULL
);

CREATE TABLE questions_dependencies (
    question_dependenceid bigint NOT NULL,
    descriptiondependence character varying(255) NOT NULL,
    questionid_from bigint NOT NULL,
    questionid_to bigint NOT NULL,
    q_answer_id bigint NOT NULL
);

CREATE TABLE questions_pattern (
    pattenr_id bigint NOT NULL,
    des_qid character varying(255),
    finallity_patter character varying(255),
    label_qid character varying(255) NOT NULL,
    level_patter integer,
    template_patron character varying(255),
    type_pattern character varying(255)
);

CREATE TABLE social_account (
    sec_user_twitter_id bigint NOT NULL,
    type_account character varying(255),
    oauth_secret_token character varying(255),
    social_account_name character varying(255) NOT NULL,
    social_account_id bigint NOT NULL,
    oauth_token character varying(255),
    type_auth character varying(255),
    account_verified boolean,
    secusers_uid bigint,
    default_selected boolean
);

CREATE TABLE survey_folder (
    survey_folderid bigint NOT NULL,
    created_at date,
    foldername character varying(255) NOT NULL,
    uid bigint NOT NULL
);

CREATE TABLE survey_format (
    id_sid_format bigint NOT NULL,
    date_created timestamp without time zone,
    name character varying(60)
);

CREATE TABLE survey_group (
    sg_id bigint NOT NULL,
    date_create timestamp without time zone,
    group_name character varying(60)
);

CREATE TABLE survey_group_format (
    sg_id bigint NOT NULL,
    id_sid_format bigint NOT NULL
);


CREATE TABLE survey_group_project (
    id_sid_format bigint NOT NULL,
    cat_id_project bigint NOT NULL
);

CREATE TABLE survey_pagination (
    pagination_id bigint NOT NULL,
    pagenumber integer,
    sid bigint NOT NULL,
    ssid bigint NOT NULL
);

CREATE TABLE survey_result (
    rid bigint NOT NULL,
    resp character varying(255) NOT NULL,
    survey_id bigint NOT NULL
);

CREATE TABLE survey_section (
    ssid bigint NOT NULL,
    desc_section character varying(255)
);

CREATE TABLE survey_section_questions (
    survey_section_ssid bigint NOT NULL,
    questionsection_qid bigint NOT NULL
);

CREATE TABLE surveys (
    sid bigint NOT NULL,
    additionalinfo character varying(255),
    closeafterdate boolean,
    close_after_quota boolean,
    close_date timestamp without time zone,
    closed_quota integer,
    custom_final_message character varying(255),
    custom_message boolean,
    custom_start_message character varying(255),
    hits integer,
    ip_protection character varying(255),
    ip_restrictions boolean,
    multiple_response character varying(255),
    name character varying(255),
    notifications boolean,
    numbervotes integer,
    optional_title character varying(255),
    password_protection character varying(255),
    password_restrictions boolean,
    showadditionalinfo boolean,
    showcomments integer,
    show_progress_bar boolean,
    showresults boolean,
    complete character varying(2),
    date_interview date,
    end_date timestamp without time zone NOT NULL,
    start_date timestamp without time zone NOT NULL,
    ticket integer NOT NULL,
    editor bigint,
    uid bigint NOT NULL,
    id_sid_format bigint NOT NULL,
    survey_folder bigint
);

CREATE TABLE tweetpoll (
    tweet_poll_id bigint NOT NULL,
    allow_live_results boolean,
    allow_repeated_votes boolean,
    captcha boolean,
    close_notification boolean,
    completed boolean NOT NULL,
    create_date timestamp without time zone,
    favourite boolean,
    hits integer,
    limit_votes integer,
    numbervotes integer,
    publish boolean,
    result_notification boolean,
    resume_live_results boolean,
    schedule_date_tweet timestamp without time zone,
    schedule boolean,
    editor bigint,
    qid bigint NOT NULL,
    uid bigint NOT NULL,
    tweetpollfolderid bigint
);

CREATE TABLE tweetpoll_folder (
    tweetpollfolderid bigint NOT NULL,
    created_at date,
    foldername character varying(255) NOT NULL,
    uid bigint NOT NULL
);

CREATE TABLE tweetpoll_hash_tags (
    tweetpoll_tweet_poll_id bigint NOT NULL,
    hashtags_hash_tag_id bigint NOT NULL
);

CREATE TABLE tweetpoll_result (
    tweetpoll_resultid bigint NOT NULL,
    ip_vote character varying(100) NOT NULL,
    tweet_date_response timestamp without time zone NOT NULL,
    tweetpoll_switch_id bigint NOT NULL
);

CREATE TABLE tweetpoll_save_published_status (
    status_save_poll_id bigint NOT NULL,
    type character varying(255),
    status_description character varying(255),
    publication_date_tweet timestamp without time zone,
    status character varying(255),
    tweet_id bigint,
    tweetpoll_tweet_poll_id bigint,
    twitteraccount_sec_user_twitter_id bigint
);

CREATE TABLE tweetpoll_switch (
    tweetpoll_switch_id bigint NOT NULL,
    tweet_code character varying(255) NOT NULL,
    q_answer_id bigint NOT NULL,
    tweet_poll_id bigint NOT NULL
);

CREATE TABLE useraccount (
    uid bigint NOT NULL,
    name character varying(50),
    date_new timestamp without time zone,
    invite_code character varying(255),
    last_ip_logged character varying(255),
    last_time_logged timestamp without time zone,
    password character varying(255) NOT NULL,
    email character varying(150) NOT NULL,
    userprofilepicture character varying(255),
    status boolean,
    twitter character varying(255),
    username character varying(30) NOT NULL,
    account_uid bigint,
    groupid bigint
);

CREATE TABLE useraccount_followers (
    uid bigint NOT NULL,
    uid_follower bigint NOT NULL
);

CREATE TABLE useraccount_permission (
    sec_id_secondary bigint NOT NULL,
    sec_id_permission bigint NOT NULL
);

CREATE TABLE useraccount_project (
    sec_id_secondary bigint NOT NULL,
    cat_id_project bigint NOT NULL
);
