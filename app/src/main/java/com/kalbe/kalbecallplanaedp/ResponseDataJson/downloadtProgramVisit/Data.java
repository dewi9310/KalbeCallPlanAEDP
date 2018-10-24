package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtProgramVisit;

import java.util.List;

public class Data{
	private List<TProgramVisitItem> tProgramVisit;
	private List<TProgramVisitSubActivityItem> tProgramVisitSubActivity;
	private List<RealisasiDataItem> realisasiData;
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

	public void setRealisasiData(List<RealisasiDataItem> realisasiData){
		this.realisasiData = realisasiData;
	}

	public List<RealisasiDataItem> getRealisasiData(){
		return realisasiData;
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
			",realisasiData = '" + realisasiData + '\'' + 
			",tProgramVisitSubActivityAttachment = '" + tProgramVisitSubActivityAttachment + '\'' + 
			"}";
		}
}