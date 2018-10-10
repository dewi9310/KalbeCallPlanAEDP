package com.kalbe.kalbecallplanaedp.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Dewi Oktaviani on 10/10/2018.
 */

@DatabaseTable
public class mUserLogin implements Serializable {
    @DatabaseField(id = true, columnName = "IntUserID")
    public int IntUserID;
    @DatabaseField(columnName = "TxtUserName")
    public String TxtUserName;
    @DatabaseField(columnName = "TxtNick")
    public String TxtNick;
    @DatabaseField(columnName = "TxtEmpID")
    public String TxtEmpID;
    @DatabaseField(columnName = "TxtEmail")
    public String TxtEmail;
    @DatabaseField(columnName = "IntDepartmentID")
    public String IntDepartmentID;
    @DatabaseField(columnName = "IntLOBID")
    public String IntLOBID;
    @DatabaseField(columnName = "TxtCompanyCode")
    public String TxtCompanyCode;

    public int getIntUserID() {
        return IntUserID;
    }

    public void setIntUserID(int intUserID) {
        IntUserID = intUserID;
    }

    public String getTxtUserName() {
        return TxtUserName;
    }

    public void setTxtUserName(String txtUserName) {
        TxtUserName = txtUserName;
    }

    public String getTxtNick() {
        return TxtNick;
    }

    public void setTxtNick(String txtNick) {
        TxtNick = txtNick;
    }

    public String getTxtEmpID() {
        return TxtEmpID;
    }

    public void setTxtEmpID(String txtEmpID) {
        TxtEmpID = txtEmpID;
    }

    public String getTxtEmail() {
        return TxtEmail;
    }

    public void setTxtEmail(String txtEmail) {
        TxtEmail = txtEmail;
    }

    public String getIntDepartmentID() {
        return IntDepartmentID;
    }

    public void setIntDepartmentID(String intDepartmentID) {
        IntDepartmentID = intDepartmentID;
    }

    public String getIntLOBID() {
        return IntLOBID;
    }

    public void setIntLOBID(String intLOBID) {
        IntLOBID = intLOBID;
    }

    public String getTxtCompanyCode() {
        return TxtCompanyCode;
    }

    public void setTxtCompanyCode(String txtCompanyCode) {
        TxtCompanyCode = txtCompanyCode;
    }
    //    @DatabaseField(columnName = "TxtPassword")
//    public String TxtPassword;
}
