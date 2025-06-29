package com.neu.zboyn.car.service;

import com.neu.zboyn.car.dto.MenuDto;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.model.Menu;

import java.util.List;

public interface MenuService {
    /**
     * 获取菜单树形结构
     * @param name 菜单名称（可选）
     * @param status 菜单状态（可选）
     * @return 菜单树形结构
     */
    Response<List<MenuDto>> getMenus(String name, Integer status);

    Response<Void> createMenu(Menu menu);
    Response<Void> updateMenu(Menu menu);
    Response<Void> deleteMenu(Long id);
    Response<MenuDto> getMenuById(Long id);
    
    /**
     * 检查菜单路径是否已存在
     * @param path 菜单路径
     * @return 是否存在
     */
    Response<Boolean> checkPathExists(String path);
    
    /**
     * 检查菜单名称是否已存在
     * @param name 菜单名称
     * @return 是否存在
     */
    Response<Boolean> checkNameExists(String name);
} 