function callJsonService(){
        new Ajax.Request('http://localhost:8080/encuestame/jota/tweetPoll/votes.json', {
              method:'get',
              parameters: {tweetPollId: 6},
              onSuccess: function(transport){
                 //alert("Success! \n\n");
                 var tran = transport;
                 var json = tran.responseText.evalJSON(true);
                // alert("Success! \n\n" + json);
                // alert(json.gender);
                 this.createChart();
                 this.createBarChart();
                 this.createLineChart();
                 this.createPointChart();
                 this.createAreaChart();
               },
               onFailure: function(){ alert('Something went wrong...') }
            });
       // this.createChart();
    };


    function createChart(){
        new Proto.Chart($('piechart'),
            [
              { data: [[2, 3], [1, 8], [8, 5], [9, 13]], label: "Diana"},
              { data: [[5, 3], [4, 2], [8, 5], [9, 13]], label: "Paola"},
              { data: [[6, 2], [2, 6], [8, 5], [9, 13]], label: "d3"},
              { data: [[2, 1], [2, 7], [8, 5], [9, 13]], label: "d4"},
              { data: [[2, 2], [6, 8], [8, 5], [9, 13]], label: "d5"},
              { data: [[4, 1], [5, 8], [8, 5], [9, 13]], label: "d6"},
              { data: [[1, 1], [2, 8], [8, 5], [9, 13]], label: "d7"}
              ],
              {
              pies: {show: true, autoScale: true},
              legend: {show: true}
              });
        };

   function createBarChart(){
       new Proto.Chart($('barchart'),
              [
                   {data: [[2, 3], [4, 8], [8, 5], [9, 13]], label: "Data 1"},
                   {data: [[5, 3], [3, 8], [2, 5], [7, 13]], label: "Data 2"}
              ],
              {
                   bars: {show: true},
                   xaxis: {min: 0, max: 14, tickSize: 1}

              });

        };


    function createLineChart(){
        new Proto.Chart($('linechart'),
               [
                    {data: [[2, 3], [4, 8], [8, 5], [9, 13]], label: "Data 1"},
                    {data: [[2, 3], [4, 8], [8, 5], [9, 13]], label: "Data 2"}
               ],
               {
                    //since line chart is the default charting view
                     //we do not need to pass any specific options for it.
                    xaxis: {min: 0, max: 14, tickSize: 1},
                });
            };

    function createPointChart(){
        new Proto.Chart($('pointchart'),
               [
                    {data: [[7, 3], [2, 8], [8, 7], [3, 11]], label: "Data 1"},
                    {data: [[4, 3], [7, 3], [1, 8], [7, 13]], label: "Data 2"}
               ],
               {
                     points: {show: true},
                     xaxis: {min: 0, max: 14, tickSize: 1},
               });

               };

    function createAreaChart(){
        new Proto.Chart($('areachart'),
                [
                    {data: [[2, 3], [1, 8], [8, 5], [9, 13]], label: "Data 1"},
                    {data: [[1, 1], [2, 8], [8, 5], [9, 13]], label: "Data 2"}
                ],
                {
                    //since line chart is the default charting view
                    //we do not need to pass any specific options for it.
                    xaxis: {min: 0, max: 14, tickSize: 1},
                    lines: {show: true, fill: true}
                });
                };



        Event.observe(window, 'load', function() {
        this.callJsonService();
    });