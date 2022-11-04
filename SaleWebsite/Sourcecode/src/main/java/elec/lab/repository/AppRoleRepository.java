package elec.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import elec.lab.domain.AppRole;



@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
	
}
