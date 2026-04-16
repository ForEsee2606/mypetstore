package org.example.mypetstore.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.mypetstore.model.PetOrder;

@Mapper
public interface OrderMapper {

    @Select("select * from pet_order order by id desc")
    List<PetOrder> findAll();

    @Insert("insert into pet_order(account_id, amount, status, receiver_name, receiver_phone, receiver_address) values(#{accountId}, #{amount}, #{status}, #{receiverName}, #{receiverPhone}, #{receiverAddress})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PetOrder order);

    @Update("update pet_order set receiver_name=#{receiverName}, receiver_phone=#{receiverPhone}, receiver_address=#{receiverAddress}, status=#{status} where id=#{id}")
    int update(PetOrder order);

    @Delete("delete from pet_order where id=#{id}")
    int delete(Long id);

    @Update("update pet_order set status='SHIPPED' where id=#{id}")
    int ship(Long id);

    @Update("update pet_order set status='DELIVERED' where id=#{id}")
    int complete(Long id);
}