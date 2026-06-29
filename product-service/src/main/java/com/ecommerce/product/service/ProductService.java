package com.ecommerce.product.service;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository; 
	
	public ProductResponse createProduct(ProductRequest productRequest) {
		Product product = new Product();
		updateProductFromRequest(product,productRequest);
		Product saveProduct = productRepository.save(product);
		return maptoProductResponse(saveProduct);
	}


	private ProductResponse maptoProductResponse(Product saveProduct) {
		ProductResponse response = new ProductResponse();
		response.setId(saveProduct.getId());
		response.setName(saveProduct.getName());
		response.setPrice(saveProduct.getPrice());
		response.setDescription(saveProduct.getDescription());
		response.setStockQuantity(saveProduct.getStockQuantity());
		response.setCategory(saveProduct.getCategory());
		response.setImageUrl(saveProduct.getImageUrl());
		response.setActive(saveProduct.getActive());
		return response;
	}

	private void updateProductFromRequest(Product product, ProductRequest productRequest) {
		product.setName(productRequest.getName());
		product.setPrice(productRequest.getPrice());
		product.setDescription(productRequest.getDescription());
		product.setStockQuantity(productRequest.getStockQuantity());
		product.setCategory(productRequest.getCategory());
		product.setImageUrl(productRequest.getImageUrl());
	}

	/* other way to write 
	public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
		return productRepository.findById(id) .map(existingProduct -> {
			updateProductFromRequest(existingProduct, productRequest);
			Product saveProduct = productRepository.save(existingProduct);
			return maptoProductResponse(saveProduct);
		}).orElseThrow(()-> new RuntimeException("Product not not :" + id));
		}*/
	
	public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
		return productRepository.findById(id) .map(existingProduct -> {
			updateProductFromRequest(existingProduct, productRequest);
			Product saveProduct = productRepository.save(existingProduct);
			return maptoProductResponse(saveProduct);
		});
	}


	public List<ProductResponse> getAllProducts() {
		return productRepository.findByActiveTrue().stream().map(this::maptoProductResponse).collect(Collectors.toList());
	}

	/*this is other way but we use another way
	public void deleteProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
		product.setActive(false);
		productRepository.save(product);
	}*/
	/*
	public boolean  deleteProduct(Long id) {
		return productRepository.findById(id).map(product -> {
			productRepository.save(product);
			return true;
		}).orElse(false);
		
	} */
	
	public boolean deleteProduct(Long id) {
	    return productRepository.findById(id).map(product -> {
	        product.setActive(false); // <--- THIS IS THE KEY!
	        productRepository.save(product); // Now save the change
	        return true;
	    }).orElse(false);
	}
	


	public List<ProductResponse> searchProducts(String keyword) {
		return productRepository.searchProducts(keyword).stream()
				.map(this::maptoProductResponse).collect(Collectors.toList());
	}
}
