package huayue.sports.dictionary.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import huayue.sports.dictionary.domain.City;
import huayue.sports.dictionary.domain.Province;
import huayue.sports.dictionary.dto.ProvinceDTO;
import huayue.sports.dictionary.dao.ProvinceRepository;
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
 * 领域类 Province(省份) 的单元测试代码
 * Created by Mac.Manon on 2018/05/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional// 使用@Transactional注解，确保每次测试后的数据将会被回滚
public class ProvinceControllerTest {
    @Autowired
    ProvinceRepository provinceRepository;

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    // 默认初始化的一条省份数据
    private Province p1;

    // 期望返回的数据
    private String expectData;

    // 实际调用返回的结果
    private String responseData;

    // 列表查询传参
    private ProvinceDTO dto;

    // 期望获得的结果数量
    private Long expectResultCount;

    private Long id = 0L;

    // 使用JUnit的@Before注解可在测试开始前进行一些初始化的工作
    @Before
    public void setUp() throws JsonProcessingException {
        /**---------------------测试用例赋值开始---------------------**/
        //TODO 参考实际业务中新增数据所提供的参数，基于"最少字段和数据正确的原则"，将下面的null值换为测试参数
        p1 = new Province();
        p1.setCountry(null);
        p1.setCode(null);
        p1.setName(null);
        p1.setSequence(null);
        Set<City> cities = new HashSet<City>(){{
            add(new City(null, null, null, null, null, 0));
        }};
        p1.setCities(cities);
        p1.setCreatorUserId(1);
        provinceRepository.save(p1);
        /**---------------------测试用例赋值结束---------------------**/

        id=p1.getProvinceId();

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
        Province p2 = new Province();
        p2.setCountry(null);
        p2.setCode(null);
        p2.setName(null);
        p2.setSequence(null);
        Set<City> cities = new HashSet<City>(){{
            add(new City(null, null, null, null, null, 0));
        }};
        p2.setCities(cities);
        p2.setCreatorUserId(2);
        //提示：构造"修改过的数据"时需要给"最近修改时间"和"最近修改者"赋值
        //p2.setLastModificationTime(new Date());
        //p2.setLastModifierUserId(1);
        //提示：构造"非物理删除的数据"时需要给"已删除"、"删除时间"和"删除者"赋值
        //p2.setIsDeleted(true);
        //p2.setDeletionTime(new Date());
        //p2.setDeleterUserId(1);
        provinceRepository.save(p2);
        /**---------------------测试用例赋值结束---------------------**/


        /**
         * 测试无搜索列表
         */

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Pageable pageable=new PageRequest(0,10, Sort.Direction.DESC,"provinceId");
        // 期望获得的结果数量(默认有两个测试用例，所以值应为"2L"，如果新增了更多测试用例，请相应设定这个值)
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        Page<Province> pagedata = provinceRepository.findByIsDeletedFalse(pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/province/list")
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
        dto = new ProvinceDTO();
        dto.setKeyword(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"provinceId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        String keyword = dto.getKeyword().trim();

        // 直接通过dao层接口方法获得期望的数据
        Specification<Province> specification=new Specification<Province>() {
            @Override
            public Predicate toPredicate(Root<Province> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate code=cb.like(cb.upper(root.get("code")), "%" + keyword.toUpperCase() + "%");
                Predicate name=cb.like(cb.upper(root.get("name")), "%" + keyword.toUpperCase() + "%");
                Predicate isDeleted=cb.equal(root.get("isDeleted").as(Boolean.class), false);
                Predicate p = cb.and(isDeleted,cb.or(code, name));

                    return p;
                }
            };
        pagedata = provinceRepository.findAll(specification,pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/province/list")
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
        dto = new ProvinceDTO();
        dto.setCode(null);
        dto.setName(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"provinceId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        pagedata = provinceRepository.findByCodeContainingAndNameContainingAllIgnoringCaseAndIsDeletedFalse(dto.getCode().trim(), dto.getName().trim(), pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();
        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/province/list")
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
     * 测试新增省份:Post请求/province/create
     * 测试修改省份:Post请求/province/modify
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {
        /**
         * 测试新增省份
         */

         //TODO 列出新增省份测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Province province = new Province();
        province.setCountry(null);
        province.setCode(null);
        province.setName(null);
        province.setSequence(null);
        Set<City> cities = new HashSet<City>(){{
            add(new City(null, null, null, null, null, 0));
        }};
        province.setCities(cities);

        Long operator = null;
        id++;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/province/create")
                                .param("country",province.getCountry().toString())
                                .param("code",province.getCode())
                                .param("name",province.getName())
                                .param("sequence",province.getSequence().toString())
                                .param("cities",province.getCities().toString())
                                .param("operator",operator.toString())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"province"
                .andExpect(content().string(containsString("province")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.province.provinceId").value(id))
                .andExpect(jsonPath("$.province.country").value(province.getCountry()))
                .andExpect(jsonPath("$.province.code").value(province.getCode()))
                .andExpect(jsonPath("$.province.name").value(province.getName()))
                .andExpect(jsonPath("$.province.sequence").value(province.getSequence()))
                .andExpect(jsonPath("$.province.cities").value(province.getCities()))
                .andExpect(jsonPath("$.province.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.province.creatorUserId").value(operator))
                .andExpect(jsonPath("$.province.lastModificationTime").isEmpty())
                .andExpect(jsonPath("$.province.lastModifierUserId").value(0))
                .andExpect(jsonPath("$.province.isDeleted").value(false))
                .andExpect(jsonPath("$.province.deletionTime").isEmpty())
                .andExpect(jsonPath("$.province.deleterUserId").value(0))
                .andReturn();


        /**
         * 测试修改省份
         */

         //TODO 列出修改省份测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        province = new Province();
        province.setProvinceId(id);
        province.setCountry(null);
        province.setCode(null);
        province.setName(null);
        province.setSequence(null);
        cities = new HashSet<City>(){{
            add(new City(null, null, null, null, null, 0));
        }};
        province.setCities(cities);

        Long operator2 = null;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/province/modify")
                        .param("provinceId",id.toString())
                        .param("country",province.getCountry().toString())
                        .param("code",province.getCode())
                        .param("name",province.getName())
                        .param("sequence",province.getSequence().toString())
                        .param("cities",province.getCities().toString())
                        .param("operator",operator2.toString())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"province"
                .andExpect(content().string(containsString("province")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.province.provinceId").value(id))
                .andExpect(jsonPath("$.province.country").value(province.getCountry()))
                .andExpect(jsonPath("$.province.code").value(province.getCode()))
                .andExpect(jsonPath("$.province.name").value(province.getName()))
                .andExpect(jsonPath("$.province.sequence").value(province.getSequence()))
                .andExpect(jsonPath("$.province.cities").value(province.getCities()))
                .andExpect(jsonPath("$.province.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.province.creatorUserId").value(operator))
                .andExpect(jsonPath("$.province.lastModificationTime").isNotEmpty())
                .andExpect(jsonPath("$.province.lastModifierUserId").value(operator2))
                .andExpect(jsonPath("$.province.isDeleted").value(false))
                .andExpect(jsonPath("$.province.deletionTime").isEmpty())
                .andExpect(jsonPath("$.province.deleterUserId").value(0))
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
                        MockMvcRequestBuilders.get("/province/view/{id}",id)
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"province"
                .andExpect(content().string(containsString("province")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.province.provinceId").value(id))
                .andExpect(jsonPath("$.province.country").value(p1.getCountry()))
                .andExpect(jsonPath("$.province.code").value(p1.getCode()))
                .andExpect(jsonPath("$.province.name").value(p1.getName()))
                .andExpect(jsonPath("$.province.sequence").value(p1.getSequence()))
                .andExpect(jsonPath("$.province.cities").value(p1.getCities()))
                .andExpect(jsonPath("$.province.creationTime").value(p1.getCreationTime()))
                .andExpect(jsonPath("$.province.creatorUserId").value(p1.getCreatorUserId()))
                .andExpect(jsonPath("$.province.lastModificationTime").value(p1.getLastModificationTime()))
                .andExpect(jsonPath("$.province.lastModifierUserId").value(p1.getLastModifierUserId()))
                .andExpect(jsonPath("$.province.isDeleted").value(p1.getIsDeleted()))
                .andExpect(jsonPath("$.province.deletionTime").value(p1.getDeletionTime()))
                .andExpect(jsonPath("$.province.deleterUserId").value(p1.getDeleterUserId()))
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
                        MockMvcRequestBuilders.get("/province/delete/{id}",id)
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
        Province province = provinceRepository.findOne(id);
        Assert.assertNotNull(province);
        Assert.assertEquals("错误，正确结果应该是true",true,province.getIsDeleted());
    }

}
