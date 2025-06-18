package com.neu.zboyn.car.mapper;

import com.neu.zboyn.car.model.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TaskMapper {
    List<Task> selectTaskList(@Param("taskName") String taskName, @Param("status") String status, @Param("startTime") String startTime, @Param("endTime") String endTime);
    Task selectTaskById(@Param("taskId") String taskId);
    int insertTask(Task task);
    int updateTask(Task task);
    int deleteTask(@Param("taskId") String taskId);
}