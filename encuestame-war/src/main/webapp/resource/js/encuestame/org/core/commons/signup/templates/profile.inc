<div class="updateProfile">
    <table>
        <tr>
            <td>
                <div class="pictureWrapper" dojoAttachPoint="_pictureWrapper">
                    <img src="${contextPath}/resource/images/default.png"/>
                </div>
            </td>
            <td>
                <div class="pictureProfileNew">
                Choose one file from your computer (10 MB máx.):
                <form method="post" action="/encuestame/user/file/upload/profile" enctype="multipart/form-data" id="imageForm">
                      <input type="file" class="file" onkeydown="javascript:return false;" dojoAttachEvent="onchange:_uploadImage"/>
                </form>
                </div>
            </td>
        </tr>
    </table>
    <div>
        <div class="profileItem">
            <label>Your Real Name</label>
            <input id="completeName" type="text" dojoAttachPoint="_suggest" dojoType="dijit.form.TextBox"
                   trim="true"
                   class="inputClass"></input>
        </div>
        <div class="profileItem">
            <label>My Email</label>
            <input type="text" dojoAttachPoint="_suggest" dojoType="dijit.form.TextBox" trim="true"
                   class="inputClass" dojoAttachEvent="onChange:_upgradeEmail"></input>
        </div>

        <div class="profileItem">
            <label>Language</label>
            <div dojoType="dijit.form.ComboButton">
                <span>
                    Select Your Language
                </span>
                <div dojoType="dijit.Menu">
                    <div dojoType="dijit.MenuItem" onClick="console.log('hi!')">
                        Spanish
                    </div>
                    <div dojoType="dijit.MenuItem" onClick="console.log('ho!')">
                        English
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>