package com.neu.zboyn.car.service.impl;

import com.neu.zboyn.car.dto.MenuDto;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.mapper.MenuMapper;
import com.neu.zboyn.car.model.Menu;
import com.neu.zboyn.car.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Response<List<MenuDto>> getMenus(String name, Integer status) {
        try {
            // 1. 查询所有菜单
            List<Menu> allMenus = menuMapper.getAllMenus();
            // 2. 过滤（如有）
            if (name != null && !name.isEmpty()) {
                allMenus = allMenus.stream().filter(m -> m.getName() != null && m.getName().contains(name)).collect(Collectors.toList());
            }
            if (status != null) {
                allMenus = allMenus.stream().filter(m -> Objects.equals(m.getStatus(), status)).collect(Collectors.toList());
            }
            // 3. 构建树形结构
            List<MenuDto> menuTree = buildMenuTree(allMenus);
            return Response.success(menuTree);
        } catch (Exception e) {
            return Response.error(500, "获取菜单失败", e.getMessage());
        }
    }

    private List<MenuDto> buildMenuTree(List<Menu> menus) {
        Map<Long, List<Menu>> menuMap = menus.stream()
                .collect(Collectors.groupingBy(menu -> menu.getPid() == null ? 0L : menu.getPid()));
        return buildMenuTreeRecursive(0L, menuMap);
    }

    private List<MenuDto> buildMenuTreeRecursive(Long parentId, Map<Long, List<Menu>> menuMap) {
        List<Menu> children = menuMap.get(parentId);
        if (children == null) {
            return new ArrayList<>();
        }
        return children.stream()
                .map(menu -> {
                    MenuDto dto = convertToDto(menu);
                    List<MenuDto> childMenus = buildMenuTreeRecursive(menu.getId(), menuMap);
                    if (!childMenus.isEmpty()) {
                        dto.setChildren(childMenus);
                    }
                    return dto;
                })
                .sorted(Comparator.comparing(dto -> {
                    if (dto.getMeta() != null && dto.getMeta().get("order") != null) {
                        return (Integer) dto.getMeta().get("order");
                    }
                    return dto.getId() == null ? 0 : dto.getId().intValue();
                }))
                .collect(Collectors.toList());
    }

    /**
     * 将Menu实体转换为MenuDto
     */
    private MenuDto convertToDto(Menu menu) {
        MenuDto dto = new MenuDto();
        dto.setId(menu.getId());
        dto.setPid(menu.getPid());
        dto.setName(menu.getName());
        dto.setPath(menu.getPath());
        dto.setComponent(menu.getComponent());
        dto.setType(menu.getType());
        dto.setStatus(menu.getStatus());
        dto.setIcon(menu.getIcon());
        dto.setAuthCode(menu.getPerms());
        // meta字段合成
        Map<String, Object> meta = new HashMap<>();
        if (menu.getTitleKey() != null) meta.put("title", menu.getTitleKey());
        if (menu.getIcon() != null) meta.put("icon", menu.getIcon());
        if (menu.getOrderNum() != null) meta.put("order", menu.getOrderNum());
        if (menu.getExtraMeta() != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> extra = mapper.readValue(menu.getExtraMeta(), Map.class);
                meta.putAll(extra);
            } catch (Exception ignored) {}
        }
        if (!meta.isEmpty()) dto.setMeta(meta);
        // 只保留响应中需要的字段
        return dto;
    }

    @Override
    public Response<Void> createMenu(Menu menu) {
        try {
            int result = menuMapper.insertMenu(menu);
            return result > 0 ? Response.success(null) : Response.error(500, "新增菜单失败", "insert error");
        } catch (Exception e) {
            return Response.error(500, "新增菜单失败", e.getMessage());
        }
    }

    @Override
    public Response<Void> updateMenu(Menu menu) {
        try {
            int result = menuMapper.updateMenu(menu);
            return result > 0 ? Response.success(null) : Response.error(500, "更新菜单失败", "update error");
        } catch (Exception e) {
            return Response.error(500, "更新菜单失败", e.getMessage());
        }
    }

    @Override
    public Response<Void> deleteMenu(Long id) {
        try {
            int result = menuMapper.deleteMenu(id);
            return result > 0 ? Response.success(null) : Response.error(500, "删除菜单失败", "delete error");
        } catch (Exception e) {
            return Response.error(500, "删除菜单失败", e.getMessage());
        }
    }

    @Override
    public Response<MenuDto> getMenuById(Long id) {
        try {
            Menu menu = menuMapper.getMenuById(id);
            if (menu == null) {
                return Response.error(404, "菜单不存在", "not found");
            }
            MenuDto dto = convertToDto(menu);
            return Response.success(dto);
        } catch (Exception e) {
            return Response.error(500, "获取菜单失败", e.getMessage());
        }
    }
} 