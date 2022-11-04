package elec.lab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import elec.lab.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Page<Category> findByNameContaining(String name, Pageable page);
	
	@Query(value = "select count(category_id) as category_id from categories", nativeQuery = true)
	Long countBycategory();
	
//	Page<Product> findById(Long id, Pageable page);
	
	
//	@Query(value = "delete from products where category_id = ?", nativeQuery = true)
//	public void deleteProductByCategoryId(Long id);
}
