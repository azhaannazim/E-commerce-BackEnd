package com.ecommerce.project.sbECom.service;

import com.ecommerce.project.sbECom.exceptions.ResourceNotFoundException;
import com.ecommerce.project.sbECom.model.Category;
import com.ecommerce.project.sbECom.model.Product;
import com.ecommerce.project.sbECom.payload.ProductDTO;
import com.ecommerce.project.sbECom.payload.ProductResponse;
import com.ecommerce.project.sbECom.repositories.CategoryRepository;
import com.ecommerce.project.sbECom.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category" ,"categoryId" ,categoryId));

        Product product = modelMapper.map(productDTO , Product.class);

        product.setCategory(category);
        product.setImage("default.png");
        double specialPrice = product.getPrice() - (product.getPrice() * (product.getDiscount() * .01));
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct , ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOS = productList.stream()
                .map(product -> modelMapper.map(product , ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category" ,"categoryId" ,id));
        List<Product> productList = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = productList.stream()
                .map(product -> modelMapper.map(product , ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword) {
        List<Product> productList = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + "%");
        List<ProductDTO> productDTOS = productList.stream()
                .map(product -> modelMapper.map(product , ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

        Product product = modelMapper.map(productDTO , Product.class);

        savedProduct.setProductName(product.getProductName());
        savedProduct.setPrice(product.getPrice());
        savedProduct.setDescription(product.getDescription());
        savedProduct.setDiscount(product.getDiscount());
        savedProduct.setQuantity(product.getQuantity());
        double specialPrice = product.getPrice() - (product.getPrice() * (product.getDiscount() * .01));
        product.setSpecialPrice(specialPrice);

        productRepository.save(savedProduct);

        return modelMapper.map(savedProduct , ProductDTO.class);

    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

        productRepository.delete(product);

        return modelMapper.map(product , ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

        //String path = "image/";
        String fileName = fileService.uploadImage(path , image);
        product.setImage(fileName);

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct , ProductDTO.class);
    }
}
