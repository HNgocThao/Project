package elec.lab.repository;

import java.util.List;

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import elec.lab.domain.Producer;





public interface ProducerRepository extends JpaRepository<Producer, Long>{

	Page<Producer> findByNameContaining(String name, Pageable page);
	
	@Query(value = "select * from producers", nativeQuery = true)
	List<Producer> getbb();
	
	@Query(value = "select DISTINCT producers.producer_id, producer_name from producers "
			+ "join products on producers.producer_id = products.producer_id where category_id = 1", nativeQuery = true)
	List<Producer> getLaptopproducer();
	@Query(value = "select DISTINCT producers.producer_id, producer_name from producers "
			+ "join products on producers.producer_id = products.producer_id where category_id = 2", nativeQuery = true)
	List<Producer> getKeyboardproducer();
	@Query(value = "select DISTINCT producers.producer_id, producer_name from producers "
			+ "join products on producers.producer_id = products.producer_id where category_id = 3", nativeQuery = true)
	List<Producer> getMouseroducer();
	@Query(value = "select DISTINCT producers.producer_id, producer_name from producers "
			+ "join products on producers.producer_id = products.producer_id where category_id = 4", nativeQuery = true)
	List<Producer> getRamproducer();
	
//	@Query(value = "select * from products where producer ='?'", nativeQuery = true)
//	Page<Product> findAllProducer(String producer, Pageable pageable);
//	
//	@Query(value = "with product_row as(select *, ROW_NUMBER() over (PARTITION BY producer order by producer) as row_no\r\n"
//	+ "from products) select * from product_row where row_no=1", nativeQuery = true)
//	List<Product> getProducer();
	
}
