<div class="tweetPollListWrapper">
         <div>
            <div style="float: left; padding-top: 14px; padding-left: 17px; font-size: 112%; font-weight: bold; color: blue;cursor: pointer;
            text-decoration: underline;">
                    <div dojoAttachEvent="onclick:_onSwichChange" dojoAttachPoint="_swichChange">
                        New TweetPoll
                    </div>
            </div>
             <div id="newTweetPoll" dojoType="dijit.Dialog" id="publishTweetPoll" draggable="false"
                  >
                <div dojoType="encuestame.org.core.commons.tweetPoll.TweetPoll"></div>
             </div>
              <div class="navigation" id="basicNode">
                    <table>
                        <tr>
                            <td>
                                <label>Search by Keyword</label>
                                <input type="text" dojoAttachPoint="_suggest"
                                       dojoType="dijit.form.TextBox"
                                       trim="true" class="inputClass"
                                       ></input>
                            </td>
                            <td align="right" style="padding-left: 95px;">
                               <a href="#"  dojoAttachEvent="onclick:_nextSearch">Next 10 Items</a>
                            </td>
                        </tr>
                    </table>
             </div>
         </div>
         <div id="tweetListContent" class="tweetListContent">
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
                <div class="panel2" dojoAttachPoint="_groups">
                    xxxxxxxxxx xxxxxxxxxxxxx xxxxxxxxxx
                </div>
            </div>
            <div class="listTweet" dojoAttachPoint="_items">

            </div>
            <div class="tweetDetail" id="detailTweet">
                <div id="detail" dojoType="encuestame.org.core.commons.tweetPoll.TweetPollListDetail"></div>
            </div>
         </div>
</div>