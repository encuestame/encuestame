/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.utils.enums;

/**
 * Encuestame Global Permissions
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 3, 2010 4:14:05 PM
 */
public enum EnMePermission {
    ENCUESTAME_USER("ENCUESTAME_USER"),
    ENCUESTAME_ADMIN("ENCUESTAME_ADMIN"),
    ENCUESTAME_OWNER("ENCUESTAME_OWNER"),
    ENCUESTAME_PUBLISHER("ENCUESTAME_PUBLISHER"),
    ENCUESTAME_EDITOR("ENCUESTAME_EDITOR"),
    ENCUESTAME_ANONYMOUS("ENCUESTAME_ANONYMOUS"),
    ENCUESTAME_API("ENCUESTAME_API"),
    ENCUESTAME_READ("ENCUESTAME_READ"),
    ENCUESTAME_WRITE("ENCUESTAME_WRITE");

   // EnMePermission(){};

    private String permissionAsString;

    /**
     *
     * @param permissionAsString
     */
    EnMePermission(String permissionAsString){
        this.permissionAsString = permissionAsString;
    }


    /**
     * Get Permission by String.
     * @param permission period
     * @return
     */
    public static EnMePermission getPermissionString(final String permission) {
        if (null == permission) { return ENCUESTAME_USER; }
        else if (permission.equalsIgnoreCase("ENCUESTAME_ADMIN")) { return ENCUESTAME_ADMIN; }
        else if (permission.equalsIgnoreCase("ENCUESTAME_OWNER")) { return ENCUESTAME_OWNER; }
        else if (permission.equalsIgnoreCase("ENCUESTAME_PUBLISHER")) { return ENCUESTAME_PUBLISHER; }
        else if (permission.equalsIgnoreCase("ENCUESTAME_EDITOR")) { return ENCUESTAME_EDITOR; }
        else if (permission.equalsIgnoreCase("ENCUESTAME_ANONYMOUS")) { return ENCUESTAME_ANONYMOUS; }
        else if (permission.equalsIgnoreCase("ENCUESTAME_USER")) { return ENCUESTAME_USER; }
        else if (permission.equalsIgnoreCase("ENCUESTAME_API")) { return ENCUESTAME_API; }
        else if (permission.equalsIgnoreCase("ENCUESTAME_READ")) { return ENCUESTAME_READ; }
        else if (permission.equalsIgnoreCase("ENCUESTAME_WRITE")) { return ENCUESTAME_WRITE; }
        else return ENCUESTAME_USER;
    }
}
