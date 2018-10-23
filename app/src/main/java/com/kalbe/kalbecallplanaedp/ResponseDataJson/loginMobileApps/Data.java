package com.kalbe.kalbecallplanaedp.ResponseDataJson.loginMobileApps;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("IntLOBID")
	private int intLOBID;

	@SerializedName("TxtUserName")
	private String txtUserName;

	@SerializedName("TxtNick")
	private String txtNick;

	@SerializedName("TxtEmail")
	private String txtEmail;

	@SerializedName("DtCreateDate")
	private String dtCreateDate;

	@SerializedName("IntDepartmentID")
	private int intDepartmentID;

	@SerializedName("TxtDomainName")
	private String txtDomainName;

	@SerializedName("DtUpdateDate")
	private String dtUpdateDate;

	@SerializedName("BitUseActiveDirectory")
	private boolean bitUseActiveDirectory;

	@SerializedName("IntUserID")
	private int intUserID;

	@SerializedName("TxtEmpID")
	private String txtEmpID;

	@SerializedName("mUserRole")
	private MUserRole mUserRole;

	@SerializedName("BitActive")
	private boolean bitActive;

	@SerializedName("TxtPassword")
	private String txtPassword;

	@SerializedName("TxtCreateBy")
	private String txtCreateBy;

	@SerializedName("TxtUpdateBy")
	private String txtUpdateBy;

	@SerializedName("TxtCompanyCode")
	private String txtCompanyCode;

	public void setIntLOBID(int intLOBID){
		this.intLOBID = intLOBID;
	}

	public int getIntLOBID(){
		return intLOBID;
	}

	public void setTxtUserName(String txtUserName){
		this.txtUserName = txtUserName;
	}

	public String getTxtUserName(){
		return txtUserName;
	}

	public void setTxtNick(String txtNick){
		this.txtNick = txtNick;
	}

	public String getTxtNick(){
		return txtNick;
	}

	public void setTxtEmail(String txtEmail){
		this.txtEmail = txtEmail;
	}

	public String getTxtEmail(){
		return txtEmail;
	}

	public void setDtCreateDate(String dtCreateDate){
		this.dtCreateDate = dtCreateDate;
	}

	public String getDtCreateDate(){
		return dtCreateDate;
	}

	public void setIntDepartmentID(int intDepartmentID){
		this.intDepartmentID = intDepartmentID;
	}

	public int getIntDepartmentID(){
		return intDepartmentID;
	}

	public void setTxtDomainName(String txtDomainName){
		this.txtDomainName = txtDomainName;
	}

	public String getTxtDomainName(){
		return txtDomainName;
	}

	public void setDtUpdateDate(String dtUpdateDate){
		this.dtUpdateDate = dtUpdateDate;
	}

	public String getDtUpdateDate(){
		return dtUpdateDate;
	}

	public void setBitUseActiveDirectory(boolean bitUseActiveDirectory){
		this.bitUseActiveDirectory = bitUseActiveDirectory;
	}

	public boolean isBitUseActiveDirectory(){
		return bitUseActiveDirectory;
	}

	public void setIntUserID(int intUserID){
		this.intUserID = intUserID;
	}

	public int getIntUserID(){
		return intUserID;
	}

	public void setTxtEmpID(String txtEmpID){
		this.txtEmpID = txtEmpID;
	}

	public String getTxtEmpID(){
		return txtEmpID;
	}

	public void setMUserRole(MUserRole mUserRole){
		this.mUserRole = mUserRole;
	}

	public MUserRole getMUserRole(){
		return mUserRole;
	}

	public void setBitActive(boolean bitActive){
		this.bitActive = bitActive;
	}

	public boolean isBitActive(){
		return bitActive;
	}

	public void setTxtPassword(String txtPassword){
		this.txtPassword = txtPassword;
	}

	public String getTxtPassword(){
		return txtPassword;
	}

	public void setTxtCreateBy(String txtCreateBy){
		this.txtCreateBy = txtCreateBy;
	}

	public String getTxtCreateBy(){
		return txtCreateBy;
	}

	public void setTxtUpdateBy(String txtUpdateBy){
		this.txtUpdateBy = txtUpdateBy;
	}

	public String getTxtUpdateBy(){
		return txtUpdateBy;
	}

	public void setTxtCompanyCode(String txtCompanyCode){
		this.txtCompanyCode = txtCompanyCode;
	}

	public String getTxtCompanyCode(){
		return txtCompanyCode;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"intLOBID = '" + intLOBID + '\'' + 
			",txtUserName = '" + txtUserName + '\'' + 
			",txtNick = '" + txtNick + '\'' + 
			",txtEmail = '" + txtEmail + '\'' + 
			",dtCreateDate = '" + dtCreateDate + '\'' + 
			",intDepartmentID = '" + intDepartmentID + '\'' + 
			",txtDomainName = '" + txtDomainName + '\'' + 
			",dtUpdateDate = '" + dtUpdateDate + '\'' + 
			",bitUseActiveDirectory = '" + bitUseActiveDirectory + '\'' + 
			",intUserID = '" + intUserID + '\'' + 
			",txtEmpID = '" + txtEmpID + '\'' + 
			",mUserRole = '" + mUserRole + '\'' + 
			",bitActive = '" + bitActive + '\'' + 
			",txtPassword = '" + txtPassword + '\'' + 
			",txtCreateBy = '" + txtCreateBy + '\'' + 
			",txtUpdateBy = '" + txtUpdateBy + '\'' + 
			",txtCompanyCode = '" + txtCompanyCode + '\'' + 
			"}";
		}
}