<div class="tweetPollWrapper">
    <h1>Create TweetPoll</h1>
    <div dojoType="dijit.form.Form" id="tableForm">
        <div class="secondOption">
            <div style="float: left; border: 1px solid #000; min-height: 300px; background-color: #EEE; width: 200px;"
                  class="infoTweetPoll">
                    INFO
            </div>
            <div style="float: left; width: 75%; margin-left: 25px;">
                <div class="tweetPollPreview" style="min-height: 28px; float: left; min-width: 700px;">
                        <div id="preview" dojoType="dijit.form.Textarea" readOnly="true" class="inputClass"
                        style="float: left; max-width: 620px;"></div>
                        <div dojoAttachPoint="_tweetCount" class="tweetCount"
                            style="float: right; font-size: 160%; font-weight: bold;">${maxTweetText}</div>
                </div>
                <div class="tweetPollOptions">
                    <div class="question boxRight">
                        <div class="questionTitle" style="font-weight: bold;">Question</div>
                        <div id="question" dojoType="dijit.form.Textarea" style="inputClass"></div>
                    </div>
                    <div>
                        <div class="boxRight" style="float: left;">
                            <div id="answers" dojoType="encuestame.org.core.commons.tweetPoll.Answers" class="answers"></div>
                        </div>
                         <div class="boxRight" style="float: right;">
                            <div id="hashtags" dojoType="encuestame.org.core.commons.tweetPoll.HashTags" class="hashtags"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>