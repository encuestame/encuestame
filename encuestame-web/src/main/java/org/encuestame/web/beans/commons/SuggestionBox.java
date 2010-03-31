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
package org.encuestame.web.beans.commons;

import java.io.Serializable;
import java.util.ArrayList;

import org.richfaces.renderkit.html.SuggestionBoxRenderer;
/**
 * Suggestion Box.
 * @author Picado, Juan juan@encuestame.org
 * @since 01/06/2009 12:24:19
 * @version $Id$
 */
public class SuggestionBox implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList tokens;

    private String rows;
    private String first;
    private String cellspacing;
    private String cellpadding;
    private String minchars;
    private String frequency;
    private String rules;
    private boolean check;
    private String shadowDepth = Integer.toString(SuggestionBoxRenderer.SHADOW_DEPTH);
    private String border = "1";
    private String width = "250";
    private String height = "150";
    private String shadowOpacity = "4";

    public SuggestionBox() {
        this.rows = "0";
        this.first = "0";
        this.cellspacing = "2";
        this.cellpadding = "2";
        this.minchars = "1";
        this.frequency = "0";
        this.rules = "none";

    }

    public final String getCellpadding() {
        return cellpadding;
    }

    public final void setCellpadding(final String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public final String getCellspacing() {
        return cellspacing;
    }

    public final void setCellspacing(final String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public final boolean isCheck() {
        return check;
    }

    public final void setCheck(final boolean check) {
        this.check = check;
    }

    public final String getFirst() {
        return first;
    }

    public final int getIntFirst() {
        return Integer.parseInt(getFirst());
    }

    public final void setFirst(final String first) {
        this.first = first;
    }

    public final String getFrequency() {
        return frequency;
    }

    public final double getDoubleFrequency() {
        return Double.parseDouble(getFrequency());
    }

    public final void setFrequency(final String frequency) {
        this.frequency = frequency;
    }

    public final String getMinchars() {
        return minchars;
    }

    public final void setMinchars(String minchars) {
        this.minchars = minchars;
    }

    public final String getRows() {
        return rows;
    }

    public final int getIntRows() {
        return Integer.parseInt(getRows());
    }

    public final void setRows(final String rows) {
        this.rows = rows;
    }

    public final String getRules() {
        return rules;
    }

    public final void setRules(final String rules) {
        this.rules = rules;
    }

    public final ArrayList getTokens() {
        return tokens;
    }

    public final void setTokens(final ArrayList tokens) {
        this.tokens = tokens;
    }

    public void OnSelect() {

    }

    public final String getShadowDepth() {
        return shadowDepth;
    }

    public final void setShadowDepth(final String shadowDepth) {
        this.shadowDepth = shadowDepth;
    }

    public final String getBorder() {
        return border;
    }

    public final void setBorder(final String border) {
        this.border = border;
    }

    public final String getWidth() {
        return width;
    }

    public final void setWidth(final String width) {
        this.width = width;
    }

    public final String getHeight() {
        return height;
    }

    public final void setHeight(final String height) {
        this.height = height;
    }

    public final String getShadowOpacity() {
        return shadowOpacity;
    }

    public final void setShadowOpacity(final String shadowOpacity) {
        this.shadowOpacity = shadowOpacity;
    }
}
