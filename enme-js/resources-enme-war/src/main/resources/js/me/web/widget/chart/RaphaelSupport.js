define([
     "dojo/_base/declare",
     'chart/raphael/raphael.amd',
     "me/core/enme"],
    function(
    declare,
    Raphael,
    _ENME) {

  return declare(null, {

    labels : ["1", "2", "3","1", "2", "3","1", "2", "3","1", "2", "3","1", "2", "3"],
    data : ['1','54','10','30','54','13','18','54','13','43','54','133','53','54','13'],

      constructor: function(node, results, size) {
              //this.node = node;
              //this.data = results;
              //this.size = size;
              //console.debug("data", this.data);
      },

      /*
       *
       */
      _reload : function(){

      },

      getAnchors : function(p1x, p1y, p2x, p2y, p3x, p3y) {
          var l1 = (p2x - p1x) / 2,
              l2 = (p3x - p2x) / 2,
              a = Math.atan((p2x - p1x) / Math.abs(p2y - p1y)),
              b = Math.atan((p3x - p2x) / Math.abs(p2y - p3y));
          a = p1y < p2y ? Math.PI - a : a;
          b = p3y < p2y ? Math.PI - b : b;
          var alpha = Math.PI / 2 - ((a + b) % (Math.PI * 2)) / 2,
              dx1 = l1 * Math.sin(alpha + a),
              dy1 = l1 * Math.cos(alpha + a),
              dx2 = l2 * Math.sin(alpha + b),
              dy2 = l2 * Math.cos(alpha + b);
          return {
              x1: p2x - dx1,
              y1: p2y + dy1,
              x2: p2x + dx2,
              y2: p2y + dy2
          };
      },


      createChart : function() {
              var width = 750,
              height = 300,
              leftgutter = 0,
              bottomgutter = 0,
              topgutter = 0,
              colorhue = .2 || Math.random(),
              color = "hsl(" + [colorhue, .5, .5] + ")",
              r = Raphael("holder", width, height),
              txt = {font: '12px Helvetica, Arial', fill: "#000"},
              txt1 = {font: '10px Helvetica, Arial', fill: "#000"},
              txt2 = {font: '12px Helvetica, Arial', fill: "#000"},
              X = (width - leftgutter) / this.labels.length,
              max = Math.max.apply(Math, this.data),
              Y = (height - bottomgutter - topgutter) / max;
          //r.drawGrid(leftgutter + X * .5 + .5, topgutter + .5, width - leftgutter - X, height - topgutter - bottomgutter, 10, 10, "#E5E5E5");
          //Creates a path element by given path data string.
          var path = r.path().attr({stroke: color, "stroke-width": 2, "stroke-linejoin": "square"}),
              bgp = r.path().attr({stroke: "none", opacity: .3, fill: color}),
              label = r.set(),
              lx = 0, ly = 0,
              is_label_visible = false,
              leave_timer,
              blanket = r.set();
          label.push(r.text(60, 12, "24 hits").attr(txt));
          label.push(r.text(60, 27, "xx label").attr(txt1).attr({fill: color}));
          label.hide();
          //var frame = r.popup(100, 100, label, "right").attr({fill: "#fff", stroke: "#666", "stroke-width": 2, "fill-opacity": .9}).hide();

          var p, bgpp;
          for (var i = 0, ii = this.labels.length; i < ii; i++) {
              var y = Math.round(height - bottomgutter - Y * this.data[i]),
                  x = Math.round(leftgutter + X * (i + .5));
                  //t = r.text(x, height - 6, labels[i]).attr(txt).toBack();
              if (!i) {
                  p = ["M", x, y, "C", x, y];
                  bgpp = ["M", leftgutter + X * .5, height - bottomgutter, "L", x, y, "C", x, y];
              }
              if (i && i < ii - 1) {
                  var Y0 = Math.round(height - bottomgutter - Y * this.data[i - 1]),
                      X0 = Math.round(leftgutter + X * (i - .5)),
                      Y2 = Math.round(height - bottomgutter - Y * this.data[i + 1]),
                      X2 = Math.round(leftgutter + X * (i + 1.5));
                  var a = this.getAnchors(X0, Y0, x, y, X2, Y2);
                  p = p.concat([a.x1, a.y1, x, y, a.x2, a.y2]);
                  bgpp = bgpp.concat([a.x1, a.y1, x, y, a.x2, a.y2]);
              }
              //dot elements
              var dot = r.circle(x, y, 4).attr({fill: "#EED5A2", stroke: color, "stroke-width": 1});
              blanket.push(r.rect(leftgutter + X * i, 0, X, height - bottomgutter).attr({stroke: "none", fill: "#000", opacity: 0}));
              var rect = blanket[blanket.length - 1];
              (function (x, y, data, lbl, dot) {
                  var timer, i = 0;
                  //on mouse hover
//                  rect.hover(function () {
//                      clearTimeout(leave_timer);
//                      var side = "right";
//                      if (x + frame.getBBox().width > width) {
//                          side = "left";
//                      }
//                      var ppp = r.popup(x, y, label, side, 1),
//                          anim = Raphael.animation({
//                              path: ppp.path,
//                              transform: ["t", ppp.dx, ppp.dy]
//                          }, 200 * is_label_visible);
//                      lx = label[0].transform()[0][1] + ppp.dx;
//                      ly = label[0].transform()[0][2] + ppp.dy;
//                      //frame.show().stop().animate(anim);
//                      //label[0].attr({text: data + " hit" + (data == 1 ? "" : "s")}).show().stop().animateWith(frame, anim, {transform: ["t", lx, ly]}, 200 * is_label_visible);
//                      //label[1].attr({text: lbl + " xxx label"}).show().stop().animateWith(frame, anim, {transform: ["t", lx, ly]}, 200 * is_label_visible);
//                      dot.attr("r", 6);
//                      is_label_visible = true;
//                  }, function () {
//                      dot.attr("r", 4);
//                      leave_timer = setTimeout(function () {
//                          frame.hide();
//                          label[0].hide();
//                          label[1].hide();
//                          is_label_visible = false;
//                      }, 1);
//                  });
              })(x, y, this.data[i], this.labels[i], dot);
          }
          p = p.concat([x, y, x, y]);
          bgpp = bgpp.concat([x, y, x, y, "L", x, height - bottomgutter, "z"]);
          path.attr({path: p});
          bgp.attr({path: bgpp});
          //frame.toFront();
          label[0].toFront();
          label[1].toFront();
          blanket.toFront();
      }

  });
});