package top.mphy.mallbackend.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import top.mphy.mallbackend.entity.Password;
import top.mphy.mallbackend.entity.RoleCount;
import top.mphy.mallbackend.entity.Shop;
import top.mphy.mallbackend.entity.User;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface UserMapper {
    // 验证登录
    @Select("SELECT * FROM `user` WHERE `username`=#{username}")
    User getUserByUsername(String username);

    // 用户注册插入数据
    @Select("INSERT INTO `user`(username, `password`) VALUES (#{username}, #{password})")
    void save(User user);

    // 根据userId获取用户信息
    @Select("SELECT * FROM `user` WHERE user_id=#{userId}")
    User getUserById(BigInteger userId);

    // !更新用户基本信息
    @Update({"<script>",
            "update user",
            "  <set>",
            "    <if test='username != null'>username=#{username},</if>",
            "    <if test='telephone != null'>telephone=#{telephone},</if>",
            "    <if test='address != null'>address=#{address},</if>",
            "    <if test='sex != null'>sex=#{sex},</if>",
            "    <if test='age != 0'>age=#{age},</if>",
            "    <if test='realName != null'>real_name=#{realName},</if>",
            "    <if test='email != null'>email=#{email},</if>",
            "    <if test='avatar != null'>avatar=#{avatar},</if>",
            "  </set>",
            "where user_id=#{userId}",
            "</script>"})
    void updateUser(@RequestBody User user);

    // !更新用户账号信息
    @Update({"<script>",
            "update user",
            "  <set>",
            "    <if test='username != null'>username=#{username},</if>",
            "    <if test='telephone != null'>telephone=#{telephone},</if>",
            "    <if test='realName != null'>real_name=#{realName},</if>",
            "    <if test='role != null'>role=#{role},</if>",
            "  </set>",
            "where user_id=#{userId}",
            "</script>"})
    void updateAccount(@RequestBody User user);

    // !获取用户数量
    @Select("SELECT count(*) FROM `user`")
    Integer countUser();

    // !获取店家数量
    @Select("SELECT count(*) FROM `user` WHERE role='shop'")
    Integer countShops();

    // !分页查询获取所有用户信息
    @Select("select * from user where concat(user_id,' ', username,' ', real_name,' ', telephone,' ', role,' ', status) like '%${key}%' limit #{offset},#{pageSize}")
    List<User> findByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize, @Param("key") String key);

    // !分页查询获取所有用户信息
    @Select("select * from user where  role='shop'  and concat(user_id,' ', username,' ', real_name,' ', telephone,' ', role,' ', status) like '%${key}%' limit #{offset},#{pageSize}")
    List<User> findShopsByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize, @Param("key") String key);

    // !修改用户状态
    @Select("UPDATE `user` SET `status`=#{status} WHERE user_id=#{userId}")
    void setStatus(@Param("userId") BigInteger userId, @Param("status") BigInteger status);

    // !重置密码
    @Update("UPDATE `user` SET `password`=12345 WHERE user_id=#{userId}")
    void reset(BigInteger userId);

    // !删除用户
    @Delete("delete from `user` WHERE user_id=#{userId};")
    void delete(BigInteger userId);

    @Delete("DELETE FROM product WHERE product.shop_id=(SELECT shop_id FROM shop WHERE owner_id=#{userId});")
    void deleteP(BigInteger userId);

    @Delete("delete from shop where owner_id=#{userId}")
    void deleteS(BigInteger userId);

    @Select("SELECT * FROM shop WHERE owner_id=#{userId}")
    Shop checkShop(BigInteger userId);

    // !删除店家

    // !添加新用户
    @Insert("INSERT INTO `user`(username,real_name,telephone,role) VALUE(#{username},#{realName},#{telephone},#{role})")
    void add(User user);

    // !用户充值
    @Update("UPDATE `user` SET money=money+#{addMoney} WHERE user_id=#{userId}")
    void recharge(@Param("userId") BigInteger userId, @Param("addMoney") Double addMoney);

    // !店家注册
    @Insert("INSERT INTO `user`(telephone,username,real_name,`password`, role) VALUE(#{telephone},#{username},#{realName},#{password}, 'shop')")
    void shopRegister(Shop shop);

    // !根据userId获取shopId
    @Select("SELECT shop_id FROM shop WHERE owner_id=#{userId}")
    BigInteger getShopId(BigInteger userId);

    // !用户修改密码
    @Update("UPDATE `user` SET `password`=#{newPassword} WHERE user_id=#{userId}")
    void setPassword(Password password);

    // !查询某用户是否购买了某商品
    @Select("SELECT order_id FROM order_master, order_detail WHERE order_master.buyer_id=#{userId} AND order_detail.product_id=#{productId} AND order_master.order_number=order_detail.order_number AND order_master.order_status=3")
    BigInteger check(@Param("userId") BigInteger userId, @Param("productId") BigInteger productId);

    // !根据订单编号获取用户信息
    @Select("SELECT * FROM `user`, order_master WHERE `user`.user_id=order_master.buyer_id AND order_master.order_number=#{orderNumber}")
    User getUserByOrderNumber(String orderNumber);

    // !获取角色数
    @Select("SELECT (SELECT count(*) FROM `user` WHERE role='admin') AS admin,  (SELECT count(*) FROM `user` WHERE role='shop') AS shop,  (SELECT count(*) FROM `user` WHERE role='user') AS 'user'")
    RoleCount getRoleCount();
}
