package org.jp.web.beans.commons;

import java.io.Serializable;
import java.util.ArrayList;

import org.richfaces.renderkit.html.SuggestionBoxRenderer;

/**
 * encuestame:  system online surveys
 * Copyright (C) 2009  encuestame Development Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * Id: SuggestionBox.java Date: 01/06/2009 12:24:19
 * @author juanpicado
 * package: org.jp.web.beans.commons
 * @version 1.0
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

    public String getCellpadding() {
        return cellpadding;
    }

    public void setCellpadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public String getCellspacing() {
        return cellspacing;
    }

    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getFirst() {
        return first;
    }

    public int getIntFirst() {
        return Integer.parseInt(getFirst());
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getFrequency() {
        return frequency;
    }

    public double getDoubleFrequency() {
        return Double.parseDouble(getFrequency());
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getMinchars() {
        return minchars;
    }

    public void setMinchars(String minchars) {
        this.minchars = minchars;
    }

    public String getRows() {
        return rows;
    }

    public int getIntRows() {
        return Integer.parseInt(getRows());
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public ArrayList getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList tokens) {
        this.tokens = tokens;
    }

    public void OnSelect() {

    }

    public String getShadowDepth() {
        return shadowDepth;
    }

    public void setShadowDepth(String shadowDepth) {
        this.shadowDepth = shadowDepth;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getShadowOpacity() {
        return shadowOpacity;
    }

    public void setShadowOpacity(String shadowOpacity) {
        this.shadowOpacity = shadowOpacity;
    }
}
