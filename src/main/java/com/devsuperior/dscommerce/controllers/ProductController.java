package com.devsuperior.dscommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	
	@GetMapping(value = "/{id}")
	public ProductDTO findByID(@PathVariable Long id) {
	  ProductDTO dto = service.findById(id);
	  return dto;
	  //ou diretamente abaixo
	  //return service.findById(id);
	}

	@GetMapping
	public Page<ProductDTO> findAll(Pageable pageable) {
	  return service.findAll(pageable);
	}
	
	
	@PostMapping
	public ProductDTO insert(@RequestBody ProductDTO dto) {
		
		dto = service.insert(dto);
		return dto;
		// ou return service.insert(dto);
	  
	}
	
	
}