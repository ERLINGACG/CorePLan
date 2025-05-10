package com.erling.serviceJ.example;

import com.erling.daoJ.example.ProductMapper;
import com.erling.pojo.example.Product;
import com.erling.utilJ.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<Product> getProducts(){ // 获取所有产品
        return productMapper.getProducts();
    }
    public void createProduct(Product product){ // 创建新产品
        productMapper.createProduct(product);
    }
    public void deleteProduct(int id){ // 删除产品
        productMapper.deleteProduct(id);
    }
    public void updateProduct(String name,String description,String category,double price,int id){ // 更新产品
        productMapper.updateProduct(name,description,category,price,id);
    }
    public void T_updateProduct(Product product,int id){ // 更新产品
        productMapper.T_updateProduct(product,id);
    }
    public ResponseEntity<Result<?>> getProductsByCategory(String category) throws Exception { // 按类别查询

             List<Product> product = productMapper.getProductsByCategory(category);
             if(product.isEmpty()){
                 return ResponseEntity
                         .status(HttpStatus.OK)
                         .body(Result.FAILURE(403,"没有该类产品",null));
             }else{
                 return ResponseEntity
                         .status(HttpStatus.OK)
                         .body(Result.SUCCESS(product));
             }


//        return productMapper.getProductsByCategory(category);
    }
}
