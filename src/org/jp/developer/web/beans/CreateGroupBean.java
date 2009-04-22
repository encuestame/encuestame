/**
 * 
 */
package org.jp.developer.web.beans;

import java.util.Date;

/**
 * @author Jota
 *
 */
public class CreateGroupBean {
	
	public String desc_group;
	public String desc_info;
	public Date validate;
	public Integer id_estado; 
	
	
	public CreateGroupBean() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the desc_group
	 */
	public String getDesc_group() {
		return desc_group;
	}


	/**
	 * @param desc_group the desc_group to set
	 */
	public void setDesc_group(String desc_group) {
		this.desc_group = desc_group;
	}


	/**
	 * @return the desc_info
	 */
	public String getDesc_info() {
		return desc_info;
	}


	/**
	 * @param desc_info the desc_info to set
	 */
	public void setDesc_info(String desc_info) {
		this.desc_info = desc_info;
	}


	/**
	 * @return the validate
	 */
	public Date getValidate() {
		return validate;
	}


	/**
	 * @param validate the validate to set
	 */
	public void setValidate(Date validate) {
		this.validate = validate;
	}


	/**
	 * @return the id_estado
	 */
	public Integer getId_estado() {
		return id_estado;
	}


	/**
	 * @param id_estado the id_estado to set
	 */
	public void setId_estado(Integer id_estado) {
		this.id_estado = id_estado;
	}


	/**
	 * 
	 */

}
