package myblog.dao;

import myblog.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    @Select("select * from user where id=#{id}")
    User findById(Integer id);

    @Select("select user.id userId, logName, roleId, nickName, pwd, img, name " +
            "from user,role where roleId=role.id and logName=#{logName}")
    @Results({@Result(column = "userId",property ="id"),
        @Result(column = "roleId",property = "role.id"),
        @Result(column = "name",property = "role.name")
    })
    User findByLogName(String logName);

    @Insert("insert into user (logName, roleId, nickName, pwd, img, email) values (#{logName},2,#{nickName},#{pwd},#{img},#{email})")
    void addUser(User user);

    @Update("update user set pwd=#{pwd} where logName=#{logName}")
    int changePwd(String logName, String pwd);

    @Select("select count(*) from user where logName=#{logName} and email=#{email}")
    int verifyEmail(String email, String logName);

    @Update("update user set img=#{photoName} where id=#{userId}")
    void changeImgById(Integer userId, String photoName);

}
