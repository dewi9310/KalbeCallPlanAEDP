package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtProgramVisit;

public class TProgramVisitSubActivityItem{
	private String txtApotekName;
	private int intType;
	private String txtProgramVisitSubActivityId;
	private String txtAreaName;
	private String txtDokterId;
	private String txtNotes;
	private String txtDokterName;
	private String txtProgramVisitId;
	private String txtApotekId;
	private int intActivityId;
	private int intSubActivityId;
	private String txtAreaId;
	private String dtStart;
	private String dtEnd;

	public void setTxtApotekName(String txtApotekName){
		this.txtApotekName = txtApotekName;
	}

	public String getTxtApotekName(){
		return txtApotekName;
	}

	public void setIntType(int intType){
		this.intType = intType;
	}

	public int getIntType(){
		return intType;
	}

	public void setTxtProgramVisitSubActivityId(String txtProgramVisitSubActivityId){
		this.txtProgramVisitSubActivityId = txtProgramVisitSubActivityId;
	}

	public String getTxtProgramVisitSubActivityId(){
		return txtProgramVisitSubActivityId;
	}

	public void setTxtAreaName(String txtAreaName){
		this.txtAreaName = txtAreaName;
	}

	public String getTxtAreaName(){
		return txtAreaName;
	}

	public void setTxtDokterId(String txtDokterId){
		this.txtDokterId = txtDokterId;
	}

	public String getTxtDokterId(){
		return txtDokterId;
	}

	public void setTxtNotes(String txtNotes){
		this.txtNotes = txtNotes;
	}

	public String getTxtNotes(){
		return txtNotes;
	}

	public void setTxtDokterName(String txtDokterName){
		this.txtDokterName = txtDokterName;
	}

	public String getTxtDokterName(){
		return txtDokterName;
	}

	public void setTxtProgramVisitId(String txtProgramVisitId){
		this.txtProgramVisitId = txtProgramVisitId;
	}

	public String getTxtProgramVisitId(){
		return txtProgramVisitId;
	}

	public void setTxtApotekId(String txtApotekId){
		this.txtApotekId = txtApotekId;
	}

	public String getTxtApotekId(){
		return txtApotekId;
	}

	public void setIntActivityId(int intActivityId){
		this.intActivityId = intActivityId;
	}

	public int getIntActivityId(){
		return intActivityId;
	}

	public void setIntSubActivityId(int intSubActivityId){
		this.intSubActivityId = intSubActivityId;
	}

	public int getIntSubActivityId(){
		return intSubActivityId;
	}

	public void setTxtAreaId(String txtAreaId){
		this.txtAreaId = txtAreaId;
	}

	public String getTxtAreaId(){
		return txtAreaId;
	}

	public void setDtStart(String dtStart){
		this.dtStart = dtStart;
	}

	public String getDtStart(){
		return dtStart;
	}

	public void setDtEnd(String dtEnd){
		this.dtEnd = dtEnd;
	}

	public String getDtEnd(){
		return dtEnd;
	}

	@Override
 	public String toString(){
		return 
			"TProgramVisitSubActivityItem{" + 
			"txtApotekName = '" + txtApotekName + '\'' + 
			",intType = '" + intType + '\'' + 
			",txtProgramVisitSubActivityId = '" + txtProgramVisitSubActivityId + '\'' + 
			",txtAreaName = '" + txtAreaName + '\'' + 
			",txtDokterId = '" + txtDokterId + '\'' + 
			",txtNotes = '" + txtNotes + '\'' + 
			",txtDokterName = '" + txtDokterName + '\'' + 
			",txtProgramVisitId = '" + txtProgramVisitId + '\'' + 
			",txtApotekId = '" + txtApotekId + '\'' + 
			",intActivityId = '" + intActivityId + '\'' + 
			",intSubActivityId = '" + intSubActivityId + '\'' + 
			",txtAreaId = '" + txtAreaId + '\'' + 
			",dtStart = '" + dtStart + '\'' + 
			",dtEnd = '" + dtEnd + '\'' + 
			"}";
		}
}
