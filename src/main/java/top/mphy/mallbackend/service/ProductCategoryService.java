package top.mphy.mallbackend.service;

import org.springframework.stereotype.Service;
import top.mphy.mallbackend.entity.Product;
import top.mphy.mallbackend.entity.ProductCategory;
import top.mphy.mallbackend.mapper.ProductCategoryMapper;

import java.math.BigInteger;
import java.util.List;

@Service
public class ProductCategoryService {

    private ProductCategoryMapper productCategoryMapper;

    public ProductCategoryService(ProductCategoryMapper productCategoryMapper) {
        this.productCategoryMapper = productCategoryMapper;
    }

    // *查询所有商品类别数据
    public List<ProductCategory> findAll() {
        return productCategoryMapper.findAll();
    }

    // 根据 category_id 查询此类所有商品
    public List<Product> findById(BigInteger categoryId) {
        return productCategoryMapper.findById(categoryId);
    }

    // 添加分类
    public void addCategory(ProductCategory productCategory) {
        productCategoryMapper.addCategory(productCategory);
    }

    // 编辑分类
    public void setCategory(ProductCategory productCategory) {
        productCategoryMapper.setCategory(productCategory);
    }

    public List<Product> findPageById(BigInteger categoryId,int offset, Integer pageSize) {
        return productCategoryMapper.findPageById(categoryId,offset,pageSize);
    }

    public Integer countProduct(BigInteger categoryId) {
        return productCategoryMapper.countProduct(categoryId);
    }

    public List<Product> findPageById(int offset, Integer pageSize) {
        return productCategoryMapper.findPage(offset,pageSize);
    }

    public Integer countProduct() {
        return productCategoryMapper.countAllProduct();
    }
}
