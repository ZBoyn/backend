package com.neu.zboyn.car.Service;

import com.neu.zboyn.car.dto.DefectDto;
import com.neu.zboyn.car.dto.PageResult;
import com.neu.zboyn.car.dto.Response;
import com.neu.zboyn.car.mapper.DefectMapper;
import com.neu.zboyn.car.mapper.TaskMapper;
import com.neu.zboyn.car.model.Defect;
import com.neu.zboyn.car.service.impl.DefectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DefectServiceTest {

    @Mock
    private DefectMapper defectMapper;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private DefectServiceImpl defectService;

    private Defect testDefect;
    private DefectDto testDefectDto;

    @BeforeEach
    void setUp() {
        testDefect = new Defect();
        testDefect.setDefectId(1L);
        testDefect.setTaskId("task1");
        testDefect.setDefectType("裂缝");
        testDefect.setDistanceFromOrigin("10.5");
        testDefect.setImageUrls("image1.jpg,image2.jpg");
        testDefect.setIsVerified(true);
        testDefect.setSeverity("严重");
        testDefect.setDefectLength(new BigDecimal("5.0"));
        testDefect.setDefectArea(new BigDecimal("25.0"));
        testDefect.setDefectQuantity(3);
        testDefect.setRecommendedAction("立即修复");
        testDefect.setReportedTime(new Date());
        testDefect.setStatus("0");

        testDefectDto = new DefectDto();
        testDefectDto.setDefectId(1L);
        testDefectDto.setTaskId("task1");
        testDefectDto.setDefectType("裂缝");
        testDefectDto.setDistanceFromOrigin("10.5");
        testDefectDto.setImageUrls(Arrays.asList("image1.jpg", "image2.jpg"));
        testDefectDto.setIsVerified("是");
        testDefectDto.setSeverity("严重");
        testDefectDto.setDefectLength(new BigDecimal("5.0"));
        testDefectDto.setDefectArea(new BigDecimal("25.0"));
        testDefectDto.setDefectQuantity(3);
        testDefectDto.setRecommendedAction("立即修复");
        testDefectDto.setReportedTime(new Date());
        testDefectDto.setStatus("已上报");
    }

    @Test
    void getDefectList_Success() {
        // 准备测试数据
        List<Defect> defectList = Arrays.asList(testDefect);
        
        // 重置mock并设置行为
        reset(defectMapper);
        doReturn(defectList).when(defectMapper).selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt());
        doReturn(1L).when(defectMapper).selectDefectCount(anyString(), anyString(), anyString());

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, null, null, null);

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
        verify(defectMapper).selectDefectList(null, null, null, 0, 10);
        verify(defectMapper).selectDefectCount(null, null, null);
    }

    @Test
    void getDefectList_WithFilters() {
        // 准备测试数据
        List<Defect> defectList = Arrays.asList(testDefect);
        
        // 重置mock并设置行为
        reset(defectMapper);
        doReturn(defectList).when(defectMapper).selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt());
        doReturn(1L).when(defectMapper).selectDefectCount(anyString(), anyString(), anyString());

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, "task1", "裂缝", "是");

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(1, response.getData().getItems().size());

        // 验证方法调用
        verify(defectMapper).selectDefectList("task1", "裂缝", "是", 0, 10);
        verify(defectMapper).selectDefectCount("task1", "裂缝", "是");
    }

    @Test
    void getDefectList_EmptyResult() {
        // 重置mock并设置行为
        reset(defectMapper);
        doReturn(Collections.emptyList()).when(defectMapper).selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt());
        doReturn(0L).when(defectMapper).selectDefectCount(anyString(), anyString(), anyString());

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(0, response.getData().getItems().size());
        assertEquals(0, response.getData().getTotal());
    }

    @Test
    void createDefect_Success() {
        // 重置mock并设置行为
        reset(defectMapper);
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
        // 重置mock并设置行为
        reset(defectMapper);
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
        // 重置mock并设置行为
        reset(defectMapper);
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
        // 重置mock并设置行为
        reset(defectMapper);
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
        // 重置mock并设置行为
        reset(defectMapper);
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
        // 重置mock并设置行为
        reset(defectMapper);
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
        
        // 重置mock并设置行为
        reset(defectMapper);
        doReturn(defectList).when(defectMapper).selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt());
        doReturn(1L).when(defectMapper).selectDefectCount(anyString(), anyString(), anyString());

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(1, response.getData().getItems().size());
        assertNull(response.getData().getItems().get(0).getImageUrls());
    }

    @Test
    void toDto_WithEmptyImageUrls() {
        // 准备测试数据
        Defect defect = new Defect();
        defect.setDefectId(1L);
        defect.setDefectType("裂缝");
        defect.setImageUrls("");
        defect.setIsVerified(false);
        defect.setStatus("0");

        List<Defect> defectList = Arrays.asList(defect);
        
        // 重置mock并设置行为
        reset(defectMapper);
        doReturn(defectList).when(defectMapper).selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt());
        doReturn(1L).when(defectMapper).selectDefectCount(anyString(), anyString(), anyString());

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(1, response.getData().getItems().size());
        assertTrue(response.getData().getItems().get(0).getImageUrls().isEmpty());
    }

    @Test
    void toEntity_WithEmptyImageUrls() {
        // 准备测试数据
        DefectDto dto = new DefectDto();
        dto.setDefectId(1L);
        dto.setDefectType("裂缝");
        dto.setImageUrls(Collections.emptyList());
        dto.setIsVerified("否");
        dto.setStatus("已上报");

        // 重置mock并设置行为
        reset(defectMapper);
        doNothing().when(defectMapper).create(any(Defect.class));

        // 执行测试
        Response<Void> response = defectService.createDefect(dto);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());

        // 验证方法调用 - 空列表时imageUrls保持为null
        verify(defectMapper).create(argThat(defect -> {
            assertEquals(1L, defect.getDefectId());
            assertEquals("裂缝", defect.getDefectType());
            assertNull(defect.getImageUrls()); // 空列表时imageUrls为null
            assertEquals(false, defect.getIsVerified());
            assertEquals("0", defect.getStatus());
            return true;
        }));
    }
} 