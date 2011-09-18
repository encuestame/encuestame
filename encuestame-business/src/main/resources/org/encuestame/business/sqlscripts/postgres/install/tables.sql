--
-- PostgreSQL database dump
--

-- Started on 2011-09-10 13:33:11 CEST

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1631 (class 1259 OID 50479)
-- Dependencies: 3
-- Name: account; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE account (
    uid bigint NOT NULL,
    account_created_date timestamp without time zone NOT NULL,
    account_enabled boolean
);


ALTER TABLE public.account OWNER TO postgres;

--
-- TOC entry 1632 (class 1259 OID 50484)
-- Dependencies: 3
-- Name: application; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

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


ALTER TABLE public.application OWNER TO postgres;

--
-- TOC entry 1633 (class 1259 OID 50492)
-- Dependencies: 3
-- Name: application_connection; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE application_connection (
    connection_id bigint NOT NULL,
    access_token character varying(255),
    api_key character varying(255),
    secret character varying(255),
    account_uid bigint,
    application_application_id bigint
);


ALTER TABLE public.application_connection OWNER TO postgres;

--
-- TOC entry 1634 (class 1259 OID 50500)
-- Dependencies: 3
-- Name: attachment; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE attachment (
    attachment_id bigint NOT NULL,
    filename character varying(255) NOT NULL,
    uploaddate timestamp without time zone,
    project_id bigint NOT NULL
);


ALTER TABLE public.attachment OWNER TO postgres;

--
-- TOC entry 1635 (class 1259 OID 50505)
-- Dependencies: 3
-- Name: client; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

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


ALTER TABLE public.client OWNER TO postgres;

--
-- TOC entry 1636 (class 1259 OID 50513)
-- Dependencies: 3
-- Name: comments; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE comments (
    commentid bigint NOT NULL,
    comment character varying(2000) NOT NULL,
    created_at date,
    dislikevote bigint,
    likevote bigint,
    parentid bigint,
    pollid bigint,
    sid bigint,
    tweetpollid bigint,
    uid bigint NOT NULL
);


ALTER TABLE public.comments OWNER TO postgres;

--
-- TOC entry 1637 (class 1259 OID 50521)
-- Dependencies: 3
-- Name: dashboard; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE dashboard (
    dashboardid bigint NOT NULL,
    sequence integer,
    description character varying(255),
    favorite boolean,
    favorite_counter integer,
    dashboardname character varying(255) NOT NULL,
    dashboad_layout integer,
    dashboard_selected boolean,
    userboard_uid bigint
);


ALTER TABLE public.dashboard OWNER TO postgres;

--
-- TOC entry 1638 (class 1259 OID 50529)
-- Dependencies: 3
-- Name: email; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE email (
    email_id bigint NOT NULL,
    created_at timestamp without time zone,
    email character varying(255) NOT NULL,
    emailaccount character varying(255),
    subscribed boolean NOT NULL,
    id_list bigint
);


ALTER TABLE public.email OWNER TO postgres;

--
-- TOC entry 1639 (class 1259 OID 50539)
-- Dependencies: 3
-- Name: emaillist; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE emaillist (
    id_list bigint NOT NULL,
    createdat timestamp without time zone,
    descripcionlist character varying(255),
    list_name character varying(255),
    liststate character varying(255),
    uid bigint NOT NULL
);


ALTER TABLE public.emaillist OWNER TO postgres;

--
-- TOC entry 1640 (class 1259 OID 50547)
-- Dependencies: 3
-- Name: emailsubscribe; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE emailsubscribe (
    id_subscribe bigint NOT NULL,
    hashcode character varying(255) NOT NULL,
    email_id bigint NOT NULL,
    id_list bigint NOT NULL
);


ALTER TABLE public.emailsubscribe OWNER TO postgres;

--
-- TOC entry 1641 (class 1259 OID 50552)
-- Dependencies: 3
-- Name: gadget; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE gadget (
    gadgetid bigint NOT NULL,
    gadgetcolor character varying(255),
    gadgetcolumn integer NOT NULL,
    gadgetname character varying(255) NOT NULL,
    gadgetposition integer,
    gadgettype integer NOT NULL,
    status boolean,
    dashboard_dashboardid bigint
);


