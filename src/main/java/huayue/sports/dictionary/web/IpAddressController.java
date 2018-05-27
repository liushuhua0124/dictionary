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
import huayue.sports.dictionary.domain.IpAddress;
import huayue.sports.dictionary.dto.IpAddressDTO;
import huayue.sports.dictionary.service.IpAddressService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * 领域类 IpAddress(IP地址) 的控制器层
 * Created by Mac.Manon on 2018/05/27
 */
@RestController
@RequestMapping("/ipaddress")
public class IpAddressController {

    @Autowired
    IpAddressService ipAddressService;

    /**
     * 提交新增IP地址的请求
     * POST: /ipaddress/create
     * @param ipAddress
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/create")
    public Map<String, Object> create(@Validated(IpAddress.CheckCreate.class) IpAddress ipAddress, BindingResult result, HttpServletRequest request){
        return save(ipAddress, result, request);
    }

    /**
     * 提交修改IP地址的请求
     * POST: /ipaddress/modify
     * @param ipAddress
     * @param result
     * @param request
     * @return
     */
    @PostMapping("/modify")
    public Map<String, Object> modify(@Validated(IpAddress.CheckModify.class) IpAddress ipAddress, BindingResult result, HttpServletRequest request){
        return save(ipAddress, result, request);
    }

    /**
     * 保存IP地址数据
     * @param ipAddress
     * @param result
     * @param request
     * @return
     */
    private Map<String, Object> save(IpAddress ipAddress, BindingResult result, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();

        if (result.hasErrors()) {
            map.put("formErrors", result.getAllErrors());
            return map;
        }

        try {
            ipAddress = ipAddressService.save(ipAddress, request);
            map.put("ipAddress",ipAddressService.findOne(ipAddress));
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }

        return map;
    }

    /**
     * 显示一条IP地址的信息详情
     * GET: /ipaddress/view/9
     * @param ipAddress
     * @return
     */
    @GetMapping("/view/{ipAddressId}")
    public Map<String, Object> findById(@PathVariable("ipAddressId") IpAddress ipAddress){
        Map<String, Object> map = Maps.newHashMap();
        map.put("ipAddress",ipAddressService.findOne(ipAddress));
        return map;
    }

    /**
     * IP地址
     * GET: /ipAddress/list
     * @param pageable
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public Map<String, Object> list(@PageableDefault(sort = { "ipAddressId" }, direction = Sort.Direction.DESC) Pageable pageable, IpAddressDTO dto){
        Map<String, Object> map = Maps.newHashMap();

        Page<IpAddress> pagedata = ipAddressService.getPageData(dto,pageable);
        map.put("dto",dto);
        map.put("pagedata",pagedata);

        return map;
    }

    /**
     * 删除IP地址
     * GET: /ipaddress/delete/9?operator=1
     * @param ipAddress
     * @param request
     * @return
     */
    @GetMapping("/delete/{ipAddressId}")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("ipAddressId") IpAddress ipAddress, HttpServletRequest request){
        Map<String, Object> map = Maps.newHashMap();
        try {
            ipAddressService.remove(ipAddress, request);
            map.put("result","success");
        }catch (Exception e){
            map.put("errorMessage", e.getLocalizedMessage());
        }
        return map;
    }

}