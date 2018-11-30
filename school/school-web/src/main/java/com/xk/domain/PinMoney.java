package com.xk.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by xiaokang on 2018/8/5.
 */
@Entity
public class PinMoney extends Entitys implements Serializable {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long stuId;

    @Column(nullable = false)
    private String money;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getStuId()
    {
        return stuId;
    }

    public void setStuId(Long stuId)
    {
        this.stuId = stuId;
    }

    public String getMoney()
    {
        return money;
    }

    public void setMoney(String money)
    {
        this.money = money;
    }
}
