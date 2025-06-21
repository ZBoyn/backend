package com.neu.zboyn.car.Dto;

import com.neu.zboyn.car.dto.PageResult;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
class PageResultTest {

    @Test
    void testPageResult() {
        List<String> items = Arrays.asList("item1", "item2", "item3");
        long total = 10;
        int page = 1;
        int pageSize = 5;

        PageResult<String> pageResult = new PageResult<>(items, total, page, pageSize);

        assertThat(pageResult.getItems()).isEqualTo(items);
        assertThat(pageResult.getTotal()).isEqualTo(total);
        assertThat(pageResult.getPage()).isEqualTo(page);
        assertThat(pageResult.getPageSize()).isEqualTo(pageSize);
    }
}