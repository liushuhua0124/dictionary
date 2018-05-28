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
import huayue.sports.dictionary.domain.AdSpots;
import huayue.sports.dictionary.dto.AdSpotsDTO;
import huayue.sports.dictionary.service.AdSpotsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * 领域类 AdSpots(广告位) 的控制器层
 * Created by Mac.Manon on 2018/05/28
 */
@RestController
@RequestMapping("/adspots")
public class AdSpotsController {

    @Autowired
    AdSpotsService adSpotsService;

    /**
     * 提交新增广告位的请求
     * POST: /adspots/create
     * @param adSpots
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/create")
    public Map<String, Object> create(@Validated(AdSpots.CheckCreate.class) AdSpots adSpots, BindingResult result, HttpServletRequest request){
        return save(adSpots, result, request);
    }

    /**
     * 提交修改广告位的请求
     * POST: /adspots/modify
     * @param adSpots
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Map<String, Object> modify(@Validated(AdSpots.CheckModify.class) AdSpots adSpots, BindingResult result, HttpServletRequest request){
        return save(adSpots, result, request);
    }

    /**
     * 保存广告位数据
     * @param adSpots
     * @param result
     * @param request
     * @return
     */
    private Map<String, Object> save(AdSpots adSpots, BindingResult result, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();

        if (result.hasErrors()) {
            map.put("formErrors", result.getAllErrors());
            return map;
        }

        try {
            adSpots = adSpotsService.save(adSpots, request);
            map.put("adSpots",adSpotsService.findOne(adSpots));
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }

        return map;
    }

    /**
     * 显示一条广告位的信息详情
     * GET: /adspots/view/9
     * @param adSpots
     * @return
     */
    @GetMapping("/view/{adSpotsId}")
    public Map<String, Object> findById(@PathVariable("adSpotsId") AdSpots adSpots){
        Map<String, Object> map = Maps.newHashMap();
        map.put("adSpots",adSpotsService.findOne(adSpots));
        return map;
    }

    /**
     * 广告位
     * GET: /adSpots/list
     * @param pageable
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public Map<String, Object> list(@PageableDefault(sort = { "adSpotsId" }, direction = Sort.Direction.DESC) Pageable pageable, AdSpotsDTO dto){
        Map<String, Object> map = Maps.newHashMap();

        Page<AdSpots> pagedata = adSpotsService.getPageData(dto,pageable);
        map.put("dto",dto);
        map.put("pagedata",pagedata);

        return map;
    }

    /**
     * 删除广告位
     * GET: /adspots/delete/9?operator=1
     * @param adSpots
     * @param request
     * @return
     */
    @GetMapping("/delete/{adSpotsId}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("adSpotsId") AdSpots adSpots, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();
        try {
            adSpotsService.remove(adSpots, request);
            map.put("result","success");
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }
        return map;
    }

}