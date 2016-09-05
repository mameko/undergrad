package com.yourcompany.struts.form;

import org.apache.struts.action.ActionForm;

public class RegisterForm extends ActionForm {

	String m_descripton;
	String m_name;
	String m_password;
	String onlinetime;
	int m_id;

	
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public String getOnlinetime() {
		return onlinetime;
	}
	public void setOnlinetime(String onlinetime) {
		this.onlinetime = onlinetime;
	}

	public String getM_descripton() {
		return m_descripton;
	}
	public void setM_descripton(String m_descripton) {
		this.m_descripton = m_descripton;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getM_password() {
		return m_password;
	}
	public void setM_password(String m_password) {
		this.m_password = m_password;
	}
	
}
