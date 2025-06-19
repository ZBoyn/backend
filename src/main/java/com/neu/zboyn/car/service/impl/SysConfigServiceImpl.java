package com.neu.zboyn.car.service.impl;

import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.mapper.SysConfigMapper;
import com.neu.zboyn.car.model.Sysconfig;
import com.neu.zboyn.car.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysConfigServiceImpl implements SysConfigService {
    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public Response<PageResult<Sysconfig>> getConfigList(int page, int pageSize, String configName, String configKey, String configType) {
        int offset = (page - 1) * pageSize;
        List<Sysconfig> configList = sysConfigMapper.selectConfigList(offset, pageSize, configName, configKey, configType);
        long total = sysConfigMapper.selectConfigCount(configName, configKey, configType);
        PageResult<Sysconfig> pageResult = new PageResult<>(configList, total, page, pageSize);
        return new Response<>(0, pageResult, "获取成功", "success");
    }

    @Override
    public Response<Void> createConfig(Sysconfig sysconfig) {
        sysConfigMapper.create(sysconfig);
        return new Response<>(0, null, "创建成功", "success");
    }

    @Override
    public Response<Void> updateConfig(Sysconfig sysconfig) {
        sysConfigMapper.update(sysconfig);
        return new Response<>(0, null, "更新成功", "success");
    }

    @Override
    public Response<Void> deleteConfig(Integer configId) {
        sysConfigMapper.delete(configId);
        return new Response<>(0, null, "删除成功", "success");
    }

    @Override
    public Sysconfig getByConfigKey(String configKey) {
        return sysConfigMapper.selectByConfigKey(configKey);
    }
} 