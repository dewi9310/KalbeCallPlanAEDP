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
    @DatabaseField(columnName = "intRoleID")
    public int intRoleID;
    @DatabaseField(columnName = "txtRoleName")
    public String txtRoleName;
    @DatabaseField(columnName = "IntDepartmentID")
    public int IntDepartmentID;
    @DatabaseField(columnName = "IntLOBID")
    public int IntLOBID;
    @DatabaseField(columnName = "TxtCompanyCode")
    public String TxtCompanyCode;
    @DatabaseField
    public String dtLogOut;
    @DatabaseField
    public String dtLogIn;


    public String Property_IntUserID = "IntUserID";
    public String Property_TxtUserName = "TxtUserName";
    public String Property_TxtNick = "TxtNick";
    public String Property_TxtEmpID = "TxtEmpID";
    public String Property_TxtEmail = "TxtEmail";
    public String Property_intRoleID = "intRoleID";
    public String Property_txtRoleName = "txtRoleName";
    public String Property_IntDepartmentID = "IntDepartmentID";
    public String Property_IntLOBID = "IntLOBID";
    public String Property_TxtCompanyCode = "TxtCompanyCode";
    public String Property_ListDatamUserLogin = "ListDatamUserLogin";


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

    public int getIntDepartmentID() {
        return IntDepartmentID;
    }

    public void setIntDepartmentID(int intDepartmentID) {
        IntDepartmentID = intDepartmentID;
    }

    public int getIntLOBID() {
        return IntLOBID;
    }

    public void setIntLOBID(int intLOBID) {
        IntLOBID = intLOBID;
    }

    public String getTxtCompanyCode() {
        return TxtCompanyCode;
    }

    public void setTxtCompanyCode(String txtCompanyCode) {
        TxtCompanyCode = txtCompanyCode;
    }

    public int getIntRoleID() {
        return intRoleID;
    }

    public void setIntRoleID(int intRoleID) {
        this.intRoleID = intRoleID;
    }

    public String getTxtRoleName() {
        return txtRoleName;
    }

    public void setTxtRoleName(String txtRoleName) {
        this.txtRoleName = txtRoleName;
    }

    public String getDtLogOut() {
        return dtLogOut;
    }

    public void setDtLogOut(String dtLogOut) {
        this.dtLogOut = dtLogOut;
    }

    public String getDtLogIn() {
        return dtLogIn;
    }

    public void setDtLogIn(String dtLogIn) {
        this.dtLogIn = dtLogIn;
    }
}
