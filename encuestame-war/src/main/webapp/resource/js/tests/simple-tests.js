doh.register("MyTests", [
  function assertTrueTest(){
    doh.assertTrue(true);
    doh.assertTrue(1);
    doh.assertTrue(!false);
  },
  {
    name: "Primer Test",
    setUp: function(){
      
    },
    runTest: function(){
      doh.assertEqual("blah", "blah");
      doh.assertFalse(false);
      // ...
      doh.assertFalse(true);
    },
    tearDown: function(){
    }
  },
  // ...
]);