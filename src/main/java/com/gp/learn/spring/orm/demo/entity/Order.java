package com.gp.learn.spring.orm.demo.entity;

import lombok.Data;

import java.io.Serializable;
/**
 * @author wjzhu
 * @createDate 2019-04-21 16:43
 */
@Data
public class Order implements Serializable {

    private Long id;
    private Long memberId;
    private String detail;
    private Long createTime;
    private String createTimeFmt;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", detail='" + detail + '\'' +
                ", createTime=" + createTime +
                ", createTimeFmt='" + createTimeFmt + '\'' +
                '}';
    }
}
