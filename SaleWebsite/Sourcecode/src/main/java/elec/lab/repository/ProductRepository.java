package elec.lab.repository;


import java.util.List;
//import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import elec.lab.domain.Product;
import elec.lab.model.ReportInventory;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
//	@Query(value = "select *  from products", nativeQuery = true)
	Page<Product> findByNameContaining(String name, Pageable page);

//	Page<Product> findAllByCategory(Pageable page);

	@Query(value = "select *  from products where category_id = ?", nativeQuery = true)
	Page<Product> findAllProductByCategoryId(Long id, Pageable pageable);
	
	@Query(value = "select *  from products where producer_id = ?", nativeQuery = true)
	Page<Product> findAllProductByProducerId(Long id, Pageable pageable);
	
	@Query(value = "select *  from products where category_id =? and producer_id = ?", nativeQuery = true)
	Page<Product> findAllProductById(Long id, Long id1,  Pageable pageable);
//	@Query(value = "select *  from products where producer_id =? and category_id in (select category_id from categories where category_id = ?)", nativeQuery = true)
//	Page<Product> findAllProductById(Long id, Long id1,  Pageable pageable);
	
	@Query(value = "select * from products where category_id = 1", nativeQuery = true)
	List<Product> getLaptop();

	@Query(value = "select * from products where category_id = 2", nativeQuery = true)
	List<Product> getKeyboard();
	
	@Query(value = "select * from products where category_id = 3", nativeQuery = true)
	List<Product> getMouse();
	
	@Query(value = "select * from products where category_id = 4", nativeQuery = true)
	List<Product> getRam();
	
	@Query(value = "select DISTINCT producer_name, products.producer_id from producers join products on producers.producer_id = products.producer_id where category_id = 1", nativeQuery = true)
	List<Product> getLaptopproducer();
	
	@Query(value = "select count(producer_id) as producer_id from products", nativeQuery = true)
	Long countByproduct();
	
//	@Query(value = "with product_row as(select *, ROW_NUMBER() over (PARTITION BY producer order by producer) as row_no\r\n"
//			+ "from products) select * from product_row where row_no=1", nativeQuery = true)
//	List<Product> getProducer();
//	@Query(value = "select * from products where producer like N'?'", nativeQuery = true)
//	Page<Product> findAllProducer(Long producer, Pageable pageable);
//	
//	@Query(value = "with product_row as(select *, ROW_NUMBER() over (PARTITION BY producer order by producer) as row_no\r\n"
//	+ "from products) select * from product_row where row_no=1", nativeQuery = true)
//	List<Product> getProducer();
	
	
	@Query("select new elec.lab.model.ReportInventory(o.name, sum(o.unitPrice), sum(o.quantityorder)) from Product o group by o.name order by sum(o.quantityorder) desc")
	List<ReportInventory> getTop10(Pageable pageable, Integer t1, Integer t2);
	
	@Query("select new elec.lab.model.ReportInventory(p.category, sum(p.unitPrice), sum(p.quantityorder)) from Product p group by p.category order by sum(p.quantityorder) desc")
	List<ReportInventory> getorderbycategory();
	
//	@Query("select new elec.lab.model.ReportInventory(o.category, sum((o.unitPrice-(o.unitPrice*o.discount/100))*o.quantity), sum(o.quantity)) from Product o group by o.category order by sum(o.quantity) desc")
	@Query("select new elec.lab.model.ReportInventory(o.category, sum(o.unitPrice*o.quantity), sum(o.quantity)) from Product o group by o.category order by sum(o.quantity) desc")
	List<ReportInventory> getInventoryByCategory();
	
	

	@Query(value = "select categories.category_name , sum(orderdetails.unit_price*orderdetails.quantity) as 'Tổng tiền', sum(orderdetails.quantity) as 'Số lượng' \r\n"
			+ "from orderdetails \r\n" + "join orders on orders.order_id = orderdetails.order_id\r\n"
			+ "join products on orderdetails.product_id = products.product_id \r\n"
			+ "join categories on categories.category_id = products.category_id\r\n" + "where orders.status = 2\r\n"
			+ "group by categories.category_name\r\n" + "order by sum(orderdetails.unit_price*orderdetails.quantity) desc", nativeQuery = true)
	List<Object[]> getBestSellingCategory();

	@Query(value = "select products.name , sum(orderdetails.unit_price*orderdetails.quantity) as 'Tổng tiền', sum(orderdetails.quantity) as 'Số lượng' from orderdetails \r\n"
			+ "join orders on orders.order_id = orderdetails.order_id\r\n"
			+ "join products on orderdetails.product_id = products.product_id \r\n" + "where orders.status = 2\r\n"
			+ "group by products.name\r\n" + "order by sum(orderdetails.quantity) desc", nativeQuery = true)
	List<Object[]> getBestSellingProduct();

	@Query(value = "select orders.customer_id, customers.name as 'Ten', COUNT(orders.order_id) as 'Tong so don', sum(orders.amount) as 'Tong tien'\r\n"
			+ "from orders\r\n"
			+ "join customers on customers.customer_id = orders.customer_id\r\n"
			+ "where orders.status = 2\r\n"
			+ "group by orders.customer_id,customers.name \r\n"
			+ "order by COUNT(orders.order_id) desc", nativeQuery = true)
	List<Object[]> getBestBuyer();

	@Query(value = "select order_date as 'Time' ,count(order_id) as 'so luong',sum(amount) as 'tong tien' from orders where status = 2\r\n"
			+ "group by order_date\r\n"
			+ "order by order_date desc", nativeQuery = true)
	List<Object[]> getStatisticalByDay();

	@Query(value = "select   cast(year(order_date) as varchar) + '-' +cast(month(order_date) as varchar) month, \r\n"
			+ "count(order_id) as 'count', sum(amount) as 'sum' from orders where status = 2\r\n"
			+ "group by month(order_date), year(order_date)\r\n"
			+ "order by month desc", nativeQuery = true)
	List<Object[]> getStatisticalByMonth();

	@Query(value = "select year(order_date) as 'year',count(order_id) as 'count', sum(amount) as 'sum' from orders where status = 2\r\n"
			+ "group by year(order_date)\r\n"
			+ "order by year(order_date) desc", nativeQuery = true)
	List<Object[]> getStatisticalByYear();

	

}