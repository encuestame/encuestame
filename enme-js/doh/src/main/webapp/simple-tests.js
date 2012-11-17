doh.register("MyTests",


[
 function assertTrueTest() {
    doh.assertTrue(true);
    doh.assertTrue(1);
    //doh.assertTrue(!false);
},
{
    name : "Primer Test",
    runTest : function() {
        doh.assertEqual("blah", "blah");
        doh.assertFalse(false);
        // ...
        //doh.assertFalse(true);
    }
},
{
    name : "Primer Testdddddddddddd este fallara",
    runTest : function() {
        doh.assertEqual("blah", "blah");
        doh.assertFalse(false);
        // ...
        //doh.assertFalse(true);
    }
},
{
    name : "Primer Testdddddddddddd este fallarsssa",
    runTest : function() {
        doh.assertEqual("blah", "blah");
        doh.assertFalse(false);
        // ...
        //doh.assertFalse(true);
        var x = "hola pao";
        for (var i=0;i < 10000000 ;i++) {
            x = x + " bella";
        }
    }
}
// ...
]);

doh.register("MyTests 22",


        [
         function assertTrueTest() {
            doh.assertTrue(true);
            doh.assertTrue(1);
            //doh.assertTrue(!false);
        },
        {
            name : "Primer Test",
            runTest : function() {
                doh.assertEqual("blah", "blah");
                doh.assertFalse(false);
                // ...
                //doh.assertFalse(true);
            }
        },
        {
            name : "Primer Testdddddddddddd este fallara",
            runTest : function() {
                doh.assertEqual("blah", "blah");
                doh.assertFalse(false);
                // ...
                //doh.assertFalse(true);
            }
        },
        {
            name : "Primer Testdddddddddddd este fallarsssa",
            runTest : function() {
                doh.assertEqual("blah", "blah");
                doh.assertFalse(false);
                // ...
                //doh.assertFalse(true);
                var x = "hola pao";
                for (var i=0;i < 10000000 ;i++) {
                    x = x + " bella";
                }
            }
        }
        // ...
        ]);