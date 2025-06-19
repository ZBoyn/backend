package com.neu.zboyn.car.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sysconfig {
    private Integer configId;        // 参数主键
    private String configName;       // 参数名称
    private String configKey;        // 参数键名
    private String configValue;      // 参数键值
    private String configType;       // 系统内置（Y是 N否）
    private Date createTime;         // 创建时间
    private Date updateTime;         // 更新时间
    private String remark;
}
