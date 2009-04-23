package bar.utils;

/**
 * encuestame:  system online surveys
 * Copyright (C) 2005-2008 encuestame Development Team
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
 * Id: JSFUtils.java Date: 22/04/2009 
 * @author Juan
 * package: bar.utils
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class JSFUtils {
	public static List<SelectItem> createList(String[] data) {
		ArrayList<SelectItem> list = new ArrayList<SelectItem>();
		for (String item : data) {
			list.add(new SelectItem(item, item));
		}
		return list;
	}

}
