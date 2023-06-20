package in.vasanth.service;

import java.util.List;

import in.vasanth.binding.DashBoardForm;
import in.vasanth.binding.EnquiryForm;
import in.vasanth.binding.EnquirySearch;
import in.vasanth.entity.EnquiryDtls;

public interface EnquiryService {
	
	public List<String> getCourseNames();
	
	public List<String> getEnquiryStatus();
	
	public boolean addEnquiry(EnquiryForm form );
	
	public DashBoardForm getDashboardData(Integer UserId);
	
	public List<EnquiryDtls> getEnquiries(Integer userId);
	
	public List<EnquiryDtls> getEnquiries(Integer userId,EnquirySearch criteria);
	
	public EnquiryDtls editEnquiry(Integer enqId);

}
