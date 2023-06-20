package in.vasanth.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vasanth.binding.LoginForm;
import in.vasanth.binding.SignUpForm;
import in.vasanth.binding.UnlockForm;
import in.vasanth.entity.UserDtls;
import in.vasanth.helper.EmailUtils;
import in.vasanth.helper.PwdUtils;
import in.vasanth.repository.UserRepository;

@Service

public class UserServiceImpl implements UserService {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private EmailUtils email;
	
	@Autowired
	private UserRepository repoUser;

	@Override
	public String login(LoginForm form) {
		UserDtls entity = repoUser.findByEmailAndPassword(form.getEmail(), form.getPassword());
		
		if(entity==null) {
			return "Invalid-Credentials";
		}
		if(entity.getAccStatus().equals("LOCKED")) {
			return "AccountLocked";
		}
		
		session.setAttribute("userId", entity.getUserId());
		return "success";
	}

	@Override
	public boolean signUp(SignUpForm form) {
		UserDtls user=repoUser.findByEmail(form.getEmail());
		if(user!=null) {
			return false;
		}
		//TODO password generation
		
		String pwd=PwdUtils.generateRandomPwd();
		
		//TODO Copy objects
		
		UserDtls ud=new UserDtls();
		
		BeanUtils.copyProperties(form, ud);
		
		ud.setPassword(pwd);
		
		ud.setAccStatus("LOCKED");
		
		repoUser.save(ud);
		
		//TODO email generation
		
		String to=form.getEmail();
		String subject="Unlock your Acccount";
		StringBuffer body= new StringBuffer();//http://localhost:8080/unlock?email= "+to+"
		
		body.append("<h1>Use below password to unlock account</h1>");
		body.append("TempPwd = "+pwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email="+to+"\">Click here to unlock account</a>");
		
		
		email.sendEmail(to, subject, body.toString());
		
		//TODO 
		
		return true;
	}

	@Override
	public String unlock(UnlockForm form) {
		System.out.println(form);
		
		UserDtls entity = repoUser.findByEmail(form.getEmail());
		if(entity.getAccStatus().equals("LOCKED")) {
		if(entity.getPassword().equals(form.getTempPwd())) {
			entity.setPassword(form.getConfirmPwd());
			entity.setAccStatus("UNLOCKED");
			
			repoUser.save(entity);
			return "unlocked";
			
		}
		else {
			return "tempPwderror";
		}
		}
		return "Alreadyunlocked";
		
		
	}

	@Override
	public String forgotPwd(String email) {
		UserDtls entity = repoUser.findByEmail(email);
		if(entity==null) {
			return "Invalid Email";
		}
		String subject="Recover Password";
		
		this.email.sendEmail(email,subject ," Your password is "+entity.getPassword());
		
		return "success";
	}

}
