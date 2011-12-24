///*
// * Random graph with fake data, temp for hashtag detail.
// */
//var r = Raphael("holder", 820, 300);
//var e = [],
//        clr = [],
//        values = [],
//        now = 0,
//
//    c = r.path("M0,0").attr({
//        fill : "none",
//        "stroke-width" : 2,
//        "stroke-linecap" : "round"
//    }),
//    bg = r.path("M0,0").attr({
//        stroke : "none",
//        opacity : .3
//    }),
//    dotsy = [];
//
//function randomPath(length, j) {
//    var path = "", x = 10, y = 0;
//    dotsy[j] = dotsy[j] || [];
//    for ( var i = 0; i < length; i++) {
//        dotsy[j][i] = Math.round(Math.random() * 200);
////         if (i) {
////         path += "C" + [x + 10, y, (x += 20) - 10, (y = 240 - dotsy[j][i]), x,
////         y];
////         } else {
////         path += "M" + [10, (y = 240 - dotsy[j][i])];
////         }
//        if (i) {
//            x += 20;
//            y = 240 - dotsy[j][i];
//            path += "," + [ x, y ];
//        } else {
//            path += "M" + [ 10, (y = 140 - dotsy[j][i]) ] + "R";
//        }
//    }
//    console.info("path", path);
//    return path;
//}
//
//
//for ( var i = 0; i < 12; i++) {
//    values[i] = randomPath(130, i);
//    clr[i] = Raphael.getColor(1);
//}
//c.attr({
//    path : values[0],
//    stroke : clr[0]
//});
//
//bg.attr({
//    path : values[0] + "L590,250 10,250z",
//    fill : clr[0]
//});