package in.vasanth.service;

import in.vasanth.binding.LoginForm;
import in.vasanth.binding.SignUpForm;
import in.vasanth.binding.UnlockForm;

public interface UserService {
	
	public String login(LoginForm form);
	
	public boolean signUp(SignUpForm form);
	
	public String unlock(UnlockForm form);
	
	public String forgotPwd(String email);

}
