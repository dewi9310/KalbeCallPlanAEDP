package com.kalbe.kalbecallplanaedp.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/22/2018.
 */

public class clsDataJson {
    private List<mUserLogin> ListDatamUserLogin;
    private List<tAkuisisiHeader> ListDataOftAkuisisiHeader;
    private List<tAkuisisiDetail> ListDataOftAkuisisiDetail;
    private List<tProgramVisitSubActivity> ListOfDatatProgramVisitSubActivity;
    private List<tProgramVisit> ListDataOftProgramVisit;
    private List<tProgramVisitSubActivityAttachment> ListOfDatatProgramVisitSubActivityAttachment;
    private List<tRealisasiVisitPlan> ListOfDatatRealisasiVisitPlan;
    private String txtUserId;
    private String txtSessionLogiId;
    private String intRoleId;
    private String intResult;
    private String txtDescription;
    private String txtMesagge;
    private String txtValue;
    private String txtMethod;
    private String txtVersionApp;

    public List<mUserLogin> getListDatamUserLogin() {
        return ListDatamUserLogin;
    }

    public void setListDatamUserLogin(List<mUserLogin> listDatamUserLogin) {
        ListDatamUserLogin = listDatamUserLogin;
    }

    public List<tAkuisisiHeader> getListDataOftAkuisisiHeader() {
        return ListDataOftAkuisisiHeader;
    }

    public void setListDataOftAkuisisiHeader(List<tAkuisisiHeader> listDataOftAkuisisiHeader) {
        ListDataOftAkuisisiHeader = listDataOftAkuisisiHeader;
    }

    public List<tAkuisisiDetail> getListDataOftAkuisisiDetail() {
        return ListDataOftAkuisisiDetail;
    }

    public void setListDataOftAkuisisiDetail(List<tAkuisisiDetail> listDataOftAkuisisiDetail) {
        ListDataOftAkuisisiDetail = listDataOftAkuisisiDetail;
    }

    public List<tProgramVisitSubActivity> getListOfDatatProgramVisitSubActivity() {
        return ListOfDatatProgramVisitSubActivity;
    }

    public void setListOfDatatProgramVisitSubActivity(List<tProgramVisitSubActivity> listOfDatatProgramVisitSubActivity) {
        ListOfDatatProgramVisitSubActivity = listOfDatatProgramVisitSubActivity;
    }

    public List<tProgramVisit> getListDataOftProgramVisit() {
        return ListDataOftProgramVisit;
    }

    public void setListDataOftProgramVisit(List<tProgramVisit> listDataOftProgramVisit) {
        ListDataOftProgramVisit = listDataOftProgramVisit;
    }

    public List<tProgramVisitSubActivityAttachment> getListOfDatatProgramVisitSubActivityAttachment() {
        return ListOfDatatProgramVisitSubActivityAttachment;
    }

    public void setListOfDatatProgramVisitSubActivityAttachment(List<tProgramVisitSubActivityAttachment> listOfDatatProgramVisitSubActivityAttachment) {
        ListOfDatatProgramVisitSubActivityAttachment = listOfDatatProgramVisitSubActivityAttachment;
    }

    public List<tRealisasiVisitPlan> getListOfDatatRealisasiVisitPlan() {
        return ListOfDatatRealisasiVisitPlan;
    }

    public void setListOfDatatRealisasiVisitPlan(List<tRealisasiVisitPlan> listOfDatatRealisasiVisitPlan) {
        ListOfDatatRealisasiVisitPlan = listOfDatatRealisasiVisitPlan;
    }

    public String getTxtUserId() {
        return txtUserId;
    }

    public void setTxtUserId(String txtUserId) {
        this.txtUserId = txtUserId;
    }

    public String getTxtSessionLogiId() {
        return txtSessionLogiId;
    }

    public void setTxtSessionLogiId(String txtSessionLogiId) {
        this.txtSessionLogiId = txtSessionLogiId;
    }

    public String getIntRoleId() {
        return intRoleId;
    }

    public void setIntRoleId(String intRoleId) {
        this.intRoleId = intRoleId;
    }

    public String getIntResult() {
        return intResult;
    }

    public void setIntResult(String intResult) {
        this.intResult = intResult;
    }

    public String getTxtDescription() {
        return txtDescription;
    }

    public void setTxtDescription(String txtDescription) {
        this.txtDescription = txtDescription;
    }

    public String getTxtMesagge() {
        return txtMesagge;
    }

    public void setTxtMesagge(String txtMesagge) {
        this.txtMesagge = txtMesagge;
    }

    public String getTxtValue() {
        return txtValue;
    }

    public void setTxtValue(String txtValue) {
        this.txtValue = txtValue;
    }

