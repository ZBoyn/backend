package com.neu.zboyn.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
    private Long id;
    private Long pid;
    private String name;
    private String path;
    private String component;
    private String type;
    private String icon;
    private Integer status;
    private String authCode; // 由 perms 映射
    private Map<String, Object> meta;
    private List<MenuDto> children;
} 