ALTER TABLE public.gadget OWNER TO postgres;

--
-- TOC entry 1642 (class 1259 OID 50560)
-- Dependencies: 3
-- Name: gadget_properties; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE gadget_properties (
    propertyid bigint NOT NULL,
    gadget_prop_name character varying(255) NOT NULL,
    gadget_prop_value character varying(255) NOT NULL,
    gadget_gadgetid bigint,
    useraccount_uid bigint
);


ALTER TABLE public.gadget_properties OWNER TO postgres;

--
-- TOC entry 1643 (class 1259 OID 50568)
-- Dependencies: 3
-- Name: geopoint; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE geopoint (
    locate_id bigint NOT NULL,
    lat real,
    lng real,
    accuracy integer,
    address character varying(255),
    country_code character varying(255),
    country_name character varying(255),
    description character varying(255),
    location_status character varying(255),
    account_uid bigint,
    geopointfolder_locate_folder_id bigint,
    loc_id_type bigint
);


ALTER TABLE public.geopoint OWNER TO postgres;

--
-- TOC entry 1644 (class 1259 OID 50576)
-- Dependencies: 3
-- Name: geopoint_folder; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE geopoint_folder (
    locate_folder_id bigint NOT NULL,
    created_at date,
    foldername character varying(255) NOT NULL,
    folder_status integer,
    type character varying(255),
    createdby_uid bigint,
    uid bigint NOT NULL,
    sublocationfolder_locate_folder_id bigint
);


ALTER TABLE public.geopoint_folder OWNER TO postgres;

--
-- TOC entry 1645 (class 1259 OID 50584)
-- Dependencies: 3
-- Name: geopoint_type; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE geopoint_type (
    loc_id_type bigint NOT NULL,
    description character varying(255),
    level integer,
    users_uid bigint
);


ALTER TABLE public.geopoint_type OWNER TO postgres;

--
-- TOC entry 1646 (class 1259 OID 50589)
-- Dependencies: 3
-- Name: group_permission; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE group_permission (
    sec_id_permission bigint NOT NULL,
    sec_id_group bigint NOT NULL
);


ALTER TABLE public.group_permission OWNER TO postgres;

--
-- TOC entry 1647 (class 1259 OID 50594)
-- Dependencies: 3
-- Name: groups; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE groups (
    group_id bigint NOT NULL,
    des_info character varying(255),
    name character varying(50),
    type character varying(255),
    id_state bigint,
    account_uid bigint
);


ALTER TABLE public.groups OWNER TO postgres;

--
-- TOC entry 1648 (class 1259 OID 50602)
-- Dependencies: 3
-- Name: groups_permission; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE groups_permission (
    sec_id_group bigint NOT NULL,
    sec_id_permission bigint NOT NULL
);


ALTER TABLE public.groups_permission OWNER TO postgres;

--
-- TOC entry 1649 (class 1259 OID 50607)
-- Dependencies: 3
-- Name: hash_tags; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE hash_tags (
    hash_tag_id bigint NOT NULL,
    tag character varying(255),
    hits bigint,
    size bigint,
    hashtag_updated_date timestamp without time zone
);


ALTER TABLE public.hash_tags OWNER TO postgres;

--
-- TOC entry 1650 (class 1259 OID 50612)
-- Dependencies: 3
-- Name: hash_tags_hits; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE hash_tags_hits (
    hashtag_hits_id bigint NOT NULL,
    hits_date timestamp without time zone NOT NULL,
    hits_ip_address character varying(100) NOT NULL,
    hashtag_hash_tag_id bigint,
    hits_user_account bigint
);


ALTER TABLE public.hash_tags_hits OWNER TO postgres;

--
-- TOC entry 1690 (class 1259 OID 51351)
-- Dependencies: 3
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 1651 (class 1259 OID 50617)
-- Dependencies: 3
-- Name: notification; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE notification (
    notification_id bigint NOT NULL,
    additional_description character varying(255) NOT NULL,
    created timestamp without time zone NOT NULL,
    description character varying(255) NOT NULL,
    group_notification boolean,
    readed boolean NOT NULL,
    reference character varying(255),
    uid bigint NOT NULL
);


