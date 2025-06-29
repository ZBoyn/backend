package com.neu.zboyn.car.mapper;

import com.neu.zboyn.car.model.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {
    List<Task> selectTaskList(@Param("taskId") String taskId, @Param("taskName") String taskName, @Param("creatorId") Long creatorId, @Param("executorId") Long executorId, @Param("status") String status, @Param("startTime") String startTime, @Param("endTime") String endTime);
    Task selectTaskById(@Param("taskId") String taskId);
    int insertTask(Task task);
    int updateTask(Task task);
    int deleteTask(@Param("taskId") String taskId);
    String selectTaskIdByTaskName(@Param("taskName") String taskName);
    String selectTaskNameByTaskId(@Param("taskId") String taskId);
    //获取用户的分页列表
    @Select("SELECT * FROM task WHERE executor_id = #{userId}")
    List<Task> getUserTaskList(String userId);
}