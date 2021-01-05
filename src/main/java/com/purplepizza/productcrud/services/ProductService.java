package com.purplepizza.productcrud.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.purplepizza.productcrud.entities.Product;
import com.purplepizza.productcrud.entities.dto.ProductDTO;
import com.purplepizza.productcrud.repositories.ProductRepository;
import com.purplepizza.productcrud.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setDate(dto.getDate());
		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = new Product();
			entity.setId(id);
			entity.setName(dto.getName());
			entity.setDescription(dto.getDescription());
			entity.setPrice(dto.getPrice());
			entity.setDate(dto.getDate());
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}	
	}
	
	public void delete(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	
	
}
