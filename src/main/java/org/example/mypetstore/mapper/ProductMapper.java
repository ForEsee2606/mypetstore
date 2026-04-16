package org.example.mypetstore.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.mypetstore.model.Category;
import org.example.mypetstore.model.Item;
import org.example.mypetstore.model.ProductType;

@Mapper
public interface ProductMapper {

    @Select("select * from category order by id")
    List<Category> findAllCategories();

    @Insert("insert into category(name, description) values(#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addCategory(Category category);

    @Update("update category set name=#{name}, description=#{description} where id=#{id}")
    int updateCategory(Category category);

    @Delete("delete from category where id=#{id}")
    int deleteCategory(Long id);

    @Select("select * from product_type order by id")
    List<ProductType> findAllProductTypes();

    @Insert("insert into product_type(name, category_id, description) values(#{name}, #{categoryId}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addProductType(ProductType productType);

    @Update("update product_type set name=#{name}, category_id=#{categoryId}, description=#{description} where id=#{id}")
    int updateProductType(ProductType productType);

    @Delete("delete from product_type where id=#{id}")
    int deleteProductType(Long id);

    @Select("select * from item order by id")
    List<Item> findAllItems();

    @Insert("insert into item(name, product_type_id, price, stock, status) values(#{name}, #{productTypeId}, #{price}, #{stock}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addItem(Item item);

    @Update("update item set name=#{name}, product_type_id=#{productTypeId}, price=#{price}, stock=#{stock}, status=#{status} where id=#{id}")
    int updateItem(Item item);

    @Delete("delete from item where id=#{id}")
    int deleteItem(Long id);

    @Update("update item set status=1 where id=#{id}")
    int publishItem(Long id);

    @Update("update item set status=0 where id=#{id}")
    int unpublishItem(Long id);
}