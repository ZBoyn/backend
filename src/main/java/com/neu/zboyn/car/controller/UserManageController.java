package com.neu.zboyn.car.controller;


import com.neu.zboyn.car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理模块
 */
@RestController
@RequestMapping("/api/system")
public class SysManageController {
    @Autowired
    private UserService userService;

    @RequestMapping("/role")


}
