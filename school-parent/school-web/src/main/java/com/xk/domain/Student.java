package com.xk.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiaokang on 2018/8/5.
 */
@Entity
public class Student extends Entitys implements Serializable {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String stuCode;

    @Column(nullable = false)
    private String stuName;

    @Column(nullable = false)
    private int sex;

    @Column(nullable = true)
    private int age;

    @Column(nullable = false)
    private Long classId;

    @Column(nullable = false)
    private String jiazhang;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private IsDelete isDelete;

    @Column(nullable = true)
    private Date createTime;

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public IsDelete getIsDelete()
    {
        return isDelete;
    }

    public void setIsDelete(IsDelete isDelete)
    {
        this.isDelete = isDelete;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getStuCode()
    {
        return stuCode;
    }

    public void setStuCode(String stuCode)
    {
        this.stuCode = stuCode;
    }

    public String getStuName()
    {
        return stuName;
    }

    public void setStuName(String stuName)
    {
        this.stuName = stuName;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId(Long classId)
    {
        this.classId = classId;
    }

    public String getJiazhang()
    {
        return jiazhang;
    }

    public void setJiazhang(String jiazhang)
    {
        this.jiazhang = jiazhang;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }
}
