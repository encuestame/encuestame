define( [
    "intern!object",
    "intern/chai!assert",
    "me/web/widget/data/CacheLinkedList",
    "me/web/widget/data/FilterList",
    "me/web/widget/data/FilterSupport",
    "me/web/widget/data/Table",
    "me/web/widget/data/TableLinkedList",
    "me/web/widget/data/TableRow"
], function(
    registerSuite,
    assert,
    CacheLinkedList,
    FilterList,
    FilterSupport,
    Table,
    TableLinkedList,
    TableRow ) {
    registerSuite({
        name: "Data Widgets",

        "CacheLinkedList Widget": function() {
            var cachedLinkedList = new CacheLinkedList({

            });
            assert.isObject( cachedLinkedList, "CacheLinkedList should be an object" );
        },

        "FilterList Widget": function() {
            var filterList = new FilterList({

            });
            assert.isObject( filterList, "FilterList should be an object" );
        },

        "FilterSupport Widget": function() {
            var filterSupport = new TableLinkedList({

            });
            assert.isObject( filterSupport, "FilterSupport should be an object" );
        },

        "Table Widget": function() {
            var table = new Table({

            });
            assert.isObject( table, "Table should be an object" );
        },

        "TableLinkedList Widget": function() {
            var tableLinkedList = new TableLinkedList({

            });
            assert.isObject( tableLinkedList, "TableLinkedList should be an object" );
        },

        "TableRow Widget": function() {
            var tableRow = new TableRow({
				data: {
					id: 1,
					name: "test",
					email: "test@test.com",
					status: "true"
				}
            });
            assert.isObject( tableRow, "TableRow should be an object" );
        }
    });
});
