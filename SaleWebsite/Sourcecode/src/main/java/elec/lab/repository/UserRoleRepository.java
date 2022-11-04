package elec.lab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import elec.lab.domain.UserRole;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	
	@Query(value = "select top 1 * from userrole where customer_id = ?", nativeQuery = true)
	Optional<UserRole> findByCustomerId(Long customerId);
	
	
}
