<div class="tweetPollWrapper">
    <h1>Create TweetPoll</h1>
    <div dojoType="dijit.form.Form" id="tableForm">
        <div class="secondOption">
            <div style="float: left; width: 75%; margin-left: 25px;">
                <div id="tweetPollPreview" class="tweetPollPreview" style="min-height: 28px; float: left; min-width: 700px;">
                        <div id="preview" dojoType="dijit.form.Textarea" readOnly="true"
                        style="float: left; max-width: 620px;"></div>
                        <div dojoAttachPoint="_tweetCount" class="tweetCount"
                            style="float: right; font-size: 160%; font-weight: bold;">${maxTweetText}</div>
                </div>
                <div class="tweetPollOptions">
                    <div class="question boxRight">
                        <div class="questionTextBox">
                            <div id="question" dojoType="dijit.form.TextBox" class="questionTextBox"></div>
                        </div>
                        <div  style="float: left; margin-top: 14px; margin-left: 10px;">
                              <button dojoType="dijit.form.Button" dojoAttachEvent="onClick:_publish">Publish</button>
                        </div>
                    </div>

                    <div style="float: left;">
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
    <div dojoType="dijit.Dialog" id="publishTweetPoll" draggable="false" style="width: 600px;">
        <div class="publicationTweetPollWrapper">
            <h2>Publish Your TweetPoll</h2>
            <table width="100%">
            <tr>
                <td width="50%;" valign="top">
                    <div id="accounts" dojoType="encuestame.org.core.commons.tweetPoll.TweetPublishTwitterAccount"></div>
                </td>
                <td>
                    <ul style="list-style: none;">
                        <li>
                            <input id="scheduled" name="mycheck"
                                   dojoType="dijit.form.CheckBox"
                                   dojoAttachEvent="onChange:_activateScheduled" />
                            <label>You want Scheduled?</label>
                        </li>
                        <li id="showDate" style="display: none;">
                            <label>Select the Date</label>
                            <input type="text" name="date1" id="date1"
                                   dojoType="dijit.form.DateTextBox"
                                   required="true"/>
                        </li>
                        <li>
                           <input id="limitVotes" name="mycheck"
                            dojoType="dijit.form.CheckBox" dojoAttachEvent="onChange:_activateSpinner"/>
                           <label>Limit Your Votes?</label>
                        </li>
                        <li id="showSpinner" style="display: none;">
                             <label>Select Range</label>
                            <input dojoType="dijit.form.NumberSpinner"
                                value="1000"
                                smallDelta="100"
                                constraints="{places:0}"
                                maxlength="20"
                                id="integerspinner">
                        </li>
                        <li>
                           <input id="captcha" name="mycheck" dojoType="dijit.form.CheckBox" />
                           <label>Captcha</label>
                        </li>
                         <li>
                           <input id="live" name="mycheck" dojoType="dijit.form.CheckBox" />
                           <label>Live Results</label>
                        </li>
                        <li>
                           <input id="repeated" name="mycheck" dojoType="dijit.form.CheckBox" />
                           <label>Allow Repeated Votes</label>
                        </li>
                        <li>
                           <input id="blockByIp" name="mycheck" dojoType="dijit.form.CheckBox" />
                           <label>Block by IP</label>
                        </li>
                         <li>
                           <input id="resumeNotify" name="mycheck" dojoType="dijit.form.CheckBox" />
                           <label>Notify Resume Results </label>
                        </li>
                    </ul>
                </td>
            </tr>
            </table>
            <div style="text-align: right;">
                <button dojoType="dijit.form.Button"  dojoAttachEvent="onClick:_cancelPublish">Cancel</button>
                <button dojoType="dijit.form.Button" dojoAttachEvent="onClick:_publishTweet">Publish Now !</button>
            </div>
        </div>
    </div>
</div>