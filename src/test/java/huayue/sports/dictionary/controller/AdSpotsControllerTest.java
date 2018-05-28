package huayue.sports.dictionary.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import huayue.sports.dictionary.domain.AdSpots;
import huayue.sports.dictionary.dto.AdSpotsDTO;
import huayue.sports.dictionary.dao.AdSpotsRepository;
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
 * 领域类 AdSpots(广告位) 的单元测试代码
 * Created by Mac.Manon on 2018/05/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional// 使用@Transactional注解，确保每次测试后的数据将会被回滚
public class AdSpotsControllerTest {
    @Autowired
    AdSpotsRepository adSpotsRepository;

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    // 默认初始化的一条广告位数据
    private AdSpots a1;

    // 期望返回的数据
    private String expectData;

    // 实际调用返回的结果
    private String responseData;

    // 列表查询传参
    private AdSpotsDTO dto;

    // 期望获得的结果数量
    private Long expectResultCount;

    private Long id = 0L;

    // 使用JUnit的@Before注解可在测试开始前进行一些初始化的工作
    @Before
    public void setUp() throws JsonProcessingException {
        /**---------------------测试用例赋值开始---------------------**/
        //TODO 参考实际业务中新增数据所提供的参数，基于"最少字段和数据正确的原则"，将下面的null值换为测试参数
        a1 = new AdSpots();
        a1.setPlaceCode(null);
        a1.setMemo(null);
        a1.setCreatorUserId(1);
        adSpotsRepository.save(a1);
        /**---------------------测试用例赋值结束---------------------**/

        id=a1.getAdSpotsId();

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
        AdSpots a2 = new AdSpots();
        a2.setPlaceCode(null);
        a2.setMemo(null);
        a2.setCreatorUserId(2);
        //提示：构造"修改过的数据"时需要给"最近修改时间"和"最近修改者"赋值
        //a2.setLastModificationTime(new Date());
        //a2.setLastModifierUserId(1);
        //提示：构造"非物理删除的数据"时需要给"已删除"、"删除时间"和"删除者"赋值
        //a2.setIsDeleted(true);
        //a2.setDeletionTime(new Date());
        //a2.setDeleterUserId(1);
        adSpotsRepository.save(a2);
        /**---------------------测试用例赋值结束---------------------**/


        /**
         * 测试无搜索列表
         */

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Pageable pageable=new PageRequest(0,10, Sort.Direction.DESC,"adSpotsId");
        // 期望获得的结果数量(默认有两个测试用例，所以值应为"2L"，如果新增了更多测试用例，请相应设定这个值)
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        Page<AdSpots> pagedata = adSpotsRepository.findByIsDeletedFalse(pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/adspots/list")
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查返回的数据节点
                .andExpect(jsonPath("$.pagedata.totalElements").value(expectResultCount))
                .andExpect(jsonPath("$.dto.keyword").isEmpty())
                .andExpect(jsonPath("$.dto.placeCode").isEmpty())
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
        dto = new AdSpotsDTO();
        dto.setKeyword(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"adSpotsId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        String keyword = dto.getKeyword().trim();

        // 直接通过dao层接口方法获得期望的数据
        Specification<AdSpots> specification=new Specification<AdSpots>() {
            @Override
            public Predicate toPredicate(Root<AdSpots> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate placeCode=cb.like(cb.upper(root.get("placeCode")), "%" + keyword.toUpperCase() + "%");
                Predicate memo=cb.like(cb.upper(root.get("memo")), "%" + keyword.toUpperCase() + "%");
                Predicate isDeleted=cb.equal(root.get("isDeleted").as(Boolean.class), false);
                Predicate p = cb.and(isDeleted,cb.or(placeCode, memo));

                    return p;
                }
            };
        pagedata = adSpotsRepository.findAll(specification,pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/adspots/list")
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
                .andExpect(jsonPath("$.dto.placeCode").isEmpty())
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
        dto = new AdSpotsDTO();
        dto.setPlaceCode(null);
        dto.setMemo(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"adSpotsId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        pagedata = adSpotsRepository.findByPlaceCodeContainingAndMemoContainingAllIgnoringCaseAndIsDeletedFalse(dto.getPlaceCode().trim(), dto.getMemo().trim(), pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();
        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/adspots/list")
                                .param("placeCode",dto.getPlaceCode())
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
                .andExpect(jsonPath("$.dto.placeCode").value(dto.getPlaceCode()))
                .andExpect(jsonPath("$.dto.memo").value(dto.getMemo()))
                .andReturn();

        // 提取返回结果中的列表数据及翻页信息
        responseData = JsonPath.read(mvcResult.getResponse().getContentAsString(),"$.pagedata").toString();

        System.out.println("=============高级查询期望结果：" + expectData);
        System.out.println("=============高级查询实际返回：" + responseData);

        Assert.assertEquals("错误，高级查询返回数据与期望结果有差异",expectData,responseData);

    }


    /**
     * 测试新增广告位:Post请求/adSpots/create
     * 测试修改广告位:Post请求/adSpots/modify
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {
        /**
         * 测试新增广告位
         */

         //TODO 列出新增广告位测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        AdSpots adSpots = new AdSpots();
        adSpots.setPlaceCode(null);
        adSpots.setMemo(null);

        Long operator = null;
        id++;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/adspots/create")
                                .param("placeCode",adSpots.getPlaceCode())
                                .param("memo",adSpots.getMemo())
                                .param("operator",operator.toString())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"adSpots"
                .andExpect(content().string(containsString("adSpots")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.adSpots.adSpotsId").value(id))
                .andExpect(jsonPath("$.adSpots.placeCode").value(adSpots.getPlaceCode()))
                .andExpect(jsonPath("$.adSpots.memo").value(adSpots.getMemo()))
                .andExpect(jsonPath("$.adSpots.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.adSpots.creatorUserId").value(operator))
                .andExpect(jsonPath("$.adSpots.lastModificationTime").isEmpty())
                .andExpect(jsonPath("$.adSpots.lastModifierUserId").value(0))
                .andExpect(jsonPath("$.adSpots.isDeleted").value(false))
                .andExpect(jsonPath("$.adSpots.deletionTime").isEmpty())
                .andExpect(jsonPath("$.adSpots.deleterUserId").value(0))
                .andReturn();


        /**
         * 测试修改广告位
         */

         //TODO 列出修改广告位测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        adSpots = new AdSpots();
        adSpots.setAdSpotsId(id);
        adSpots.setPlaceCode(null);
        adSpots.setMemo(null);

        Long operator2 = null;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/adspots/modify")
                        .param("adSpotsId",id.toString())
                        .param("placeCode",adSpots.getPlaceCode())
                        .param("memo",adSpots.getMemo())
                        .param("operator",operator2.toString())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"adSpots"
                .andExpect(content().string(containsString("adSpots")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.adSpots.adSpotsId").value(id))
                .andExpect(jsonPath("$.adSpots.placeCode").value(adSpots.getPlaceCode()))
                .andExpect(jsonPath("$.adSpots.memo").value(adSpots.getMemo()))
                .andExpect(jsonPath("$.adSpots.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.adSpots.creatorUserId").value(operator))
                .andExpect(jsonPath("$.adSpots.lastModificationTime").isNotEmpty())
                .andExpect(jsonPath("$.adSpots.lastModifierUserId").value(operator2))
                .andExpect(jsonPath("$.adSpots.isDeleted").value(false))
                .andExpect(jsonPath("$.adSpots.deletionTime").isEmpty())
                .andExpect(jsonPath("$.adSpots.deleterUserId").value(0))
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
                        MockMvcRequestBuilders.get("/adspots/view/{id}",id)
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"adSpots"
                .andExpect(content().string(containsString("adSpots")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.adSpots.adSpotsId").value(id))
                .andExpect(jsonPath("$.adSpots.placeCode").value(a1.getPlaceCode()))
                .andExpect(jsonPath("$.adSpots.memo").value(a1.getMemo()))
                .andExpect(jsonPath("$.adSpots.creationTime").value(a1.getCreationTime()))
                .andExpect(jsonPath("$.adSpots.creatorUserId").value(a1.getCreatorUserId()))
                .andExpect(jsonPath("$.adSpots.lastModificationTime").value(a1.getLastModificationTime()))
                .andExpect(jsonPath("$.adSpots.lastModifierUserId").value(a1.getLastModifierUserId()))
                .andExpect(jsonPath("$.adSpots.isDeleted").value(a1.getIsDeleted()))
                .andExpect(jsonPath("$.adSpots.deletionTime").value(a1.getDeletionTime()))
                .andExpect(jsonPath("$.adSpots.deleterUserId").value(a1.getDeleterUserId()))
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
                        MockMvcRequestBuilders.get("/adspots/delete/{id}",id)
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
        AdSpots adSpots = adSpotsRepository.findOne(id);
        Assert.assertNotNull(adSpots);
        Assert.assertEquals("错误，正确结果应该是true",true,adSpots.getIsDeleted());
    }

}
