<div>
        <div>
             <a href="#" dojoAttachEvent="onclick:_autorize"  dojoAttachPoint="_addTitle">Add new Twitter Account</a>
             <div class="newTwitterAccount ">
                    <div id="twitterNewAccountForm" dojoType="dijit.form.Form" class="defaultDisplayHide" dojoAttachPoint="_form">
                         <label for="_newTwitterAccount">Pin</label>
                         <input type="text" name="_pin" id="_pin"
                                dojoType="dijit.form.ValidationTextBox"
                                required="true"
                                invalidMessage="Pin is Required"
                                class="defaultDisplayHide"
                                selectOnClick="true">
                        <button id="_authorize" dojoType="dijit.form.Button" type="button" dojoAttachEvent="onClick:_autorizeOpen">
                            Click to Open Authorize Window
                        </button>
                        <button id="_validate" dojoType="dijit.form.Button" type="button" class="defaultDisplayHide" dojoAttachEvent="onClick:_validatePin">
                            Validate
                        </button>
                    </div>
             </div>
        </div>
        <div class="twitterAccounts" dojoAttachPoint="_list">

        </div>
</div>
