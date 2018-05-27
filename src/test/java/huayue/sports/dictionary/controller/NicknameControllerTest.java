package huayue.sports.dictionary.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import huayue.sports.dictionary.domain.Nickname;
import huayue.sports.dictionary.dto.NicknameDTO;
import huayue.sports.dictionary.dao.NicknameRepository;
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
 * 领域类 Nickname(昵称字典) 的单元测试代码
 * Created by Mac.Manon on 2018/05/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional// 使用@Transactional注解，确保每次测试后的数据将会被回滚
public class NicknameControllerTest {
    @Autowired
    NicknameRepository nicknameRepository;

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    // 默认初始化的一条昵称字典数据
    private Nickname n1;

    // 期望返回的数据
    private String expectData;

    // 实际调用返回的结果
    private String responseData;

    // 列表查询传参
    private NicknameDTO dto;

    // 期望获得的结果数量
    private Long expectResultCount;

    private Long id = 0L;

    // 使用JUnit的@Before注解可在测试开始前进行一些初始化的工作
    @Before
    public void setUp() throws JsonProcessingException {
        /**---------------------测试用例赋值开始---------------------**/
        //TODO 参考实际业务中新增数据所提供的参数，基于"最少字段和数据正确的原则"，将下面的null值换为测试参数
        n1 = new Nickname();
        n1.setName(null);
        nicknameRepository.save(n1);
        /**---------------------测试用例赋值结束---------------------**/

        id=n1.getNicknameId();

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
        Nickname n2 = new Nickname();
        n2.setName(null);
        nicknameRepository.save(n2);
        /**---------------------测试用例赋值结束---------------------**/


        /**
         * 测试无搜索列表
         */

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Pageable pageable=new PageRequest(0,10, Sort.Direction.DESC,"nicknameId");
        // 期望获得的结果数量(默认有两个测试用例，所以值应为"2L"，如果新增了更多测试用例，请相应设定这个值)
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        // 直接通过dao层接口方法获得期望的数据
        Page<Nickname> pagedata = nicknameRepository.findAll(pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/nickname/list")
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查返回的数据节点
                .andExpect(jsonPath("$.pagedata.totalElements").value(expectResultCount))
                .andExpect(jsonPath("$.dto.keyword").isEmpty())
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
        dto = new NicknameDTO();
        dto.setKeyword(null);

        pageable=new PageRequest(0,10, Sort.Direction.DESC,"nicknameId");

        // 期望获得的结果数量
        expectResultCount = null;
        /**---------------------测试用例赋值结束---------------------**/

        String keyword = dto.getKeyword().trim();

        // 直接通过dao层接口方法获得期望的数据
        pagedata = nicknameRepository.findByNameContainingAllIgnoringCase(keyword, pageable);
        expectData = JsonPath.read(Obj2Json(pagedata),"$").toString();

        mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/nickname/list")
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
                .andExpect(jsonPath("$.dto.name").isEmpty())
                .andReturn();

        // 提取返回结果中的列表数据及翻页信息
        responseData = JsonPath.read(mvcResult.getResponse().getContentAsString(),"$.pagedata").toString();

        System.out.println("=============标准查询期望结果：" + expectData);
        System.out.println("=============标准查询实际返回：" + responseData);

        Assert.assertEquals("错误，标准查询返回数据与期望结果有差异",expectData,responseData);




    }


    /**
     * 测试新增昵称字典:Post请求/nickname/create
     * 测试修改昵称字典:Post请求/nickname/modify
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {
        /**
         * 测试新增昵称字典
         */

         //TODO 列出新增昵称字典测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        Nickname nickname = new Nickname();
        nickname.setName(null);

        id++;
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/nickname/create")
                                .param("name",nickname.getName())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"nickname"
                .andExpect(content().string(containsString("nickname")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.nickname.nicknameId").value(id))
                .andExpect(jsonPath("$.nickname.name").value(nickname.getName()))
                .andReturn();


        /**
         * 测试修改昵称字典
         */

         //TODO 列出修改昵称字典测试用例清单

        /**---------------------测试用例赋值开始---------------------**/
        //TODO 将下面的null值换为测试参数
        nickname = new Nickname();
        nickname.setNicknameId(id);
        nickname.setName(null);
        /**---------------------测试用例赋值结束---------------------**/

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/nickname/modify")
                        .param("nicknameId",id.toString())
                        .param("name",nickname.getName())
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"nickname"
                .andExpect(content().string(containsString("nickname")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.nickname.nicknameId").value(id))
                .andExpect(jsonPath("$.nickname.name").value(nickname.getName()))
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
                        MockMvcRequestBuilders.get("/nickname/view/{id}",id)
                                .accept(MediaType.APPLICATION_JSON)
                )
                // 打印结果
                .andDo(print())
                // 检查状态码为200
                .andExpect(status().isOk())
                // 检查内容有"nickname"
                .andExpect(content().string(containsString("nickname")))
                // 检查返回的数据节点
                .andExpect(jsonPath("$.nickname.nicknameId").value(id))
                .andExpect(jsonPath("$.nickname.name").value(n1.getName()))
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
                        MockMvcRequestBuilders.get("/nickname/delete/{id}",id)
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
        Nickname nickname = nicknameRepository.findOne(id);
        Assert.assertNull(nickname);
    }

}
