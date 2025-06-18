package com.neu.zboyn.car.service.impl;

import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.dto.TaskDto;
import com.neu.zboyn.car.mapper.TaskMapper;
import com.neu.zboyn.car.model.Task;
import com.neu.zboyn.car.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public Response<PageResult<TaskDto>> getTaskList(Integer page, Integer pageSize, String taskName, String status, String startTime, String endTime) {
        List<Task> tasks = taskMapper.selectTaskList(taskName, status, startTime, endTime);
        List<TaskDto> dtoList = new ArrayList<>();
        for (Task task : tasks) {
            TaskDto dto = new TaskDto();
            BeanUtils.copyProperties(task, dto);
            dtoList.add(dto);
        }
        PageResult<TaskDto> result = new PageResult<>();
        result.setItems(dtoList);
        result.setTotal(dtoList.size());
        result.setPage(page == null ? 1 : page);
        result.setPageSize(pageSize == null ? 20 : pageSize);
        return new Response<>(0, result, "success", "success");
    }

    @Override
    public Response<TaskDto> getTaskById(String taskId) {
        Task task = taskMapper.selectTaskById(taskId);
        if (task == null) return Response.error(404, "任务不存在", "not found");
        TaskDto dto = new TaskDto();
        BeanUtils.copyProperties(task, dto);
        return Response.success(dto);
    }

    @Override
    public Response<Void> createTask(TaskDto taskDto) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        int res = taskMapper.insertTask(task);
        return res > 0 ? Response.success(null) : Response.error(500, "新增失败", "insert error");
    }

    @Override
    public Response<Void> updateTask(TaskDto taskDto) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        int res = taskMapper.updateTask(task);
        return res > 0 ? Response.success(null) : Response.error(500, "更新失败", "update error");
    }

    @Override
    public Response<Void> deleteTask(String taskId) {
        int res = taskMapper.deleteTask(taskId);
        return res > 0 ? Response.success(null) : Response.error(500, "删除失败", "delete error");
    }
}