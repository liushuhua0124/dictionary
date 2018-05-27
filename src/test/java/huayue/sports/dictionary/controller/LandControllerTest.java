package huayue.sports.dictionary.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import huayue.sports.dictionary.domain.Country;
import huayue.sports.dictionary.domain.Land;
import huayue.sports.dictionary.dto.LandDTO;
import huayue.sports.dictionary.dao.LandRepository;
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
 * 领域类 Land(洲) 的单元测试代码
 * Created by Mac.Manon on 2018/05/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional// 使用@Transactional注解，确保每次测试后的数据将会被回滚
public class LandControllerTest {
    @Autowired
    LandRepository landRepository;

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    // 默认初始化的一条洲数据
    private Land l1;

    // 期望返回的数据
    private String expectData;

    // 实际调用返回的结果
    private String responseData;

    // 列表查询传参
    private LandDTO dto;

    // 期望获得的结果数量
    private Long expectResultCount;

    private Long id = 0L;

    // 使用JUnit的@Before注解可在测试开始前进行一些初始化的工作
    @Before
    public void setUp() throws JsonProcessingException {
        /**---------------------测试用例赋值开始---------------------**/
        //TODO 参考实际业务中新增数据所提供的参数，基于"最少字段和数据正确的原则"，将下面的null值换为测试参数
        l1 = new Land();
        l1.setCode(null);
        l1.setName(null);
        l1.setSequence(null);
        Set<Country> countries = new HashSet<Country>(){{
            add(new Country(null, null, null, null, null, null, 0));
        }};
        l1.setCountries(countries);
        l1.setCreatorUserId(1);
        landRepository.save(l1);
        /**---------------------测试用例赋值结束---------------------**/

        id=l1.getLandId();

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
        Land l2 = new Land();
        l2.setCode(null);
        l2.setName(null);
        l2.setSequence(null);
        Set<Country> countries = new HashSet<Country>(){{
            add(new Country(null, null, null, null, null, null, 0));
        }};
        l2.setCountries(countries);
        l2.setCreatorUserId(2);
        //提示：构造"修改过的数据"时需要给"最近修改时间"和"最近修改者"赋值
        //l2.setLastModificationTime(new Date());
        //l2.setLastModifierUserId(1);
        //提示：构造"非物理删除的数据"时需要给"已删除"、"删除时间"和"删除者"赋值
        //l2.setIsDeleted(true);
        //l2.setDeletionTime(new Date());
        //l2.setDeleterUserId(1);
        landRepository.save(l2);
        /**---------------------测试用例赋值结束---------------------**/


        /**
         * 测试无搜索列表
         */

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Pageable pageable=new PageRequest(0,10, Sort.Direction.DESC,"landId");
        // 期望获得的结果数量(默认有两个测试用例，所以值应为"2L"，如果新增了更多测试用例，请相应设定这个值)
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        Page<Land> pagedata = landRepository.findByIsDeletedFalse(pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/land/list")
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查返回的数据节点
                .andExpect(jsonPath("$.pagedata.totalElements").value(expectResultCount))
                .andExpect(jsonPath("$.dto.keyword").isEmpty())
                .andExpect(jsonPath("$.dto.code").isEmpty())
                .andExpect(jsonPath("$.dto.name").isEmpty())
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
        dto = new LandDTO();
        dto.setKeyword(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"landId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        String keyword = dto.getKeyword().trim();

        // 直接通过dao层接口方法获得期望的数据
        Specification<Land> specification=new Specification<Land>() {
            @Override
            public Predicate toPredicate(Root<Land> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate code=cb.like(cb.upper(root.get("code")), "%" + keyword.toUpperCase() + "%");
                Predicate name=cb.like(cb.upper(root.get("name")), "%" + keyword.toUpperCase() + "%");
                Predicate isDeleted=cb.equal(root.get("isDeleted").as(Boolean.class), false);
                Predicate p = cb.and(isDeleted,cb.or(code, name));

                    return p;
                }
            };
        pagedata = landRepository.findAll(specification,pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/land/list")
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
                .andExpect(jsonPath("$.dto.code").isEmpty())
                .andExpect(jsonPath("$.dto.name").isEmpty())
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
        dto = new LandDTO();
        dto.setCode(null);
        dto.setName(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"landId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        pagedata = landRepository.findByCodeContainingAndNameContainingAllIgnoringCaseAndIsDeletedFalse(dto.getCode().trim(), dto.getName().trim(), pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();
        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/land/list")
                                .param("code",dto.getCode())
                                .param("name",dto.getName())
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查返回的数据节点
                .andExpect(jsonPath("$.pagedata.totalElements").value(expectResultCount))
                .andExpect(jsonPath("$.dto.keyword").isEmpty())
                .andExpect(jsonPath("$.dto.code").value(dto.getCode()))
                .andExpect(jsonPath("$.dto.name").value(dto.getName()))
                .andReturn();

        // 提取返回结果中的列表数据及翻页信息
        responseData = JsonPath.read(mvcResult.getResponse().getContentAsString(),"$.pagedata").toString();

        System.out.println("=============高级查询期望结果：" + expectData);
        System.out.println("=============高级查询实际返回：" + responseData);

        Assert.assertEquals("错误，高级查询返回数据与期望结果有差异",expectData,responseData);

    }


    /**
     * 测试新增洲:Post请求/land/create
     * 测试修改洲:Post请求/land/modify
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {
        /**
         * 测试新增洲
         */

         //TODO 列出新增洲测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Land land = new Land();
        land.setCode(null);
        land.setName(null);
        land.setSequence(null);
        Set<Country> countries = new HashSet<Country>(){{
            add(new Country(null, null, null, null, null, null, 0));
        }};
        land.setCountries(countries);

        Long operator = null;
        id++;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/land/create")
                                .param("code",land.getCode())
                                .param("name",land.getName())
                                .param("sequence",land.getSequence().toString())
                                .param("countries",land.getCountries().toString())
                                .param("operator",operator.toString())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"land"
                .andExpect(content().string(containsString("land")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.land.landId").value(id))
                .andExpect(jsonPath("$.land.code").value(land.getCode()))
                .andExpect(jsonPath("$.land.name").value(land.getName()))
                .andExpect(jsonPath("$.land.sequence").value(land.getSequence()))
                .andExpect(jsonPath("$.land.countries").value(land.getCountries()))
                .andExpect(jsonPath("$.land.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.land.creatorUserId").value(operator))
                .andExpect(jsonPath("$.land.lastModificationTime").isEmpty())
                .andExpect(jsonPath("$.land.lastModifierUserId").value(0))
                .andExpect(jsonPath("$.land.isDeleted").value(false))
                .andExpect(jsonPath("$.land.deletionTime").isEmpty())
                .andExpect(jsonPath("$.land.deleterUserId").value(0))
                .andReturn();


        /**
         * 测试修改洲
         */

         //TODO 列出修改洲测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        land = new Land();
        land.setLandId(id);
        land.setCode(null);
        land.setName(null);
        land.setSequence(null);
        countries = new HashSet<Country>(){{
            add(new Country(null, null, null, null, null, null, 0));
        }};
        land.setCountries(countries);

        Long operator2 = null;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/land/modify")
                        .param("landId",id.toString())
                        .param("code",land.getCode())
                        .param("name",land.getName())
                        .param("sequence",land.getSequence().toString())
                        .param("countries",land.getCountries().toString())
                        .param("operator",operator2.toString())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"land"
                .andExpect(content().string(containsString("land")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.land.landId").value(id))
                .andExpect(jsonPath("$.land.code").value(land.getCode()))
                .andExpect(jsonPath("$.land.name").value(land.getName()))
                .andExpect(jsonPath("$.land.sequence").value(land.getSequence()))
                .andExpect(jsonPath("$.land.countries").value(land.getCountries()))
                .andExpect(jsonPath("$.land.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.land.creatorUserId").value(operator))
                .andExpect(jsonPath("$.land.lastModificationTime").isNotEmpty())
                .andExpect(jsonPath("$.land.lastModifierUserId").value(operator2))
                .andExpect(jsonPath("$.land.isDeleted").value(false))
                .andExpect(jsonPath("$.land.deletionTime").isEmpty())
                .andExpect(jsonPath("$.land.deleterUserId").value(0))
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
                        MockMvcRequestBuilders.get("/land/view/{id}",id)
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"land"
                .andExpect(content().string(containsString("land")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.land.landId").value(id))
                .andExpect(jsonPath("$.land.code").value(l1.getCode()))
                .andExpect(jsonPath("$.land.name").value(l1.getName()))
                .andExpect(jsonPath("$.land.sequence").value(l1.getSequence()))
                .andExpect(jsonPath("$.land.countries").value(l1.getCountries()))
                .andExpect(jsonPath("$.land.creationTime").value(l1.getCreationTime()))
                .andExpect(jsonPath("$.land.creatorUserId").value(l1.getCreatorUserId()))
                .andExpect(jsonPath("$.land.lastModificationTime").value(l1.getLastModificationTime()))
                .andExpect(jsonPath("$.land.lastModifierUserId").value(l1.getLastModifierUserId()))
                .andExpect(jsonPath("$.land.isDeleted").value(l1.getIsDeleted()))
                .andExpect(jsonPath("$.land.deletionTime").value(l1.getDeletionTime()))
                .andExpect(jsonPath("$.land.deleterUserId").value(l1.getDeleterUserId()))
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
                        MockMvcRequestBuilders.get("/land/delete/{id}",id)
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
        Land land = landRepository.findOne(id);
        Assert.assertNotNull(land);
        Assert.assertEquals("错误，正确结果应该是true",true,land.getIsDeleted());
    }

}
