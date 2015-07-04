define([
    'intern!object',
    'intern/chai!assert',
    'me/web/widget/comments/Comment',
    'me/web/widget/comments/Comments'
], function (
    registerSuite,
    assert,
    FolderOperations,
    FoldersActions,
    FolderSelect,
    FoldersItemAction) {
    registerSuite({
        name: 'Comments Widgets',
        
        'default data': function () {
            var comments = new Comments({

            });
        }
    });
});