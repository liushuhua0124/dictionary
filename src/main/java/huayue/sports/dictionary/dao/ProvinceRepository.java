package huayue.sports.dictionary.dao;

import huayue.sports.dictionary.domain.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 领域类 Province(省份) 的DAO Repository接口层
 * Created by Mac.Manon on 2018/05/27
 */

//@RepositoryRestResource(path = "newpath")
public interface ProvinceRepository extends JpaRepository<Province,Long>, JpaSpecificationExecutor {

    Page<Province> findByIsDeletedFalse(Pageable pageable);

    //TODO:请根据实际需要调整方法名的构造 高级查询
    Page<Province> findByCodeContainingAndNameContainingAllIgnoringCaseAndIsDeletedFalse(String code, String name, Pageable pageable);

}