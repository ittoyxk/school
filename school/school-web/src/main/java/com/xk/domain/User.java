package com.xk.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hengxiaokang
 * Date:2018/6/22
 * Time:16:35
 */
@Entity
public class User extends Entitys implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private String passWord;
    @Column(nullable = true, unique = true)
    private String phone;
    @Column(nullable = true, unique = true)
    private String nickName;
    @Column(nullable = true)
    private String profilePicture;
    @Column(nullable = true, length = 65535, columnDefinition = "Text")
    private String introduction;
    @Column(nullable = false)
    private Long createTime;
    @Column(nullable = false)
    private Long lastModifyTime;
    @Column(nullable = true)
    private String outDate;
    @Column(nullable = true)
    private String validataCode;
    @Column(nullable = true)
    private String backgroundPicture;

    public User() {
        super();
    }
    public User(String email, String nickName, String passWord, String userName) {
        super();
        this.phone = email;
        this.passWord = passWord;
        this.userName = userName;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public Long getLastModifyTime() {
        return lastModifyTime;
    }
    public void setLastModifyTime(Long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
    public String getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    public String getOutDate() {
        return outDate;
    }
    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }
    public String getValidataCode() {
        return validataCode;
    }
    public void setValidataCode(String validataCode) {
        this.validataCode = validataCode;
    }
    public String getBackgroundPicture() {
        return backgroundPicture;
    }
    public void setBackgroundPicture(String backgroundPicture) {
        this.backgroundPicture = backgroundPicture;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }
}