package top.mphy.mallbackend.mapper;

import org.apache.ibatis.annotations.*;
import top.mphy.mallbackend.entity.Product;
import top.mphy.mallbackend.entity.ProductCategory;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface ProductCategoryMapper {
    // 查询所有商品类别
    @Select("SELECT * FROM product_category")
    List<ProductCategory> findAll();

    // 根据 category_id 查询此类所有商品
    @Select("SELECT * FROM product WHERE category_id=#{categoryId}")
    List<Product> findById(BigInteger categoryId);

    // 添加分类
    @Insert("INSERT INTO product_category(category_name) VALUE(#{categoryName})")
    void addCategory(ProductCategory productCategory);

    // 编辑分类
    @Update("UPDATE product_category SET category_name=#{categoryName} WHERE category_id=#{categoryId}")
    void setCategory(ProductCategory productCategory);

    @Select("SELECT * FROM product WHERE category_id=#{categoryId} limit #{offset},#{pageSize}")
    List<Product> findPageById(@Param("categoryId") BigInteger categoryId, @Param("offset") int offset, @Param("pageSize") Integer pageSize);

    @Select("SELECT * FROM product limit #{offset},#{pageSize}")
    List<Product> findPage(@Param("offset") int offset, @Param("pageSize") Integer pageSize);

    @Select("SELECT count(*) FROM product WHERE category_id=#{categoryId}")
    Integer countProduct(@Param("categoryId") BigInteger categoryId);

    @Select("SELECT count(*) FROM product")
    Integer countAllProduct();
}
