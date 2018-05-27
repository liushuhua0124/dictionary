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
import huayue.sports.dictionary.domain.Business;
import huayue.sports.dictionary.dto.BusinessDTO;
import huayue.sports.dictionary.service.BusinessService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * 领域类 Business(业务) 的控制器层
 * Created by Mac.Manon on 2018/05/27
 */
@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    BusinessService businessService;

    /**
     * 提交新增业务的请求
     * POST: /business/create
     * @param business
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/create")
    public Map<String, Object> create(@Validated(Business.CheckCreate.class) Business business, BindingResult result, HttpServletRequest request){
        return save(business, result, request);
    }

    /**
     * 提交修改业务的请求
     * POST: /business/modify
     * @param business
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Map<String, Object> modify(@Validated(Business.CheckModify.class) Business business, BindingResult result, HttpServletRequest request){
        return save(business, result, request);
    }

    /**
     * 保存业务数据
     * @param business
     * @param result
     * @param request
     * @return
     */
    private Map<String, Object> save(Business business, BindingResult result, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();

        if (result.hasErrors()) {
            map.put("formErrors", result.getAllErrors());
            return map;
        }

        try {
            business = businessService.save(business, request);
            map.put("business",businessService.findOne(business));
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }

        return map;
    }

    /**
     * 显示一条业务的信息详情
     * GET: /business/view/9
     * @param business
     * @return
     */
    @GetMapping("/view/{businessId}")
    public Map<String, Object> findById(@PathVariable("businessId") Business business){
        Map<String, Object> map = Maps.newHashMap();
        map.put("business",businessService.findOne(business));
        return map;
    }

    /**
     * 业务
     * GET: /business/list
     * @param pageable
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public Map<String, Object> list(@PageableDefault(sort = { "businessId" }, direction = Sort.Direction.DESC) Pageable pageable, BusinessDTO dto){
        Map<String, Object> map = Maps.newHashMap();

        Page<Business> pagedata = businessService.getPageData(dto,pageable);
        map.put("dto",dto);
        map.put("pagedata",pagedata);

        return map;
    }

    /**
     * 删除业务
     * GET: /business/delete/9?operator=1
     * @param business
     * @param request
     * @return
     */
    @GetMapping("/delete/{businessId}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("businessId") Business business, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();
        try {
            businessService.remove(business, request);
            map.put("result","success");
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }
        return map;
    }

}