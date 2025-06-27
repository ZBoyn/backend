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
            @Param("is_verified") String isVerified,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize);

    long selectDefectCount(
            @Param("taskId") String taskId,
            @Param("defectType") String defectType,
            @Param("is_verified") String isVerified);

    void create(Defect defect);

    void update(Defect defect);

    void delete(@Param("defectId") Long defectId);


    void updateIsVerified(@Param("defectId") Long defectId, @Param("isVerified") Boolean isVerified);

    void updateStatus(@Param("defectId") Long defectId, @Param("status") String status);
} 