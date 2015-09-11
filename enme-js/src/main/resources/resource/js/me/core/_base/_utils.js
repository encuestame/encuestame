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

/***
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.147
 *  @module ModuleName
 *  @namespace Widget
 *  @class FileName
 */

define([
    'me/core/_base/_log',
    "dojo/number",
    "dojo/date/locale",
    "me/third-party/moment",
    "dojo/_base/array",
    "me/core/_base/_config"
    ],function(
        log,
        number,
        locale,
        moment,
        _array,
        _config) {
    return {

        /**
         * Get the protocol and host as single string.
         * @method
         */
        getHttpProtocol: function() {
            return window.location.protocol + "//" + window.location.host;
        },


        /**
         * Convert a format date to relative time.
         * @param date date on string format {String}
         * @param format format of date {String}
         */
        fromNow : function(date, format) {
            try {
                if (moment !== "undefined") {
                    //format || "YYYY-MM-DD"
                    return moment(date).fromNow();
                } else {
                    return date;
                }
            } catch (error) {
                return date;
            }
        },

        /**
         * Check if a string is empty or not.
         */
        isEmpty : function(str) {
            return (!str || 0 === str.length);
        },

        /**
         * Build a user profile url.
         * @param username the user name
         * @method
         */
        usernameLink :function(username) {
            var url = _config.get('contextPath');
            if (username) {
                url = url.concat("/profile/");
                url = url.concat(username);
                return url;
            } else {
                url = url.concat("/404");
                return url;
            }
        },

        /**
         *
         * @param value
         * @returns {boolean}
         */
        validateCharacterDigits : function(value) {
            var nameRegex = /^[a-zA-Z0-9]+$/;
            return value.match(nameRegex) != null;
        },

        /**
         * Get a image path.
         * @method getImage
         */
        getImage: function(image) {
            var image_path = _config.get('contextPath') + "/resources/images/";
            return image_path + image;
        },

        getURLParametersAsObject : function () {
            var query_string = {};
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i=0;i<vars.length;i++) {
                var pair = vars[i].split("=");
                // If first entry with this name
                if (typeof query_string[pair[0]] === "undefined") {
                    query_string[pair[0]] = pair[1];
                    // If second entry with this name
                } else if (typeof query_string[pair[0]] === "string") {
                    var arr = [ query_string[pair[0]], pair[1] ];
                    query_string[pair[0]] = arr;
                    // If third or later entry with this name
                } else {
                    query_string[pair[0]].push(pair[1]);
                }
            }
            return query_string;
        },

        /**
         *
         * @method
         */
        getBoolean : function(value) {
            if (value !== null) {
                if (typeof value === "boolean") {
                    return value;
                } else {
                    return (value === "true" ? true : false);
                }
            }
            return false;
        },

        /**
         *
         * @param provider
         * @returns {String}
         */
        shortPicture : function(provider) {
            var url = _config.get('contextPath') +
                "/resources/images/social/" + provider.toLowerCase() +
                "/enme_icon_" + provider.toLowerCase() + ".png";
            return url;
        },


        /**
         * Convert huge number to relative quantities
         * @method a number to evaluate
         */
        relativeQuantity : function (quantity) {
            if (typeof quantity === 'number') {
                if (quantity > 9999) {
                    var q = "" + quantity;
                    return ">1K";
                } else if (quantity < 9999) {
                    return quantity;
                }
            }
        },

        /**
         * Get format time based on format string.
         * @param date
         * @param fmt
         * @returns
         */
        getFormatTime : function(date, fmt) {
            return locale.format(date, {
                selector: "date",
                datePattern: fmt
            });
        },

        /**
         * Finds the index of an element in the array.
         */
        indexOf : function(array, item, fromIndex) {
            var length = array.length;
            if (fromIndex == null) {
                fromIndex = 0;
            } else {
                if (fromIndex < 0) {
                    fromIndex = Math.max(0, length + fromIndex);
                }
            }
            for ( var i = fromIndex; i < length; i++) {
                if (array[i] === item) {
                    return i;
                }
            }
            return -1;
        },

        /**
         * Looks for an element inside the array.
         */
        contains : function(array, item) {
            return this.indexOf(array, item) > -1;
        },

        /**
         * Check if the url is valid.
         * @returns {Boolean}
         */
        validURL : function (str) {
            var expression = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
            //remove posible parameters
            var regex = new RegExp(expression);
            if (str.match(regex) ) {
                return true;
            } else {
                return false;
            }
        },
        /**
         * Set a fake image if the flag is false
         * @param flag define if is set the fake image
         * @param size define the size of fake image.
         * @param original {String} original url path
         */
        fakeImage : function (size, original) {
            var domain = _config.get('domain'),
                url = "";
            if (!this.validURL(original || '')) {
                switch(size) {
                    case "24":
                        url = domain  + "/resources/images/social/fake_24_24.png";
                        break;
                    case "32":
                        url = domain  + "/resources/images/social/fake_32_32.png";
                        break;
                    case "64":
                        url = domain  + "/resources/images/social/fake_64_64.png";
                        break;
                    case "128":
                        url = domain  + "/resources/images/social/fake_128_128.png";
                        break;
                    default:
                        url = domain  + "/resources/images/social/fake_24_24.png";
                }
                return url;
            } else {
                return original;
            }
        },

        /**
         * Convert a normal number value and return a format a number like 10,332.
         * @param value
         * @returns
         */
        numberFormat : function (value) {
            return number.format(value, {places: 0});
        },

        /**
         *
         * @method
         */
        isEmtpy : function(str) {
            return (!str || 0 === str.length);
        },

        /**
         *
         * @param hashtagName
         * @returns
         */
        hashtagContext : function(hashtagName) {
            if (hashtagName) {
                // http://jsperf.com/concat-test-jc
                var url = _config.get("contextPath");
                url = url + "/tag/";
                url = url + hashtagName;
                url = url + "/";
                return url;
            } else {
                throw new Error("hashtag name is required");
            }
        },
        /**
         *
         * @method
         */
        pollDetailContext : function(id, slug) {
            if (id != null && slug != null) {
                // http://jsperf.com/concat-test-jc
                var url = _config.get("contextPath");
                url +="/poll/";
                url += id;
                url += "/";
                url += slug;
                return url;
            } else {
                throw new Error("poll id is required");
            }
        },

        shortAmmount : function(quantity){
            if (typeof(quantity) === "number") {
                quantity = ( quantity < 0 ? 0  : quantity);
                var text = quantity.toString();
                // 5634 --> 5634k
                if (quantity > 1000) {
                    var quantityReduced = Math.round(quantity / 100);
                    text = quantityReduced.toString();
                    text = text + "K";
                }
                return text;
            } else {
                throw new Error("invalid number");
            }
        },

        /**
         *
         * @method
         */
        orderByDate : function(arrayItems, property, order) {
            var cmp = function(x, y) {
                return x > y? 1 : x < y ? -1 : 0;
            };
            if (order === 'asc') {
                return arrayItems.sort( function(row1, row2) {
                    var k1 = row1[order], k2 = row2[order];
                    return (k1 > k2) ? 1 : ( (k2 > k1) ? -1 : 0 );
                });
            } else if (order === 'desc') {
                return arrayItems.sort( function(row1, row2) {
                    var k1 = row1[order], k2 = row2[order];
                    return (k1 > k2) ? 1 : ( (k2 > k1) ? -1 : 0 );
                });
            } else {
                return arrayItems;
            }
        },

        /**
         * Reorder dynamically an array with objects and multiple properties
         * http://stackoverflow.com/questions/1129216/sorting-objects-in-an-array-by-a-field-value-in-javascript
         * How to use:
         * this.data == array [
         *  { day : 8, month: 1, year : 2003},
         *  { day : 12, month: 4, year : 2023},
         *  { day : 21, month: 5, year : 1985},
         *  { day : 3, month: 2, year : 2003}
         * ]
         *
         * this.data.sort(_ENME.dynamicOrder("day", "month", "year"));
         * @returns {dynamicSortMultiple}
         */
        dynamicOrder : function() {
                var  dynamicSortMultiple = function() {
                    var dynamicSort = function(property) {
                        var sortOrder = 1;
                        if(property[0] === "-") {
                            sortOrder = -1;
                            property = property.substr(1);
                        }
                        return function (a,b) {
                            var result = (a[property] < b[property]) ? -1 : (a[property] > b[property]) ? 1 : 0;
                            return result * sortOrder;
                        }
                    }
                    /*
                     * save the arguments object as it will be overwritten
                     * note that arguments object is an array-like object
                     * consisting of the names of the properties to sort by
                     */
                    var props = arguments;
                    return function (obj1, obj2) {
                        var i = 0, result = 0, numberOfProperties = props.length;
                        /* try getting a different result from 0 (equal)
                         * as long as we have extra properties to compare
                         */
                        while(result === 0 && i < numberOfProperties) {
                            result = dynamicSort(props[i])(obj1, obj2);
                            i++;
                        }
                        return result;
                    }
            }
            return dynamicSortMultiple;
        }
    };
});