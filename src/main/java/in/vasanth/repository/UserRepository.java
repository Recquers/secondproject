package in.vasanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.vasanth.entity.UserDtls;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {
	
	public UserDtls findByEmail(String email);
	
	public UserDtls findByEmailAndPassword(String email,String password);


}