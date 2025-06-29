package com.neu.zboyn.car.Service;

import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.dto.TaskDto;
import com.neu.zboyn.car.mapper.TaskMapper;
import com.neu.zboyn.car.model.Task;
import com.neu.zboyn.car.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task testTask;
    private TaskDto testTaskDto;

    @BeforeEach
    void setUp() {
        testTask = new Task();
        testTask.setTaskId("1");
        testTask.setTaskName("测试任务");
        testTask.setStatus(1);
        testTask.setCreateTime(new Date());
        testTask.setUploadTime(new Date());

        testTaskDto = new TaskDto();
        testTaskDto.setTaskId("1");
        testTaskDto.setTaskName("测试任务");
        testTaskDto.setStatus(1);
    }

    @Test
    void getTaskList_Success() {
        // 准备测试数据
        List<Task> taskList = Arrays.asList(testTask);
        when(taskMapper.selectTaskList(anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString(), anyString()))
                .thenReturn(taskList);

        // 执行测试
        Response<PageResult<TaskDto>> response = taskService.getTaskList(1, 10, null, null, null, null, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals("success", response.getMessage());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().getItems().size());
        assertEquals("1", response.getData().getItems().get(0).getTaskId());
        assertEquals("测试任务", response.getData().getItems().get(0).getTaskName());
        assertEquals(1, response.getData().getTotal());
        assertEquals(1, response.getData().getPage());
        assertEquals(10, response.getData().getPageSize());

        // 验证方法调用
        verify(taskMapper).selectTaskList(null, null, null, null, null, null, null);
    }

    @Test
    void getTaskList_WithFilters() {
        // 准备测试数据
        List<Task> taskList = Arrays.asList(testTask);
        when(taskMapper.selectTaskList("1", "测试", 1L, 2L, "1", "2023-01-01", "2023-12-31"))
                .thenReturn(taskList);

        // 执行测试
        Response<PageResult<TaskDto>> response = taskService.getTaskList(1, 10, "1", "测试", 1L, 2L, "1", "2023-01-01", "2023-12-31");

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(1, response.getData().getItems().size());

        // 验证方法调用
        verify(taskMapper).selectTaskList("1", "测试", 1L, 2L, "1", "2023-01-01", "2023-12-31");
    }

    @Test
    void getTaskList_EmptyResult() {
        // 准备测试数据
        when(taskMapper.selectTaskList(anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString(), anyString()))
                .thenReturn(Collections.emptyList());

        // 执行测试
        Response<PageResult<TaskDto>> response = taskService.getTaskList(1, 10, null, null, null, null, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(0, response.getData().getItems().size());
        assertEquals(0, response.getData().getTotal());
    }

    @Test
    void getTaskList_DefaultPagination() {
        // 准备测试数据
        when(taskMapper.selectTaskList(anyString(), anyString(), anyLong(), anyLong(), anyString(), anyString(), anyString()))
                .thenReturn(Collections.emptyList());

        // 执行测试（传入null值）
        Response<PageResult<TaskDto>> response = taskService.getTaskList(null, null, null, null, null, null, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(1, response.getData().getPage()); // 默认值
        assertEquals(20, response.getData().getPageSize()); // 默认值
    }

    @Test
    void getTaskById_Success() {
        // 准备测试数据
        when(taskMapper.selectTaskById("1")).thenReturn(testTask);

        // 执行测试
        Response<TaskDto> response = taskService.getTaskById("1");

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNotNull(response.getData());
        assertEquals("1", response.getData().getTaskId());
        assertEquals("测试任务", response.getData().getTaskName());
        assertEquals(1, response.getData().getStatus());

        // 验证方法调用
        verify(taskMapper).selectTaskById("1");
    }

    @Test
    void getTaskById_NotFound() {
        // 准备测试数据
        when(taskMapper.selectTaskById("999")).thenReturn(null);

        // 执行测试
        Response<TaskDto> response = taskService.getTaskById("999");

        // 验证结果
        assertNotNull(response);
        assertEquals(404, response.getCode());
        assertEquals("任务不存在", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(taskMapper).selectTaskById("999");
    }

    @Test
    void createTask_Success() {
        // 准备测试数据
        when(taskMapper.insertTask(any(Task.class))).thenReturn(1);

        // 执行测试
        Response<Void> response = taskService.createTask(testTaskDto);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(taskMapper).insertTask(argThat(task -> {
            assertEquals("1", task.getTaskId());
            assertEquals("测试任务", task.getTaskName());
            assertEquals(1, task.getStatus());
            assertNotNull(task.getCreateTime());
            assertNotNull(task.getUploadTime());
            return true;
        }));
    }

    @Test
    void createTask_Failed() {
        // 准备测试数据
        when(taskMapper.insertTask(any(Task.class))).thenReturn(0);

        // 执行测试
        Response<Void> response = taskService.createTask(testTaskDto);

        // 验证结果
        assertNotNull(response);
        assertEquals(500, response.getCode());
        assertEquals("新增失败", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(taskMapper).insertTask(any(Task.class));
    }

    @Test
    void updateTask_Success() {
        // 准备测试数据
        when(taskMapper.updateTask(any(Task.class))).thenReturn(1);

        // 执行测试
        Response<Void> response = taskService.updateTask("1", testTaskDto);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(taskMapper).updateTask(argThat(task -> {
            assertEquals("1", task.getTaskId());
            assertEquals("测试任务", task.getTaskName());
            assertEquals(1, task.getStatus());
            return true;
        }));
    }

    @Test
    void updateTask_Failed() {
        // 准备测试数据
        when(taskMapper.updateTask(any(Task.class))).thenReturn(0);

        // 执行测试
        Response<Void> response = taskService.updateTask("1", testTaskDto);

        // 验证结果
        assertNotNull(response);
        assertEquals(500, response.getCode());
        assertEquals("更新失败", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(taskMapper).updateTask(any(Task.class));
    }

    @Test
    void deleteTask_Success() {
        // 准备测试数据
        when(taskMapper.deleteTask("1")).thenReturn(1);

        // 执行测试
        Response<Void> response = taskService.deleteTask("1");

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(taskMapper).deleteTask("1");
    }

    @Test
    void deleteTask_Failed() {
        // 准备测试数据
        when(taskMapper.deleteTask("999")).thenReturn(0);

        // 执行测试
        Response<Void> response = taskService.deleteTask("999");

        // 验证结果
        assertNotNull(response);
        assertEquals(500, response.getCode());
        assertEquals("删除失败", response.getMessage());
        assertNull(response.getData());

        // 验证方法调用
        verify(taskMapper).deleteTask("999");
    }
} 