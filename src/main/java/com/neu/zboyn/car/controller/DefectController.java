package com.neu.zboyn.car.controller;

import com.neu.zboyn.car.dto.DefectDto;
import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.service.DefectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/defect")
public class DefectController {

    @Autowired
    private DefectService defectService;

    // 分页查询
    @GetMapping("/list")
    public Response<PageResult<DefectDto>> list(
            @RequestParam int page,
            @RequestParam int pageSize,
            @RequestParam(required = false) String defectType,
            @RequestParam(required = false) String severity
    ) {
        return defectService.getDefectList(page, pageSize, defectType, severity);
    }

    // 新增
    @PostMapping("/create")
    public Response<Void> create(@RequestBody DefectDto defectDto) {

        return defectService.createDefect(defectDto);
    }

    // 修改
    @PostMapping("/update")
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
}
