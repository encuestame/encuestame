<div class="suggest ">
    <label>${label}</label>
    <input type="text" dojoAttachPoint="_suggest"
           dojoType="dijit.form.TextBox"
           trim="true"
           propercase="true"></input>
    <button id="suggestButton" type="button" style="display: none;" dojoAttachPoint="_suggestButton">
    Add</button>
    <div class="suggestBox" style="opacity :0;"
            dojoAttachPoint="_suggestItems">
    </div>
</div>