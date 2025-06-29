package com.neu.zboyn.car.mapper;

import com.neu.zboyn.car.model.Defect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface DefectMapper {
    List<Defect> selectDefectList(
            @Param("taskId") String taskId,
            @Param("defectType") String defectType,
            @Param("isVerified") String isVerified,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize);

    long selectDefectCount(
            @Param("taskId") String taskId,
            @Param("defectType") String defectType,
            @Param("isVerified") String isVerified);

    void create(Defect defect);

    void update(Defect defect);

    void delete(@Param("defectId") Long defectId);


    void updateIsVerified(@Param("defectId") Long defectId, @Param("isVerified") Boolean isVerified);

    void updateStatus(@Param("defectId") Long defectId, @Param("status") String status);
    
    /**
     * 根据任务ID获取处理后的图片URL
     * 
     * @param taskId 任务ID
     * @return 处理后的图片URL
     */
    String getProcessedImageUrlByTaskId(@Param("taskId") String taskId);
} 