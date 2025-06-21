package com.neu.zboyn.car.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Defect {
    private Long defectId;                // 缺陷编号
    private String taskId;                // 所属任务编号
    private String defectType;            // 缺陷类型
    private String distanceFromOrigin;    // 缺陷距离原点位置
    private String imageUrls;             // 缺陷图片URL列表（建议用逗号分隔）
    private Boolean isVerified;           // 是否属实
    private String severity;              // 严重程度
    private BigDecimal defectLength;      // 缺陷长度
    private BigDecimal defectArea;        // 缺陷面积
    private Integer defectQuantity;       // 缺陷数量
    private String recommendedAction;     // 建议整改方式
    private Date reportedTime;            // 缺陷上报时间
    private String status;                // 缺陷状态（0已上报 1已整改）


}