ALTER TABLE public.notification OWNER TO postgres;

--
-- TOC entry 1652 (class 1259 OID 50625)
-- Dependencies: 3
-- Name: permission; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE permission (
    id_permission bigint NOT NULL,
    permission character varying(255),
    description character varying(255)
);


ALTER TABLE public.permission OWNER TO postgres;

--
-- TOC entry 1653 (class 1259 OID 50633)
-- Dependencies: 3
-- Name: poll; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE poll (
    poll_id bigint NOT NULL,
    lat real,
    lng real,
    additionalinfo character varying(255),
    closeafterdate boolean,
    close_after_quota boolean,
    close_date timestamp without time zone,
    closed_quota integer,
    custom_final_message integer,
    custom_message boolean,
    custom_start_message character varying(255),
    hits integer,
    ip_protection character varying(255),
    ip_restrictions boolean,
    multiple_response integer,
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
    poll_completed boolean NOT NULL,
    poll_hash character varying(255) NOT NULL,
    publish_poll boolean,
    poll_show_results boolean,
    update_date timestamp without time zone,
    editor bigint,
    poll_folder bigint,
    uid bigint NOT NULL,
    qid bigint NOT NULL
);


ALTER TABLE public.poll OWNER TO postgres;

--
-- TOC entry 1654 (class 1259 OID 50643)
-- Dependencies: 3
-- Name: poll_folder; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE poll_folder (
    pollfolderid bigint NOT NULL,
    created_at date,
    foldername character varying(255) NOT NULL,
    folder_status integer,
    createdby_uid bigint,
    uid bigint NOT NULL
);


ALTER TABLE public.poll_folder OWNER TO postgres;

--
-- TOC entry 1655 (class 1259 OID 50648)
-- Dependencies: 3
-- Name: poll_result; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE poll_result (
    poll_resultid bigint NOT NULL,
    ip_address character varying(255) NOT NULL,
    votation_date timestamp without time zone NOT NULL,
    q_answer_id bigint NOT NULL,
    poll_id bigint NOT NULL
);


ALTER TABLE public.poll_result OWNER TO postgres;

--
-- TOC entry 1656 (class 1259 OID 50653)
-- Dependencies: 3
-- Name: project; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE project (
    project_id bigint NOT NULL,
    hide_project boolean,
    notify_members boolean,
    priority character varying(255),
    date_finish timestamp without time zone NOT NULL,
    date_start timestamp without time zone NOT NULL,
    description character varying(600),
    project_info text,
    project_name character varying(255) NOT NULL,
    project_status character varying(255),
    published boolean,
    lead_uid bigint,
    users_uid bigint
);


ALTER TABLE public.project OWNER TO postgres;

--
-- TOC entry 1657 (class 1259 OID 50661)
-- Dependencies: 3
-- Name: project_geopoint; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE project_geopoint (
    cat_id_project bigint NOT NULL,
    cat_id_loc bigint NOT NULL
);


ALTER TABLE public.project_geopoint OWNER TO postgres;

--
-- TOC entry 1658 (class 1259 OID 50666)
-- Dependencies: 3
-- Name: project_group; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE project_group (
    sec_id_group bigint NOT NULL,
    cat_id_project bigint NOT NULL
);


ALTER TABLE public.project_group OWNER TO postgres;

--
-- TOC entry 1659 (class 1259 OID 50671)
-- Dependencies: 3
-- Name: project_locations; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE project_locations (
    cat_id_loc bigint NOT NULL,
    cat_id_project bigint NOT NULL
);


ALTER TABLE public.project_locations OWNER TO postgres;

--
-- TOC entry 1660 (class 1259 OID 50676)
-- Dependencies: 3
-- Name: question_category; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE question_category (
    qcategory bigint NOT NULL,
    category character varying(18)
);


ALTER TABLE public.question_category OWNER TO postgres;

--
-- TOC entry 1661 (class 1259 OID 50681)
-- Dependencies: 3
-- Name: question_category_questions; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE question_category_questions (
    question_category_qcategory bigint NOT NULL,
    questionlibrary_qid bigint NOT NULL
);


ALTER TABLE public.question_category_questions OWNER TO postgres;

