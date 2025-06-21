package com.neu.zboyn.car.Dto;

import com.neu.zboyn.car.dto.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseTest {

    @Test
    void testSuccessResponse() {
        String data = "test data";
        Response<String> response = Response.success(data);

        assertThat(response.getCode()).isEqualTo(0);
        assertThat(response.getData()).isEqualTo(data);
        assertThat(response.getMessage()).isEqualTo("操作成功");
        assertThat(response.getError()).isEqualTo("ok");
    }

    @Test
    void testErrorResponse() {
        int code = 400;
        String message = "Bad Request";
        String error = "error";
        Response<String> response = Response.error(code, message, error);

        assertThat(response.getCode()).isEqualTo(code);
        assertThat(response.getData()).isNull();
        assertThat(response.getMessage()).isEqualTo(message);
        assertThat(response.getError()).isEqualTo(error);
    }
}