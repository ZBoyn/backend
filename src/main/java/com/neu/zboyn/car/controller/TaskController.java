package com.neu.zboyn.car.controller;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.dto.TaskDto;
import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.model.User;
import com.neu.zboyn.car.service.TaskService;
import com.neu.zboyn.car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 任务管理模块
 */
@RestController
@RequestMapping("/api/inspection/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    /**
     * 获取任务列表
     * @param page 当前页码，默认为1
     * @param pageSize 每页条数，默认为20
     * @param taskName 任务名称
     * @param status 任务状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 任务列表和分页信息
     */
    @RequestMapping("/list")
    public Response<PageResult<TaskDto>> getTaskList(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String taskId,
            @RequestParam(required = false) String taskName,
            @RequestParam(required = false) Long creatorId,
            @RequestParam(required = false) Long executorId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime
    ) {
        // 设置默认值
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 20 : pageSize;
        return taskService.getTaskList(page, pageSize, taskId, taskName, creatorId, executorId, status, startTime, endTime);
    }

    /**
     * 根据任务ID获取任务信息
     * @param taskId 任务ID
     * @return 任务信息
     */
    @GetMapping("/{taskId}")
    public Response<TaskDto> getTaskById(@PathVariable String taskId) {
        return taskService.getTaskById(taskId);
    }

    /**
     * 创建新任务
     * @param taskDto 任务信息
     * @return 创建结果
     */
    @PostMapping("")
    public Response<Void> create(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    /**
     * 更新任务信息
     * @param taskDto 任务信息
     * @return 更新结果
     */
    @PutMapping("/{taskId}")
    public Response<Void> update(
            @PathVariable String taskId,
            @RequestBody TaskDto taskDto) {
        return taskService.updateTask(taskId, taskDto);
    }

    /**
     * 删除任务
     * @param taskId 任务ID
     * @return 删除结果
     */
    @DeleteMapping("/{taskId}")
    public Response<Void> delete(@PathVariable String taskId) {
        return taskService.deleteTask(taskId);
    }

    /**
     * 获取任务创建者的用户信息
     * @param creatorId 创建者ID
     * @return 用户信息
     */
    @GetMapping("/task/{creatorId}")
    public Response<User> getCreatorInfo(@PathVariable String creatorId) {
        return userService.getUserByCreatorId(creatorId);
    }
}