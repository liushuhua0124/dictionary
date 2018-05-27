package huayue.sports.dictionary.service.impl;

import huayue.sports.dictionary.dao.NicknameRepository;
import huayue.sports.dictionary.domain.Nickname;
import huayue.sports.dictionary.dto.NicknameDTO;
import huayue.sports.dictionary.service.NicknameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 领域类 Nickname(昵称字典) 的服务实现层
 * Created by Mac.Manon on 2018/05/27
 */

@Service
public class NicknameServiceImpl implements NicknameService {

    @Autowired
    NicknameRepository nicknameRepository;

    /**
     * 保存数据
     * @param nickname 昵称字典
     * @param request
     * @return 返回Nickname实体
     */
    @Override
    //@Transactional(rollbackFor={IllegalArgumentException.class}) //事务回滚：指定特定异常(如：throw new IllegalArgumentException)时，数据回滚
    @CachePut(value = "nickname", key = "#nickname.nicknameId")//@CachePut缓存新增的或更新的数据到缓存，其中缓存名称为nickname，数据的key是nickname的nicknameId
    public Nickname save(Nickname nickname, HttpServletRequest request) throws Exception {

        if(nickname.getNicknameId()==null){
            return nicknameRepository.save(nickname);
        }else{
            Nickname n = this.findOne(nickname);

            if(request.getParameterValues("name") != null && !nickname.getName().equals(n.getName()))
                n.setName(nickname.getName());

            return nicknameRepository.saveAndFlush(n);
        }
    }

    /**
     * 查找符合条件的一条数据
     * @param nickname 昵称字典
     * @return 返回Nickname实体
     */
    @Override
    @Cacheable(value = "nickname", key = "#nickname.nicknameId")//缓存key为nickname的nicknameId数据到缓存nickname中
    public Nickname findOne(Nickname nickname) {
        return nicknameRepository.findOne(nickname.getNicknameId());
    }

    /**
     * 查找符合条件的数据列表
     * @param dto 查询条件DTO
     * @param pageable 翻页和排序
     * @return 返回支持排序和翻页的数据列表
     */
    @Override
    public Page<Nickname> getPageData(NicknameDTO dto, Pageable pageable){
        if(dto.getKeyword() != null) {
            String keyword = dto.getKeyword().trim();
            return nicknameRepository.findByNameContainingAllIgnoringCase(keyword, pageable);
        }

        return nicknameRepository.findAll(pageable);
    }

    /**
     * 根据主键id删除一条数据记录
     * @param nickname 昵称字典
     * @param request
     */
    @Override
    @CacheEvict(value = "nickname", key = "#nickname.nicknameId")//清除key为nickname的nicknameId数据缓存
    public void remove(Nickname nickname, HttpServletRequest request) throws Exception {
        nicknameRepository.delete(nickname.getNicknameId());
    }

}