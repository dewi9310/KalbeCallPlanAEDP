package com.kalbe.kalbecallplanaedp.ResponseDataJson.downloadtProgramVisit;

public class DownloadtProgramVisit{
	private Result result;
	private Data data;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"DownloadtProgramVisit{" + 
			"result = '" + result + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}
