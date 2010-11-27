<div style="padding: 10px;">
    <div class="menuOptions">
        <div class="left">
            <a dojoAttachEvent="onclick:_newUser">New User</a>
        </div>
        <div class="right">
            <div dojoType="dijit.form.DropDownButton" iconClass="noteIcon">
                  <span>Actions</span>
                  <div dojoType="dijit.Menu">
                    <div dojoType="dijit.MenuItem"  iconClass="dijitEditorIcon dijitEditorIconCut"
                      onClick="console.log('not actually cutting anything, just a test!')">Cut</div>
                    <div dojoType="dijit.MenuItem"  iconClass="dijitEditorIcon dijitEditorIconCopy"
                      onClick="console.log('not actually copying anything, just a test!')">Copy</div>
                    <div dojoType="dijit.MenuItem"  iconClass="dijitEditorIcon dijitEditorIconPaste"
                      onClick="console.log('not actually pasting anything, just a test!')">Paste</div>
                    <div dojoType="dijit.MenuSeparator"></div>
                    <div dojoType="dijit.PopupMenuItem">
                      <span>Submenu</span>
                      <div dojoType="dijit.Menu" id="submenu2">
                        <div dojoType="dijit.MenuItem" onClick="console.log('Submenu 1!')">Submenu Item One</div>
                        <div dojoType="dijit.MenuItem" onClick="console.log('Submenu 2!')">Submenu Item Two</div>
                        <div dojoType="dijit.PopupMenuItem">
                          <span>Deeper Submenu</span>
                          <div dojoType="dijit.Menu" id="submenu4"">
                            <div dojoType="dijit.MenuItem" onClick="console.log('Sub-submenu 1!')">Sub-sub-menu Item One</div>
                            <div dojoType="dijit.MenuItem" onClick="console.log('Sub-submenu 2!')">Sub-sub-menu Item Two</div>
                          </div>
                        </div>
                      </div>
                    </div>
              </div>
          </div>
    </div>
    </div>
    <div>
        <div dojoType="dijit.form.Form" id="tableForm">
            <table id="hor-minimalist-b" cellpadding="5" cellspacing="4" class="defaultTable" width="800px">
                    <thead>
                        <tr>
                            <th scope="col">Select</th>
                            <th scope="col">Username</th>
                            <th scope="col" width="150px">Real Name</th>
                            <th scope="col" width="150px">Email</th>
                            <th scope="col">Permissions</th>
                            <th scope="col" width="150px">User from</th>
                            <th scope="col">Status</th>
                        </tr>
                     </thead>
                      <tfoot>
                        <tr>
                            <td colspan="7" class="rounded-foot-left">
                                <div class="controlsTable" style="cursor: pointer;">
                                    <a dojoAttachEvent="onclick:first" dojoAttachPoint="_first"> First Page</a>
                                    <a dojoAttachEvent="onclick:previous" dojoAttachPoint="_previous"> Previous </a>
                                    <a dojoAttachEvent="onclick:next" dojoAttachPoint="_next">Next > </a>
                                    <a dojoAttachEvent="onclick:last" dojoAttachPoint="_last">Last Page  </a>
                                </div>
                            </td>
                            <td class="rounded-foot-right">&nbsp;</td>
                        </tr>
                    </tfoot>
                <tbody dojoAttachPoint="_body">
                </tbody>
            </table>
        </div>
    </div>
    <div jsId="basicStandby6" dojoType="dojox.widget.Standby" target="newUser"/>
    <div dojoType="dojox.widget.Dialog" dimensions="[1030,310]" id="newUser">
            <div dojoType="dijit.layout.AccordionContainer" style="min-height: 300px; width: 1024px;">
                <div dojoType="dijit.layout.ContentPane" title="Add User Directly" selected="true">

                    <div dojoType="dijit.form.Form" id="newUserSimpleForm" class="formDefault">
                        <table>
                            <tr>
                              <td><label for="name">Username </label></td>
                              <td><input dojoType="dijit.form.ValidationTextBox"
                                   required="true"
                                   size="30"
                                   type="text" name="newUsername" id="newUsername"></td>
                            </tr>
                            <tr>
                              <td><label for="loc">Email </label></td>
                              <td><input dojoType="dijit.form.ValidationTextBox"
                                   size="30"
                                   required="true"
                                   regExp="[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}"
                                   type="text" name="newEmailUser" id="newEmailUser"></td>
                            </tr>
                          </table>
                          <button dojoType="dijit.form.Button" type="button" dojoAttachEvent="onClick:_createDirectlyUser">Save</button>
                      </div>
                </div>
                <div dojoType="dijit.layout.ContentPane" title="Invitate User" >
                     <div style="padding: 20px;">
                         <label for="desc">Write list of emails: </label>
                         <input dojoType="dijit.form.TextBox" type="text" name="emailList" id="emailList">
                     </div>
                </div>
                <div dojoType="dijit.layout.ContentPane" title="Search User (LDAP..)">
                    <div style="padding: 20px;">
                        LDAP Search
                    </div>
                </div>
        </div>
    </div>
    <div dojoType="dijit.Dialog" id="userEdit" title="User Edit"  width="400px">
        <div dojoType="dijit.layout.TabContainer" style="width: 1024px; height: 500px" tabposition="top">
            <div dojoType="dijit.layout.ContentPane" title="User Detail" selected="true">
                <div dojoType="dijit.form.Form" id="editUserForm" class="formDefault">
                    <table cellpadding="9" cellspacing="8">
                        <tr>
                          <td><label for="name">Username </label></td>
                          <td><input dojoType="dijit.form.TextBox" type="text" name="name" id="name"></td>
                        </tr>
                        <tr>
                          <td><label for="loc">Email: </label></td>
                          <td><input dojoType="dijit.form.TextBox" type="text" name="email" id="email"></td>
                        </tr>
                         <tr>
                          <td><label for="loc">Real Name: </label></td>
                          <td><input dojoType="dijit.form.TextBox" type="text" name="realName" id="realName"></td>
                        </tr>
                        <tr>
                          <td colspan="2" align="left">
                            <button dojoType="dijit.form.Button" type="button" dojoAttachEvent="onclick:_updateUser">Update</button></td>
                        </tr>
                      </table>
                 </div>
            </div>
            <div id="groups" dojoType="dijit.layout.ContentPane" title="Groups">
                Groups

            </div>
            <div id="permissions" dojoType="dijit.layout.ContentPane" title="Permisions">
                 <div id="widgetPermission" dojoType="encuestame.org.core.admon.user.UserPermissions"></div>
            </div>
            <div id="social" dojoType="dijit.layout.ContentPane" title="Social Accounts">
                Social
            </div>
            <div id="log" dojoType="dijit.layout.ContentPane" title="Log">
                Social
            </div>
        </div>
    </div>
</div>