package com.neu.zboyn.car.Controller;

import com.neu.zboyn.car.controller.TaskController;
import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.dto.TaskDto;
import com.neu.zboyn.car.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    private ObjectMapper objectMapper;

    private TaskDto taskDto;

    @BeforeEach
    void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        
        // 创建TaskController实例
        TaskController taskController = new TaskController();
        
        // 使用反射设置私有字段
        Field taskServiceField = TaskController.class.getDeclaredField("taskService");
        taskServiceField.setAccessible(true);
        taskServiceField.set(taskController, taskService);
        
        mockMvc = MockMvcBuilders.standaloneSetup(taskController)
                .build();

        taskDto = new TaskDto();
        taskDto.setTaskId("1");
        taskDto.setTaskName("测试任务");
        taskDto.setStatus(1);
    }

    @Test
    void getTaskList() throws Exception {
        // 准备测试数据
        TaskDto taskDto1 = new TaskDto();
        taskDto1.setTaskId("1");
        taskDto1.setTaskName("任务1");

        TaskDto taskDto2 = new TaskDto();
        taskDto2.setTaskId("2");
        taskDto2.setTaskName("任务2");

        PageResult<TaskDto> pageResult = new PageResult<>(
                Arrays.asList(taskDto1, taskDto2),
                1,
                10,
                2
        );

        // 模拟服务层返回
        when(taskService.getTaskList(1, 10, null, null, null, null, null, null, null))
                .thenReturn(Response.success(pageResult));

        // 执行请求
        mockMvc.perform(get("/api/inspection/task/list")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.data.items", hasSize(2)))
                .andExpect(jsonPath("$.data.items[0].taskId", is("1")))
                .andExpect(jsonPath("$.data.items[1].taskName", is("任务2")));
    }

    @Test
    void getTaskById() throws Exception {
        // 模拟服务层返回
        when(taskService.getTaskById("1"))
                .thenReturn(Response.success(taskDto));

        // 执行请求
        mockMvc.perform(get("/api/inspection/task/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.data.taskId", is("1")))
                .andExpect(jsonPath("$.data.taskName", is("测试任务")));
    }

    @Test
    void createTask() throws Exception {
        // 模拟服务层返回
        when(taskService.createTask(taskDto))
                .thenReturn(Response.success(null));

        // 执行请求
        mockMvc.perform(post("/api/inspection/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(0)));
    }

    @Test
    void updateTask() throws Exception {
        // 模拟服务层返回
        when(taskService.updateTask("1", taskDto))
                .thenReturn(Response.success(null));

        // 执行请求
        mockMvc.perform(put("/api/inspection/task/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(0)));
    }

    @Test
    void deleteTask() throws Exception {
        // 模拟服务层返回
        when(taskService.deleteTask("1"))
                .thenReturn(Response.success(null));

        // 执行请求
        mockMvc.perform(delete("/api/inspection/task/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(0)));
    }

    @Test
    void getTaskListWithParams() throws Exception {
        // 准备测试数据
        TaskDto taskDto1 = new TaskDto();
        taskDto1.setTaskId("1");
        taskDto1.setTaskName("测试任务");
        taskDto1.setStatus(1);

        PageResult<TaskDto> pageResult = new PageResult<>(
                Collections.singletonList(taskDto1),
                1,
                10,
                1
        );

        // 模拟服务层返回
        when(taskService.getTaskList(1, 10, null, "测试", null, null, "完成", null, null))
                .thenReturn(Response.success(pageResult));

        // 执行请求
        mockMvc.perform(get("/api/inspection/task/list")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .param("taskName", "测试")
                        .param("status", "完成")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(0)))
                .andExpect(jsonPath("$.data.items", hasSize(1)))
                .andExpect(jsonPath("$.data.items[0].taskName", is("测试任务")))
                .andExpect(jsonPath("$.data.items[0].status", is(1)));
    }
}