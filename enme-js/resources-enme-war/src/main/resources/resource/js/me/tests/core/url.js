define([
	"doh/runner"], function(doh){

    doh.register("URL TEST", [
      function assertTrueTest(){
        doh.assertTrue(true);
        doh.assertTrue(3);
        doh.assertTrue(!false);
        // console.log("ENME", ENME);


        // doh.assertEqual(ENME.STATUS[0], 'SUCCESS');
        // doh.assertEqual(ENME.STATUS[1], 'FAILED');
        // doh.assertEqual(ENME.STATUS[2], 'STAND_BY');
        // doh.assertEqual(ENME.STATUS[3], 'RE_SCHEDULED');
        // doh.assertEqual(ENME.STATUS[4], 'RE_SEND');
      }
    ]);

});
