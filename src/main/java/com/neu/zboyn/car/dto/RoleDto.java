package com.neu.zboyn.car.dto;

import java.util.Date;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private String roleId;
    private String roleName;
    private String roleKey;
    private Integer roleSort;
    private String dataScope;
    private String status;
    private Date createTime;
    private Date updateTime;
    private String remark;
} 