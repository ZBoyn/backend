package com.neu.zboyn.car.Service;

import com.neu.zboyn.car.dto.DefectDto;
import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.mapper.DefectMapper;
import com.neu.zboyn.car.model.Defect;
import com.neu.zboyn.car.service.impl.DefectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefectServiceTest {

    @Mock
    private DefectMapper defectMapper;

    @InjectMocks
    private DefectServiceImpl defectService;

    private Defect testDefect;
    private DefectDto testDefectDto;

    @BeforeEach
    void setUp() {
        testDefect = new Defect();
        testDefect.setDefectId(1L);
        testDefect.setTaskId("TASK001");
        testDefect.setDefectType("裂缝");
        testDefect.setDistanceFromOrigin("100.0");
        testDefect.setImageUrls("image1.jpg,image2.jpg");
        testDefect.setIsVerified(true);
        testDefect.setSeverity("严重");
        testDefect.setDefectLength(new BigDecimal("50.0"));
        testDefect.setDefectArea(new BigDecimal("100.0"));
        testDefect.setDefectQuantity(1);
        testDefect.setRecommendedAction("立即修复");
        testDefect.setReportedTime(new Date());
        testDefect.setStatus("0");

        testDefectDto = new DefectDto();
        testDefectDto.setDefectId(1L);
        testDefectDto.setTaskId("TASK001");
        testDefectDto.setDefectType("裂缝");
        testDefectDto.setDistanceFromOrigin("100.0");
        testDefectDto.setImageUrls(Arrays.asList("image1.jpg", "image2.jpg"));
        testDefectDto.setIsVerified("是");
        testDefectDto.setSeverity("严重");
        testDefectDto.setDefectLength(new BigDecimal("50.0"));
        testDefectDto.setDefectArea(new BigDecimal("100.0"));
        testDefectDto.setDefectQuantity(1);
        testDefectDto.setRecommendedAction("立即修复");
        testDefectDto.setReportedTime(new Date());
        testDefectDto.setStatus("已上报");
    }

    @Test
    void getDefectList_Success() {
        // 准备测试数据
        List<Defect> defectList = Arrays.asList(testDefect);
        when(defectMapper.selectDefectList(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(defectList);
        when(defectMapper.selectDefectCount(anyString(), anyString()))
                .thenReturn(1L);

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().getItems().size());
        assertEquals(1L, response.getData().getItems().get(0).getDefectId());
        assertEquals("裂缝", response.getData().getItems().get(0).getDefectType());
        assertEquals(1, response.getData().getTotal());
        assertEquals(1, response.getData().getPage());
        assertEquals(10, response.getData().getPageSize());

        // 验证方法调用
        verify(defectMapper).selectDefectList(null, null, 0, 10);
        verify(defectMapper).selectDefectCount(null, null);
    }

    @Test
    void getDefectList_WithFilters() {
        // 准备测试数据
        List<Defect> defectList = Arrays.asList(testDefect);
        when(defectMapper.selectDefectList("裂缝", "严重", 0, 10))
                .thenReturn(defectList);
        when(defectMapper.selectDefectCount("裂缝", "严重"))
                .thenReturn(1L);

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, "裂缝", "严重");

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(1, response.getData().getItems().size());

        // 验证方法调用
        verify(defectMapper).selectDefectList("裂缝", "严重", 0, 10);
        verify(defectMapper).selectDefectCount("裂缝", "严重");
    }

    @Test
    void getDefectList_EmptyResult() {
        // 准备测试数据
        when(defectMapper.selectDefectList(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());
        when(defectMapper.selectDefectCount(anyString(), anyString()))
                .thenReturn(0L);

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(0, response.getData().getItems().size());
        assertEquals(0, response.getData().getTotal());
    }

    @Test
    void createDefect_Success() {
        // 准备测试数据
        doNothing().when(defectMapper).create(any(Defect.class));

        // 执行测试
        Response<Void> response = defectService.createDefect(testDefectDto);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(defectMapper).create(argThat(defect -> {
            assertEquals(1L, defect.getDefectId());
            assertEquals("裂缝", defect.getDefectType());
            assertEquals("image1.jpg,image2.jpg", defect.getImageUrls());
            assertEquals(true, defect.getIsVerified());
            assertEquals("0", defect.getStatus());
            return true;
        }));
    }

    @Test
    void updateDefect_Success() {
        // 准备测试数据
        doNothing().when(defectMapper).update(any(Defect.class));

        // 执行测试
        Response<Void> response = defectService.updateDefect(testDefectDto);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(defectMapper).update(argThat(defect -> {
            assertEquals(1L, defect.getDefectId());
            assertEquals("裂缝", defect.getDefectType());
            assertEquals("image1.jpg,image2.jpg", defect.getImageUrls());
            return true;
        }));
    }

    @Test
    void deleteDefect_Success() {
        // 准备测试数据
        doNothing().when(defectMapper).delete(1L);

        // 执行测试
        Response<Void> response = defectService.deleteDefect(1L);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(defectMapper).delete(1L);
    }

    @Test
    void verifyDefect_Success() {
        // 准备测试数据
        doNothing().when(defectMapper).updateIsVerified(1L, true);

        // 执行测试
        Response<Void> response = defectService.verifyDefect(1L, true);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(defectMapper).updateIsVerified(1L, true);
    }

    @Test
    void verifyDefect_Unverified() {
        // 准备测试数据
        doNothing().when(defectMapper).updateIsVerified(1L, false);

        // 执行测试
        Response<Void> response = defectService.verifyDefect(1L, false);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(defectMapper).updateIsVerified(1L, false);
    }

    @Test
    void markDefectRectified_Success() {
        // 准备测试数据
        doNothing().when(defectMapper).updateStatus(1L, "1");

        // 执行测试
        Response<Void> response = defectService.markDefectRectified(1L);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(defectMapper).updateStatus(1L, "1");
    }

    @Test
    void toDto_WithNullImageUrls() {
        // 准备测试数据
        Defect defect = new Defect();
        defect.setDefectId(1L);
        defect.setDefectType("裂缝");
        defect.setImageUrls(null);
        defect.setIsVerified(false);
        defect.setStatus("0");

        List<Defect> defectList = Arrays.asList(defect);
        when(defectMapper.selectDefectList(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(defectList);
        when(defectMapper.selectDefectCount(anyString(), anyString()))
                .thenReturn(1L);

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(1, response.getData().getItems().size());
        assertEquals("否", response.getData().getItems().get(0).getIsVerified());
        assertEquals("已上报", response.getData().getItems().get(0).getStatus());
    }

    @Test
    void toEntity_WithEmptyImageUrls() {
        // 准备测试数据
        DefectDto dto = new DefectDto();
        dto.setDefectId(1L);
        dto.setDefectType("裂缝");
        dto.setImageUrls(Collections.emptyList());
        dto.setIsVerified("否");
        dto.setStatus("已整改");

        doNothing().when(defectMapper).create(any(Defect.class));

        // 执行测试
        Response<Void> response = defectService.createDefect(dto);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());

        // 验证方法调用
        verify(defectMapper).create(argThat(defect -> {
            assertEquals(1L, defect.getDefectId());
            assertEquals("裂缝", defect.getDefectType());
            assertEquals(false, defect.getIsVerified());
            assertEquals("1", defect.getStatus());
            return true;
        }));
    }
} 