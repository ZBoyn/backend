package com.neu.zboyn.car.service;

import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.dto.TaskDto;
import com.neu.zboyn.car.dto.PageResult;

public interface TaskService {
    Response<PageResult<TaskDto>> getTaskList(Integer page, Integer pageSize, String taskId, String taskName, Long creatorId, Long executorId, String status, String startTime, String endTime);
    Response<TaskDto> getTaskById(String taskId);
    Response<Void> createTask(TaskDto taskDto);
    Response<Void> updateTask(String taskId, TaskDto taskDto);
    Response<Void> deleteTask(String taskId);
}