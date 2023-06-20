package in.vasanth.entity;

import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class UserDtls {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String name;
	
	private String email;
	private String password;
	
	private Long phone;
	private String accStatus;
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private List<EnquiryDtls> enquiries;
	

}
