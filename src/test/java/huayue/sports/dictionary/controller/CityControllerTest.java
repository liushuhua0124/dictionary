package huayue.sports.dictionary.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import huayue.sports.dictionary.domain.Area;
import huayue.sports.dictionary.domain.City;
import huayue.sports.dictionary.dto.CityDTO;
import huayue.sports.dictionary.dao.CityRepository;
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
 * 领域类 City(城市) 的单元测试代码
 * Created by Mac.Manon on 2018/05/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional// 使用@Transactional注解，确保每次测试后的数据将会被回滚
public class CityControllerTest {
    @Autowired
    CityRepository cityRepository;

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    // 默认初始化的一条城市数据
    private City c1;

    // 期望返回的数据
    private String expectData;

    // 实际调用返回的结果
    private String responseData;

    // 列表查询传参
    private CityDTO dto;

    // 期望获得的结果数量
    private Long expectResultCount;

    private Long id = 0L;

    // 使用JUnit的@Before注解可在测试开始前进行一些初始化的工作
    @Before
    public void setUp() throws JsonProcessingException {
        /**---------------------测试用例赋值开始---------------------**/
        //TODO 参考实际业务中新增数据所提供的参数，基于"最少字段和数据正确的原则"，将下面的null值换为测试参数
        c1 = new City();
        c1.setProvince(null);
        c1.setCode(null);
        c1.setName(null);
        c1.setSequence(null);
        Set<Area> areas = new HashSet<Area>(){{
            add(new Area(null, null, null, null, 0));
        }};
        c1.setAreas(areas);
        c1.setCreatorUserId(1);
        cityRepository.save(c1);
        /**---------------------测试用例赋值结束---------------------**/

        id=c1.getCityId();

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
        City c2 = new City();
        c2.setProvince(null);
        c2.setCode(null);
        c2.setName(null);
        c2.setSequence(null);
        Set<Area> areas = new HashSet<Area>(){{
            add(new Area(null, null, null, null, 0));
        }};
        c2.setAreas(areas);
        c2.setCreatorUserId(2);
        //提示：构造"修改过的数据"时需要给"最近修改时间"和"最近修改者"赋值
        //c2.setLastModificationTime(new Date());
        //c2.setLastModifierUserId(1);
        //提示：构造"非物理删除的数据"时需要给"已删除"、"删除时间"和"删除者"赋值
        //c2.setIsDeleted(true);
        //c2.setDeletionTime(new Date());
        //c2.setDeleterUserId(1);
        cityRepository.save(c2);
        /**---------------------测试用例赋值结束---------------------**/


        /**
         * 测试无搜索列表
         */

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Pageable pageable=new PageRequest(0,10, Sort.Direction.DESC,"cityId");
        // 期望获得的结果数量(默认有两个测试用例，所以值应为"2L"，如果新增了更多测试用例，请相应设定这个值)
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        Page<City> pagedata = cityRepository.findByIsDeletedFalse(pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/city/list")
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
        dto = new CityDTO();
        dto.setKeyword(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"cityId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        String keyword = dto.getKeyword().trim();

        // 直接通过dao层接口方法获得期望的数据
        Specification<City> specification=new Specification<City>() {
            @Override
            public Predicate toPredicate(Root<City> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate code=cb.like(cb.upper(root.get("code")), "%" + keyword.toUpperCase() + "%");
                Predicate name=cb.like(cb.upper(root.get("name")), "%" + keyword.toUpperCase() + "%");
                Predicate isDeleted=cb.equal(root.get("isDeleted").as(Boolean.class), false);
                Predicate p = cb.and(isDeleted,cb.or(code, name));

                    return p;
                }
            };
        pagedata = cityRepository.findAll(specification,pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/city/list")
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
        dto = new CityDTO();
        dto.setCode(null);
        dto.setName(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"cityId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        pagedata = cityRepository.findByCodeContainingAndNameContainingAllIgnoringCaseAndIsDeletedFalse(dto.getCode().trim(), dto.getName().trim(), pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();
        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/city/list")
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
     * 测试新增城市:Post请求/city/create
     * 测试修改城市:Post请求/city/modify
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {
        /**
         * 测试新增城市
         */

         //TODO 列出新增城市测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        City city = new City();
        city.setProvince(null);
        city.setCode(null);
        city.setName(null);
        city.setSequence(null);
        Set<Area> areas = new HashSet<Area>(){{
            add(new Area(null, null, null, null, 0));
        }};
        city.setAreas(areas);

        Long operator = null;
        id++;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/city/create")
                                .param("province",city.getProvince().toString())
                                .param("code",city.getCode())
                                .param("name",city.getName())
                                .param("sequence",city.getSequence().toString())
                                .param("areas",city.getAreas().toString())
                                .param("operator",operator.toString())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"city"
                .andExpect(content().string(containsString("city")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.city.cityId").value(id))
                .andExpect(jsonPath("$.city.province").value(city.getProvince()))
                .andExpect(jsonPath("$.city.code").value(city.getCode()))
                .andExpect(jsonPath("$.city.name").value(city.getName()))
                .andExpect(jsonPath("$.city.sequence").value(city.getSequence()))
                .andExpect(jsonPath("$.city.areas").value(city.getAreas()))
                .andExpect(jsonPath("$.city.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.city.creatorUserId").value(operator))
                .andExpect(jsonPath("$.city.lastModificationTime").isEmpty())
                .andExpect(jsonPath("$.city.lastModifierUserId").value(0))
                .andExpect(jsonPath("$.city.isDeleted").value(false))
                .andExpect(jsonPath("$.city.deletionTime").isEmpty())
                .andExpect(jsonPath("$.city.deleterUserId").value(0))
                .andReturn();


        /**
         * 测试修改城市
         */

         //TODO 列出修改城市测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        city = new City();
        city.setCityId(id);
        city.setProvince(null);
        city.setCode(null);
        city.setName(null);
        city.setSequence(null);
        areas = new HashSet<Area>(){{
            add(new Area(null, null, null, null, 0));
        }};
        city.setAreas(areas);

        Long operator2 = null;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/city/modify")
                        .param("cityId",id.toString())
                        .param("province",city.getProvince().toString())
                        .param("code",city.getCode())
                        .param("name",city.getName())
                        .param("sequence",city.getSequence().toString())
                        .param("areas",city.getAreas().toString())
                        .param("operator",operator2.toString())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"city"
                .andExpect(content().string(containsString("city")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.city.cityId").value(id))
                .andExpect(jsonPath("$.city.province").value(city.getProvince()))
                .andExpect(jsonPath("$.city.code").value(city.getCode()))
                .andExpect(jsonPath("$.city.name").value(city.getName()))
                .andExpect(jsonPath("$.city.sequence").value(city.getSequence()))
                .andExpect(jsonPath("$.city.areas").value(city.getAreas()))
                .andExpect(jsonPath("$.city.creationTime").isNotEmpty())
                .andExpect(jsonPath("$.city.creatorUserId").value(operator))
                .andExpect(jsonPath("$.city.lastModificationTime").isNotEmpty())
                .andExpect(jsonPath("$.city.lastModifierUserId").value(operator2))
                .andExpect(jsonPath("$.city.isDeleted").value(false))
                .andExpect(jsonPath("$.city.deletionTime").isEmpty())
                .andExpect(jsonPath("$.city.deleterUserId").value(0))
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
                        MockMvcRequestBuilders.get("/city/view/{id}",id)
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"city"
                .andExpect(content().string(containsString("city")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.city.cityId").value(id))
                .andExpect(jsonPath("$.city.province").value(c1.getProvince()))
                .andExpect(jsonPath("$.city.code").value(c1.getCode()))
                .andExpect(jsonPath("$.city.name").value(c1.getName()))
                .andExpect(jsonPath("$.city.sequence").value(c1.getSequence()))
                .andExpect(jsonPath("$.city.areas").value(c1.getAreas()))
                .andExpect(jsonPath("$.city.creationTime").value(c1.getCreationTime()))
                .andExpect(jsonPath("$.city.creatorUserId").value(c1.getCreatorUserId()))
                .andExpect(jsonPath("$.city.lastModificationTime").value(c1.getLastModificationTime()))
                .andExpect(jsonPath("$.city.lastModifierUserId").value(c1.getLastModifierUserId()))
                .andExpect(jsonPath("$.city.isDeleted").value(c1.getIsDeleted()))
                .andExpect(jsonPath("$.city.deletionTime").value(c1.getDeletionTime()))
                .andExpect(jsonPath("$.city.deleterUserId").value(c1.getDeleterUserId()))
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
                        MockMvcRequestBuilders.get("/city/delete/{id}",id)
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
        City city = cityRepository.findOne(id);
        Assert.assertNotNull(city);
        Assert.assertEquals("错误，正确结果应该是true",true,city.getIsDeleted());
    }

}
