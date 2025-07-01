package com.neu.zboyn.car.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private Long id;           // menu_id
    private Long pid;          // parent_id
    private String name;       // name（路由名称）
    private String menuName;   // menu_name（菜单名称/显示名）
    private Integer orderNum;  // order_num
    private String path;       // path
    private String component;  // component
    private String type;       // menu_type
    private String perms;      // perms（权限标识）
    private String icon;       // icon
    private String titleKey;   // title_key
    private String extraMeta;  // extra_meta（json）
    private Integer status;    // status
    private Meta meta;         // meta（JSON对象）
}