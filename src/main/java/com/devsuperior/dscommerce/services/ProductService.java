package com.devsuperior.dscommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repository;
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> result = repository.findById(id); //busca no banco o produto = id
		Product product = result.get();// atribui o resutl set para a variavel product
		//agora tem que copiar os dados do result set(result) para o produto product. Para não fazer isso abaixo tem a criação no contrutor
		//do ProductDTO
		//ProductDTO dto = new ProductDTO(product.getId(), product.getName(), product.getDescription()......
		ProductDTO dto = new ProductDTO(product); // copia para a variavel dto o product para fornecer ao controlador
		return dto;
		
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		Page<Product> result = repository.findAll(pageable);
		return result.map(x -> new ProductDTO(x));
		
		
	}	

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		//copiar para a entidade os dados que vieram da dto(la do controler)
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setimgUrl(dto.getImgUrl());
		entity = repository.save(entity);
		
		return new ProductDTO(entity);
		
		
	}	

	
}
