package com.example.SaleCampaignApplication.Controller;

import com.example.SaleCampaignApplication.Config.AppConstants;
import com.example.SaleCampaignApplication.Payloads.ProductDTO;
import com.example.SaleCampaignApplication.Payloads.ProductResponse;
import com.example.SaleCampaignApplication.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product/")
public class ProductController {


    @Autowired
    private ProductService productService;

    @PostMapping("/saveProduct")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO){
        ProductDTO savedProductDTO = this.productService.saveProduct(productDTO);
        return new ResponseEntity<>(savedProductDTO , HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ProductResponse> getProducts(
            @RequestParam(value = "PageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)Integer pageNumber,
            @RequestParam(value = "PageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize
    ){
        ProductResponse productResponse = this.productService.getAllProducts(pageNumber, pageSize);
        return new ResponseEntity<>(productResponse , HttpStatus.OK);
    }

    @GetMapping("/getById/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer productId){
        ProductDTO productDTO = this.productService.getProductById(productId);
        return new ResponseEntity<>(productDTO , HttpStatus.OK);
    }



}
