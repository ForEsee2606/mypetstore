package org.example.mypetstore.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.example.mypetstore.model.Account;

@Mapper
public interface AccountMapper {

    @Select("select id, username, phone, email, password, address from account order by id")
    List<Account> findAll();

    @Select("select id, username, phone, email, password, address from account where id=#{id}")
    Account findById(Long id);

    @Insert("insert into account(username, phone, email, password, address) values(#{username}, #{phone}, #{email}, #{password}, #{address})")
    int insert(Account account);

    @Update("update account set username=#{username}, phone=#{phone}, email=#{email}, address=#{address} where id=#{id}")
    int update(Account account);

    @Delete("delete from account where id=#{id}")
    int delete(Long id);

    @Update("update account set password=#{newPassword} where id=#{id}")
    int resetPassword(@Param("id") Long id, @Param("newPassword") String newPassword);

    @Select("select id, username, phone, email, password, address from account where username like concat('%', #{username}, '%')")
    List<Account> findByUsername(@Param("username") String username);
}