package org.example.mypetstore.controller;

import java.util.List;
import org.example.mypetstore.mapper.ProductMapper;
import org.example.mypetstore.model.Category;
import org.example.mypetstore.model.Item;
import org.example.mypetstore.model.ProductType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductMapper productMapper;

    public ProductController(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @GetMapping("/categories")
    public List<Category> listCategories() {
        return productMapper.findAllCategories();
    }

    @PostMapping("/categories")
    public Category addCategory(@RequestBody Category category) {
        productMapper.addCategory(category);
        return category;
    }

    @PutMapping("/categories/{id}")
    public String updateCategory(@PathVariable Long id, @RequestBody Category category) {
        category.setId(id);
        productMapper.updateCategory(category);
        return "ok";
    }

    @DeleteMapping("/categories/{id}")
    public String deleteCategory(@PathVariable Long id) {
        productMapper.deleteCategory(id);
        return "ok";
    }

    @GetMapping("/types")
    public List<ProductType> listTypes() {
        return productMapper.findAllProductTypes();
    }

    @PostMapping("/types")
    public ProductType addType(@RequestBody ProductType productType) {
        productMapper.addProductType(productType);
        return productType;
    }

    @PutMapping("/types/{id}")
    public String updateType(@PathVariable Long id, @RequestBody ProductType productType) {
        productType.setId(id);
        productMapper.updateProductType(productType);
        return "ok";
    }

    @DeleteMapping("/types/{id}")
    public String deleteType(@PathVariable Long id) {
        productMapper.deleteProductType(id);
        return "ok";
    }

    @GetMapping("/items")
    public List<Item> listItems() {
        return productMapper.findAllItems();
    }

    @PostMapping("/items")
    public Item addItem(@RequestBody Item item) {
        if (item.getStatus() == null) {
            item.setStatus(0);
        }
        productMapper.addItem(item);
        return item;
    }

    @PutMapping("/items/{id}")
    public String updateItem(@PathVariable Long id, @RequestBody Item item) {
        item.setId(id);
        productMapper.updateItem(item);
        return "ok";
    }

    @DeleteMapping("/items/{id}")
    public String deleteItem(@PathVariable Long id) {
        productMapper.deleteItem(id);
        return "ok";
    }

    @PatchMapping("/items/{id}/publish")
    public String publishItem(@PathVariable Long id) {
        productMapper.publishItem(id);
        return "ok";
    }

    @PatchMapping("/items/{id}/unpublish")
    public String unpublishItem(@PathVariable Long id) {
        productMapper.unpublishItem(id);
        return "ok";
    }
}