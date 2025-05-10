package com.erling.daoJ.example;


import com.erling.pojo.example.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
//    获取所有产品：GET http://localhost:8080/api/products
//    创建新产品：POST http://localhost:8080/api/products
//    更新产品：PUT http://localhost:8080/api/products/1
//    删除产品：DELETE http://localhost:8080/api/products/1
//    按类别查询 - GET http://localhost:8080/api/products/category/phone

    @Select("SELECT * FROM products") // 从数据库中获取所有产品
    List<Product> getProducts();

    // 创建新产品
    @Insert("INSERT INTO products (name, description,category, price) VALUES (#{name}, #{description}, #{category}, #{price})") // 创建新产品
    void createProduct(Product product);

    // 更新产品
    @Insert("UPDATE products SET name = #{name}, description = #{description}, category = #{category}, price = #{price} WHERE id = #{id}") // 更新产品
    void updateProduct(String name, String description, String category, double price, int id);

    @Update("UPDATE products SET name = #{product.name}, description = #{product.description}, category = #{product.category}, price = #{product.price} WHERE id = #{id}")// 更新产品
    void T_updateProduct(Product product,int id);
    // 删除产品
    @Delete("DELETE FROM products WHERE id = #{id}") // 删除产品
    void deleteProduct(int id);

    // 按类别查询
    @Select("SELECT * FROM products WHERE category = #{category}") // 按类别查询
    List<Product> getProductsByCategory(String category);

}
