package in.vasanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.vasanth.entity.EnquiryDtls;


public interface StudentEnqRepo extends JpaRepository<EnquiryDtls, Integer>{

}
