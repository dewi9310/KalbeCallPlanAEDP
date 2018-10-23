package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtProgramVisit;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Data{

	@SerializedName("tProgramVisit")
	private List<TProgramVisitItem> tProgramVisit;

	@SerializedName("tProgramVisitSubActivity")
	private List<TProgramVisitSubActivityItem> tProgramVisitSubActivity;

	@SerializedName("tProgramVisitSubActivityAttachment")
	private List<TProgramVisitSubActivityAttachmentItem> tProgramVisitSubActivityAttachment;

	public void setTProgramVisit(List<TProgramVisitItem> tProgramVisit){
		this.tProgramVisit = tProgramVisit;
	}

	public List<TProgramVisitItem> getTProgramVisit(){
		return tProgramVisit;
	}

	public void setTProgramVisitSubActivity(List<TProgramVisitSubActivityItem> tProgramVisitSubActivity){
		this.tProgramVisitSubActivity = tProgramVisitSubActivity;
	}

	public List<TProgramVisitSubActivityItem> getTProgramVisitSubActivity(){
		return tProgramVisitSubActivity;
	}

	public void setTProgramVisitSubActivityAttachment(List<TProgramVisitSubActivityAttachmentItem> tProgramVisitSubActivityAttachment){
		this.tProgramVisitSubActivityAttachment = tProgramVisitSubActivityAttachment;
	}

	public List<TProgramVisitSubActivityAttachmentItem> getTProgramVisitSubActivityAttachment(){
		return tProgramVisitSubActivityAttachment;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"tProgramVisit = '" + tProgramVisit + '\'' + 
			",tProgramVisitSubActivity = '" + tProgramVisitSubActivity + '\'' + 
			",tProgramVisitSubActivityAttachment = '" + tProgramVisitSubActivityAttachment + '\'' + 
			"}";
		}
}