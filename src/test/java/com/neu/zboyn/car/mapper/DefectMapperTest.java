package com.neu.zboyn.car.mapper;

import com.neu.zboyn.car.model.Defect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class DefectMapperTest {

    @Autowired
    private DefectMapper defectMapper;

    private Defect testDefect;

    @BeforeEach
    void setUp() {
        // 创建测试用的缺陷对象
        testDefect = new Defect();
        testDefect.setTaskId("TASK001");
        testDefect.setDefectType("裂缝");
        testDefect.setDistanceFromOrigin("100m");
        testDefect.setImageUrls("image1.jpg,image2.jpg");
        testDefect.setIsVerified(false);
        testDefect.setSeverity("中等");
        testDefect.setDefectLength(new BigDecimal("5.5"));
        testDefect.setDefectArea(new BigDecimal("10.0"));
        testDefect.setDefectQuantity(1);
        testDefect.setRecommendedAction("修补");
        testDefect.setReportedTime(new Date());
        testDefect.setStatus("0");
    }

    @Test
    void testCreate() {
        // 测试创建缺陷
        defectMapper.create(testDefect);
        
        // 验证缺陷是否创建成功（通过查询验证）
        List<Defect> defects = defectMapper.selectDefectList(null, null, 0, 10);
        assertFalse(defects.isEmpty());
        
        Defect createdDefect = defects.stream()
                .filter(d -> d.getTaskId().equals("TASK001"))
                .findFirst()
                .orElse(null);
        
        assertNotNull(createdDefect);
        assertEquals("裂缝", createdDefect.getDefectType());
        assertEquals("中等", createdDefect.getSeverity());
        assertEquals("0", createdDefect.getStatus());
    }

    @Test
    void testSelectDefectList() {
        // 先创建测试数据
        defectMapper.create(testDefect);
        
        // 测试查询所有缺陷
        List<Defect> allDefects = defectMapper.selectDefectList(null, null, 0, 10);
        assertFalse(allDefects.isEmpty());
        
        // 测试按缺陷类型查询
        List<Defect> crackDefects = defectMapper.selectDefectList("裂缝", null, 0, 10);
        assertFalse(crackDefects.isEmpty());
        crackDefects.forEach(defect -> assertEquals("裂缝", defect.getDefectType()));
        
        // 测试按严重程度查询
        List<Defect> mediumSeverityDefects = defectMapper.selectDefectList(null, "中等", 0, 10);
        assertFalse(mediumSeverityDefects.isEmpty());
        mediumSeverityDefects.forEach(defect -> assertEquals("中等", defect.getSeverity()));
        
        // 测试分页查询
        List<Defect> pagedDefects = defectMapper.selectDefectList(null, null, 0, 1);
        assertTrue(pagedDefects.size() <= 1);
    }

    @Test
    void testSelectDefectCount() {
        // 先创建测试数据
        defectMapper.create(testDefect);
        
        // 创建第二个缺陷
        Defect secondDefect = new Defect();
        secondDefect.setTaskId("TASK002");
        secondDefect.setDefectType("裂缝");
        secondDefect.setDistanceFromOrigin("200m");
        secondDefect.setImageUrls("image3.jpg");
        secondDefect.setIsVerified(true);
        secondDefect.setSeverity("严重");
        secondDefect.setDefectLength(new BigDecimal("8.0"));
        secondDefect.setDefectArea(new BigDecimal("15.0"));
        secondDefect.setDefectQuantity(2);
        secondDefect.setRecommendedAction("更换");
        secondDefect.setReportedTime(new Date());
        secondDefect.setStatus("1");
        defectMapper.create(secondDefect);
        
        // 测试总计数
        long totalCount = defectMapper.selectDefectCount(null, null);
        assertTrue(totalCount >= 2);
        
        // 测试按缺陷类型计数
        long crackCount = defectMapper.selectDefectCount("裂缝", null);
        assertTrue(crackCount >= 2);
        
        // 测试按严重程度计数
        long mediumCount = defectMapper.selectDefectCount(null, "中等");
        assertTrue(mediumCount >= 1);
        
        // 测试组合条件计数
        long mediumCrackCount = defectMapper.selectDefectCount("裂缝", "中等");
        assertTrue(mediumCrackCount >= 1);
    }

    @Test
    void testUpdate() {
        // 先创建测试数据
        defectMapper.create(testDefect);
        
        // 获取创建的缺陷ID
        List<Defect> defects = defectMapper.selectDefectList(null, null, 0, 10);
        Defect createdDefect = defects.stream()
                .filter(d -> d.getTaskId().equals("TASK001"))
                .findFirst()
                .orElse(null);
        assertNotNull(createdDefect);
        
        // 更新缺陷信息
        Defect updateDefect = new Defect();
        updateDefect.setDefectId(createdDefect.getDefectId());
        updateDefect.setDefectType("坑洞");
        updateDefect.setSeverity("严重");
        updateDefect.setStatus("1");
        
        defectMapper.update(updateDefect);
        
        // 验证更新结果
        List<Defect> updatedDefects = defectMapper.selectDefectList(null, null, 0, 10);
        Defect updatedDefect = updatedDefects.stream()
                .filter(d -> d.getDefectId().equals(createdDefect.getDefectId()))
                .findFirst()
                .orElse(null);
        
        assertNotNull(updatedDefect);
        assertEquals("坑洞", updatedDefect.getDefectType());
        assertEquals("严重", updatedDefect.getSeverity());
        assertEquals("1", updatedDefect.getStatus());
    }

    @Test
    void testDelete() {
        // 先创建测试数据
        defectMapper.create(testDefect);
        
        // 获取创建的缺陷ID
        List<Defect> defects = defectMapper.selectDefectList(null, null, 0, 10);
        Defect createdDefect = defects.stream()
                .filter(d -> d.getTaskId().equals("TASK001"))
                .findFirst()
                .orElse(null);
        assertNotNull(createdDefect);
        
        // 删除缺陷
        defectMapper.delete(createdDefect.getDefectId());
        
        // 验证删除结果
        List<Defect> remainingDefects = defectMapper.selectDefectList(null, null, 0, 10);
        Defect deletedDefect = remainingDefects.stream()
                .filter(d -> d.getDefectId().equals(createdDefect.getDefectId()))
                .findFirst()
                .orElse(null);
        
        assertNull(deletedDefect);
    }

    @Test
    void testUpdateIsVerified() {
        // 先创建测试数据
        defectMapper.create(testDefect);
        
        // 获取创建的缺陷ID
        List<Defect> defects = defectMapper.selectDefectList(null, null, 0, 10);
        Defect createdDefect = defects.stream()
                .filter(d -> d.getTaskId().equals("TASK001"))
                .findFirst()
                .orElse(null);
        assertNotNull(createdDefect);
        
        // 更新验证状态
        defectMapper.updateIsVerified(createdDefect.getDefectId(), true);
        
        // 验证更新结果
        List<Defect> updatedDefects = defectMapper.selectDefectList(null, null, 0, 10);
        Defect updatedDefect = updatedDefects.stream()
                .filter(d -> d.getDefectId().equals(createdDefect.getDefectId()))
                .findFirst()
                .orElse(null);
        
        assertNotNull(updatedDefect);
        assertTrue(updatedDefect.getIsVerified());
    }

    @Test
    void testUpdateStatus() {
        // 先创建测试数据
        defectMapper.create(testDefect);
        
        // 获取创建的缺陷ID
        List<Defect> defects = defectMapper.selectDefectList(null, null, 0, 10);
        Defect createdDefect = defects.stream()
                .filter(d -> d.getTaskId().equals("TASK001"))
                .findFirst()
                .orElse(null);
        assertNotNull(createdDefect);
        
        // 更新状态
        defectMapper.updateStatus(createdDefect.getDefectId(), "1");
        
        // 验证更新结果
        List<Defect> updatedDefects = defectMapper.selectDefectList(null, null, 0, 10);
        Defect updatedDefect = updatedDefects.stream()
                .filter(d -> d.getDefectId().equals(createdDefect.getDefectId()))
                .findFirst()
                .orElse(null);
        
        assertNotNull(updatedDefect);
        assertEquals("1", updatedDefect.getStatus());
    }

    @Test
    void testCreateWithNullValues() {
        // 测试创建包含空值的缺陷
        Defect defectWithNulls = new Defect();
        defectWithNulls.setTaskId("TASK003");
        defectWithNulls.setDefectType("裂缝");
        defectWithNulls.setSeverity("轻微");
        defectWithNulls.setStatus("0");
        
        // 应该能够正常创建
        assertDoesNotThrow(() -> defectMapper.create(defectWithNulls));
        
        // 验证创建成功
        List<Defect> defects = defectMapper.selectDefectList(null, null, 0, 10);
        Defect createdDefect = defects.stream()
                .filter(d -> d.getTaskId().equals("TASK003"))
                .findFirst()
                .orElse(null);
        
        assertNotNull(createdDefect);
        assertEquals("裂缝", createdDefect.getDefectType());
        assertEquals("轻微", createdDefect.getSeverity());
    }

    @Test
    void testPagination() {
        // 创建多个测试数据
        for (int i = 1; i <= 5; i++) {
            Defect defect = new Defect();
            defect.setTaskId("TASK" + String.format("%03d", i));
            defect.setDefectType("裂缝");
            defect.setDistanceFromOrigin(i * 100 + "m");
            defect.setImageUrls("image" + i + ".jpg");
            defect.setIsVerified(false);
            defect.setSeverity("中等");
            defect.setDefectLength(new BigDecimal("5.0"));
            defect.setDefectArea(new BigDecimal("10.0"));
            defect.setDefectQuantity(1);
            defect.setRecommendedAction("修补");
            defect.setReportedTime(new Date());
            defect.setStatus("0");
            defectMapper.create(defect);
        }
        
        // 测试第一页（每页2条）
        List<Defect> firstPage = defectMapper.selectDefectList(null, null, 0, 2);
        assertEquals(2, firstPage.size());
        
        // 测试第二页
        List<Defect> secondPage = defectMapper.selectDefectList(null, null, 2, 2);
        assertEquals(2, secondPage.size());
        
        // 测试第三页
        List<Defect> thirdPage = defectMapper.selectDefectList(null, null, 4, 2);
        assertTrue(thirdPage.size() >= 1);
        
        // 验证分页数据不重复
        assertNotEquals(firstPage.get(0).getDefectId(), secondPage.get(0).getDefectId());
    }
} 