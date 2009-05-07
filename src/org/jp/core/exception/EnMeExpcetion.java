package org.jp.core.exception;
/**
 * encuestame:  system online surveys
 * Copyright (C) 2009 encuestame Development Team
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
 * Id: EnMeExpcetion.java Date: 07/05/2009 
 * @author juanpicado
 * package: org.jp.core.exception
 * @version 1.0
 */
public class EnMeExpcetion extends Exception{

	
	private static final long serialVersionUID = 7631058192250904935L;

	public EnMeExpcetion() {
		super();
		
	}

	public EnMeExpcetion(String message, Throwable cause) {
		super(message, cause);
	
	}

	public EnMeExpcetion(String message) {
		super(message);
		
	}

	public EnMeExpcetion(Throwable cause) {
		super(cause);
		
	}
	
	

}
