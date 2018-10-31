package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtAkuisisi;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class AkuisisiHeaderItem{

	@SerializedName("intUserId")
	private int intUserId;

	@SerializedName("txtDokterId")
	private String txtDokterId;

	@SerializedName("intOutletId")
	private String intOutletId;

	@SerializedName("intRoleId")
	private int intRoleId;

	@SerializedName("intAreaId")
	private String intAreaId;

	@SerializedName("txtNoDoc")
	private String txtNoDoc;

	@SerializedName("bitActive")
	private int bitActive;

	@SerializedName("txtApotekId")
	private String txtApotekId;

	@SerializedName("tAkuisisiHeaderId")
	private String tAkuisisiHeaderId;

	@SerializedName("intFlag")
	private int intFlag;

	@SerializedName("intSubDetailActivityId")
	private int intSubDetailActivityId;

	@SerializedName("dtExpiredDate")
	private String dtExpiredDate;

	public void setIntUserId(int intUserId){
		this.intUserId = intUserId;
	}

	public int getIntUserId(){
		return intUserId;
	}

	public void setTxtDokterId(String txtDokterId){
		this.txtDokterId = txtDokterId;
	}

	public String getTxtDokterId(){
		return txtDokterId;
	}

	public void setIntOutletId(String intOutletId){
		this.intOutletId = intOutletId;
	}

	public String getIntOutletId(){
		return intOutletId;
	}

	public void setIntRoleId(int intRoleId){
		this.intRoleId = intRoleId;
	}

	public int getIntRoleId(){
		return intRoleId;
	}

	public void setIntAreaId(String intAreaId){
		this.intAreaId = intAreaId;
	}

	public String getIntAreaId(){
		return intAreaId;
	}

	public void setTxtNoDoc(String txtNoDoc){
		this.txtNoDoc = txtNoDoc;
	}

	public String getTxtNoDoc(){
		return txtNoDoc;
	}

	public void setBitActive(int bitActive){
		this.bitActive = bitActive;
	}

	public int getBitActive(){
		return bitActive;
	}

	public void setTxtApotekId(String txtApotekId){
		this.txtApotekId = txtApotekId;
	}

	public String getTxtApotekId(){
		return txtApotekId;
	}

	public void setTAkuisisiHeaderId(String tAkuisisiHeaderId){
		this.tAkuisisiHeaderId = tAkuisisiHeaderId;
	}

	public String getTAkuisisiHeaderId(){
		return tAkuisisiHeaderId;
	}

	public void setIntFlag(int intFlag){
		this.intFlag = intFlag;
	}

	public int getIntFlag(){
		return intFlag;
	}

	public void setIntSubDetailActivityId(int intSubDetailActivityId){
		this.intSubDetailActivityId = intSubDetailActivityId;
	}

	public int getIntSubDetailActivityId(){
		return intSubDetailActivityId;
	}

	public void setDtExpiredDate(String dtExpiredDate){
		this.dtExpiredDate = dtExpiredDate;
	}

	public String getDtExpiredDate(){
		return dtExpiredDate;
	}

	@Override
 	public String toString(){
		return 
			"AkuisisiHeaderItem{" + 
			"intUserId = '" + intUserId + '\'' + 
			",txtDokterId = '" + txtDokterId + '\'' + 
			",intOutletId = '" + intOutletId + '\'' + 
			",intRoleId = '" + intRoleId + '\'' + 
			",intAreaId = '" + intAreaId + '\'' + 
			",txtNoDoc = '" + txtNoDoc + '\'' + 
			",bitActive = '" + bitActive + '\'' + 
			",txtApotekId = '" + txtApotekId + '\'' + 
			",tAkuisisiHeaderId = '" + tAkuisisiHeaderId + '\'' + 
			",intFlag = '" + intFlag + '\'' + 
			",intSubDetailActivityId = '" + intSubDetailActivityId + '\'' + 
			",dtExpiredDate = '" + dtExpiredDate + '\'' + 
			"}";
		}
}