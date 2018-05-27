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
import huayue.sports.dictionary.domain.City;
import huayue.sports.dictionary.dto.CityDTO;
import huayue.sports.dictionary.service.CityService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * 领域类 City(城市) 的控制器层
 * Created by Mac.Manon on 2018/05/27
 */
@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    CityService cityService;

    /**
     * 提交新增城市的请求
     * POST: /city/create
     * @param city
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/create")
    public Map<String, Object> create(@Validated(City.CheckCreate.class) City city, BindingResult result, HttpServletRequest request){
        return save(city, result, request);
    }

    /**
     * 提交修改城市的请求
     * POST: /city/modify
     * @param city
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Map<String, Object> modify(@Validated(City.CheckModify.class) City city, BindingResult result, HttpServletRequest request){
        return save(city, result, request);
    }

    /**
     * 保存城市数据
     * @param city
     * @param result
     * @param request
     * @return
     */
    private Map<String, Object> save(City city, BindingResult result, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();

        if (result.hasErrors()) {
            map.put("formErrors", result.getAllErrors());
            return map;
        }

        try {
            city = cityService.save(city, request);
            map.put("city",cityService.findOne(city));
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }

        return map;
    }

    /**
     * 显示一条城市的信息详情
     * GET: /city/view/9
     * @param city
     * @return
     */
    @GetMapping("/view/{cityId}")
    public Map<String, Object> findById(@PathVariable("cityId") City city){
        Map<String, Object> map = Maps.newHashMap();
        map.put("city",cityService.findOne(city));
        return map;
    }

    /**
     * 城市
     * GET: /city/list
     * @param pageable
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public Map<String, Object> list(@PageableDefault(sort = { "cityId" }, direction = Sort.Direction.DESC) Pageable pageable, CityDTO dto){
        Map<String, Object> map = Maps.newHashMap();

        Page<City> pagedata = cityService.getPageData(dto,pageable);
        map.put("dto",dto);
        map.put("pagedata",pagedata);

        return map;
    }

    /**
     * 删除城市
     * GET: /city/delete/9?operator=1
     * @param city
     * @param request
     * @return
     */
    @GetMapping("/delete/{cityId}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("cityId") City city, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();
        try {
            cityService.remove(city, request);
            map.put("result","success");
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }
        return map;
    }

}