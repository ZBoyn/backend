package com.neu.zboyn.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefectDto {
    private Long defectId;
    private String taskId;
    private String defectType;
    private String distanceFromOrigin;
    private List<String> imageUrls; // 图片URL列表
    private String isVerified;      // "是"/"否"
    private String severity;
    private BigDecimal defectLength;
    private BigDecimal defectArea;
    private Integer defectQuantity;
    private String recommendedAction;
    private Date reportedTime;
    private String status;          // "已上报"/"已整改"

} 