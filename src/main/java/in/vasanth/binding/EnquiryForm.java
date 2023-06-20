package in.vasanth.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EnquiryForm {
	
	private Integer enquiryId;
	private String studentName;
	private Long phone;
	private String classMode;
	private String courseName;
	private String enquiryStatus;
	private LocalDate dateCreated;
	
	

}
