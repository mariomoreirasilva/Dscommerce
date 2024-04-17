package com.devsuperior.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.exceptions.DatabaseException;
import com.devsuperior.dscommerce.exceptions.ResourceNotFoundException;
import com.devsuperior.dscommerce.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {		
		Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado")) ; 
		return new ProductDTO(product); 
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(String name, Pageable pegeable) {
		Page<Product> result = repository.searchByName(name, pegeable);
		return result.map(x -> new ProductDTO(x));
		
		
	}	

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		//copiar para a entidade os dados que vieram da dto(la do controler)
		/*entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setimgUrl(dto.getImgUrl());
		entity = repository.save(entity);
		*/
		copyDTOToEntity(dto, entity);
		return new ProductDTO(entity);		
		
	}	

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try{//diferente do método findbyid. Não vai no banco e faz sql. Só prepara 
			Product entity = repository.getReferenceById(id);
			//copiar para a entidade os dados que vieram da dto(la do controler)
			/*entity.setName(dto.getName());
			entity.setDescription(dto.getDescription());
			entity.setPrice(dto.getPrice());
			entity.setimgUrl(dto.getImgUrl());
			entity = repository.save(entity);
			*/
			copyDTOToEntity(dto, entity);
			
			return new ProductDTO(entity);		
		}
		catch (EntityNotFoundException e){
			throw new ResourceNotFoundException("Recurso não encontrado."); 
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
	        	repository.deleteById(id);    		
		}
	    	catch (DataIntegrityViolationException e) {
	        	throw new DatabaseException("Falha de integridade referencial");
	   	}
	}

	private void copyDTOToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setimgUrl(dto.getImgUrl());
		entity = repository.save(entity);		
	}	


	
	
}
