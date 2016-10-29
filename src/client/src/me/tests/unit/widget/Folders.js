define( [
    "intern!object",
    "intern/chai!assert",
    "me/web/widget/folder/FolderOperations",
    "me/web/widget/folder/FoldersActions",
    "me/web/widget/folder/FolderSelect",
    "me/web/widget/folder/FoldersItemAction"
], function(
    registerSuite,
    assert,
    FolderOperations,
    FoldersActions,
    FolderSelect,
    FoldersItemAction ) {
    registerSuite({
        name: "Folder Widgets",

        "default data": function() {
            var foldersActions = new FoldersActions({

            });
	        assert.isObject( foldersActions, "FoldersActions should be an object" );
        },

        "FolderOperations Widget": function() {
            var folderOperations = new FolderOperations({

            });
            assert.isFunction( FolderOperations, "Folder Operations should be an object" );
        },

        "FoldersSelect Widget": function() {
            var folderSelect = new FolderSelect({

            });
            assert.isObject( folderSelect, "FolderSelect should be an object" );
        },

        "FoldersItemAction Widget": function() {
            var foldersItem = new FoldersItemAction({

            });
            assert.isObject( foldersItem, "FoldersItemAction should be an object" );
        }

    });
});
