package com.huokangtou.server.model;

import java.util.Date;

public class User {

    private Integer id;
    private String account;
    private String passwd;
    private String email;
    private Integer userRole;
    private Integer valied;
    private Date createTime;
    private String againPasswd;
    private Date startTime;
    private Date endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public Integer getValied() {
        return valied;
    }

    public void setValied(Integer valied) {
        this.valied = valied;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAgainPasswd() {
        return againPasswd;
    }

    public void setAgainPasswd(String againPasswd) {
        this.againPasswd = againPasswd;
    }

    public boolean againPasswdOk() {
        boolean isOk = true;
        if (this.passwd == null) {
            isOk = false;
        }
        if (this.againPasswd == null) {
            isOk = false;
        }
        if (!passwd.equalsIgnoreCase(againPasswd)) {
            isOk = false;
        }
        return isOk;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
