"use strict";

var poll_form = require('./impl/poll_form'),
    poll_votes = require('./impl/poll_votes'),
    tweetpoll_form = require('./impl/tweetpoll_form'),
    tweetpoll_votes = require('./impl/tweetpoll_votes'),
    hashtag = require('./impl/hashtag'),
    profile = require('./impl/profile'),
    services = require('../util/services');


var fn = {

    /**
     *
     * @method createPollFormm
     */
    createPollForm: function(widget) {
        return poll_form.render({
            widget : widget,
            url : services().poll_form
        }, function(widget, iframe, css) {
            var node = widget.widget.node;
            document.body.appendChild(iframe);
            node.parentElement.insertBefore(iframe, node);
            var _i_document = iframe.contentDocument;
            _i_document.head.appendChild(css);
           css.onload = function () {
               _i_document.body.innerHTML = widget.body.body;
               iframe.height = "";
               iframe.height = iframe.contentWindow.document.body.scrollHeight;
           }
        });
    },

    /**
     *
     * @method
     */
    createPollVote: function(widget) {
        return poll_votes.render({
            widget : widget,
            url : services().poll_results
        }, function(widget, iframe, css, afterRender) {
            var node = widget.widget.node;
            document.body.appendChild(iframe);
            node.parentElement.insertBefore(iframe, node);
            var _i_document = iframe.contentDocument;
            _i_document.body.innerHTML = widget.body.body;
            css.onload = function () {
                afterRender(_i_document);
                iframe.height = "";
                iframe.height = iframe.contentWindow.document.body.scrollHeight;
            };
            _i_document.head.appendChild(css);
        });
    },

    /**
     *
     * @method
     */
    createTpForm: function(widget) {
        return tweetpoll_form.render({
            widget : widget,
            url : services().tp_poll_form
        }, function(widget, iframe, css) {
            var node = widget.widget.node;
            document.body.appendChild(iframe);
            node.parentElement.insertBefore(iframe, node);
            var _i_document = iframe.contentDocument;
            _i_document.body.innerHTML = widget.body.body;
            _i_document.head.appendChild(css);
        });
    },

    /**
     *
     * @method
     */
    createTpVote: function(widget) {
        return tweetpoll_votes.render({
            widget : widget,
            url : services().tp_poll_votes
        }, function(widget, iframe, css, afterRender) {
            var node = widget.widget.node;
            document.body.appendChild(iframe);
            node.parentElement.insertBefore(iframe, node);
            var _i_document = iframe.contentDocument;
            _i_document.body.innerHTML = widget.body.body;
            css.onload = function () {
                afterRender(_i_document);
                iframe.height = "";
                iframe.height = iframe.contentWindow.document.body.scrollHeight;
            };
            _i_document.head.appendChild(css);
        });
    },

    /**
     *
     * @method
     */
    createProfile: function(widget) {
        return profile.render({
            widget : widget,
            url : services().user_profile
        }, function(widget, iframe, css) {
            var node = widget.widget.node;
            document.body.appendChild(iframe);
            node.parentElement.insertBefore(iframe, node);
            var _i_document = iframe.contentDocument;
            _i_document.body.innerHTML = widget.body.body;
            _i_document.head.appendChild(css);
        });
    },

    /**
     *
     * @method
     */
    createHashtag: function(widget) {

    }
};
module.exports = fn;
