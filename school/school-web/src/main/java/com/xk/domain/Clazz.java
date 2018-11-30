package com.xk.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hengxiaokang
 * Date:2018/6/24
 * Time:17:14
 */
@Entity
public class Clazz extends Entitys implements Serializable
{

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String classCode;

    @Column(nullable = false)
    private String className;

    @Column(nullable = true)
    private String classAdmin;

    @Column(nullable = true)
    private Date createTime;

    @Column(nullable = true)
    private Date updateTime;
    @Column(nullable = true)
    private int counts;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private IsDelete isDelete;

    @Column(nullable = true)
    private long userId;

    public long getUserid()
    {
        return userId;
    }

    public void setUserid(long userid)
    {
        this.userId = userid;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public int getCounts()
    {
        return counts;
    }

    public void setCounts(int counts)
    {
        this.counts = counts;
    }

    public IsDelete isDelete()
    {
        return isDelete;
    }

    public void setDelete(IsDelete delete)
    {
        isDelete = delete;
    }


    public String getClassCode()
    {
        return classCode;
    }

    public void setClassCode(String classCode)
    {
        this.classCode = classCode;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getClassAdmin()
    {
        return classAdmin;
    }

    public void setClassAdmin(String classAdmin)
    {
        this.classAdmin = classAdmin;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
}