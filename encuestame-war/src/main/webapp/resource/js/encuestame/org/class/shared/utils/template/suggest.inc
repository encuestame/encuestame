<div class="suggest ">
    <label>${label}</label>
    <input type="text" dojoAttachPoint="_suggest"
           dojoType="dijit.form.TextBox"
           trim="true"
           propercase="true"></input>
    <div class="suggestBox" style="display: none;
            position: absolute;
            min-height: 200px;
            width: 400px;
            border: 1px solid #000;
            background-color: #FFF;
            right: 120px;
            top: 75px;"
            dojoAttachPoint="_suggestItems">
    </div>
</div>