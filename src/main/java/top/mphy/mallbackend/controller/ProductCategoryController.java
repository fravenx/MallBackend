package top.mphy.mallbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.mphy.mallbackend.common.ResponseData;
import top.mphy.mallbackend.common.ResponseDataUtils;
import top.mphy.mallbackend.entity.Product;
import top.mphy.mallbackend.entity.ProductCategory;
import top.mphy.mallbackend.service.ProductCategoryService;
import top.mphy.mallbackend.vo.Page;

import java.math.BigInteger;
import java.util.List;

@CrossOrigin(origins = "http://119.23.46.102:8081", maxAge = 3600)
@RestController
@RequestMapping("/category")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/all")
    public List<ProductCategory> findAll() {
        return productCategoryService.findAll();
    }

    // 根据 category_id 查询此类所有商品
    // {id}是占位符，接口示例：http://127.0.0.1:8080/user/6
    @GetMapping("/{categoryId}")
    public List<Product> findById(@PathVariable("categoryId") BigInteger categoryId) {
        return productCategoryService.findById(categoryId);
    }

    @GetMapping("/page/{categoryId}")
    public ResponseData<?> findPageById(@PathVariable("categoryId") BigInteger categoryId,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "12") Integer pageSize
                                    ) {
        int offset = (pageNum - 1) * pageSize;
        List<Product> userData = null;
        if(categoryId.intValue() != 0) {
            userData = productCategoryService.findPageById(categoryId,offset, pageSize);
        }else{
            userData = productCategoryService.findPageById(offset, pageSize);
        }
        Page<Product> page = new Page<>();
        page.setData(userData);
        Integer total = 0;
        if(categoryId.intValue() != 0) {
            total = productCategoryService.countProduct(categoryId);
        }else {
            total = productCategoryService.countProduct();
        }
        page.setTotal(total);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        return ResponseDataUtils.buildSuccess("0", "商品信息获取成功！", page);
    }

    // 添加分类
    @PostMapping
    public ResponseData<?> addCategory(@RequestBody ProductCategory productCategory) {
        productCategoryService.addCategory(productCategory);
        return ResponseDataUtils.buildSuccess("0", "添加分类成功！");
    }

    // 编辑分类
    @PutMapping
    public ResponseData<?> setCategory(@RequestBody ProductCategory productCategory) {
        productCategoryService.setCategory(productCategory);
        return ResponseDataUtils.buildSuccess("0", "修改分类成功！");
    }
}
