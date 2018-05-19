package com.example.anamika.surveyigl.model;

public class LoginResponce {

    public LoginResponce() {
    }

    private String response;
    private String orgid;
    private String orgname;
    private String Uid;
    private String roleid;

    public LoginResponce(String response, String orgid, String orgname, String uid, String roleid) {
        this.response = response;
        this.orgid = orgid;
        this.orgname = orgname;
        Uid = uid;
        this.roleid = roleid;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }
}