--
-- TOC entry 1662 (class 1259 OID 50686)
-- Dependencies: 3
-- Name: question_collection; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE question_collection (
    id_q_colection bigint NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    des_coleccion character varying(255) NOT NULL,
    uid bigint NOT NULL
);


ALTER TABLE public.question_collection OWNER TO postgres;

--
-- TOC entry 1663 (class 1259 OID 50691)
-- Dependencies: 3
-- Name: question_dependence_survey; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE question_dependence_survey (
    question_dependence_survey bigint NOT NULL,
    sid bigint NOT NULL
);


ALTER TABLE public.question_dependence_survey OWNER TO postgres;

--
-- TOC entry 1664 (class 1259 OID 50696)
-- Dependencies: 3
-- Name: question_relations; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE question_relations (
    question_id bigint NOT NULL,
    id_q_colection bigint NOT NULL
);


ALTER TABLE public.question_relations OWNER TO postgres;

--
-- TOC entry 1665 (class 1259 OID 50701)
-- Dependencies: 3
-- Name: questions; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE questions (
    qid bigint NOT NULL,
    question_created_date timestamp without time zone,
    question_hits bigint,
    qid_key character varying(255),
    question character varying(255) NOT NULL,
    shared_question boolean,
    question_slug character varying(255) NOT NULL,
    uid bigint NOT NULL,
    id_question_pattern bigint
);


ALTER TABLE public.questions OWNER TO postgres;

--
-- TOC entry 1666 (class 1259 OID 50709)
-- Dependencies: 3
-- Name: questions_answers; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE questions_answers (
    q_answer_id bigint NOT NULL,
    answer character varying(255) NOT NULL,
    answertype integer,
    color character varying(255) NOT NULL,
    created_date timestamp without time zone,
    short_url_provider integer,
    answer_hash character varying(255),
    answer_url character varying(255),
    id_question_answer bigint NOT NULL
);


ALTER TABLE public.questions_answers OWNER TO postgres;

--
-- TOC entry 1667 (class 1259 OID 50717)
-- Dependencies: 3
-- Name: questions_dependencies; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE questions_dependencies (
    question_dependenceid bigint NOT NULL,
    descriptiondependence character varying(255) NOT NULL,
    questionid_from bigint NOT NULL,
    questionid_to bigint NOT NULL,
    q_answer_id bigint NOT NULL
);


ALTER TABLE public.questions_dependencies OWNER TO postgres;

--
-- TOC entry 1668 (class 1259 OID 50728)
-- Dependencies: 3
-- Name: questions_pattern; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE questions_pattern (
    pattenr_id bigint NOT NULL,
    des_qid character varying(255),
    finallity_patter character varying(255),
    label_qid character varying(255) NOT NULL,
    level_patter integer,
    template_patron character varying(255),
    type_pattern character varying(255)
);


ALTER TABLE public.questions_pattern OWNER TO postgres;

--
-- TOC entry 1669 (class 1259 OID 50736)
-- Dependencies: 3
-- Name: social_account; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE social_account (
    social_account_id bigint NOT NULL,
    oauth_token character varying(255),
    type_account integer,
    oauth_app_key bigint,
    oauth2_expires character varying(255),
    picture_profile_url character varying(255),
    public_profile_url character varying(255),
    oauth_refresh_token character varying(255),
    oauth_secret_token character varying(255),
    social_profile_id character varying(255) NOT NULL,
    added_account_date timestamp without time zone NOT NULL,
    default_selected boolean,
    description_profile character varying(255),
    social_account_email character varying(255),
    picture_url character varying(255),
    picture_thumbnail_url character varying(255),
    profile_url character varying(255),
    real_name character varying(255),
    social_account_name character varying(255) NOT NULL,
    social_support integer,
    type_auth character varying(255),
    upgraded_credentials_last_date timestamp without time zone NOT NULL,
    account_verified boolean,
    account_uid bigint,
    userowner_uid bigint
);


ALTER TABLE public.social_account OWNER TO postgres;

--
-- TOC entry 1670 (class 1259 OID 50748)
-- Dependencies: 3
-- Name: survey_folder; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE survey_folder (
    survey_folderid bigint NOT NULL,
    created_at date,
    foldername character varying(255) NOT NULL,
    folder_status integer,
    createdby_uid bigint,
    uid bigint NOT NULL
);


