/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

$().ready(function() {
    $("#loginForm").validate({
        rules: {
            j_username: "required",
            j_password: "required",
            j_username: {
                required: true,
                minlength: 4
            },
            j_password: {
                required: true,
                minlength: 5
            }
        },
        messages: {
            j_username: {
                required: "Please enter a username",
                minlength: "Your username must consist of at least 4 characters"
            },
            j_password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 5 characters long"
            }
       }
    });
});

$(document).ready(function(){
    $("#submit").click(function(){
       if($("#loginForm").valid()){;
          document.loginForm.submit();
       }
    });
});