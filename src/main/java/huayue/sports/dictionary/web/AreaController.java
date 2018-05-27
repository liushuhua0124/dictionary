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
import huayue.sports.dictionary.domain.Area;
import huayue.sports.dictionary.dto.AreaDTO;
import huayue.sports.dictionary.service.AreaService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * 领域类 Area(区县) 的控制器层
 * Created by Mac.Manon on 2018/05/27
 */
@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    AreaService areaService;

    /**
     * 提交新增区县的请求
     * POST: /area/create
     * @param area
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/create")
    public Map<String, Object> create(@Validated(Area.CheckCreate.class) Area area, BindingResult result, HttpServletRequest request){
        return save(area, result, request);
    }

    /**
     * 提交修改区县的请求
     * POST: /area/modify
     * @param area
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Map<String, Object> modify(@Validated(Area.CheckModify.class) Area area, BindingResult result, HttpServletRequest request){
        return save(area, result, request);
    }

    /**
     * 保存区县数据
     * @param area
     * @param result
     * @param request
     * @return
     */
    private Map<String, Object> save(Area area, BindingResult result, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();

        if (result.hasErrors()) {
            map.put("formErrors", result.getAllErrors());
            return map;
        }

        try {
            area = areaService.save(area, request);
            map.put("area",areaService.findOne(area));
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }

        return map;
    }

    /**
     * 显示一条区县的信息详情
     * GET: /area/view/9
     * @param area
     * @return
     */
    @GetMapping("/view/{areaId}")
    public Map<String, Object> findById(@PathVariable("areaId") Area area){
        Map<String, Object> map = Maps.newHashMap();
        map.put("area",areaService.findOne(area));
        return map;
    }

    /**
     * 区县
     * GET: /area/list
     * @param pageable
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public Map<String, Object> list(@PageableDefault(sort = { "areaId" }, direction = Sort.Direction.DESC) Pageable pageable, AreaDTO dto){
        Map<String, Object> map = Maps.newHashMap();

        Page<Area> pagedata = areaService.getPageData(dto,pageable);
        map.put("dto",dto);
        map.put("pagedata",pagedata);

        return map;
    }

    /**
     * 删除区县
     * GET: /area/delete/9?operator=1
     * @param area
     * @param request
     * @return
     */
    @GetMapping("/delete/{areaId}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("areaId") Area area, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();
        try {
            areaService.remove(area, request);
            map.put("result","success");
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }
        return map;
    }

}