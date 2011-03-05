<div class="{type}WrapperRow">
    <div dojoType="dijit.form.Form">
        <div class="title">${account.account}</div>
        <input type="text" name="secretToken" id="secretToken"
                            dojoType="dijit.form.ValidationTextBox"
                            required="true"
                            invalidMessage="Consumer Secret is Required"
                            value="${account.secretToken}"
                            selectOnClick="true">
                            <label for="consumer">
                                Secret Token
                            </label>
         <input type="text" name="token" id="token"
                            dojoType="dijit.form.ValidationTextBox"
                            required="true"
                            invalidMessage="Token is Required"
                            value="${account.token}"
                            selectOnClick="true">
                            <label for="consumer">
                                Token
                            </label>
    </div>
</div>
