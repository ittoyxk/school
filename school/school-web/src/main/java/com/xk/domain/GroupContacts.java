package com.xk.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiaokang on 2018/8/7.
 */
@Entity
public class GroupContacts extends Entitys implements Serializable {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String groupName;

    @Column(nullable = false)
    private Long classId;

    @Column(nullable = false)
    private String stuId;

    @Column(nullable = true)
    private Date createTime;

    @Column(nullable = true)
    private int counts;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private IsDelete isDelete;


    @Column(nullable = true)
    private long userId;

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId(Long classId)
    {
        this.classId = classId;
    }

    public String getStuId()
    {
        return stuId;
    }

    public void setStuId(String stuId)
    {
        this.stuId = stuId;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public int getCounts()
    {
        return counts;
    }

    public void setCounts(int counts)
    {
        this.counts = counts;
    }

    public IsDelete getIsDelete()
    {
        return isDelete;
    }

    public void setIsDelete(IsDelete isDelete)
    {
        this.isDelete = isDelete;
    }
}
