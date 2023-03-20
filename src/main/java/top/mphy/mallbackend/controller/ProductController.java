package top.mphy.mallbackend.controller;

import org.springframework.web.bind.annotation.*;
import top.mphy.mallbackend.common.ResponseData;
import top.mphy.mallbackend.common.ResponseDataUtils;
import top.mphy.mallbackend.entity.OrderDetail;
import top.mphy.mallbackend.entity.Product;
import top.mphy.mallbackend.service.ProductService;
import top.mphy.mallbackend.vo.Page;

import java.math.BigInteger;
import java.util.List;

@CrossOrigin(origins = "http://119.23.46.102:8081", maxAge = 3600)
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // *获取所有商品
    @GetMapping("/all")
    public List<Product> findAll() {
        return productService.findAll();
    }

    // *根据商品ID查询指定商品
    @GetMapping("/{id}")
    public Product findOne(@PathVariable("id") BigInteger id) {
        return productService.findOne(id);
    }

    // *查询最新上架商品：指定日期之后
    @GetMapping("/new")
    public List<Product> finNew() {
        return productService.findNew();
    }

    // *查询热门商品，销量高于指定售货量的商品
    @GetMapping("/hot")
    public List<Product> findHot() {
        return productService.findHot();
    }

    // !分页查询
    @GetMapping("/shop")
    public ResponseData<?> findByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      @RequestParam(defaultValue = "") String key,
                                      @RequestParam Integer ownerId) {
        int offset = (pageNum - 1) * pageSize;
        List<Product> userData = productService.findByPage(offset, pageSize, key, ownerId);
        Page<Product> page = new Page<>();
        page.setData(userData);
        Integer total = productService.countShopProduct(ownerId);
        page.setTotal(total);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        return ResponseDataUtils.buildSuccess("0", "商品信息获取成功！", page);
    }

    // !商家更新商品信息
    @PutMapping
    public ResponseData<?> updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
        return ResponseDataUtils.buildSuccess("0", "商品信息修改成功！");
    }

    // !获取某一订单的所有商品信息
    @GetMapping("/order/{orderNumber}")
    public ResponseData<?> findOrderDetail(@PathVariable String orderNumber) {
        List<OrderDetail> orderDetails = productService.findOrderDetail(orderNumber);
        return ResponseDataUtils.buildSuccess("0", "获取商品信息成功！", orderDetails);
    }

    // !添加新产品
    @PostMapping
    public ResponseData<?> addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return ResponseDataUtils.buildSuccess("0", "商品添加成功！");
    }

    // !根据 shopId 获取所有商品
    @GetMapping("/shop_id/{shopId}")
    public ResponseData<?> findByShopId(@PathVariable BigInteger shopId) {
        List<Product> products = productService.findByShopId(shopId);
        return ResponseDataUtils.buildSuccess("0", "该店商品信息获取成功！", products);
    }

    // !搜索
    @GetMapping("/search")
    public ResponseData<?> search(@RequestParam(defaultValue = "") String key) {
        List<Product> products = productService.search(key);
        return ResponseDataUtils.buildSuccess("0", "搜索成功！", products);
    }

    // !删除商品
    @DeleteMapping("/{productId}")
    public ResponseData<?> deleteP(@PathVariable BigInteger productId) {
        productService.deleteP(productId);
        return ResponseDataUtils.buildSuccess("0", "删除商品成功！");
    }

}
