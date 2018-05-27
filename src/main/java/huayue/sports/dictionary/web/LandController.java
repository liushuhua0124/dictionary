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
import huayue.sports.dictionary.domain.Land;
import huayue.sports.dictionary.dto.LandDTO;
import huayue.sports.dictionary.service.LandService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * 领域类 Land(洲) 的控制器层
 * Created by Mac.Manon on 2018/05/27
 */
@RestController
@RequestMapping("/land")
public class LandController {

    @Autowired
    LandService landService;

    /**
     * 提交新增洲的请求
     * POST: /land/create
     * @param land
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/create")
    public Map<String, Object> create(@Validated(Land.CheckCreate.class) Land land, BindingResult result, HttpServletRequest request){
        return save(land, result, request);
    }

    /**
     * 提交修改洲的请求
     * POST: /land/modify
     * @param land
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Map<String, Object> modify(@Validated(Land.CheckModify.class) Land land, BindingResult result, HttpServletRequest request){
        return save(land, result, request);
    }

    /**
     * 保存洲数据
     * @param land
     * @param result
     * @param request
     * @return
     */
    private Map<String, Object> save(Land land, BindingResult result, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();

        if (result.hasErrors()) {
            map.put("formErrors", result.getAllErrors());
            return map;
        }

        try {
            land = landService.save(land, request);
            map.put("land",landService.findOne(land));
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }

        return map;
    }

    /**
     * 显示一条洲的信息详情
     * GET: /land/view/9
     * @param land
     * @return
     */
    @GetMapping("/view/{landId}")
    public Map<String, Object> findById(@PathVariable("landId") Land land){
        Map<String, Object> map = Maps.newHashMap();
        map.put("land",landService.findOne(land));
        return map;
    }

    /**
     * 洲
     * GET: /land/list
     * @param pageable
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public Map<String, Object> list(@PageableDefault(sort = { "landId" }, direction = Sort.Direction.DESC) Pageable pageable, LandDTO dto){
        Map<String, Object> map = Maps.newHashMap();

        Page<Land> pagedata = landService.getPageData(dto,pageable);
        map.put("dto",dto);
        map.put("pagedata",pagedata);

        return map;
    }

    /**
     * 删除洲
     * GET: /land/delete/9?operator=1
     * @param land
     * @param request
     * @return
     */
    @GetMapping("/delete/{landId}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("landId") Land land, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();
        try {
            landService.remove(land, request);
            map.put("result","success");
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }
        return map;
    }

}