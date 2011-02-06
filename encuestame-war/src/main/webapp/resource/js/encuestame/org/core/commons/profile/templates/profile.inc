<div class="accountUpdateWrapper">

       <h2>User Profile </h2>
       <div dojoType="dijit.form.Form" id="profileForm" class="formDefault" style="display=none;" dojoAttachPoint="_form">
           <div>
               <input type="text" name="email" id="email"
                        dojoType="dijit.form.ValidationTextBox"
                        required="true"
                        validateType="email"
                        regExp="[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}" required="false"
                        invalidMessage="Invalid email." lowercase="true" maxLength="40" selectOnClick="true">
                        <label for="email">
                            We need email to sent notificaitons, email required.
                        </label>
           </div>
           <div>
               <input type="text" name="username" id="username"
                        dojoType="dijit.form.ValidationTextBox"
                        required="true"
                        validateType="username"
                        regExp="[\w]+" required="false" invalidMessage="Invalid username">
                        <label for="username">
                            without spaces, check availability
                        </label>
            </div>
            <div>
                <input type="text" name="completeName" id="completeName"
                        dojoType="dijit.form.ValidationTextBox"
                        regExp="[\w]+" required="false" >
                        <label for="completeName">
                            Optional, your real name
                        </label>
             </div>
             <div>
                 <select name="language" dojoType="dijit.form.Select">
                    <option value="ES_es">
                        Español
                    </option>
                    <option value="EN_en" selected="selected">
                        English
                    </option>
                </select>
             </div>
             <div>
                <textarea id="bio" name="bio" dojoType="dijit.form.Textarea"
                    style="width:200px;">
                        Write your bio....
                    </textarea>

             </div>
              <button dojoType="dijit.form.Button" type="button" dojoAttachEvent="onClick:_updateProfile">Save</button>
      </div>
</div>