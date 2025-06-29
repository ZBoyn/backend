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
} 