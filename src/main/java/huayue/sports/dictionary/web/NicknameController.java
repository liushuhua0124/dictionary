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
import huayue.sports.dictionary.domain.Nickname;
import huayue.sports.dictionary.dto.NicknameDTO;
import huayue.sports.dictionary.service.NicknameService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * 领域类 Nickname(昵称字典) 的控制器层
 * Created by Mac.Manon on 2018/05/27
 */
@RestController
@RequestMapping("/nickname")
public class NicknameController {

    @Autowired
    NicknameService nicknameService;

    /**
     * 提交新增昵称字典的请求
     * POST: /nickname/create
     * @param nickname
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/create")
    public Map<String, Object> create(@Validated(Nickname.CheckCreate.class) Nickname nickname, BindingResult result, HttpServletRequest request){
        return save(nickname, result, request);
    }

    /**
     * 提交修改昵称字典的请求
     * POST: /nickname/modify
     * @param nickname
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Map<String, Object> modify(@Validated(Nickname.CheckModify.class) Nickname nickname, BindingResult result, HttpServletRequest request){
        return save(nickname, result, request);
    }

    /**
     * 保存昵称字典数据
     * @param nickname
     * @param result
     * @param request
     * @return
     */
    private Map<String, Object> save(Nickname nickname, BindingResult result, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();

        if (result.hasErrors()) {
            map.put("formErrors", result.getAllErrors());
            return map;
        }

        try {
            nickname = nicknameService.save(nickname, request);
            map.put("nickname",nicknameService.findOne(nickname));
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }

        return map;
    }

    /**
     * 显示一条昵称字典的信息详情
     * GET: /nickname/view/9
     * @param nickname
     * @return
     */
    @GetMapping("/view/{nicknameId}")
    public Map<String, Object> findById(@PathVariable("nicknameId") Nickname nickname){
        Map<String, Object> map = Maps.newHashMap();
        map.put("nickname",nicknameService.findOne(nickname));
        return map;
    }

    /**
     * 昵称字典
     * GET: /nickname/list
     * @param pageable
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public Map<String, Object> list(@PageableDefault(sort = { "nicknameId" }, direction = Sort.Direction.DESC) Pageable pageable, NicknameDTO dto){
        Map<String, Object> map = Maps.newHashMap();

        Page<Nickname> pagedata = nicknameService.getPageData(dto,pageable);
        map.put("dto",dto);
        map.put("pagedata",pagedata);

        return map;
    }

    /**
     * 删除昵称字典
     * GET: /nickname/delete/9?operator=1
     * @param nickname
     * @param request
     * @return
     */
    @GetMapping("/delete/{nicknameId}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("nicknameId") Nickname nickname, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();
        try {
            nicknameService.remove(nickname, request);
            map.put("result","success");
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }
        return map;
    }

}