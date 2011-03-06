<div class="${type}WrapperRow socialWrapperRow">
    <div dojoType="dijit.form.Form">
        <div class="optionToken">
                <a href="#"  dojoAttachEvent="onclick:_remove">Remove</a><br/>
                <a href="#"  dojoAttachEvent="onclick:_changeStatusAccount">Enable/Disable Account</a><br/>
        </div>
        <div class="title">${account.account}</div>
        <div class="tokensWrapper">
            <label for="consumer">Secret Token</label>
            <input type="text" name="${id}_secretToken" id="${id}_secretToken"
                                dojoType="dijit.form.ValidationTextBox"
                                required="true"
                                invalidMessage="Consumer Secret is Required"
                                value="${account.secretToken}"
                                selectOnClick="true">
             <label for="consumer">Token</label>
             <input type="text" name="${id}_token" id="${id}_token"
                                dojoType="dijit.form.ValidationTextBox"
                                required="true"
                                invalidMessage="Token is Required"
                                value="${account.token}"
                                selectOnClick="true">
         </div>
    </div>
</div>
