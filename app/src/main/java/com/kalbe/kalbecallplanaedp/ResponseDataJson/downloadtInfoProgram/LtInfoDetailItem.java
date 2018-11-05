package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtInfoProgram;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LtInfoDetailItem{

	@SerializedName("txtFilePath")
	private String txtFilePath;

	@SerializedName("txtInfoProgramDetailId")
	private String txtInfoProgramDetailId;

	@SerializedName("dtDateChecklist")
	private String dtDateChecklist;

	@SerializedName("txtFileLinkEncrypt")
	private String txtFileLinkEncrypt;

	@SerializedName("txtFileName")
	private String txtFileName;

	@SerializedName("txtFileLink")
	private String txtFileLink;

	@SerializedName("txtDesc")
	private String txtDesc;

	@SerializedName("txtInfoProgramHeaderId")
	private String txtInfoProgramHeaderId;

	@SerializedName("intSubDetailActivityId")
	private int intSubDetailActivityId;

	@SerializedName("bitCheck")
	private boolean bitCheck;

	public void setTxtFilePath(String txtFilePath){
		this.txtFilePath = txtFilePath;
	}

	public String getTxtFilePath(){
		return txtFilePath;
	}

	public void setTxtInfoProgramDetailId(String txtInfoProgramDetailId){
		this.txtInfoProgramDetailId = txtInfoProgramDetailId;
	}

	public String getTxtInfoProgramDetailId(){
		return txtInfoProgramDetailId;
	}

	public void setDtDateChecklist(String dtDateChecklist){
		this.dtDateChecklist = dtDateChecklist;
	}

	public String getDtDateChecklist(){
		return dtDateChecklist;
	}

	public void setTxtFileLinkEncrypt(String txtFileLinkEncrypt){
		this.txtFileLinkEncrypt = txtFileLinkEncrypt;
	}

	public String getTxtFileLinkEncrypt(){
		return txtFileLinkEncrypt;
	}

	public void setTxtFileName(String txtFileName){
		this.txtFileName = txtFileName;
	}

	public String getTxtFileName(){
		return txtFileName;
	}

	public void setTxtFileLink(String txtFileLink){
		this.txtFileLink = txtFileLink;
	}

	public String getTxtFileLink(){
		return txtFileLink;
	}

	public void setTxtDesc(String txtDesc){
		this.txtDesc = txtDesc;
	}

	public String getTxtDesc(){
		return txtDesc;
	}

	public void setTxtInfoProgramHeaderId(String txtInfoProgramHeaderId){
		this.txtInfoProgramHeaderId = txtInfoProgramHeaderId;
	}

	public String getTxtInfoProgramHeaderId(){
		return txtInfoProgramHeaderId;
	}

	public void setIntSubDetailActivityId(int intSubDetailActivityId){
		this.intSubDetailActivityId = intSubDetailActivityId;
	}

	public int getIntSubDetailActivityId(){
		return intSubDetailActivityId;
	}

	public void setBitCheck(boolean bitCheck){
		this.bitCheck = bitCheck;
	}

	public boolean isBitCheck(){
		return bitCheck;
	}

	@Override
 	public String toString(){
		return 
			"LtInfoDetailItem{" + 
			"txtFilePath = '" + txtFilePath + '\'' + 
			",txtInfoProgramDetailId = '" + txtInfoProgramDetailId + '\'' + 
			",dtDateChecklist = '" + dtDateChecklist + '\'' + 
			",txtFileLinkEncrypt = '" + txtFileLinkEncrypt + '\'' + 
			",txtFileName = '" + txtFileName + '\'' + 
			",txtFileLink = '" + txtFileLink + '\'' + 
			",txtDesc = '" + txtDesc + '\'' + 
			",txtInfoProgramHeaderId = '" + txtInfoProgramHeaderId + '\'' + 
			",intSubDetailActivityId = '" + intSubDetailActivityId + '\'' + 
			",bitCheck = '" + bitCheck + '\'' + 
			"}";
		}
}