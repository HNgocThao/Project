package elec.lab.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import elec.lab.domain.AppRole;
import elec.lab.domain.Customer;
import elec.lab.domain.UserRole;
import elec.lab.repository.AppRoleRepository;
import elec.lab.repository.CustomerRepository;
import elec.lab.repository.UserRoleRepository;

@Service
public class UserDetailService implements UserDetailsService{

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AppRoleRepository appRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Customer> userOpt = customerRepository.FindByEmail(email);

		if (userOpt.isEmpty()) {
			System.out.println("Email not found! " + email);
			throw new UsernameNotFoundException("Email: " + email + " was not found in the database");
		}

		System.out.println("Found User: " + userOpt.get());
		Optional<UserRole> urole = userRoleRepository.findByCustomerId(Long.valueOf(userOpt.get().getCustomerId()));
		Optional<AppRole> arole = appRoleRepository.findById(urole.get().getAppRole().getRoleId());

		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority = new SimpleGrantedAuthority(arole.get().getName());
		grantList.add(authority);
		
		System.out.println(arole.get().getName());

		UserDetails userDetails = (UserDetails) new User(userOpt.get().getEmail(),
				userOpt.get().getPassword().trim(), grantList);

		return userDetails;
	}
}
