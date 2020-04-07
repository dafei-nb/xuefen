package cn.cdqf.dmsjportal.entity;

import java.util.Date;

public class DmsjProject {
    private String projectId;

    private String projectName;

    private String projectOutline;

    private Long projectMoney;

    private Integer projectDay;

    private Byte projectStatus;

    private Date deploydate;

    private Long supportmoney;

    private Integer supporter;

    private Integer completion;

    private Integer memberid;

    private String headerPicturePath;

    private Integer typeId;

    private String typeName;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getProjectOutline() {
        return projectOutline;
    }

    public void setProjectOutline(String projectOutline) {
        this.projectOutline = projectOutline == null ? null : projectOutline.trim();
    }

    public Long getProjectMoney() {
        return projectMoney;
    }

    public void setProjectMoney(Long projectMoney) {
        this.projectMoney = projectMoney;
    }

    public Integer getProjectDay() {
        return projectDay;
    }

    public void setProjectDay(Integer projectDay) {
        this.projectDay = projectDay;
    }

    public Byte getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Byte projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Date getDeploydate() {
        return deploydate;
    }

    public void setDeploydate(Date deploydate) {
        this.deploydate = deploydate;
    }

    public Long getSupportmoney() {
        return supportmoney;
    }

    public void setSupportmoney(Long supportmoney) {
        this.supportmoney = supportmoney;
    }

    public Integer getSupporter() {
        return supporter;
    }

    public void setSupporter(Integer supporter) {
        this.supporter = supporter;
    }

    public Integer getCompletion() {
        return completion;
    }

    public void setCompletion(Integer completion) {
        this.completion = completion;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public String getHeaderPicturePath() {
        return headerPicturePath;
    }

    public void setHeaderPicturePath(String headerPicturePath) {
        this.headerPicturePath = headerPicturePath == null ? null : headerPicturePath.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }
}