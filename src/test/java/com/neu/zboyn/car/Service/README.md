# Service层测试类说明

本目录包含了根据Controller测试类模式编写的Service层JUnit测试类。

## 测试类列表

### 1. LoginServiceTest.java
- **测试目标**: LoginService接口的所有方法
- **主要测试场景**:
  - 用户登录成功/失败（用户名不存在、密码错误、账号禁用）
  - Token生成失败的处理
  - 根据Token获取用户信息
  - 用户注册功能
- **Mock对象**: UserMapper, BCryptUtil, JWTUtil (静态方法)
- **测试方法数**: 12个

### 2. TaskServiceTest.java
- **测试目标**: TaskService接口的所有方法
- **主要测试场景**:
  - 任务列表查询（带分页、带过滤条件、空结果）
  - 根据ID获取任务详情
  - 创建、更新、删除任务
- **Mock对象**: TaskMapper
- **测试方法数**: 12个

### 3. UserServiceTest.java
- **测试目标**: UserService接口的所有方法
- **主要测试场景**:
  - 用户列表查询（带分页、带过滤条件）
  - 创建、更新、删除用户
  - 密码重置（默认密码、自定义密码）
  - 用户角色变更
  - 根据创建者ID获取用户
- **Mock对象**: UserManageMapper, BCryptUtil, SysConfigService
- **测试方法数**: 13个

### 4. RoleServiceTest.java
- **测试目标**: RoleService接口的所有方法
- **主要测试场景**:
  - 角色列表查询（带分页、带过滤条件）
  - 根据ID获取角色详情
  - 创建、更新、删除角色
  - 获取角色名称列表
  - 变更用户角色
- **Mock对象**: RoleMapper
- **测试方法数**: 13个

### 5. DepartmentServiceTest.java
- **测试目标**: DepartmentService接口的所有方法
- **主要测试场景**:
  - 部门信息查询（全部、带过滤条件）
  - 插入、更新、删除部门
- **Mock对象**: DepartmentMapper
- **测试方法数**: 10个

### 6. DefectServiceTest.java
- **测试目标**: DefectService接口的所有方法
- **主要测试场景**:
  - 缺陷列表查询（带分页、带过滤条件）
  - 创建、更新、删除缺陷
  - 缺陷验证和标记已整改
  - DTO与实体转换测试
- **Mock对象**: DefectMapper
- **测试方法数**: 12个

## 测试特点

### 1. 测试结构
- 使用`@ExtendWith(MockitoExtension.class)`注解
- 使用`@Mock`注解模拟依赖对象
- 使用`@InjectMocks`注解注入被测试的Service实现类
- 使用`@BeforeEach`设置测试数据

### 2. 测试模式
- **Given-When-Then**模式：准备数据、执行测试、验证结果
- 每个测试方法都有明确的成功和失败场景
- 验证方法调用和返回结果
- 使用`argThat`验证传递给Mock对象的参数

### 3. 测试覆盖
- **正常流程测试**: 验证业务逻辑的正确执行
- **异常流程测试**: 验证错误情况的处理
- **边界条件测试**: 验证空值、默认值等边界情况
- **数据转换测试**: 验证DTO与实体之间的转换逻辑

### 4. Mock策略
- 模拟所有外部依赖（Mapper、工具类等）
- 使用`when().thenReturn()`设置Mock行为
- 使用`verify()`验证方法调用
- 对于静态方法使用`MockedStatic`

## 运行测试

```bash
# 运行所有Service测试
mvn test -Dtest="*ServiceTest"

# 运行特定Service测试
mvn test -Dtest="LoginServiceTest"
mvn test -Dtest="TaskServiceTest"
```

## 注意事项

1. **类型匹配**: 确保测试数据与实体类的字段类型匹配
2. **Mock配置**: 正确配置Mock对象的行为和返回值
3. **验证完整性**: 不仅验证返回结果，还要验证方法调用
4. **测试独立性**: 每个测试方法应该独立运行，不依赖其他测试

## 扩展建议

1. **集成测试**: 可以添加集成测试来验证Service与真实数据库的交互
2. **性能测试**: 对于复杂查询可以添加性能测试
3. **并发测试**: 对于涉及事务的方法可以添加并发测试
4. **异常测试**: 可以添加更多异常场景的测试 