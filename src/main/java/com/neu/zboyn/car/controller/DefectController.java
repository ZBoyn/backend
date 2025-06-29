package com.neu.zboyn.car.controller;

import com.neu.zboyn.car.dto.DefectDto;
import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.service.DefectService;
import com.neu.zboyn.car.mapper.TaskMapper;
import com.neu.zboyn.car.mapper.DefectMapper;
import com.neu.zboyn.car.model.Defect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 缺陷检测控制器
 */
@RestController
@RequestMapping("/api/inspection/defect")
public class DefectController {

    @Autowired
    private DefectService defectService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private DefectMapper defectMapper;

    // 分页查询
    @GetMapping("/list")
    public Response<PageResult<DefectDto>> list(
            @RequestParam int page,
            @RequestParam int pageSize,
            @RequestParam(required = false) String taskId,
            @RequestParam(required = false) String defectType,
            @RequestParam(required = false) String isVerified
    ) {
        // 添加调试日志
        System.out.println("=== Controller Debug Info ===");
        System.out.println("原始taskId: " + taskId);
        System.out.println("原始defectType: " + defectType);
        System.out.println("原始isVerified: " + isVerified);
        
        String realTaskId = null;
        if (taskId != null && !taskId.isEmpty()) {
            realTaskId = taskMapper.selectTaskIdByTaskName(taskId);
            System.out.println("转换后realTaskId: " + realTaskId);
        }
        return defectService.getDefectList(page, pageSize, realTaskId, defectType, isVerified);
    }

    // 新增
    @PostMapping("/create")
    public Response<Void> create(@RequestBody DefectDto defectDto) {

        return defectService.createDefect(defectDto);
    }

    // 修改
    @PostMapping("/{id}")
    public Response<Void> update(@RequestBody DefectDto defectDto) {
        return defectService.updateDefect(defectDto);
    }

    // 删除
    @PostMapping("/delete")
    public Response<Void> delete(@RequestParam Long defectId) {
        return defectService.deleteDefect(defectId);
    }


    // 缺陷确认
    @PostMapping("/verify")
    public Response<Void> verify(@RequestParam Long defectId, @RequestParam Boolean isVerified) {
        return defectService.verifyDefect(defectId, isVerified);
    }

    // 已整改标记
    @PostMapping("/markRectified")
    public Response<Void> markRectified(@RequestParam Long defectId) {
        return defectService.markDefectRectified(defectId);
    }


    /**
     * 测试接口：检查数据库中的数据
     */
    @GetMapping("/test")
    public Response<String> testDatabase() {
        try {
            // 直接查询所有缺陷数据
            List<Defect> allDefects = defectMapper.selectDefectList(null, null, null, 0, 1000);
            long total = defectMapper.selectDefectCount(null, null, null);
            
            StringBuilder result = new StringBuilder();
            result.append("数据库中共有 ").append(total).append(" 条缺陷记录\n");
            result.append("查询到 ").append(allDefects.size()).append(" 条记录\n");
            
            if (!allDefects.isEmpty()) {
                result.append("前5条记录的缺陷类型：\n");
                for (int i = 0; i < Math.min(5, allDefects.size()); i++) {
                    Defect defect = allDefects.get(i);
                    result.append(i + 1).append(". ID:").append(defect.getDefectId())
                          .append(", 类型:").append(defect.getDefectType())
                          .append(", 任务ID:").append(defect.getTaskId())
                          .append(", 是否验证:").append(defect.getIsVerified())
                          .append("\n");
                }
            }
            
            return Response.success(result.toString(), "数据库检查完成", "ok");
        } catch (Exception e) {
            return Response.error(500, "数据库检查失败: " + e.getMessage(), "系统错误");
        }
    }
}
