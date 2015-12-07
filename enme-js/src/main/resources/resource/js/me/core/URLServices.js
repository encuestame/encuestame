define( [ "me/core/enme" ],
    function( _ENME ) {
    return {
        get: _ENME.xhr.get,
        post: _ENME.xhr.post,
        del: _ENME.xhr.del,
        put: _ENME.xhr.put
    };
});
