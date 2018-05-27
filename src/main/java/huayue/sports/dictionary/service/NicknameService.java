package huayue.sports.dictionary.service;

import huayue.sports.dictionary.domain.Nickname;
import huayue.sports.dictionary.dto.NicknameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;

/**
 * 领域类 Nickname(昵称字典) 的服务接口层
 * Created by Mac.Manon on 2018/05/27
 */

public interface NicknameService {

    /**
     * 保存数据
     * @param nickname 昵称字典
     * @param request
     * @return 返回Nickname实体
     */
    Nickname save(Nickname nickname, HttpServletRequest request) throws Exception;

    /**
     * 查找符合条件的一条数据
     * @param nickname 昵称字典
     * @return 返回Nickname实体
     */
    Nickname findOne(Nickname nickname);

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    Page<Nickname> getPageData(NicknameDTO dto, Pageable pageable);

    /**
     * 根据主键id删除一条数据记录
     * @param nickname 昵称字典
     * @param request
     */
    void remove(Nickname nickname, HttpServletRequest request) throws Exception;

}