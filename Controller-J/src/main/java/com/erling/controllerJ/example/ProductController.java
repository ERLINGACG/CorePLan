package com.erling.controllerJ.example;

import com.erling.pojo.example.Product;
import com.erling.serviceJ.example.ProductService;

import com.erling.utilJ.result.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/example/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getProducts")
    public List<Product> getProducts() {
      return productService.getProducts();
    }
    @PostMapping("/createProduct")
    public void CreateProduct(@RequestBody Product product) {
        productService.createProduct(product);
    }
    @DeleteMapping("/deleteProduct")
    public void deleteProduct(@RequestParam int id) {
        productService.deleteProduct(id);
    }
    @PutMapping("/updateProduct")
    public void updateProduct(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam String description,
            @RequestParam String category
    ) {

        productService.updateProduct(name, category, description, price, id);
    }
    @PutMapping("/T_updateProduct")
    public void T_updateProduct(@RequestBody Product product){
        productService.T_updateProduct(product,product.getId());
    }
    @GetMapping("/getProductsByCategory")
    public ResponseEntity<Result<?>> getProductsByCategory(@RequestParam String category) throws Exception {
        return productService.getProductsByCategory(category);
    }
}
