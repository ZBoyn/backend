package com.neu.zboyn.car.service.impl;

import com.neu.zboyn.car.dto.DefectDto;
import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.mapper.DefectMapper;
import com.neu.zboyn.car.mapper.TaskMapper;
import com.neu.zboyn.car.model.Defect;
import com.neu.zboyn.car.service.DefectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DefectServiceImpl implements DefectService {

    @Autowired
    private DefectMapper defectMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Value("${file.server-url}")
    private String serverUrl;

    private DefectDto toDto(Defect defect) {
        if (defect == null) return null;
        DefectDto dto = new DefectDto();
        dto.setDefectId(defect.getDefectId());
        // 将taskId转为任务名
        String taskName = taskMapper != null ? taskMapper.selectTaskNameByTaskId(defect.getTaskId()) : defect.getTaskId();
        dto.setTaskId(taskName);
        dto.setDefectType(defect.getDefectType());
        dto.setDistanceFromOrigin(defect.getDistanceFromOrigin());
        // 图片URL分割并拼接完整路径
        if (defect.getImageUrls() != null && !defect.getImageUrls().isEmpty()) {
            List<String> relativeUrls = Arrays.asList(defect.getImageUrls().split(","));
            List<String> fullUrls = new ArrayList<>();
            for (String rel : relativeUrls) {
                // 避免重复拼接
                if (rel.startsWith("http")) {
                    fullUrls.add(rel);
                } else {
                    fullUrls.add(serverUrl + "/uploads/" + rel.replaceFirst("^/+", ""));
                }
            }
            dto.setImageUrls(fullUrls);
        }
        dto.setIsVerified(defect.getIsVerified() != null && defect.getIsVerified() ? "是" : "否");
        dto.setSeverity(defect.getSeverity());
        dto.setDefectLength(defect.getDefectLength());
        dto.setDefectArea(defect.getDefectArea());
        dto.setDefectQuantity(defect.getDefectQuantity());
        dto.setRecommendedAction(defect.getRecommendedAction());
        dto.setReportedTime(defect.getReportedTime());
        dto.setStatus("1".equals(defect.getStatus()) ? "已整改" : "已上报");
        return dto;
    }

    private Defect toEntity(DefectDto dto) {
        if (dto == null) return null;
        Defect defect = new Defect();
        defect.setDefectId(dto.getDefectId());
        defect.setTaskId(dto.getTaskId());
        defect.setDefectType(dto.getDefectType());
        defect.setDistanceFromOrigin(dto.getDistanceFromOrigin());
        // 图片URL合并
        if (dto.getImageUrls() != null && !dto.getImageUrls().isEmpty()) {
            defect.setImageUrls(String.join(",", dto.getImageUrls()));
        }
        defect.setIsVerified("是".equals(dto.getIsVerified()));
        defect.setSeverity(dto.getSeverity());
        defect.setDefectLength(dto.getDefectLength());
        defect.setDefectArea(dto.getDefectArea());
        defect.setDefectQuantity(dto.getDefectQuantity());
        defect.setRecommendedAction(dto.getRecommendedAction());
        defect.setReportedTime(dto.getReportedTime());
        defect.setStatus("已整改".equals(dto.getStatus()) ? "1" : "0");
        return defect;
    }

    @Override
    public Response<PageResult<DefectDto>> getDefectList(int page, int pageSize, String taskId, String defectType, String isVerified) {
        // 添加调试日志
        System.out.println("=== DefectService Debug Info ===");
        System.out.println("page: " + page);
        System.out.println("pageSize: " + pageSize);
        System.out.println("taskId: " + taskId);
        System.out.println("defectType: " + defectType);
        System.out.println("isVerified: " + isVerified);
        
        int offset = (page - 1) * pageSize;
        List<Defect> list = defectMapper.selectDefectList(taskId, defectType, isVerified, offset, pageSize);
        long total = defectMapper.selectDefectCount(taskId, defectType, isVerified);
        
        System.out.println("查询结果数量: " + list.size());
        System.out.println("总记录数: " + total);
        
        List<DefectDto> dtoList = new ArrayList<>();
        for (Defect d : list) {
            dtoList.add(toDto(d));
        }
        PageResult<DefectDto> pageResult = new PageResult<>(dtoList, total, page, pageSize);
        return Response.success(pageResult);
    }

    @Override
    public Response<Void> createDefect(DefectDto defectDto) {
        defectMapper.create(toEntity(defectDto));
        return Response.success(null);
    }

    @Override
    public Response<Void> updateDefect(DefectDto defectDto) {
        defectMapper.update(toEntity(defectDto));
        return Response.success(null);
    }

    @Override
    public Response<Void> deleteDefect(Long defectId) {
        defectMapper.delete(defectId);
        return Response.success(null);
    }


    @Override
    public Response<Void> verifyDefect(Long defectId, Boolean isVerified) {
        defectMapper.updateIsVerified(defectId, isVerified);
        return Response.success(null);
    }

    @Override
    public Response<Void> markDefectRectified(Long defectId) {
        defectMapper.updateStatus(defectId, "1");
        return Response.success(null);
    }
}