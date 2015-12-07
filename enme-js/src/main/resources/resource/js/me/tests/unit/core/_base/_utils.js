/**
 * Copyright 2014 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

define( [
	"intern!object",
	"intern/chai!assert",
	"intern/chai!expect",
	"../../Helper",
	"me/core/_base/_utils"
], function(
	registerSuite,
	assert,
	expect,
	Helper,
	_utils ) {

	registerSuite({
		name: "Base Utils Test",

		setup: function() {
			Helper.init();
		},

		"getHttpProtocol Test": function() {
			var http = _utils.getHttpProtocol();
			assert.match( http, /http/ );
		},

		"fromNow Test": function() {
			var a = new Date( 2014, 1, 1 );
			var _date = _utils.fromNow( a, "YYYY-MM-DD");

			// Note, is not possible to be more accurate due the response change in the time
			assert.match( _date, /ago/ );
		},

		"usernameLink Test": function() {
			var usernameLink = _utils.usernameLink("test");

			// Note, is not possible to be more accurate due the response change in the time
			expect( usernameLink ).to.equal( "/js/me/tests/resources/profile/test" );
		},

		"validateCharacterDigits Test": function() {
			var usernameLink = _utils.validateCharacterDigits("test4343");

			// Note, is not possible to be more accurate due the response change in the time
			assert.isTrue( usernameLink );
		},

		"getImage Test": function() {
			var getImage = _utils.getImage("test.png");

			// Note, is not possible to be more accurate due the response change in the time
			expect( getImage ).to.equal( "/js/me/tests/resources/resources/images/test.png" );
		},

		"getURLParametersAsObject Test": function() {
			var getURLParametersAsObject = _utils.getURLParametersAsObject();

			// Note, is not possible to be more accurate due the response change in the time
			assert.isObject( getURLParametersAsObject );
		},

		"getBoolean Test": function() {
			expect( _utils.getBoolean("true") ).to.equal( true );
			expect( _utils.getBoolean("false") ).to.equal( false );
			expect( _utils.getBoolean("") ).to.equal( false );
			expect( _utils.getBoolean( null ) ).to.equal( false );
		},

		"shortPicture Test": function() {
			var shortPicture = _utils.shortPicture( "jotaPicture" );
			expect( shortPicture ).to.equal( "/js/me/tests/resources/resources/images/social/jotapicture/enme_icon_jotapicture.png" );
		},

		"relativeQuantity Test": function() {
			expect( _utils.relativeQuantity( 1 ) ).to.equal( 1 );
			expect( _utils.relativeQuantity( 100 ) ).to.equal( 100 );
			expect( _utils.relativeQuantity( 1000 ) ).to.equal( 1000 );
			expect( _utils.relativeQuantity( 10000 ) ).to.equal( ">1K" );
			expect( _utils.relativeQuantity( 1000000000 ) ).to.equal( ">1K" );
		},

		"getFormatTime Test": function() {
			var a = new Date( 2014, 1, 1 );
			expect( _utils.getFormatTime( a, "yyyy-MM-dd" ) ).to.equal( "2014-02-01" );
		},

		"indexOf Test": function() {
			expect( _utils.indexOf( [ 1, 2, 3, 4, 5, 6 ], 1 ) ).to.equal( 0 );
			expect( _utils.indexOf( [ 1, 2, 3, 4, 5, 6 ], 3 ) ).to.equal( 2 );
			expect( _utils.indexOf( [ 1, 2, 3, 4, 5, 6 ], 3, 3 ) ).to.equal( -1 );
			expect( _utils.indexOf( [ 1, 2, 3, 4, 5, 6 ], 3, 2 ) ).to.equal( 2 );
		},

		"contains Test": function() {
			expect( _utils.contains( [ 1, 2, 3, 4, 5, 6 ], 1 ) ).to.equal( true );
			expect( _utils.contains( [ 1, 2, 3, 4, 5, 6 ], 3 ) ).to.equal( true );
			expect( _utils.contains( [ 1, 2, 3, 4, 5, 6 ], 5 ) ).to.equal( true );
			expect( _utils.contains( [ 1, 2, 3, 4, 5, 6 ], 66 ) ).to.equal( false );
		},

		"validURL Test": function() {
			expect( _utils.validURL( "http://www.google.es" ) ).to.equal( true );
			expect( _utils.validURL( "ftp://www.google.es" ) ).to.equal( true );
			expect( _utils.validURL( "htts://www.google.es" ) ).to.equal( false );
			expect( _utils.validURL( "https://www.google.es" ) ).to.equal( true );
			expect( _utils.validURL( "test" ) ).to.equal( false );
		},

		"fakeImage Test": function() {
			expect( _utils.fakeImage( "24" ) ).to.equal( "/js/me/tests/resources/resources/images/social/fake_24_24.png" );
			expect( _utils.fakeImage( "32" ) ).to.equal( "/js/me/tests/resources/resources/images/social/fake_32_32.png" );
			expect( _utils.fakeImage( "64" ) ).to.equal( "/js/me/tests/resources/resources/images/social/fake_64_64.png" );
			expect( _utils.fakeImage( "128" ) ).to.equal( "/js/me/tests/resources/resources/images/social/fake_128_128.png" );
			expect( _utils.fakeImage( "" ) ).to.equal( "/js/me/tests/resources/resources/images/social/fake_24_24.png" );
		},

		"numberFormat Test": function() {
			expect( _utils.numberFormat( 1 ) ).to.equal( "1" );

            //FIXME: This value could change in depends of the browser language.
			expect( _utils.numberFormat( 1000 ) ).to.equal( "1,000" );
			expect( _utils.numberFormat( 200000 ) ).to.equal( "200,000" );
		},

		"isEmtpy Test": function() {
			expect( _utils.isEmtpy( "" ) ).to.equal( true );
			expect( _utils.isEmtpy( "test" ) ).to.equal( false );
		},

		"hashtagContext Test": function() {
			expect( _utils.hashtagContext( "hashtag1" ) ).to.equal( "/js/me/tests/resources/tag/hashtag1/" );
			expect( _utils.hashtagContext( "hashtag2" ) ).to.equal( "/js/me/tests/resources/tag/hashtag2/" );
		},

		"pollDetailContext Test": function() {
			expect( _utils.pollDetailContext( "test", "test-slug-1" ) ).to.equal( "/js/me/tests/resources/poll/test/test-slug-1" );
			expect( _utils.pollDetailContext( "test", "test-slug-2" ) ).to.equal( "/js/me/tests/resources/poll/test/test-slug-2" );
		},

		"shortAmmount Test": function() {
			expect( _utils.shortAmmount( 1 ) ).to.equal( "1" );
			expect( _utils.shortAmmount( 1001 ) ).to.equal( "10K" ); //FIXME: review this
			expect( _utils.shortAmmount( 222222 ) ).to.equal( "2222K" );
		},

		"orderByDate Test": function() {
			var data = [{ "comment":"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.", "likeVote":755, "type":"TWEETPOLL", "id":64, "created_at":1388127673918, "dislike_vote":89, "item_id":11, "url":"/tweetpoll/11/what-are-your-favorite-hobbies-interests/#comment64", "commented_by":"complete demo username", "commented_username":"demo4", "parent_id":null, "comment_status":"PUBLISHED" }, { "comment":"Glauben oder nicht glauben, Lorem Ipsum ist nicht nur ein zufälliger Text. Er hat Wurzeln aus der Lateinischen Literatur von 45 v. Chr, was ihn über 2000 Jahre alt macht. Richar McClintock, ein Lateinprofessor des Hampden-Sydney College in Virgnia untersuche einige undeutliche Worte, \"consectetur\", einer Lorem Ipsum Passage und fand eine unwiederlegbare Quelle. Lorem Ipsum komm aus der Sektion 1.10.32 und 1.10.33 des \"de Finibus Bonorum et Malorum\" (Die Extreme von Gut und Böse) von Cicero, geschrieben 45 v. Chr. Dieses Buch ist Abhandlung der Ethiktheorien, sehr bekannt wärend der Renaissance. Die erste Zeile des Lorem Ipsum, \"Lorem ipsum dolor sit amet...\", kommt aus einer Zeile der Sektion 1.10.32.\n\n", "likeVote":721, "type":"TWEETPOLL", "id":66, "created_at":1392102073981, "dislike_vote":127, "item_id":11, "url":"/tweetpoll/11/what-are-your-favorite-hobbies-interests/#comment66", "commented_by":"complete demo username", "commented_username":"demo9", "parent_id":null, "comment_status":"PUBLISHED" }, { "comment":"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?", "likeVote":494, "type":"TWEETPOLL", "id":63, "created_at":1394262073906, "dislike_vote":103, "item_id":11, "url":"/tweetpoll/11/what-are-your-favorite-hobbies-interests/#comment63", "commented_by":"complete demo username", "commented_username":"demo6", "parent_id":null, "comment_status":"PUBLISHED" }, { "comment":"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?", "likeVote":488, "type":"TWEETPOLL", "id":62, "created_at":1387695673879, "dislike_vote":72, "item_id":11, "url":"/tweetpoll/11/what-are-your-favorite-hobbies-interests/#comment62", "commented_by":"complete demo username", "commented_username":"demo8", "parent_id":null, "comment_status":"PUBLISHED" }, { "comment":"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains", "likeVote":402, "type":"TWEETPOLL", "id":65, "created_at":1384758073954, "dislike_vote":71, "item_id":11, "url":"/tweetpoll/11/what-are-your-favorite-hobbies-interests/#comment65", "commented_by":"complete demo username", "commented_username":"demo6", "parent_id":null, "comment_status":"PUBLISHED" }, { "comment":"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"", "likeVote":182, "type":"TWEETPOLL", "id":61, "created_at":1381647673844, "dislike_vote":83, "item_id":11, "url":"/tweetpoll/11/what-are-your-favorite-hobbies-interests/#comment61", "commented_by":"complete demo username", "commented_username":"demo3", "parent_id":null, "comment_status":"PUBLISHED" }];
			var orderer_data = [{ "comment":"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"", "likeVote":182, "type":"TWEETPOLL", "id":61, "created_at":1381647673844, "dislike_vote":83, "item_id":11, "url":"/tweetpoll/11/what-are-your-favorite-hobbies-interests/#comment61", "commented_by":"complete demo username", "commented_username":"demo3", "parent_id":null, "comment_status":"PUBLISHED" }, { "comment":"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains", "likeVote":402, "type":"TWEETPOLL", "id":65, "created_at":1384758073954, "dislike_vote":71, "item_id":11, "url":"/tweetpoll/11/what-are-your-favorite-hobbies-interests/#comment65", "commented_by":"complete demo username", "commented_username":"demo6", "parent_id":null, "comment_status":"PUBLISHED" }, { "comment":"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?", "likeVote":488, "type":"TWEETPOLL", "id":62, "created_at":1387695673879, "dislike_vote":72, "item_id":11, "url":"/tweetpoll/11/what-are-your-favorite-hobbies-interests/#comment62", "commented_by":"complete demo username", "commented_username":"demo8", "parent_id":null, "comment_status":"PUBLISHED" }, { "comment":"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?", "likeVote":494, "type":"TWEETPOLL", "id":63, "created_at":1394262073906, "dislike_vote":103, "item_id":11, "url":"/tweetpoll/11/what-are-your-favorite-hobbies-interests/#comment63", "commented_by":"complete demo username", "commented_username":"demo6", "parent_id":null, "comment_status":"PUBLISHED" }, { "comment":"Glauben oder nicht glauben, Lorem Ipsum ist nicht nur ein zufälliger Text. Er hat Wurzeln aus der Lateinischen Literatur von 45 v. Chr, was ihn über 2000 Jahre alt macht. Richar McClintock, ein Lateinprofessor des Hampden-Sydney College in Virgnia untersuche einige undeutliche Worte, \"consectetur\", einer Lorem Ipsum Passage und fand eine unwiederlegbare Quelle. Lorem Ipsum komm aus der Sektion 1.10.32 und 1.10.33 des \"de Finibus Bonorum et Malorum\" (Die Extreme von Gut und Böse) von Cicero, geschrieben 45 v. Chr. Dieses Buch ist Abhandlung der Ethiktheorien, sehr bekannt wärend der Renaissance. Die erste Zeile des Lorem Ipsum, \"Lorem ipsum dolor sit amet...\", kommt aus einer Zeile der Sektion 1.10.32.\n\n", "likeVote":721, "type":"TWEETPOLL", "id":66, "created_at":1392102073981, "dislike_vote":127, "item_id":11, "url":"/tweetpoll/11/what-are-your-favorite-hobbies-interests/#comment66", "commented_by":"complete demo username", "commented_username":"demo9", "parent_id":null, "comment_status":"PUBLISHED" }, { "comment":"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.", "likeVote":755, "type":"TWEETPOLL", "id":64, "created_at":1388127673918, "dislike_vote":89, "item_id":11, "url":"/tweetpoll/11/what-are-your-favorite-hobbies-interests/#comment64", "commented_by":"complete demo username", "commented_username":"demo4", "parent_id":null, "comment_status":"PUBLISHED" }];

			//FIXME: not well implemented
			expect( _utils.orderByDate( data, "created_at", "desc" ).reverse() ).to.equal( data );
			expect( _utils.orderByDate( data, "created_at", "asc" ).reverse() ).to.equal( data );
		}
	});
});
