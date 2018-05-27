package huayue.sports.dictionary.web;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import huayue.sports.dictionary.domain.Country;
import huayue.sports.dictionary.dto.CountryDTO;
import huayue.sports.dictionary.service.CountryService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * 领域类 Country(国家) 的控制器层
 * Created by Mac.Manon on 2018/05/27
 */
@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountryService countryService;

    /**
     * 提交新增国家的请求
     * POST: /country/create
     * @param country
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/create")
    public Map<String, Object> create(@Validated(Country.CheckCreate.class) Country country, BindingResult result, HttpServletRequest request){
        return save(country, result, request);
    }

    /**
     * 提交修改国家的请求
     * POST: /country/modify
     * @param country
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Map<String, Object> modify(@Validated(Country.CheckModify.class) Country country, BindingResult result, HttpServletRequest request){
        return save(country, result, request);
    }

    /**
     * 保存国家数据
     * @param country
     * @param result
     * @param request
     * @return
     */
    private Map<String, Object> save(Country country, BindingResult result, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();

        if (result.hasErrors()) {
            map.put("formErrors", result.getAllErrors());
            return map;
        }

        try {
            country = countryService.save(country, request);
            map.put("country",countryService.findOne(country));
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }

        return map;
    }

    /**
     * 显示一条国家的信息详情
     * GET: /country/view/9
     * @param country
     * @return
     */
    @GetMapping("/view/{countryId}")
    public Map<String, Object> findById(@PathVariable("countryId") Country country){
        Map<String, Object> map = Maps.newHashMap();
        map.put("country",countryService.findOne(country));
        return map;
    }

    /**
     * 国家
     * GET: /country/list
     * @param pageable
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public Map<String, Object> list(@PageableDefault(sort = { "countryId" }, direction = Sort.Direction.DESC) Pageable pageable, CountryDTO dto){
        Map<String, Object> map = Maps.newHashMap();

        Page<Country> pagedata = countryService.getPageData(dto,pageable);
        map.put("dto",dto);
        map.put("pagedata",pagedata);

        return map;
    }

    /**
     * 删除国家
     * GET: /country/delete/9?operator=1
     * @param country
     * @param request
     * @return
     */
    @GetMapping("/delete/{countryId}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("countryId") Country country, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();
        try {
            countryService.remove(country, request);
            map.put("result","success");
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }
        return map;
    }

}