<div class="addAnswers">
    <div class="answerAddForm">
        <div class="labelQuestion">
            <label>Add new answer</label>
            <input type="text" dojoAttachPoint="_suggest"
               dojoType="dijit.form.TextBox"
               trim="true" class="inputClass"
               ></input>
               <button id="answerAddButton" type="button" dojoAttachPoint="_addButton">
                  Add
               </button>
        </div>
         <!-- List of Answers -->
        <div class="listAnswerWrapper" dojoAttachPoint="_listAnswers">

        </div>
        <div dojoType="dijit.Dialog" id="option_${id}" title="Options" style="width: 400px;">
              <div class="optionAnswerItem">
                    <span class="optionItem">
                        <button dojoType="dijit.form.Button" dojoAttachEvent="onClick:_removeItem">Remove</button>
                    </span>
                    <span class="optionItem">
                        <button dojoType="dijit.form.Button">Related Previous Answers</button>
                    </span>
                    <span class="optionItem">

                    </span>
              </div>
        </div>
    </div>
</div>