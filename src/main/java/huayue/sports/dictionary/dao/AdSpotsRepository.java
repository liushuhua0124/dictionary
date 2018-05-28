package huayue.sports.dictionary.dao;

import huayue.sports.dictionary.domain.AdSpots;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 领域类 AdSpots(广告位) 的DAO Repository接口层
 * Created by Mac.Manon on 2018/05/28
 */

//@RepositoryRestResource(path = "newpath")
public interface AdSpotsRepository extends JpaRepository<AdSpots,Long>, JpaSpecificationExecutor {

    Page<AdSpots> findByIsDeletedFalse(Pageable pageable);

    //TODO:请根据实际需要调整方法名的构造 高级查询
    Page<AdSpots> findByPlaceCodeContainingAndMemoContainingAllIgnoringCaseAndIsDeletedFalse(String placeCode, String memo, Pageable pageable);

}