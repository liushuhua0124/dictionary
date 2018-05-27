package huayue.sports.dictionary.dao;

import huayue.sports.dictionary.domain.Nickname;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 领域类 Nickname(昵称字典) 的DAO Repository接口层
 * Created by Mac.Manon on 2018/05/27
 */

//@RepositoryRestResource(path = "newpath")
public interface NicknameRepository extends JpaRepository<Nickname,Long> {

    //TODO:请根据实际需要调整方法名的构造 标准查询
    Page<Nickname> findByNameContainingAllIgnoringCase(String name, Pageable pageable);

}