package myblog.dao;

import java.util.List;
import myblog.bean.Type;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDao {

    @Select("select * from type")
    List<Type> findAll();
}