CREATE TABLE access_rate (
  rateid bigint NOT NULL,
  ipaddress character varying(255) NOT NULL,
  rate boolean,
  created_at date,
  comments_commentid bigint,
  poll_poll_id bigint,
  survey_sid bigint,
  tweetpoll_tweet_poll_id bigint,
  user_uid bigint
);


CREATE TABLE account (
  uid bigint NOT NULL,
  account_created_date timestamp without time zone NOT NULL,
  account_enabled boolean
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


CREATE TABLE attachment (
  attachment_id bigint NOT NULL,
  filename character varying(255) NOT NULL,
  uploaddate timestamp without time zone,
  project_id bigint NOT NULL
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


CREATE TABLE comments (
  commentid bigint NOT NULL,
  comment character varying(2000) NOT NULL,
  comment_status integer,
  created_at timestamp without time zone,
  dislikevote bigint,
  is_published boolean,
  is_spam boolean,
  likevote bigint,
  parentid bigint,
  pollid bigint,
  sid bigint,
  tweetpollid bigint,
  uid bigint NOT NULL
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

CREATE TABLE gadget (
  gadgetid bigint NOT NULL,
  gadgetcolor character varying(255),
  gadgetcolumn integer NOT NULL,
  gadgetname character varying(255) NOT NULL,
  gadgetposition integer,
  gadgettype integer NOT NULL,
  status boolean
);


CREATE TABLE gadget_properties (
  propertyid bigint NOT NULL,
  gadget_prop_name character varying(255) NOT NULL,
  gadget_prop_value character varying(255) NOT NULL,
  gadget_gadgetid bigint,
  useraccount_uid bigint
);

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
  created_updated timestamp without time zone,
  tag character varying(255),
  hits bigint,
  size bigint,
  hashtag_updated_date timestamp without time zone
);

CREATE TABLE hash_tags_ranking (
  rank_id bigint NOT NULL,
  average double precision,
  ranking_updated date,
  hashtag_hash_tag_id bigint
);


CREATE SEQUENCE hibernate_sequence
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


CREATE TABLE hits (
  hit_id bigint NOT NULL,
  hit_category integer NOT NULL,
  created_at date,
  hits_ip_address character varying(100) NOT NULL,
  hashtag_hash_tag_id bigint,
  poll_poll_id bigint,
  survey_sid bigint,
  tweetpoll_tweet_poll_id bigint,
  type_item integer DEFAULT NULL,
  question_qid bigint,
  useraccount_uid bigint
);


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

CREATE TABLE permission (
  id_permission bigint NOT NULL,
  permission character varying(255),
  description character varying(255)
);


CREATE TABLE poll (
  poll_id bigint NOT NULL,
  lat real,
  lng real,
  additional_info character varying(255),
  closeafterdate boolean,
  close_after_quota boolean,
  close_date timestamp without time zone,
  closed_quota integer,
  created_at timestamp without time zone,
  custom_final_message integer,
  custom_message boolean,
  custom_start_message character varying(255),
  dislike_vote bigint,
  limit_votes integer,
  limits_votes_enabled boolean,
  repeated_votes integer,
  repeated_votes_enabled boolean,
  end_date timestamp without time zone,
  favorites boolean,
  hits bigint,
  ip_protection character varying(255),
  ip_restrictions boolean,
  like_vote bigint,
  multiple_response integer,
  name character varying(255),
  notifications boolean,
  number_votes bigint,
  optional_title character varying(255),
  password_protection character varying(255),
  password_restrictions boolean,
  relevance bigint,
  schedule_date_tweet timestamp without time zone,
  schedule boolean,
  showadditionalinfo boolean,
  comment_option integer,
  show_results integer,
  update_date timestamp without time zone,
  poll_completed boolean NOT NULL,
  poll_hash character varying(255) NOT NULL,
  publish_poll boolean,
  editor bigint,
  owner_id bigint,
  poll_folder bigint,
  qid bigint NOT NULL,
  is_hidden boolean,
  is_password_protected boolean,
  poll_password character varying(255) NULL
);



CREATE TABLE poll_folder (
  pollfolderid bigint NOT NULL,
  created_at date,
  foldername character varying(255) NOT NULL,
  folder_status integer,
  createdby_uid bigint,
  uid bigint NOT NULL
);


CREATE TABLE poll_hashtags (
  poll_id bigint NOT NULL,
  hastag_id bigint NOT NULL
);

CREATE TABLE poll_result (
  poll_resultid bigint NOT NULL,
  ipaddress character varying(255) NOT NULL,
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
  project_name character varying(255) NOT NULL,
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


CREATE TABLE question_preferences (
  preferenceid bigint NOT NULL,
  preference character varying(255),
  preference_value character varying(255),
  question_qid bigint
);


CREATE TABLE question_relations (
  question_id bigint NOT NULL,
  id_q_colection bigint NOT NULL
);


CREATE TABLE questions (
  qid bigint NOT NULL,
  question_created_date timestamp without time zone,
  question_hits bigint,
  qid_key character varying(255),
  question character varying(255) NOT NULL,
  question_pattern integer,
  shared_question boolean,
  question_slug character varying(255) NOT NULL,
  uid bigint NOT NULL,
  section_ssid bigint
);

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

CREATE TABLE questions_dependencies (
  question_dependenceid bigint NOT NULL,
  descriptiondependence character varying(255) NOT NULL,
  questionid_from bigint NOT NULL,
  questionid_to bigint NOT NULL,
  q_answer_id bigint NOT NULL
);


CREATE TABLE scheduled (
  publish_scheduled_id bigint NOT NULL,
  publication_date timestamp without time zone,
  publish_attempts integer,
  scheduled_date timestamp without time zone,
  status integer,
  tweet_text character varying(255),
  type_search integer,
  poll_poll_id bigint,
  socialaccount_social_account_id bigint,
  survey_sid bigint,
  tpoll_tweet_poll_id bigint,
  tpollsavedpublished_status_save_poll_id bigint
);


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

CREATE TABLE survey_folder (
  survey_folderid bigint NOT NULL,
  created_at date,
  foldername character varying(255) NOT NULL,
  folder_status integer,
  createdby_uid bigint,
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

CREATE TABLE survey_hashtags (
  sid bigint NOT NULL,
  hastag_id bigint NOT NULL
);



CREATE TABLE survey_pagination (
  pagination_id bigint NOT NULL,
  pagenumber integer,
  sid bigint NOT NULL,
  ssid bigint NOT NULL
);



CREATE TABLE survey_result (
  rid bigint NOT NULL,
  txtresponse character varying(255),
  answer_q_answer_id bigint,
  question_qid bigint,
  survey_sid bigint
);


CREATE TABLE survey_section (
  ssid bigint NOT NULL,
  desc_section character varying(255),
  section_name character varying(255),
  survey_sid bigint
);

CREATE TABLE survey_temporal_result (
  idtempresult bigint NOT NULL,
  txtresponse character varying(255),
  hash character varying(255) NOT NULL,
  answer_q_answer_id bigint,
  question_qid bigint,
  survey_sid bigint
);


CREATE TABLE surveys (
  sid bigint NOT NULL,
  lat real,
  lng real,
  additional_info character varying(255),
  closeafterdate boolean,
  close_after_quota boolean,
  close_date timestamp without time zone,
  closed_quota integer,
  created_at timestamp without time zone,
  custom_final_message integer,
  limit_votes integer,
  limits_votes_enabled boolean,
  repeated_votes integer,
  repeated_votes_enabled boolean,
  custom_message boolean,
  custom_start_message character varying(255),
  dislike_vote bigint,
  end_date timestamp without time zone,
  favorites boolean,
  hits bigint,
  ip_protection character varying(255),
  ip_restrictions boolean,
  like_vote bigint,
  multiple_response integer,
  name character varying(255),
  notifications boolean,
  number_votes bigint,
  optional_title character varying(255),
  password_protection character varying(255),
  password_restrictions boolean,
  relevance bigint,
  schedule_date_tweet timestamp without time zone,
  schedule boolean,
  showadditionalinfo boolean,
  comment_option integer,
  show_results integer,
  update_date timestamp without time zone,
  complete character varying(2),
  date_interview date,
  is_schedule boolean,
  show_progress_bar boolean,
  start_date timestamp without time zone,
  survey_slug_name character varying(255),
  ticket integer,
  editor bigint,
  owner_id bigint,
  project_project_id bigint,
  is_hidden boolean,
  is_password_protected boolean,
  poll_password character varying(255) NULL,
  survey_folder bigint
);

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
  dislikevote bigint,
  favourite boolean,
  hits bigint,
  likevote bigint,
  limit_votes integer,
  limits_votes_enabled boolean,
  max_repeated_votes integer,
  numbervotes bigint,
  publish boolean,
  relevance bigint,
  result_notification boolean,
  resume_live_results boolean,
  schedule_date_tweet timestamp without time zone,
  schedule boolean,
  comment_option integer,
  last_date_updated timestamp without time zone,
  editor bigint,
  qid bigint NOT NULL,
  uid bigint NOT NULL,
  tweetpollfolderid bigint
);


CREATE TABLE tweetpoll_folder (
  tweetpollfolderid bigint NOT NULL,
  created_at date,
  foldername character varying(255) NOT NULL,
  folder_status integer,
  createdby_uid bigint,
  uid bigint NOT NULL
);


CREATE TABLE tweetpoll_hashtags (
  tweetpoll_id bigint NOT NULL,
  hastag_id bigint NOT NULL
);

CREATE TABLE tweetpoll_rate (
  tweetpollrateid bigint NOT NULL,
  status boolean,
  user_uid bigint,
  tweetpoll_tweet_poll_id bigint
);


CREATE TABLE tweetpoll_result (
  tweetpoll_resultid bigint NOT NULL,
  lat real,
  lng real,
  ip_vote character varying(100) NOT NULL,
  tweet_date_response timestamp without time zone NOT NULL,
  tweetpoll_switch_id bigint NOT NULL
);



CREATE TABLE tweetpoll_save_published_status (
  status_save_poll_id bigint NOT NULL,
  type character varying(255),
  status_description character varying(255),
  publication_date_tweet timestamp without time zone,
  status integer,
  tweet_content character varying(255),
  tweet_id character varying(255),
  poll_poll_id bigint,
  socialaccount_social_account_id bigint,
  survey_sid bigint,
  tweetpoll_tweet_poll_id bigint
);



CREATE TABLE tweetpoll_switch (
  tweetpoll_switch_id bigint NOT NULL,
  tweet_code character varying(255) NOT NULL,
  last_date_updated timestamp without time zone NOT NULL,
  relative_url character varying(400),
  short_url character varying(400),
  q_answer_id bigint NOT NULL,
  tweet_poll_id bigint NOT NULL
);



CREATE TABLE useraccount (
  uid bigint NOT NULL,
  lat real,
  lng real,
  name character varying(50),
  date_new timestamp without time zone,
  invite_code character varying(255),
  user_language character varying(8),
  last_ip_logged character varying(255),
  last_time_logged timestamp without time zone,
  password character varying(255) NOT NULL,
  picture_source integer,
  shared_profile boolean,
  email character varying(150) NOT NULL,
  userprofilepicture character varying(255),
  status boolean,
  welcome_page boolean,
  help_links boolean,
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


CREATE TABLE helppage (
    help_page_id bigint NOT NULL,
    url_path character varying(255),
    help_user_id bigint
);
