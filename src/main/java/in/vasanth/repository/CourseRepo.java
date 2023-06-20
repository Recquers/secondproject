package in.vasanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.vasanth.entity.CourseEntity;


public interface CourseRepo extends JpaRepository<CourseEntity, Integer>{

}