    public String getTxtMethod() {
        return txtMethod;
    }

    public void setTxtMethod(String txtMethod) {
        this.txtMethod = txtMethod;
    }

    public String getTxtVersionApp() {
        return txtVersionApp;
    }

    public void setTxtVersionApp(String txtVersionApp) {
        this.txtVersionApp = txtVersionApp;
    }

    public JSONObject txtJSON() throws JSONException{
        JSONObject resJson = new JSONObject();
        Collection<JSONObject> itemLIstQuery = new ArrayList<>();

        if (this.getListDatamUserLogin()!=null){
            mUserLogin dataLogin = new mUserLogin();
            itemLIstQuery = new ArrayList<>();
            for (mUserLogin data : this.getListDatamUserLogin()){
                JSONObject item = new JSONObject();
            }
        }

        if (this.getListDataOftAkuisisiHeader()!=null){
            tAkuisisiHeader dataAkuisisiHeader = new tAkuisisiHeader();
            itemLIstQuery  = new ArrayList<>();
            for (tAkuisisiHeader data : this.getListDataOftAkuisisiHeader()){
                JSONObject item = new JSONObject();
                item.put(dataAkuisisiHeader.Property_intSubSubActivityId, String.valueOf(data.intSubSubActivityId));
                item.put(dataAkuisisiHeader.Property_intHeaderId, String.valueOf(data.getTxtHeaderId()));
                item.put(dataAkuisisiHeader.Property_intSubSubActivityTypeId, String.valueOf(data.getIntSubSubActivityTypeId()));
                item.put(dataAkuisisiHeader.Property_txtNoDoc, String.valueOf(data.getTxtNoDoc()));
                item.put(dataAkuisisiHeader.Property_dtExpiredDate, String.valueOf(data.getDtExpiredDate()));
                item.put(dataAkuisisiHeader.Property_intUserId, String.valueOf(data.getIntUserId()));
                item.put(dataAkuisisiHeader.Property_intRoleId, String.valueOf(data.getIntRoleId()));
                item.put(dataAkuisisiHeader.Property_intDokterId, String.valueOf(data.getIntDokterId()));
                item.put(dataAkuisisiHeader.Property_intApotekID, String.valueOf(data.getIntApotekID()));
                item.put(dataAkuisisiHeader.Property_intOutletId, String.valueOf(data.getIntOutletId()));
                item.put(dataAkuisisiHeader.Property_intAreaId, String.valueOf(data.getIntAreaId()));
                itemLIstQuery.add(item);
            }
            resJson.put(dataAkuisisiHeader.Property_ListDataOftAkuisisiHeader, new JSONArray(itemLIstQuery));
        }

        if (this.getListDataOftAkuisisiDetail()!=null){
            tAkuisisiDetail akuisisiDetailData = new tAkuisisiDetail();
            itemLIstQuery = new ArrayList<JSONObject>();
            for (tAkuisisiDetail data : this.getListDataOftAkuisisiDetail()){
                JSONObject item = new JSONObject();
                item.put(akuisisiDetailData.Property_intHeaderId, String.valueOf(data.getTxtHeaderId()));
                item.put(akuisisiDetailData.Property_txtDetailId, String.valueOf(data.getTxtDetailId()));
                item.put(akuisisiDetailData.Property_txtImgName, String.valueOf(data.getTxtImgName()));
                item.put(akuisisiDetailData.Property_txtImg, String.valueOf(data.getTxtImg()));
                itemLIstQuery.add(item);
            }
            resJson.put(akuisisiDetailData.Property_ListDataOftAkuisisiDetail, new JSONArray(itemLIstQuery));
        }

        resJson.put(Property_txtUserId, getTxtUserId());
        resJson.put(Property_intRoleId, getIntRoleId());
        resJson.put(Property_txtSessionLogiId, getTxtSessionLogiId());
        resJson.put(Property_intResult, getIntResult());
        resJson.put(Property_txtMesagge, getTxtMesagge());
        resJson.put(Property_txtDescription, getTxtDescription());
        resJson.put(Property_txtValue, getTxtValue());
        resJson.put(Property_txtMethod, getTxtMethod());
        resJson.put(Property_txtVersionApp, getTxtVersionApp());

        return resJson;
    }
    private String Property_txtUserId = "txtUserId";
    private String Property_txtSessionLogiId = "txtSessionLogiId";
    private String Property_intRoleId = "intRoleId";
    private String Property_intResult = "intResult";
    private String Property_txtDescription = "txtDescription";
    private String Property_txtMesagge = "txtMesagge";
    private String Property_txtValue = "txtValue";
    private String Property_txtMethod = "txtMethod";
    private String Property_txtVersionApp = "txtVersionApp";

}
