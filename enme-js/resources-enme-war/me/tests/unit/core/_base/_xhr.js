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

define([
	'intern!object',
	'intern/chai!assert',
	'intern/chai!expect',
	'../../Helper',
	'me/core/_base/_xhr'
], function (
	registerSuite,
	assert,
	expect,
	Helper,
	_xhr) {

	registerSuite({
		name: 'Base _xhr Test',

		setup: function () {
			Helper.init();
		},

		'service GET Test' : function() {
			var url = _xhr.service('encuestame.service.list.userList');
			expect(url).to.equal("../../tests/resources/api/admon/users.json");
			var url1 = _xhr.service('encuestame.service.list.userList');
			expect(url1).to.equal("../../tests/resources/api/admon/users.json", {});
		},

		//FIXME: These test it doesn't works well with Selenium

		'_xhr GET Test' : function() {
//			var dfd = this.async(5000);
//			dfd.progress(function(){
//				console.log("defferedddd", arguments);
//			});
//			var s = dfd.callback(function (data) {
//				assert.isObject(data);
//			});
//			_xhr.get('encuestame.service.list.userList', {}, s, setTimeout(function(){
//				dfd.reject("ooops");
//			}, 1500));
		},

		'_xhr POST Test' : function() {
//			var dfd = this.async(5000);
//			dfd.progress(function(){
//				console.log("defferedddd", arguments);
//			});
//			var s = dfd.callback(function (data) {
//				assert.isObject(data);
//			});
			//methods not alloved in that enviroment
//			_xhr.post('encuestame.service.list.userList', {}, s, setTimeout(function(){
//				dfd.reject("ooops");
//			}, 1500));
		},

		'_xhr PUT Test' : function() {
//			var dfd = this.async(5000);
//			dfd.progress(function(){
//				console.log("defferedddd", arguments);
//			});
//			var s = dfd.callback(function (data) {
//				assert.isObject(data);
//			});
			//methods not alloved in that enviroment
//			_xhr.put('encuestame.service.list.userList', {}, s, setTimeout(function(){
//				dfd.reject("ooops");
//			}, 1500));
		},

		'_xhr DEL Test' : function() {
//			var dfd = this.async(5000);
//			dfd.progress(function(){
//				console.log("defferedddd", arguments);
//			});
//			var s = dfd.callback(function (data) {
//				assert.isObject(data);
//			});
			//methods not alloved in that enviroment
//			_xhr.del('encuestame.service.list.userList', {}, s, setTimeout(function(){
//				dfd.reject("ooops");
//			}, 1500));
		}


	});
});