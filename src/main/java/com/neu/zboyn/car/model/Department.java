package com.neu.zboyn.car.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private int deptId;
    private int parentId;
    private String deptName;
    private Integer status;
    private Date createTime;
    private String remark;
}
