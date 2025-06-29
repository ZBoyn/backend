package com.neu.zboyn.car.mapper;

import com.neu.zboyn.car.model.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {
    /**
     * 获取所有菜单列表
     * @return 菜单列表
     */
    List<Menu> getAllMenus();
    
    int insertMenu(Menu menu);
    int updateMenu(Menu menu);
    int deleteMenu(Long id);
    Menu getMenuById(Long id);
    
    /**
     * 根据路径统计菜单数量
     * @param path 菜单路径
     * @return 数量
     */
    int countByPath(@Param("path") String path);
    
    /**
     * 根据名称统计菜单数量
     * @param name 菜单名称
     * @return 数量
     */
    int countByName(@Param("name") String name);
} 