ALTER TABLE public.survey_folder OWNER TO postgres;

--
-- TOC entry 1671 (class 1259 OID 50753)
-- Dependencies: 3
-- Name: survey_format; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE survey_format (
    id_sid_format bigint NOT NULL,
    date_created timestamp without time zone,
    name character varying(60)
);


ALTER TABLE public.survey_format OWNER TO postgres;

--
-- TOC entry 1672 (class 1259 OID 50758)
-- Dependencies: 3
-- Name: survey_group; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE survey_group (
    sg_id bigint NOT NULL,
    date_create timestamp without time zone,
    group_name character varying(60)
);


ALTER TABLE public.survey_group OWNER TO postgres;

--
-- TOC entry 1673 (class 1259 OID 50763)
-- Dependencies: 3
-- Name: survey_group_format; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE survey_group_format (
    sg_id bigint NOT NULL,
    id_sid_format bigint NOT NULL
);


ALTER TABLE public.survey_group_format OWNER TO postgres;

--
-- TOC entry 1674 (class 1259 OID 50768)
-- Dependencies: 3
-- Name: survey_group_project; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE survey_group_project (
    id_sid_format bigint NOT NULL,
    cat_id_project bigint NOT NULL
);


ALTER TABLE public.survey_group_project OWNER TO postgres;

--
-- TOC entry 1675 (class 1259 OID 50773)
-- Dependencies: 3
-- Name: survey_pagination; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE survey_pagination (
    pagination_id bigint NOT NULL,
    pagenumber integer,
    sid bigint NOT NULL,
    ssid bigint NOT NULL
);


ALTER TABLE public.survey_pagination OWNER TO postgres;

--
-- TOC entry 1676 (class 1259 OID 50778)
-- Dependencies: 3
-- Name: survey_result; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE survey_result (
    rid bigint NOT NULL,
    resp character varying(255) NOT NULL,
    survey_id bigint NOT NULL
);


ALTER TABLE public.survey_result OWNER TO postgres;

--
-- TOC entry 1677 (class 1259 OID 50783)
-- Dependencies: 3
-- Name: survey_section; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE survey_section (
    ssid bigint NOT NULL,
    desc_section character varying(255)
);


ALTER TABLE public.survey_section OWNER TO postgres;

--
-- TOC entry 1678 (class 1259 OID 50788)
-- Dependencies: 3
-- Name: survey_section_questions; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE survey_section_questions (
    survey_section_ssid bigint NOT NULL,
    questionsection_qid bigint NOT NULL
);


ALTER TABLE public.survey_section_questions OWNER TO postgres;

