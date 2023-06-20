package in.vasanth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vasanth.binding.DashBoardForm;
import in.vasanth.binding.EnquiryForm;
import in.vasanth.binding.EnquirySearch;
import in.vasanth.entity.CourseEntity;
import in.vasanth.entity.EnquiryDtls;
import in.vasanth.entity.EnquiryEntity;
import in.vasanth.entity.UserDtls;
import in.vasanth.repository.CourseRepo;
import in.vasanth.repository.EnqStatusRepo;
import in.vasanth.repository.StudentEnqRepo;
import in.vasanth.repository.UserRepository;
@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnqStatusRepo enqRepo;
	@Autowired
	private StudentEnqRepo stuRepo;
	
	@Autowired
	private HttpSession session;

	@Override
	public List<String> getCourseNames() {
		List<CourseEntity> findAll = courseRepo.findAll();
		List<String> courses=new ArrayList<>();
		
		for(CourseEntity entity:findAll) {
			courses.add(entity.getCourseName());
		}
		
		
		return courses;
	}

	@Override
	public List<String> getEnquiryStatus() {
		 List<EnquiryEntity> findAll = enqRepo.findAll();
		 List<String> enqStatus=new ArrayList<>();
		 for(EnquiryEntity entity:findAll) {
			 enqStatus.add(entity.getEnquiryStatus());
		 }
		 
		return enqStatus;
	}

	@Override
	public boolean addEnquiry(EnquiryForm form) {
		Integer userId = (Integer)session.getAttribute("userId");
		
		
		EnquiryDtls enqDtls=new EnquiryDtls();
		BeanUtils.copyProperties(form, enqDtls);
		
		System.out.println(enqDtls);
		UserDtls userDtls = userRepo.findById(userId).get();
		
		enqDtls.setUser(userDtls);	
		
		stuRepo.save(enqDtls);
		
		return true;
	}

	@Override
	public DashBoardForm getDashboardData(Integer userId) {
		
		DashBoardForm response=new DashBoardForm();
		Optional<UserDtls> findById = userRepo.findById(userId);
		if(findById.isPresent()) {
			UserDtls userDtls = findById.get();
			List<EnquiryDtls> enquiries = userDtls.getEnquiries();
			Integer totalcnt = enquiries.size();
			Integer enrolledCnt = enquiries.stream().filter(e-> e.getEnquiryStatus().
					equals("Enrolled")).collect(Collectors.toList()).size();
			Integer lostCnt=enquiries.stream().filter(e-> e.getEnquiryStatus().
					equals("Lost")).collect(Collectors.toList()).size();
			response.setTotalEnquiryCnt(totalcnt);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);
			
		}
		
		
		
		return response;
	}

	@Override
	public List<EnquiryDtls> getEnquiries(Integer userId) {
	 Optional<UserDtls> findById = userRepo.findById(userId);
	 if(findById.isPresent()) {
	       UserDtls userDtls = findById.get();
			List<EnquiryDtls> enquiries = userDtls.getEnquiries();
			return enquiries;
	}
		return null;
	}

	@Override
	public EnquiryDtls editEnquiry(Integer enqId) {
		Integer userId = (Integer)session.getAttribute("userId");
		UserDtls userDtls = userRepo.findById(userId).get();
		List<EnquiryDtls> enquiries = userDtls.getEnquiries();
			EnquiryDtls enqDtls=enquiries.stream()
				.filter(e-> e.getEnquiryId().equals(enqId)).findAny().get();
				
		
		return enqDtls;
	}

	@Override
	public List<EnquiryDtls> getEnquiries(Integer userId, EnquirySearch criteria) {
		Optional<UserDtls> findById = userRepo.findById(userId);
		 if(findById.isPresent()) {
		       UserDtls userDtls = findById.get();
				List<EnquiryDtls> enquiries = userDtls.getEnquiries();
				if(null!=criteria.getCourse()&!"".equals(criteria.getCourse())) {
					enquiries= enquiries.stream()
					.filter(e-> e.getCourseName().equals(criteria.getCourse()))
					.collect(Collectors.toList());
				}
				if(null!=criteria.getEnqStatus()&!"".equals(criteria.getEnqStatus())) {
					enquiries= enquiries.stream()
					.filter(e-> e.getEnquiryStatus().equals(criteria.getEnqStatus()))
					.collect(Collectors.toList());
				}
				if(null!=criteria.getClassMode()&!"".equals(criteria.getClassMode())) {
					enquiries= enquiries.stream()
					.filter(e-> e.getClassMode().equals(criteria.getClassMode()))
					.collect(Collectors.toList());
				}
				
				return enquiries;
		}
		
		return null;
	}

}
