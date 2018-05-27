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
import huayue.sports.dictionary.domain.Province;
import huayue.sports.dictionary.dto.ProvinceDTO;
import huayue.sports.dictionary.service.ProvinceService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * 领域类 Province(省份) 的控制器层
 * Created by Mac.Manon on 2018/05/27
 */
@RestController
@RequestMapping("/province")
public class ProvinceController {

    @Autowired
    ProvinceService provinceService;

    /**
     * 提交新增省份的请求
     * POST: /province/create
     * @param province
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/create")
    public Map<String, Object> create(@Validated(Province.CheckCreate.class) Province province, BindingResult result, HttpServletRequest request){
        return save(province, result, request);
    }

    /**
     * 提交修改省份的请求
     * POST: /province/modify
     * @param province
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Map<String, Object> modify(@Validated(Province.CheckModify.class) Province province, BindingResult result, HttpServletRequest request){
        return save(province, result, request);
    }

    /**
     * 保存省份数据
     * @param province
     * @param result
     * @param request
     * @return
     */
    private Map<String, Object> save(Province province, BindingResult result, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();

        if (result.hasErrors()) {
            map.put("formErrors", result.getAllErrors());
            return map;
        }

        try {
            province = provinceService.save(province, request);
            map.put("province",provinceService.findOne(province));
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }

        return map;
    }

    /**
     * 显示一条省份的信息详情
     * GET: /province/view/9
     * @param province
     * @return
     */
    @GetMapping("/view/{provinceId}")
    public Map<String, Object> findById(@PathVariable("provinceId") Province province){
        Map<String, Object> map = Maps.newHashMap();
        map.put("province",provinceService.findOne(province));
        return map;
    }

    /**
     * 省份
     * GET: /province/list
     * @param pageable
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public Map<String, Object> list(@PageableDefault(sort = { "provinceId" }, direction = Sort.Direction.DESC) Pageable pageable, ProvinceDTO dto){
        Map<String, Object> map = Maps.newHashMap();

        Page<Province> pagedata = provinceService.getPageData(dto,pageable);
        map.put("dto",dto);
        map.put("pagedata",pagedata);

        return map;
    }

    /**
     * 删除省份
     * GET: /province/delete/9?operator=1
     * @param province
     * @param request
     * @return
     */
    @GetMapping("/delete/{provinceId}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("provinceId") Province province, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();
        try {
            provinceService.remove(province, request);
            map.put("result","success");
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }
        return map;
    }

}