package huayue.sports.dictionary.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import huayue.sports.dictionary.domain.IpAddress;
import huayue.sports.dictionary.dto.IpAddressDTO;
import huayue.sports.dictionary.dao.IpAddressRepository;
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
 * 领域类 IpAddress(IP地址) 的单元测试代码
 * Created by Mac.Manon on 2018/05/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional// 使用@Transactional注解，确保每次测试后的数据将会被回滚
public class IpAddressControllerTest {
    @Autowired
    IpAddressRepository ipAddressRepository;

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    // 默认初始化的一条IP地址数据
    private IpAddress i1;

    // 期望返回的数据
    private String expectData;

    // 实际调用返回的结果
    private String responseData;

    // 列表查询传参
    private IpAddressDTO dto;

    // 期望获得的结果数量
    private Long expectResultCount;

    private Long id = 0L;

    // 使用JUnit的@Before注解可在测试开始前进行一些初始化的工作
    @Before
    public void setUp() throws JsonProcessingException {
        /**---------------------测试用例赋值开始---------------------**/
        //TODO 参考实际业务中新增数据所提供的参数，基于"最少字段和数据正确的原则"，将下面的null值换为测试参数
        i1 = new IpAddress();
        i1.setStartIp(null);
        i1.setEndIp(null);
        i1.setStartNum(null);
        i1.setEndNum(null);
        i1.setLand(null);
        i1.setCountry(null);
        i1.setProvince(null);
        i1.setCity(null);
        i1.setArea(null);
        i1.setIsp(null);
        i1.setCode(null);
        i1.setLongitude(null);
        i1.setLatitude(null);
        ipAddressRepository.save(i1);
        /**---------------------测试用例赋值结束---------------------**/

        id=i1.getIpAddressId();

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
        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        IpAddress i2 = new IpAddress();
        i2.setStartIp(null);
        i2.setEndIp(null);
        i2.setStartNum(null);
        i2.setEndNum(null);
        i2.setLand(null);
        i2.setCountry(null);
        i2.setProvince(null);
        i2.setCity(null);
        i2.setArea(null);
        i2.setIsp(null);
        i2.setCode(null);
        i2.setLongitude(null);
        i2.setLatitude(null);
        ipAddressRepository.save(i2);
        /**---------------------测试用例赋值结束---------------------**/


        /**
         * 测试无搜索列表
         */

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Pageable pageable=new PageRequest(0,10, Sort.Direction.DESC,"ipAddressId");
        // 期望获得的结果数量(默认有两个测试用例，所以值应为"2L"，如果新增了更多测试用例，请相应设定这个值)
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        Page<IpAddress> pagedata = ipAddressRepository.findAll(pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/ipaddress/list")
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查返回的数据节点
                .andExpect(jsonPath("$.pagedata.totalElements").value(expectResultCount))
                .andExpect(jsonPath("$.dto.keyword").isEmpty())
                .andExpect(jsonPath("$.dto.startIp").isEmpty())
                .andExpect(jsonPath("$.dto.endIp").isEmpty())
                .andExpect(jsonPath("$.dto.isp").isEmpty())
                .andExpect(jsonPath("$.dto.code").isEmpty())
                .andExpect(jsonPath("$.dto.longitude").isEmpty())
                .andExpect(jsonPath("$.dto.latitude").isEmpty())
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
        dto = new IpAddressDTO();
        dto.setKeyword(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"ipAddressId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        String keyword = dto.getKeyword().trim();

        // 直接通过dao层接口方法获得期望的数据
        pagedata = ipAddressRepository.standSearch(keyword, keyword, keyword, keyword, keyword, keyword, pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/ipaddress/list")
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
                .andExpect(jsonPath("$.dto.startIp").isEmpty())
                .andExpect(jsonPath("$.dto.endIp").isEmpty())
                .andExpect(jsonPath("$.dto.isp").isEmpty())
                .andExpect(jsonPath("$.dto.code").isEmpty())
                .andExpect(jsonPath("$.dto.longitude").isEmpty())
                .andExpect(jsonPath("$.dto.latitude").isEmpty())
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
        dto = new IpAddressDTO();
        dto.setStartIp(null);
        dto.setEndIp(null);
        dto.setIsp(null);
        dto.setCode(null);
        dto.setLongitude(null);
        dto.setLatitude(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"ipAddressId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        pagedata = ipAddressRepository.advancedSearch(dto.getStartIp().trim(), dto.getEndIp().trim(), dto.getIsp().trim(), dto.getCode().trim(), dto.getLongitude().trim(), dto.getLatitude().trim(), pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();
        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/ipaddress/list")
                                .param("startIp",dto.getStartIp())
                                .param("endIp",dto.getEndIp())
                                .param("isp",dto.getIsp())
                                .param("code",dto.getCode())
                                .param("longitude",dto.getLongitude())
                                .param("latitude",dto.getLatitude())
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查返回的数据节点
                .andExpect(jsonPath("$.pagedata.totalElements").value(expectResultCount))
                .andExpect(jsonPath("$.dto.keyword").isEmpty())
                .andExpect(jsonPath("$.dto.startIp").value(dto.getStartIp()))
                .andExpect(jsonPath("$.dto.endIp").value(dto.getEndIp()))
                .andExpect(jsonPath("$.dto.isp").value(dto.getIsp()))
                .andExpect(jsonPath("$.dto.code").value(dto.getCode()))
                .andExpect(jsonPath("$.dto.longitude").value(dto.getLongitude()))
                .andExpect(jsonPath("$.dto.latitude").value(dto.getLatitude()))
                .andReturn();

        // 提取返回结果中的列表数据及翻页信息
        responseData = JsonPath.read(mvcResult.getResponse().getContentAsString(),"$.pagedata").toString();

        System.out.println("=============高级查询期望结果：" + expectData);
        System.out.println("=============高级查询实际返回：" + responseData);

        Assert.assertEquals("错误，高级查询返回数据与期望结果有差异",expectData,responseData);

    }


    /**
     * 测试新增IP地址:Post请求/ipAddress/create
     * 测试修改IP地址:Post请求/ipAddress/modify
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {
        /**
         * 测试新增IP地址
         */

         //TODO 列出新增IP地址测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        IpAddress ipAddress = new IpAddress();
        ipAddress.setStartIp(null);
        ipAddress.setEndIp(null);
        ipAddress.setStartNum(null);
        ipAddress.setEndNum(null);
        ipAddress.setLand(null);
        ipAddress.setCountry(null);
        ipAddress.setProvince(null);
        ipAddress.setCity(null);
        ipAddress.setArea(null);
        ipAddress.setIsp(null);
        ipAddress.setCode(null);
        ipAddress.setLongitude(null);
        ipAddress.setLatitude(null);

        id++;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/ipaddress/create")
                                .param("startIp",ipAddress.getStartIp())
                                .param("endIp",ipAddress.getEndIp())
                                .param("startNum",ipAddress.getStartNum().toString())
                                .param("endNum",ipAddress.getEndNum().toString())
                                .param("land",ipAddress.getLand().toString())
                                .param("country",ipAddress.getCountry().toString())
                                .param("province",ipAddress.getProvince().toString())
                                .param("city",ipAddress.getCity().toString())
                                .param("area",ipAddress.getArea().toString())
                                .param("isp",ipAddress.getIsp())
                                .param("code",ipAddress.getCode())
                                .param("longitude",ipAddress.getLongitude())
                                .param("latitude",ipAddress.getLatitude())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"ipAddress"
                .andExpect(content().string(containsString("ipAddress")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.ipAddress.ipAddressId").value(id))
                .andExpect(jsonPath("$.ipAddress.startIp").value(ipAddress.getStartIp()))
                .andExpect(jsonPath("$.ipAddress.endIp").value(ipAddress.getEndIp()))
                .andExpect(jsonPath("$.ipAddress.startNum").value(ipAddress.getStartNum()))
                .andExpect(jsonPath("$.ipAddress.endNum").value(ipAddress.getEndNum()))
                .andExpect(jsonPath("$.ipAddress.land").value(ipAddress.getLand()))
                .andExpect(jsonPath("$.ipAddress.country").value(ipAddress.getCountry()))
                .andExpect(jsonPath("$.ipAddress.province").value(ipAddress.getProvince()))
                .andExpect(jsonPath("$.ipAddress.city").value(ipAddress.getCity()))
                .andExpect(jsonPath("$.ipAddress.area").value(ipAddress.getArea()))
                .andExpect(jsonPath("$.ipAddress.isp").value(ipAddress.getIsp()))
                .andExpect(jsonPath("$.ipAddress.code").value(ipAddress.getCode()))
                .andExpect(jsonPath("$.ipAddress.longitude").value(ipAddress.getLongitude()))
                .andExpect(jsonPath("$.ipAddress.latitude").value(ipAddress.getLatitude()))
                .andExpect(jsonPath("$.ipAddress.creationTime").isNotEmpty())
                .andReturn();


        /**
         * 测试修改IP地址
         */

         //TODO 列出修改IP地址测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        ipAddress = new IpAddress();
        ipAddress.setIpAddressId(id);
        ipAddress.setStartIp(null);
        ipAddress.setEndIp(null);
        ipAddress.setStartNum(null);
        ipAddress.setEndNum(null);
        ipAddress.setLand(null);
        ipAddress.setCountry(null);
        ipAddress.setProvince(null);
        ipAddress.setCity(null);
        ipAddress.setArea(null);
        ipAddress.setIsp(null);
        ipAddress.setCode(null);
        ipAddress.setLongitude(null);
        ipAddress.setLatitude(null);
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/ipaddress/modify")
                        .param("ipAddressId",id.toString())
                        .param("startIp",ipAddress.getStartIp())
                        .param("endIp",ipAddress.getEndIp())
                        .param("startNum",ipAddress.getStartNum().toString())
                        .param("endNum",ipAddress.getEndNum().toString())
                        .param("land",ipAddress.getLand().toString())
                        .param("country",ipAddress.getCountry().toString())
                        .param("province",ipAddress.getProvince().toString())
                        .param("city",ipAddress.getCity().toString())
                        .param("area",ipAddress.getArea().toString())
                        .param("isp",ipAddress.getIsp())
                        .param("code",ipAddress.getCode())
                        .param("longitude",ipAddress.getLongitude())
                        .param("latitude",ipAddress.getLatitude())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"ipAddress"
                .andExpect(content().string(containsString("ipAddress")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.ipAddress.ipAddressId").value(id))
                .andExpect(jsonPath("$.ipAddress.startIp").value(ipAddress.getStartIp()))
                .andExpect(jsonPath("$.ipAddress.endIp").value(ipAddress.getEndIp()))
                .andExpect(jsonPath("$.ipAddress.startNum").value(ipAddress.getStartNum()))
                .andExpect(jsonPath("$.ipAddress.endNum").value(ipAddress.getEndNum()))
                .andExpect(jsonPath("$.ipAddress.land").value(ipAddress.getLand()))
                .andExpect(jsonPath("$.ipAddress.country").value(ipAddress.getCountry()))
                .andExpect(jsonPath("$.ipAddress.province").value(ipAddress.getProvince()))
                .andExpect(jsonPath("$.ipAddress.city").value(ipAddress.getCity()))
                .andExpect(jsonPath("$.ipAddress.area").value(ipAddress.getArea()))
                .andExpect(jsonPath("$.ipAddress.isp").value(ipAddress.getIsp()))
                .andExpect(jsonPath("$.ipAddress.code").value(ipAddress.getCode()))
                .andExpect(jsonPath("$.ipAddress.longitude").value(ipAddress.getLongitude()))
                .andExpect(jsonPath("$.ipAddress.latitude").value(ipAddress.getLatitude()))
                .andExpect(jsonPath("$.ipAddress.creationTime").isNotEmpty())
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
                        MockMvcRequestBuilders.get("/ipaddress/view/{id}",id)
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"ipAddress"
                .andExpect(content().string(containsString("ipAddress")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.ipAddress.ipAddressId").value(id))
                .andExpect(jsonPath("$.ipAddress.startIp").value(i1.getStartIp()))
                .andExpect(jsonPath("$.ipAddress.endIp").value(i1.getEndIp()))
                .andExpect(jsonPath("$.ipAddress.startNum").value(i1.getStartNum()))
                .andExpect(jsonPath("$.ipAddress.endNum").value(i1.getEndNum()))
                .andExpect(jsonPath("$.ipAddress.land").value(i1.getLand()))
                .andExpect(jsonPath("$.ipAddress.country").value(i1.getCountry()))
                .andExpect(jsonPath("$.ipAddress.province").value(i1.getProvince()))
                .andExpect(jsonPath("$.ipAddress.city").value(i1.getCity()))
                .andExpect(jsonPath("$.ipAddress.area").value(i1.getArea()))
                .andExpect(jsonPath("$.ipAddress.isp").value(i1.getIsp()))
                .andExpect(jsonPath("$.ipAddress.code").value(i1.getCode()))
                .andExpect(jsonPath("$.ipAddress.longitude").value(i1.getLongitude()))
                .andExpect(jsonPath("$.ipAddress.latitude").value(i1.getLatitude()))
                .andExpect(jsonPath("$.ipAddress.creationTime").value(i1.getCreationTime()))
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
                        MockMvcRequestBuilders.get("/ipaddress/delete/{id}",id)
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
        IpAddress ipAddress = ipAddressRepository.findOne(id);
        Assert.assertNull(ipAddress);
    }

}
