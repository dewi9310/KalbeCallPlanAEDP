package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtProgramVisit;

public class TProgramVisitSubActivityAttachmentItem{
	private String txtFilePath;
	private String txtNoDocument;
	private String txtProgramVisitSubActivityAttachmentId;
	private String txtProgramVisitSubActivityId;
	private String dtExpiredDate;

	public void setTxtFilePath(String txtFilePath){
		this.txtFilePath = txtFilePath;
	}

	public String getTxtFilePath(){
		return txtFilePath;
	}

	public void setTxtNoDocument(String txtNoDocument){
		this.txtNoDocument = txtNoDocument;
	}

	public String getTxtNoDocument(){
		return txtNoDocument;
	}

	public void setTxtProgramVisitSubActivityAttachmentId(String txtProgramVisitSubActivityAttachmentId){
		this.txtProgramVisitSubActivityAttachmentId = txtProgramVisitSubActivityAttachmentId;
	}

	public String getTxtProgramVisitSubActivityAttachmentId(){
		return txtProgramVisitSubActivityAttachmentId;
	}

	public void setTxtProgramVisitSubActivityId(String txtProgramVisitSubActivityId){
		this.txtProgramVisitSubActivityId = txtProgramVisitSubActivityId;
	}

	public String getTxtProgramVisitSubActivityId(){
		return txtProgramVisitSubActivityId;
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
			"TProgramVisitSubActivityAttachmentItem{" + 
			"txtFilePath = '" + txtFilePath + '\'' + 
			",txtNoDocument = '" + txtNoDocument + '\'' + 
			",txtProgramVisitSubActivityAttachmentId = '" + txtProgramVisitSubActivityAttachmentId + '\'' + 
			",txtProgramVisitSubActivityId = '" + txtProgramVisitSubActivityId + '\'' + 
			",dtExpiredDate = '" + dtExpiredDate + '\'' + 
			"}";
		}
}
