package com.neu.zboyn.car.service;

import com.neu.zboyn.car.dto.DefectDto;
import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;

public interface DefectService {
    Response<PageResult<DefectDto>> getDefectList(int page, int pageSize, String taskId, String defectType, String severity);
    Response<Void> createDefect(DefectDto defectDto);
    Response<Void> updateDefect(DefectDto defectDto);
    Response<Void> deleteDefect(Long defectId);
    Response<Void> verifyDefect(Long defectId, Boolean isVerified);
    Response<Void> markDefectRectified(Long defectId);
} 