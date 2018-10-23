package com.kalbe.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/19/2018.
 */

@DatabaseTable
public class tRealisasiVisitPlan implements Serializable{
    @DatabaseField(id = true, columnName = "txtRealisasiVisitId")
    public String txtRealisasiVisitId;
    @DatabaseField(columnName = "txtProgramVisitId")
    public String txtProgramVisitId;
    @DatabaseField(columnName = "intVisitType")
    public int intVisitType;
    @DatabaseField(columnName = "intPlanType")
    public int intPlanType;
    @DatabaseField(columnName = "intUserId")
    public int intUserId;
    @DatabaseField(columnName = "intRoleId")
    public int intRoleID;
    @DatabaseField(columnName = "txtDokterId")
    public String txtDokterId;
    @DatabaseField(columnName = "txtDokterName")
    public String txtDokterName;
    @DatabaseField(columnName = "txtApotekId")
    public String txtApotekId;
    @DatabaseField(columnName = "txtApotekName")
    public String txtApotekName;
    @DatabaseField(columnName = "dtCheckIn")
    public String dtCheckIn;
    @DatabaseField(columnName = "dtCheckOut")
    public String dtCheckOut;
    @DatabaseField(columnName = "dtDateRealisasi")
    public String dtDateRealisasi;
    @DatabaseField(columnName = "intNumberRealisasi")
    public int intNumberRealisasi;
    @DatabaseField(columnName = "txtAcc")
    public String txtAcc;
    @DatabaseField(columnName = "txtLong")
    public String txtLong;
    @DatabaseField(columnName = "txtLat")
    public String txtLat;
    @DatabaseField(columnName = "txtImgName1")
    public String txtImgName1;
    @DatabaseField(columnName = "blobImg1", dataType = DataType.BYTE_ARRAY)
    public byte[] blobImg1;
    @DatabaseField(columnName = "txtImgName2")
    public String txtImgName2;
    @DatabaseField(columnName = "blobImg2", dataType = DataType.BYTE_ARRAY)
    public byte[] blobImg2;
    @DatabaseField(columnName = "intFlagPush")
    public int intFlagPush;

    public String Property_txtRealisasiVisitId = "txtRealisasiVisitId";
    public String Property_txtProgramVisitId = "txtProgramVisitId";
    public String Property_intVisitType = "intVisitType";
    public String Property_intPlanType = "intPlanType";
    public String Property_intUserId = "intUserId";
    public String Property_intRoleID = "intRoleID";
    public String Property_txtDokterId = "txtDokterId";
    public String Property_txtDokterName = "txtDokterName";
    public String Property_txtApotekId = "txtApotekId";
    public String Property_txtApotekName = "txtApotekName";
    public String Property_dtCheckIn = "dtCheckIn";
    public String Property_dtCheckOut = "dtCheckOut";
    public String Property_dtDateRealisasi = "dtDateRealisasi";
    public String Property_intNumberRealisasi = "intNumberRealisasi";
    public String Property_txtAcc = "txtAcc";
    public String Property_txtLong = "txtLong";
    public String Property_txtLat = "txtLat";
    public String Property_txtImgName1 = "txtImgName1";
    public String Property_blobImg1 = "blobImg1";
    public String Property_txtImgName2 = "txtImgName2";
    public String Property_blobImg2 = "blobImg2";
    public String Property_intFlagPush = "intFlagPush";
    public String Property_ListOfDatatRealisasiVisitPlan = "ListOfDatatRealisasiVisitPlan";

    public String getTxtRealisasiVisitId() {
        return txtRealisasiVisitId;
    }

    public void setTxtRealisasiVisitId(String txtRealisasiVisitId) {
        this.txtRealisasiVisitId = txtRealisasiVisitId;
    }

    public String getTxtProgramVisitId() {
        return txtProgramVisitId;
    }

    public void setTxtProgramVisitId(String txtProgramVisitId) {
        this.txtProgramVisitId = txtProgramVisitId;
    }

    public int getIntVisitType() {
        return intVisitType;
    }

    public void setIntVisitType(int intVisitType) {
        this.intVisitType = intVisitType;
    }

    public int getIntPlanType() {
        return intPlanType;
    }

    public void setIntPlanType(int intPlanType) {
        this.intPlanType = intPlanType;
    }

    public int getIntUserId() {
        return intUserId;
    }

    public void setIntUserId(int intUserId) {
        this.intUserId = intUserId;
    }

    public int getIntRoleID() {
        return intRoleID;
    }

    public void setIntRoleID(int intRoleID) {
        this.intRoleID = intRoleID;
    }

    public String getTxtDokterId() {
        return txtDokterId;
    }

    public void setTxtDokterId(String txtDokterId) {
        this.txtDokterId = txtDokterId;
    }

    public String getTxtDokterName() {
        return txtDokterName;
    }

    public void setTxtDokterName(String txtDokterName) {
        this.txtDokterName = txtDokterName;
    }

    public String getTxtApotekId() {
        return txtApotekId;
    }

    public void setTxtApotekId(String txtApotekId) {
        this.txtApotekId = txtApotekId;
    }

    public String getTxtApotekName() {
        return txtApotekName;
    }

    public void setTxtApotekName(String txtApotekName) {
        this.txtApotekName = txtApotekName;
    }

    public String getDtCheckIn() {
        return dtCheckIn;
    }

    public void setDtCheckIn(String dtCheckIn) {
        this.dtCheckIn = dtCheckIn;
    }

    public String getDtCheckOut() {
        return dtCheckOut;
    }

    public void setDtCheckOut(String dtCheckOut) {
        this.dtCheckOut = dtCheckOut;
    }

    public String getDtDateRealisasi() {
        return dtDateRealisasi;
    }

    public void setDtDateRealisasi(String dtDateRealisasi) {
        this.dtDateRealisasi = dtDateRealisasi;
    }

    public int getIntNumberRealisasi() {
        return intNumberRealisasi;
    }

    public void setIntNumberRealisasi(int intNumberRealisasi) {
        this.intNumberRealisasi = intNumberRealisasi;
    }

    public String getTxtAcc() {
        return txtAcc;
    }

    public void setTxtAcc(String txtAcc) {
        this.txtAcc = txtAcc;
    }

    public String getTxtLong() {
        return txtLong;
    }

    public void setTxtLong(String txtLong) {
        this.txtLong = txtLong;
    }

    public String getTxtLat() {
        return txtLat;
    }

    public void setTxtLat(String txtLat) {
        this.txtLat = txtLat;
    }

    public String getTxtImgName1() {
        return txtImgName1;
    }

    public void setTxtImgName1(String txtImgName1) {
        this.txtImgName1 = txtImgName1;
    }

    public byte[] getBlobImg1() {
        return blobImg1;
    }

    public void setBlobImg1(byte[] blobImg1) {
        this.blobImg1 = blobImg1;
    }

    public String getTxtImgName2() {
        return txtImgName2;
    }

    public void setTxtImgName2(String txtImgName2) {
        this.txtImgName2 = txtImgName2;
    }

    public byte[] getBlobImg2() {
        return blobImg2;
    }

    public void setBlobImg2(byte[] blobImg2) {
        this.blobImg2 = blobImg2;
    }

    public int getIntFlagPush() {
        return intFlagPush;
    }

    public void setIntFlagPush(int intFlagPush) {
        this.intFlagPush = intFlagPush;
    }
}
