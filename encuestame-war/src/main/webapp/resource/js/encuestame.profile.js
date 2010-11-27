dependencies ={
    layers:  [
        {
            name: "encuestame.js",
            dependencies: [
                "encuestame.org.core.contextWidget",
                "encuestame.org.core.commons",
                "encuestame.org.core.commons.tweetPoll.TweetPoll",
                "encuestame.org.core.commons.tweetPoll.HashTags",
                "encuestame.org.core.commons.tweetPoll.Answers",
                "encuestame.org.core.commons.dashboard.Dashboard",
                "encuestame.org.core.commons.dashboard.chart.DashboardPie",
                "encuestame.org.core.commons.dashboard.chart.DashboardLine",
                "encuestame.org.core.commons.dashboard.chart.DashboardColumn2D",
                "encuestame.org.core.admon.project.Projects",
                "encuestame.org.core.admon.user.Users"
                ]
            }
        ],
        prefixes: [
            ["encuestame", "/home/jpicado/workspaceTrunk/encuestame/encuestame-war/src/main/webapp/resource/js/encuestame"],
            ["dijit", "../dijit" ],
            ["dojox", "../dojox" ]
        ]
    };