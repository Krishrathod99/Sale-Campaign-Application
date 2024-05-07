package com.example.SaleCampaignApplication.Service;

import com.example.SaleCampaignApplication.Entities.PriceHistory;
import com.example.SaleCampaignApplication.Entities.Product;
import com.example.SaleCampaignApplication.Exception.ResourceNotFoundException;
import com.example.SaleCampaignApplication.Payloads.ProductDTO;
import com.example.SaleCampaignApplication.Payloads.ProductResponse;
import com.example.SaleCampaignApplication.Repositories.PriceHistoryRepository;
import com.example.SaleCampaignApplication.Repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = this.modelMapper.map(productDTO, Product.class);
        Product saved = this.productRepository.save(product);

        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setProductId(saved.getProductId());
        priceHistory.setTitle(saved.getTitle());
        priceHistory.setCurrentPrice(saved.getCurrentPrice());
        this.priceHistoryRepository.save(priceHistory);

        return this.modelMapper.map(saved , ProductDTO.class);
    }

    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize) {

        Pageable p  = PageRequest.of(pageNumber , pageSize);

        Page<Product> productPage = this.productRepository.findAll(p);
        List<Product> productList = productPage.getContent();
        List<ProductDTO> productDTOS = productList.stream().map(product -> this.modelMapper.map(product, ProductDTO.class)).toList();


        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

    public ProductDTO getProductById(Integer productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
        return this.modelMapper.map(product , ProductDTO.class);
    }
}
