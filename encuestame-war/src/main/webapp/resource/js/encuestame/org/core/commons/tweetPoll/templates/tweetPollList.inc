<div class="tweetPollListWrapper">
    <div id="newTweetPoll" dojoType="encuestame.org.core.commons.tweetPoll.TweetPoll"
         class="defaultDisplayHide"></div>
         <div class="tweetListContent">
            <div class="options">
                <div class="panel1">
                    <div class="optionItem" dojoAttachEvent="onclick:_searchByAccount">
                        <span>By Account</span>
                    </div>
                    <div class="optionItem" dojoAttachEvent="onclick:_searchByLastDay">
                        <span>Today</span>
                    </div>
                    <div class="optionItem" dojoAttachEvent="onclick:_searchByLastWeek">
                        <span>Last Week</span>
                    </div>
                    <div class="optionItem" dojoAttachEvent="onclick:_searchByFavourites">
                        <span>Favourites</span>
                    </div>
                    <div class="optionItem" dojoAttachEvent="onclick:_searchByScheduled">
                        <span>Scheduled</span>
                    </div>
                    <div class="optionItem" dojoAttachEvent="onclick:_searchByAll">
                        <span>See All</span>
                    </div>
                </div>
                <div class="panel2">
                    xxxxxxxxxx xxxxxxxxxxxxx xxxxxxxxxx
                </div>
            </div>
            <div class="listTweet" dojoAttachPoint="_items">

            </div>
            <div class="tweetDetail">
                <div id="detail" dojoType="encuestame.org.core.commons.tweetPoll.TweetPollListDetail"></div>
            </div>
         </div>
</div>