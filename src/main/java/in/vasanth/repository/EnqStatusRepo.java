package in.vasanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.vasanth.entity.EnquiryEntity;



public interface EnqStatusRepo extends JpaRepository<EnquiryEntity, Integer>{

}
