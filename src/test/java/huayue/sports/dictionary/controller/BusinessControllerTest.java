package huayue.sports.dictionary.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import huayue.sports.dictionary.domain.Business;
import huayue.sports.dictionary.dto.BusinessDTO;
import huayue.sports.dictionary.dao.BusinessRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.Locale;
import java.util.HashSet;
import java.util.Set;
import static huayue.sports.dictionary.DictionaryApplicationTests.Obj2Json;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 领域类 Business(业务) 的单元测试代码
 * Created by Mac.Manon on 2018/05/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional// 使用@Transactional注解，确保每次测试后的数据将会被回滚
public class BusinessControllerTest {
    @Autowired
    BusinessRepository businessRepository;

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    // 默认初始化的一条业务数据
    private Business b1;

    // 期望返回的数据
    private String expectData;

    // 实际调用返回的结果
    private String responseData;

    // 列表查询传参
    private BusinessDTO dto;

    // 期望获得的结果数量
    private Long expectResultCount;

    private Long id = 0L;

    // 使用JUnit的@Before注解可在测试开始前进行一些初始化的工作
    @Before
    public void setUp() throws JsonProcessingException {
        /**---------------------测试用例赋值开始---------------------**/
        //TODO 参考实际业务中新增数据所提供的参数，基于"最少字段和数据正确的原则"，将下面的null值换为测试参数
        b1 = new Business();
        b1.setBusinessCode(null);
        b1.setMemo(null);
        b1.setSendSms(null);
        b1.setCreatorUserId(1);
        businessRepository.save(b1);
        /**---------------------测试用例赋值结束---------------------**/

        id=b1.getBusinessId();

        // 获取mockMvc对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    /**
     * 测试列表
     * @throws Exception
     */
    @Test
    public void testList() throws Exception {

        //TODO 建议借鉴下面的测试用例赋值模版构造更多数据以充分测试"无搜索列表"、"标准查询"和"高级查询"的表现

        //提示：构建"新增数据"提示：根据新增数据时客户端实际能提供的参数，依据"最少字段和数据正确的原则"构建
        //提示：构建"修改过的数据"提示：根据修改数据时客户端实际能提供的参数构建
        //提示：可以构建"非物理删除的数据"
        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Business b2 = new Business();
        b2.setBusinessCode(null);
        b2.setMemo(null);
        b2.setSendSms(null);
        b2.setCreatorUserId(2);
        //提示：构造"修改过的数据"时需要给"最近修改时间"和"最近修改者"赋值
        //b2.setLastModificationTime(new Date());
        //b2.setLastModifierUserId(1);
        //提示：构造"非物理删除的数据"时需要给"已删除"、"删除时间"和"删除者"赋值
        //b2.setIsDeleted(true);
        //b2.setDeletionTime(new Date());
        //b2.setDeleterUserId(1);
        businessRepository.save(b2);
        /**---------------------测试用例赋值结束---------------------**/


        /**
         * 测试无搜索列表
         */

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Pageable pageable=new PageRequest(0,10, Sort.Direction.DESC,"businessId");
        // 期望获得的结果数量(默认有两个测试用例，所以值应为"2L"，如果新增了更多测试用例，请相应设定这个值)
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        Page<Business> pagedata = businessRepository.findByIsDeletedFalse(pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/business/list")
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查返回的数据节点
                .andExpect(jsonPath("$.pagedata.totalElements").value(expectResultCount))
                .andExpect(jsonPath("$.dto.keyword").isEmpty())
                .andExpect(jsonPath("$.dto.businessCode").isEmpty())
                .andExpect(jsonPath("$.dto.memo").isEmpty())
                .andReturn();

        // 提取返回结果中的列表数据及翻页信息
        responseData = JsonPath.read(mvcResult.getResponse().getContentAsString(),"$.pagedata").toString();

        System.out.println("=============无搜索列表期望结果：" + expectData);
        System.out.println("=============无搜索列表实际返回：" + responseData);

        Assert.assertEquals("错误，无搜索列表返回数据与期望结果有差异",expectData,responseData);




        /**
         * 测试标准查询
         */

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        dto = new BusinessDTO();
        dto.setKeyword(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"businessId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        String keyword = dto.getKeyword().trim();

        // 直接通过dao层接口方法获得期望的数据
        Specification<Business> specification=new Specification<Business>() {
            @Override
            public Predicate toPredicate(Root<Business> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate businessCode=cb.like(cb.upper(root.get("businessCode")), "%" + keyword.toUpperCase() + "%");
                Predicate memo=cb.like(cb.upper(root.get("memo")), "%" + keyword.toUpperCase() + "%");
                Predicate isDeleted=cb.equal(root.get("isDeleted").as(Boolean.class), false);
                Predicate p = cb.and(isDeleted,cb.or(businessCode, memo));

                    return p;
                }
            };
        pagedata = businessRepository.findAll(specification,pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/business/list")
                                .param("keyword",dto.getKeyword())
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查返回的数据节点
                .andExpect(jsonPath("$.pagedata.totalElements").value(expectResultCount))
                .andExpect(jsonPath("$.dto.keyword").value(dto.getKeyword()))
                .andExpect(jsonPath("$.dto.businessCode").isEmpty())
                .andExpect(jsonPath("$.dto.memo").isEmpty())
                .andReturn();

        // 提取返回结果中的列表数据及翻页信息
        responseData = JsonPath.read(mvcResult.getResponse().getContentAsString(),"$.pagedata").toString();

        System.out.println("=============标准查询期望结果：" + expectData);
        System.out.println("=============标准查询实际返回：" + responseData);

        Assert.assertEquals("错误，标准查询返回数据与期望结果有差异",expectData,responseData);




        /**
         * 测试高级查询
         */

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        dto = new BusinessDTO();
        dto.setBusinessCode(null);
        dto.setMemo(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"businessId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        pagedata = businessRepository.findByBusinessCodeContainingAndMemoContainingAllIgnoringCaseAndIsDeletedFalse(dto.getBusinessCode().trim(), dto.getMemo().trim(), pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();
        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/business/list")
                                .param("businessCode",dto.getBusinessCode())
                                .param("memo",dto.getMemo())
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查返回的数据节点
                .andExpect(jsonPath("$.pagedata.totalElements").value(expectResultCount))
                .andExpect(jsonPath("$.dto.keyword").isEmpty())
                .andExpect(jsonPath("$.dto.businessCode").value(dto.getBusinessCode()))
                .andExpect(jsonPath("$.dto.memo").value(dto.getMemo()))
                .andReturn();

        // 提取返回结果中的列表数据及翻页信息
        responseData = JsonPath.read(mvcResult.getResponse().getContentAsString(),"$.pagedata").toString();

        System.out.println("=============高级查询期望结果：" + expectData);
        System.out.println("=============高级查询实际返回：" + responseData);

        Assert.assertEquals("错误，高级查询返回数据与期望结果有差异",expectData,responseData);

    }


    /**
     * 测试新增业务:Post请求/business/create
     * 测试修改业务:Post请求/business/modify
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {
        /**
         * 测试新增业务
         */

         //TODO 列出新增业务测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Business business = new Business();
        business.setBusinessCode(null);
        business.setMemo(null);
        business.setSendSms(null);

        Long operator = null;
        id++;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/business/create")
                                .param("businessCode",business.getBusinessCode())
                                .param("memo",business.getMemo())
                                .param("sendSms",business.getSendSms().toString())
                                .param("operator",operator.toString())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"business"
                .andExpect(content().string(containsString("business")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.business.businessId").value(id))
                .andExpect(jsonPath("$.business.businessCode").value(business.getBusinessCode()))
                .andExpect(jsonPath("$.business.memo").value(business.getMemo()))
                .andExpect(jsonPath("$.business.sendSms").value(business.getSendSms()))
                .andExpect(jsonPath("$.business.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.business.creatorUserId").value(operator))
                .andExpect(jsonPath("$.business.lastModificationTime").isEmpty())
                .andExpect(jsonPath("$.business.lastModifierUserId").value(0))
                .andExpect(jsonPath("$.business.isDeleted").value(false))
                .andExpect(jsonPath("$.business.deletionTime").isEmpty())
                .andExpect(jsonPath("$.business.deleterUserId").value(0))
                .andReturn();


        /**
         * 测试修改业务
         */

         //TODO 列出修改业务测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        business = new Business();
        business.setBusinessId(id);
        business.setBusinessCode(null);
        business.setMemo(null);
        business.setSendSms(null);

        Long operator2 = null;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/business/modify")
                        .param("businessId",id.toString())
                        .param("businessCode",business.getBusinessCode())
                        .param("memo",business.getMemo())
                        .param("sendSms",business.getSendSms().toString())
                        .param("operator",operator2.toString())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"business"
                .andExpect(content().string(containsString("business")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.business.businessId").value(id))
                .andExpect(jsonPath("$.business.businessCode").value(business.getBusinessCode()))
                .andExpect(jsonPath("$.business.memo").value(business.getMemo()))
                .andExpect(jsonPath("$.business.sendSms").value(business.getSendSms()))
                .andExpect(jsonPath("$.business.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.business.creatorUserId").value(operator))
                .andExpect(jsonPath("$.business.lastModificationTime").isNotEmpty())
                .andExpect(jsonPath("$.business.lastModifierUserId").value(operator2))
                .andExpect(jsonPath("$.business.isDeleted").value(false))
                .andExpect(jsonPath("$.business.deletionTime").isEmpty())
                .andExpect(jsonPath("$.business.deleterUserId").value(0))
                .andReturn();
    }


    /**
     * 测试查询详情
     * @throws Exception
     */
    @Test
    public void testView() throws Exception
    {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/business/view/{id}",id)
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"business"
                .andExpect(content().string(containsString("business")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.business.businessId").value(id))
                .andExpect(jsonPath("$.business.businessCode").value(b1.getBusinessCode()))
                .andExpect(jsonPath("$.business.memo").value(b1.getMemo()))
                .andExpect(jsonPath("$.business.sendSms").value(b1.getSendSms()))
                .andExpect(jsonPath("$.business.creationTime").value(b1.getCreationTime()))
                .andExpect(jsonPath("$.business.creatorUserId").value(b1.getCreatorUserId()))
                .andExpect(jsonPath("$.business.lastModificationTime").value(b1.getLastModificationTime()))
                .andExpect(jsonPath("$.business.lastModifierUserId").value(b1.getLastModifierUserId()))
                .andExpect(jsonPath("$.business.isDeleted").value(b1.getIsDeleted()))
                .andExpect(jsonPath("$.business.deletionTime").value(b1.getDeletionTime()))
                .andExpect(jsonPath("$.business.deleterUserId").value(b1.getDeleterUserId()))
                .andReturn();
    }


    /**
     * 测试删除
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception
    {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/business/delete/{id}",id)
                                .param("operator","2")
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查返回的数据节点
                .andExpect(jsonPath("$.result").value("success"))
                .andReturn();

        // 验证数据库是否已经删除
        Business business = businessRepository.findOne(id);
        Assert.assertNotNull(business);
        Assert.assertEquals("错误，正确结果应该是true",true,business.getIsDeleted());
    }

}
