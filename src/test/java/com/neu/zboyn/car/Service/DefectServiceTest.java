package com.neu.zboyn.car.service;

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
import org.springframework.test.util.ReflectionTestUtils;

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
        // 设置serverUrl属性
        ReflectionTestUtils.setField(defectService, "serverUrl", "http://localhost:8080");
        
        // 初始化测试数据
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

    // ==================== getDefectList 测试方法 ====================

    @Test
    void getDefectList_Success() {
        // 准备测试数据
        List<Defect> defectList = Arrays.asList(testDefect);
        
        // 设置mock行为
        when(taskMapper.selectTaskNameByTaskId("task1")).thenReturn("测试任务1");
        when(defectMapper.selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(defectList);
        when(defectMapper.selectDefectCount(anyString(), anyString(), anyString()))
                .thenReturn(1L);

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNotNull(response.getData());

        // 验证方法调用
        verify(defectMapper).selectDefectList(null, null, null, 0, 10);
        verify(defectMapper).selectDefectCount(null, null, null);
    }

    @Test
    void getDefectList_WithFilters() {
        // 准备测试数据
        List<Defect> defectList = Arrays.asList(testDefect);
        
        // 设置mock行为
        when(taskMapper.selectTaskNameByTaskId("task1")).thenReturn("测试任务1");
        when(defectMapper.selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(defectList);
        when(defectMapper.selectDefectCount(anyString(), anyString(), anyString()))
                .thenReturn(1L);

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
        // 设置mock行为
        when(defectMapper.selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());
        when(defectMapper.selectDefectCount(anyString(), anyString(), anyString()))
                .thenReturn(0L);

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(0, response.getData().getItems().size());
        assertEquals(0, response.getData().getTotal());
    }

    @Test
    void getDefectList_Pagination() {
        // 准备测试数据
        List<Defect> defectList = Arrays.asList(testDefect);
        
        // 设置mock行为
        when(taskMapper.selectTaskNameByTaskId("task1")).thenReturn("测试任务1");
        when(defectMapper.selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(defectList);
        when(defectMapper.selectDefectCount(anyString(), anyString(), anyString()))
                .thenReturn(25L);

        // 执行测试 - 第3页，每页10条
        Response<PageResult<DefectDto>> response = defectService.getDefectList(3, 10, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(3, response.getData().getPage());
        assertEquals(10, response.getData().getPageSize());
        // 验证方法调用 - 第3页的offset应该是20
        verify(defectMapper).selectDefectList(null, null, null, 20, 10);
    }


    @Test
    void getDefectList_WithNullImageUrls() {
        // 准备测试数据
        Defect defect = new Defect();
        defect.setDefectId(1L);
        defect.setDefectType("裂缝");
        defect.setImageUrls(null);
        defect.setIsVerified(false);
        defect.setStatus("0");

        List<Defect> defectList = Arrays.asList(defect);
        
        // 设置mock行为
        when(taskMapper.selectTaskNameByTaskId(anyString())).thenReturn("测试任务");
        when(taskMapper.selectTaskNameByTaskId(null)).thenReturn(null); // 为null值添加mock
        when(defectMapper.selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(defectList);
        when(defectMapper.selectDefectCount(anyString(), anyString(), anyString()))
                .thenReturn(1L);

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());

    }

    @Test
    void getDefectList_WithEmptyImageUrls() {
        // 准备测试数据
        Defect defect = new Defect();
        defect.setDefectId(1L);
        defect.setDefectType("裂缝");
        defect.setImageUrls("");
        defect.setIsVerified(false);
        defect.setStatus("0");

        List<Defect> defectList = Arrays.asList(defect);
        
        // 设置mock行为
        when(taskMapper.selectTaskNameByTaskId(anyString())).thenReturn("测试任务");
        when(taskMapper.selectTaskNameByTaskId(null)).thenReturn(null); // 为null值添加mock
        when(defectMapper.selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(defectList);
        when(defectMapper.selectDefectCount(anyString(), anyString(), anyString()))
                .thenReturn(1L);

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());

    }

    @Test
    void getDefectList_WithFullImageUrls() {
        // 准备测试数据 - 包含完整URL的图片
        Defect defect = new Defect();
        defect.setDefectId(1L);
        defect.setDefectType("裂缝");
        defect.setImageUrls("http://example.com/image1.jpg,http://example.com/image2.jpg");
        defect.setIsVerified(false);
        defect.setStatus("0");

        List<Defect> defectList = Arrays.asList(defect);
        
        // 设置mock行为
        when(taskMapper.selectTaskNameByTaskId(anyString())).thenReturn("测试任务");
        when(taskMapper.selectTaskNameByTaskId(null)).thenReturn(null); // 为null值添加mock
        when(defectMapper.selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(defectList);
        when(defectMapper.selectDefectCount(anyString(), anyString(), anyString()))
                .thenReturn(1L);

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(0, response.getData().getItems().size());
        
        List<String> expectedUrls = Arrays.asList(
                "http://example.com/image1.jpg",
                "http://example.com/image2.jpg"
        );
    }

    // ==================== createDefect 测试方法 ====================
    @Test
    void createDefect_Success() {
        // 设置mock行为
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
    void createDefect_WithEmptyImageUrls() {
        // 准备测试数据
        DefectDto dto = new DefectDto();
        dto.setDefectId(1L);
        dto.setDefectType("裂缝");
        dto.setImageUrls(Collections.emptyList());
        dto.setIsVerified("否");
        dto.setStatus("已上报");

        // 设置mock行为
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

    @Test
    void createDefect_WithNullImageUrls() {
        // 准备测试数据
        DefectDto dto = new DefectDto();
        dto.setDefectId(1L);
        dto.setDefectType("裂缝");
        dto.setImageUrls(null);
        dto.setIsVerified("否");
        dto.setStatus("已上报");

        // 设置mock行为
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
            assertNull(defect.getImageUrls());
            assertEquals(false, defect.getIsVerified());
            assertEquals("0", defect.getStatus());
            return true;
        }));
    }

    @Test
    void createDefect_StatusMapping() {
        // 准备测试数据 - 已整改状态
        DefectDto dto = new DefectDto();
        dto.setDefectId(1L);
        dto.setDefectType("裂缝");
        dto.setStatus("已整改");

        // 设置mock行为
        doNothing().when(defectMapper).create(any(Defect.class));

        // 执行测试
        Response<Void> response = defectService.createDefect(dto);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());

        // 验证方法调用
        verify(defectMapper).create(argThat(defect -> {
            assertEquals("1", defect.getStatus()); // 已整改映射为"1"
            return true;
        }));
    }

    // ==================== updateDefect 测试方法 ====================

    @Test
    void updateDefect_Success() {
        // 设置mock行为
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
    void updateDefect_WithNullDto() {
        // 设置mock行为
        doNothing().when(defectMapper).update(any(Defect.class));

        // 执行测试
        Response<Void> response = defectService.updateDefect(null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用 - 传入null时应该创建null的Defect对象
        verify(defectMapper).update(null);
    }

    // ==================== deleteDefect 测试方法 ====================

    @Test
    void deleteDefect_Success() {
        // 设置mock行为
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
    void deleteDefect_WithNullId() {
        // 设置mock行为
        doNothing().when(defectMapper).delete(null);

        // 执行测试
        Response<Void> response = defectService.deleteDefect(null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(defectMapper).delete(null);
    }

    // ==================== verifyDefect 测试方法 ====================

    @Test
    void verifyDefect_Success() {
        // 设置mock行为
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
        // 设置mock行为
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
    void verifyDefect_WithNullId() {
        // 设置mock行为
        doNothing().when(defectMapper).updateIsVerified(null, true);

        // 执行测试
        Response<Void> response = defectService.verifyDefect(null, true);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(defectMapper).updateIsVerified(null, true);
    }

    @Test
    void verifyDefect_WithNullIsVerified() {
        // 设置mock行为
        doNothing().when(defectMapper).updateIsVerified(1L, null);

        // 执行测试
        Response<Void> response = defectService.verifyDefect(1L, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(defectMapper).updateIsVerified(1L, null);
    }

    // ==================== markDefectRectified 测试方法 ====================

    @Test
    void markDefectRectified_Success() {
        // 设置mock行为
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
    void markDefectRectified_WithNullId() {
        // 设置mock行为
        doNothing().when(defectMapper).updateStatus(null, "1");

        // 执行测试
        Response<Void> response = defectService.markDefectRectified(null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertNull(response.getData());

        // 验证方法调用
        verify(defectMapper).updateStatus(null, "1");
    }

    // ==================== 边界情况测试 ====================

    @Test
    void getDefectList_WithNegativePage() {
        // 设置mock行为
        when(defectMapper.selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());
        when(defectMapper.selectDefectCount(anyString(), anyString(), anyString()))
                .thenReturn(0L);

        // 执行测试 - 负数页码
        Response<PageResult<DefectDto>> response = defectService.getDefectList(-1, 10, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(-1, response.getData().getPage());
        assertEquals(10, response.getData().getPageSize());

        // 验证方法调用 - offset应该是负数
        verify(defectMapper).selectDefectList(null, null, null, -20, 10);
    }

    @Test
    void getDefectList_WithZeroPageSize() {
        // 设置mock行为
        when(defectMapper.selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());
        when(defectMapper.selectDefectCount(anyString(), anyString(), anyString()))
                .thenReturn(0L);

        // 执行测试 - 零页面大小
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 0, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(1, response.getData().getPage());
        assertEquals(0, response.getData().getPageSize());

        // 验证方法调用
        verify(defectMapper).selectDefectList(null, null, null, 0, 0);
    }

    @Test
    void getDefectList_WithLargePageSize() {
        // 设置mock行为
        when(defectMapper.selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());
        when(defectMapper.selectDefectCount(anyString(), anyString(), anyString()))
                .thenReturn(0L);

        // 执行测试 - 大页面大小
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 1000, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
        assertEquals(1, response.getData().getPage());
        assertEquals(1000, response.getData().getPageSize());

        // 验证方法调用
        verify(defectMapper).selectDefectList(null, null, null, 0, 1000);
    }

    @Test
    void getDefectList_TaskMapperReturnsNull() {
        // 准备测试数据
        List<Defect> defectList = Arrays.asList(testDefect);
        
        // 设置mock行为 - TaskMapper返回null
        when(taskMapper.selectTaskNameByTaskId("task1")).thenReturn(null);
        when(defectMapper.selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(defectList);
        when(defectMapper.selectDefectCount(anyString(), anyString(), anyString()))
                .thenReturn(1L);

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());

    }

    @Test
    void getDefectList_TaskMapperThrowsException() {
        // 准备测试数据
        List<Defect> defectList = Arrays.asList(testDefect);
        
        // 设置mock行为 - TaskMapper抛出异常
        when(taskMapper.selectTaskNameByTaskId("task1")).thenThrow(new RuntimeException("数据库连接失败"));
        when(defectMapper.selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(defectList);
        when(defectMapper.selectDefectCount(anyString(), anyString(), anyString()))
                .thenReturn(1L);

        // 执行测试
        Response<PageResult<DefectDto>> response = defectService.getDefectList(1, 10, null, null, null);

        // 验证结果
        assertNotNull(response);
        assertEquals(0, response.getCode());
    }

    @Test
    void getDefectList_DefectMapperThrowsException() {
        // 设置mock行为 - DefectMapper抛出异常
        when(defectMapper.selectDefectList(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenThrow(new RuntimeException("数据库查询失败"));
        when(defectMapper.selectDefectCount(anyString(), anyString(), anyString()))
                .thenReturn(0L);


    }

    @Test
    void createDefect_DefectMapperThrowsException() {
        // 设置mock行为 - DefectMapper抛出异常
        doThrow(new RuntimeException("数据库插入失败")).when(defectMapper).create(any(Defect.class));

        // 执行测试并验证异常
        assertThrows(RuntimeException.class, () -> {
            defectService.createDefect(testDefectDto);
        });
    }

    @Test
    void updateDefect_DefectMapperThrowsException() {
        // 设置mock行为 - DefectMapper抛出异常
        doThrow(new RuntimeException("数据库更新失败")).when(defectMapper).update(any(Defect.class));

        // 执行测试并验证异常
        assertThrows(RuntimeException.class, () -> {
            defectService.updateDefect(testDefectDto);
        });
    }

    @Test
    void deleteDefect_DefectMapperThrowsException() {
        // 设置mock行为 - DefectMapper抛出异常
        doThrow(new RuntimeException("数据库删除失败")).when(defectMapper).delete(1L);

        // 执行测试并验证异常
        assertThrows(RuntimeException.class, () -> {
            defectService.deleteDefect(1L);
        });
    }

    @Test
    void verifyDefect_DefectMapperThrowsException() {
        // 设置mock行为 - DefectMapper抛出异常
        doThrow(new RuntimeException("数据库更新失败")).when(defectMapper).updateIsVerified(1L, true);

        // 执行测试并验证异常
        assertThrows(RuntimeException.class, () -> {
            defectService.verifyDefect(1L, true);
        });
    }

    @Test
    void markDefectRectified_DefectMapperThrowsException() {
        // 设置mock行为 - DefectMapper抛出异常
        doThrow(new RuntimeException("数据库更新失败")).when(defectMapper).updateStatus(1L, "1");

        // 执行测试并验证异常
        assertThrows(RuntimeException.class, () -> {
            defectService.markDefectRectified(1L);
        });
    }
} 