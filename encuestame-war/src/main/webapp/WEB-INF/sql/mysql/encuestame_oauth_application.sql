insert into oauth_account_social_provider(
name,
implementation,
api_key,
secret,
request_token_url,
authorize_url,
access_token_url)
values
(
'twitter',
'org.encuestame.core.segurity.oauth.TwitterServiceProvider',
'nFboU4T1Zhv8cqMC4cP0ug',
'GwOPUEJEaCbNBiBzq6J8StDhb7FOmwDcjfX6zMe0',
'http://twitter.com/oauth/request_token',
'http://twitter.com/oauth/authorize?oauth_token={token}',
'http://twitter.com/oauth/access_token'
);

insert into oauth_account_social_provider(
name,
implementation,
api_key,
secret,
app_id)
values
(
'facebook',
'org.encuestame.core.segurity.oauth.FacebookServiceProvider',
'db89ecfed3c7f3e212e01b3fc919838ff04049503d8402034b7148d5d3b7976fe4a314a31db0a42d',
'f85145c9f11fd9d920759b89a29c8e3f4ca05864257eeb4e394557f21588ad41a5f44f16242b14d7',
140372495981006
);

insert into oauth_account_social_provider(
name,
implementation,
api_key,
secret,
request_token_url,
authorize_url,
access_token_url)
values
(
'linkedin',
'org.encuestame.core.segurity.oauth.LinkedInServiceProvider',
'oD6j6Qpczo5Qpvyp1oQFgc6m19A-cV1N6zUfIrOq6qCBZ-9mLB7nNrrFtcU09aJ4',
'mkzTYoT-2nsS6bJbNTy_GeKqa-qJ5e7lSK63YL46caH72wytdcNLmbyLyrMbq1LH',
'https://api.linkedin.com/uas/oauth/requestToken',
'https://www.linkedin.com/uas/oauth/authorize?oauth_token={token}',
'https://api.linkedin.com/uas/oauth/accessToken'
);

insert into oauth_account_social_provider(
name,
implementation,
api_key,
secret,
request_token_url,
authorize_url,
access_token_url)
values
(
'tripit',
'org.encuestame.core.segurity.oauth.TripItServiceProvider',
'ed00208afc958b1d6fbd06f7a20faa03e810fa27',
'47c29a0bb66df8c633580e3810536b9b25ff3a59',
'https://api.tripit.com/oauth/request_token',
'https://www.tripit.com/oauth/authorize?oauth_token={token}&oauth_callback=https://xxxx.com/connect/tripit',
'https://api.tripit.com/oauth/access_token'
);


INSERT INTO `encuestame_core`.`application` (`application_id`, `api_key`, `callback_url`, `description`, `icon_url`, `name`, `secret`, `slug`, `account_uid`) VALUES (NULL, 'a08318eb478a1ee31f69a55276f3af64', 'http://localhost:8080/encuestame', 'encuestame oauth app', NULL, 'encuestame', '80e7f8f7ba724aae9103f297e5fb9bdf', NULL, '1');