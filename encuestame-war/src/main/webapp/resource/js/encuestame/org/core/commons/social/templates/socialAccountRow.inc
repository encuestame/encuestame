<div class="${type}WrapperRow socialWrapperRow">
    <div dojoType="dijit.form.Form" class="socialNewForm">
        <table  dojoAttachEvent="onclick:_showHideSecrets">
            <tr>
                <td>
                    <div class="title">${account.account}</div>
                </td>
                <td>
                    <a href="#"  dojoAttachEvent="onclick:_showHideSecrets">Show Details</a><br/>
                </td>
            </tr>
        </table>
        <div class="tokensWrapper defaultDisplayHide" dojoAttachPoint="_secretView">
            <div class="tokenHideOptions">
                <div class="optionToken">
                      <button id="${id}_remove" dojoType="dijit.form.Button" type="button" dojoAttachEvent="onClick:_remove">
                               Remove
                      </button>
                      <button id="${id}_statusAccount" dojoType="dijit.form.Button" type="button" dojoAttachEvent="onClick:_changeStatusAccount">
                               Disable
                      </button>
                      <button id="${id}_default" dojoType="dijit.form.Button" type="button" dojoAttachEvent="onClick:_changeStatusAccount">
                               Set as Default
                      </button>
                </div>
                <table>
                    <tr>
                        <td>
                            <label>Secret Token</label>
                        </td>
                         <td>
                            <input type="text" name="${id}_secretToken" id="${id}_secretToken"
                                        dojoType="dijit.form.ValidationTextBox"
                                        required="true"
                                        style="width:400px;"
                                        invalidMessage="Consumer Secret is Required"
                                        value="${account.secretToken}"
                                        selectOnClick="true">
                        </td>
                    </tr>
                    <tr>
                        <td>
                           <label> Token</label>
                        </td>
                         <td>
                             <input type="text" name="${id}_token" id="${id}_token"
                                        dojoType="dijit.form.ValidationTextBox"
                                        required="true"
                                        style="width:400px;"
                                        invalidMessage="Token is Required"
                                        value="${account.token}"
                                        selectOnClick="true">
                        </td>
                    </tr>
                     <tr>
                        <td>
                            <label>Profile Url</label>
                        </td>
                        <td>
                            <a href="#" target="_blank">http://twitter.com/xxxxx</a>
                        </td>
                     </tr>
               </table>
            </div>
         </div>
    </div>
</div>