--
-- TOC entry 1679 (class 1259 OID 50793)
-- Dependencies: 3
-- Name: surveys; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE surveys (
    sid bigint NOT NULL,
    lat real,
    lng real,
    additionalinfo character varying(255),
    closeafterdate boolean,
    close_after_quota boolean,
    close_date timestamp without time zone,
    closed_quota integer,
    custom_final_message integer,
    custom_message boolean,
    custom_start_message character varying(255),
    hits integer,
    ip_protection character varying(255),
    ip_restrictions boolean,
    multiple_response integer,
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


ALTER TABLE public.surveys OWNER TO postgres;

--
-- TOC entry 1680 (class 1259 OID 50801)
-- Dependencies: 3
-- Name: tweetpoll; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE tweetpoll (
    tweet_poll_id bigint NOT NULL,
    lat real,
    lng real,
    allow_live_results boolean,
    allow_repeated_votes boolean,
    captcha boolean,
    close_notification boolean,
    completed boolean NOT NULL,
    create_date timestamp without time zone,
    limit_with_date boolean,
    date_limited timestamp without time zone,
    favourite boolean,
    hits integer,
    limit_votes integer,
    limits_votes_enabled boolean,
    max_repeated_votes integer,
    numbervotes integer,
    publish boolean,
    result_notification boolean,
    resume_live_results boolean,
    resume_tweetpoll_dashboard boolean,
    schedule_date_tweet timestamp without time zone,
    schedule boolean,
    last_date_updated timestamp without time zone,
    editor bigint,
    qid bigint NOT NULL,
    uid bigint NOT NULL,
    tweetpollfolderid bigint
);


ALTER TABLE public.tweetpoll OWNER TO postgres;

--
-- TOC entry 1681 (class 1259 OID 50806)
-- Dependencies: 3
-- Name: tweetpoll_folder; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE tweetpoll_folder (
    tweetpollfolderid bigint NOT NULL,
    created_at date,
    foldername character varying(255) NOT NULL,
    folder_status integer,
    createdby_uid bigint,
    uid bigint NOT NULL
);


ALTER TABLE public.tweetpoll_folder OWNER TO postgres;

--
-- TOC entry 1683 (class 1259 OID 50819)
-- Dependencies: 3
-- Name: tweetpoll_hashtags; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE tweetpoll_hashtags (
    tweetpoll_id bigint NOT NULL,
    hastag_id bigint NOT NULL
);


ALTER TABLE public.tweetpoll_hashtags OWNER TO postgres;

--
-- TOC entry 1684 (class 1259 OID 50824)
-- Dependencies: 3
-- Name: tweetpoll_result; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE tweetpoll_result (
    tweetpoll_resultid bigint NOT NULL,
    lat real,
    lng real,
    ip_vote character varying(100) NOT NULL,
    tweet_date_response timestamp without time zone NOT NULL,
    tweetpoll_switch_id bigint NOT NULL
);


ALTER TABLE public.tweetpoll_result OWNER TO postgres;

--
-- TOC entry 1682 (class 1259 OID 50811)
-- Dependencies: 3
-- Name: tweetpoll_save_published_status; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE tweetpoll_save_published_status (
    status_save_poll_id bigint NOT NULL,
    type character varying(255),
    status_description character varying(255),
    publication_date_tweet timestamp without time zone,
    status integer,
    tweet_content character varying(255),
    tweet_id character varying(255),
    socialaccount_social_account_id bigint,
    tweetpoll_tweet_poll_id bigint
);


ALTER TABLE public.tweetpoll_save_published_status OWNER TO postgres;

--
-- TOC entry 1685 (class 1259 OID 50829)
-- Dependencies: 3
-- Name: tweetpoll_switch; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE tweetpoll_switch (
    tweetpoll_switch_id bigint NOT NULL,
    tweet_code character varying(255) NOT NULL,
    last_date_updated timestamp without time zone NOT NULL,
    short_url character varying(255),
    q_answer_id bigint NOT NULL,
    tweet_poll_id bigint NOT NULL
);


ALTER TABLE public.tweetpoll_switch OWNER TO postgres;

--
-- TOC entry 1686 (class 1259 OID 50839)
-- Dependencies: 3
-- Name: useraccount; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE useraccount (
    uid bigint NOT NULL,
    lat real,
    lng real,
    name character varying(50),
    date_new timestamp without time zone,
    invite_code character varying(255),
    last_ip_logged character varying(255),
    last_time_logged timestamp without time zone,
    password character varying(255) NOT NULL,
    picture_source integer,
    shared_profile boolean,
    email character varying(150) NOT NULL,
    userprofilepicture character varying(255),
    status boolean,
    username character varying(30) NOT NULL,
    account_uid bigint,
    groupid bigint
);


ALTER TABLE public.useraccount OWNER TO postgres;

--
-- TOC entry 1687 (class 1259 OID 50853)
-- Dependencies: 3
-- Name: useraccount_followers; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE useraccount_followers (
    uid bigint NOT NULL,
    uid_follower bigint NOT NULL
);


ALTER TABLE public.useraccount_followers OWNER TO postgres;

--
-- TOC entry 1688 (class 1259 OID 50858)
-- Dependencies: 3
-- Name: useraccount_permission; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE useraccount_permission (
    sec_id_secondary bigint NOT NULL,
    sec_id_permission bigint NOT NULL
);


ALTER TABLE public.useraccount_permission OWNER TO postgres;

--
-- TOC entry 1689 (class 1259 OID 50863)
-- Dependencies: 3
-- Name: useraccount_project; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE useraccount_project (
    sec_id_secondary bigint NOT NULL,
    cat_id_project bigint NOT NULL
);
