package com.neu.zboyn.car.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private String taskId;
    private String taskName;
    private Long creatorId;
    private Long executorId;
    private String startLocation;
    private Double distance;
    private Date deadlineTime;
    private Date completionTime;
    private Date uploadTime;
    private Integer status;
    private Date createTime;
}
