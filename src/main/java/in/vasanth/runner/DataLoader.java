package in.vasanth.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.vasanth.entity.CourseEntity;
import in.vasanth.entity.EnquiryEntity;
import in.vasanth.repository.CourseRepo;
import in.vasanth.repository.EnqStatusRepo;
@Component
public class DataLoader implements ApplicationRunner{
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo enqRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
	courseRepo.deleteAll();	
	CourseEntity c1=new CourseEntity();
	CourseEntity c2=new CourseEntity();
	CourseEntity c3=new CourseEntity();
	CourseEntity c4=new CourseEntity();
	c1.setCourseName("Java");
	c2.setCourseName("Python");
	c3.setCourseName("Devops");
	c4.setCourseName(".Net");
	courseRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
	enqRepo.deleteAll();
	EnquiryEntity e1=new EnquiryEntity();
	EnquiryEntity e2=new EnquiryEntity();
	EnquiryEntity e3=new EnquiryEntity();
	e1.setEnquiryStatus("New");
	e2.setEnquiryStatus("Enrolled");
	e3.setEnquiryStatus("Lost");
	enqRepo.saveAll(Arrays.asList(e1,e2,e3));

		
		
	}
	

}
