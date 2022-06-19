package com.kazuya.product.service;

import com.kazuya.product.service.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

  private static final Map<Long, ProductDto> MOCK_DATA =
      Map.of(
          1L,
          new ProductDto(1, "The Art of Doing Science and Engineering", 2300),
          2L,
          new ProductDto(2, "The Making of Prince of Persia: Journals 1985-1993", 2500),
          3L,
          new ProductDto(3, "Working in Public: The Making and Maintenance of Open Source", 2800));

  public Optional<ProductDto> getProductById(long id) {
    return Optional.ofNullable(MOCK_DATA.get(id));
  }
}
