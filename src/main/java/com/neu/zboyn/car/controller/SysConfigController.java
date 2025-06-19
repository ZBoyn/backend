package com.neu.zboyn.car.controller;

import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.model.Sysconfig;
import com.neu.zboyn.car.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 系统参数配置管理
 */
@RestController
@RequestMapping("/api/system/config")
public class SysConfigController {
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 分页查询参数配置列表
     */
    @GetMapping("/list")
    public Response<PageResult<Sysconfig>> getConfigList(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String configName,
            @RequestParam(required = false) String configKey,
            @RequestParam(required = false) String configType
    ) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 20 : pageSize;
        return sysConfigService.getConfigList(page, pageSize, configName, configKey, configType);
    }

    /**
     * 新增参数配置
     */
    @PostMapping("")
    public Response<Void> createConfig(@RequestBody Sysconfig sysConfig) {
        return sysConfigService.createConfig(sysConfig);
    }

    /**
     * 修改参数配置
     */
    @PutMapping("/{configId}")
    public Response<Void> updateConfig(@PathVariable Integer configId, @RequestBody Sysconfig sysConfig) {
        sysConfig.setConfigId(configId);
        return sysConfigService.updateConfig(sysConfig);
    }

    /**
     * 删除参数配置
     */
    @DeleteMapping("/{configId}")
    public Response<Void> deleteConfig(@PathVariable Integer configId) {
        return sysConfigService.deleteConfig(configId);
    }



} 