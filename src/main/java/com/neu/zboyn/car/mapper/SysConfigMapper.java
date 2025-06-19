package com.neu.zboyn.car.mapper;

import com.neu.zboyn.car.model.Sysconfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysConfigMapper {
    List<Sysconfig> selectConfigList(
        @Param("offset") int offset,
        @Param("pageSize") int pageSize,
        @Param("configName") String configName,
        @Param("configKey") String configKey,
        @Param("configType") String configType
    );

    long selectConfigCount(
        @Param("configName") String configName,
        @Param("configKey") String configKey,
        @Param("configType") String configType
    );

    void create(Sysconfig sysconfig);

    void update(Sysconfig sysconfig);

    void delete(@Param("configId") Integer configId);

    Sysconfig selectByConfigKey(@Param("configKey") String configKey);
} 