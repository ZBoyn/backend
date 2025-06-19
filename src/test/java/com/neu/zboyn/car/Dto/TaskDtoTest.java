package com.neu.zboyn.car.Dto;

import com.neu.zboyn.car.dto.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class TaskDtoTest {

    @Test
    void testTaskDto() {
        String taskId = "1";
        String taskName = "Test Task";
        Long creatorId = 1L;
        Long executorId = 2L;
        String startLocation = "Location";
        Double distance = 10.0;
        Date deadlineTime = new Date();
        Date completionTime = new Date();
        Date uploadTime = new Date();
        Integer status = 1;
        Date createTime = new Date();

        TaskDto taskDto = new TaskDto(taskId, taskName, creatorId, executorId, startLocation, distance, deadlineTime, completionTime, uploadTime, status, createTime);

        assertThat(taskDto.getTaskId()).isEqualTo(taskId);
        assertThat(taskDto.getTaskName()).isEqualTo(taskName);
        assertThat(taskDto.getCreatorId()).isEqualTo(creatorId);
        assertThat(taskDto.getExecutorId()).isEqualTo(executorId);
        assertThat(taskDto.getStartLocation()).isEqualTo(startLocation);
        assertThat(taskDto.getDistance()).isEqualTo(distance);
        assertThat(taskDto.getDeadlineTime()).isEqualTo(deadlineTime);
        assertThat(taskDto.getCompletionTime()).isEqualTo(completionTime);
        assertThat(taskDto.getUploadTime()).isEqualTo(uploadTime);
        assertThat(taskDto.getStatus()).isEqualTo(status);
        assertThat(taskDto.getCreateTime()).isEqualTo(createTime);
    }
}