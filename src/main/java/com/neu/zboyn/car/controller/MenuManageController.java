package com.neu.zboyn.car.controller;

import com.neu.zboyn.car.dto.MenuDto;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.model.Menu;
import com.neu.zboyn.car.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理控制器
 */
@RestController
@RequestMapping("/api/system/menu")
public class MenuManageController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单列表（树形结构）
     * @param name 菜单名称（可选）
     * @param status 菜单状态（可选）
     * @return 菜单树形结构
     */
    @RequestMapping("/list")
    public Response<List<MenuDto>> getMenus(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        return menuService.getMenus(name, status);
    }

    @PostMapping("")
    public Response<Void> create(@RequestBody Menu menu) {
        return menuService.createMenu(menu);
    }

    @PutMapping("/{id}")
    public Response<Void> update(@PathVariable Long id, @RequestBody Menu menu) {
        menu.setId(id);
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        return menuService.deleteMenu(id);
    }

    @GetMapping("/{id}")
    public Response<MenuDto> getMenuById(@PathVariable Long id) {
        return menuService.getMenuById(id);
    }

    @GetMapping("/path-exists")
    public Response<Boolean> checkPathExists(@RequestParam String path) {
        return menuService.checkPathExists(path);
    }

    @GetMapping("/name-exists")
    public Response<Boolean> checkNameExists(@RequestParam String name) {
        return menuService.checkNameExists(name);
    }
}
