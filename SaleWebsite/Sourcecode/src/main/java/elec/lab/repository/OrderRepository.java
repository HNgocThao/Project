package elec.lab.repository;

import java.util.Date;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import elec.lab.domain.Order;
import elec.lab.model.Statisitc;



@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query(value = "select * from orders where customer_id = ?1 and status = ?2", nativeQuery = true)
	Page<Order> findByCustomerId(int id, int status, Pageable pageable);
	
	@Query(value = "select * from orders where status = ?", nativeQuery = true)
	Page<Order> findByStatus(int status, Pageable pageable);
	
	Page<Order> findByorderId(int id, Pageable pageable);
	
	@Query(value = "select count(order_id) as order_id from orders where status = 2", nativeQuery = true)
	Long countByorder();

	@Query(value = "select sum(amount) as amount from orders where status = 2", nativeQuery = true)
	Double tongtien();
	
	@Query(value = "select * from orders order by order_id desc ", nativeQuery = true)
	List<Order> listall();
	@Query(value = "select count(order_id) as order_id from orders where status = 0", nativeQuery = true)
	Long countAdminorder();
	
	@Query("SELECT new elec.lab.model.Statisitc(sum(o.orderId), sum(o.amount), o.orderDate) FROM Order o where status = 2  group by o.orderDate order by o.orderDate asc")
	List<Statisitc> getStatistic30Day();
	
	@Query("SELECT new elec.lab.model.Statisitc(sum(o.orderId), sum(o.amount), o.orderDate) FROM Order o where status = 2 and o.orderDate >=?1 AND o.orderDate <=?2 group by o.orderDate order by o.orderDate asc")
	List<Statisitc> getStatisticDayByDay(Date s1, Date s2 );
}
