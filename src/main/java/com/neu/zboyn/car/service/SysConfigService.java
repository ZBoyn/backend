package com.neu.zboyn.car.service;

import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.model.Sysconfig;

public interface SysConfigService {

    Response<PageResult<Sysconfig>> getConfigList(
            int page,
            int pageSize,
            String configName,
            String configKey,
            String configType
    );

    Response<Void> createConfig(Sysconfig sysconfig);

    Response<Void> updateConfig(Sysconfig sysconfig);

    Response<Void> deleteConfig(Integer configId);

    Sysconfig getByConfigKey(String configKey);

} 