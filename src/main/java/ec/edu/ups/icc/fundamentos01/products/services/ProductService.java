package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.fundamentos01.products.dtos.ProductResponseDto;

public class ProductService {

    
    List<ProductResponseDto> findAll();

    ProductResponseDto findOne(int id);

    ProductResponseDto create(CreateProductDto dto);

    ProductResponseDto update(int id, UpdateProductDto dto);

    ProductResponseDto partialUpdate(int id, PartialUpdateProductDto dto);

    void delete(int id);
}
  
}
