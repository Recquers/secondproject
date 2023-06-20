package in.vasanth.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.vasanth.binding.LoginForm;
import in.vasanth.binding.SignUpForm;
import in.vasanth.binding.UnlockForm;
import in.vasanth.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	
	
	@PostMapping("/login")
	public String loginUser(  @ModelAttribute("loginUser") LoginForm login ,Model model) {
		System.out.println(login);
		String status = userService.login(login);
		if(status.contains("success")) {
			return "redirect:/dashboard";
		}
		
		model.addAttribute("error", status);
		return "login";
	}
	
	
	@GetMapping("/login")
	public String login(Model model) {
		
		model.addAttribute("loginUser", new LoginForm());
		
		return "login";
	}
	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user") SignUpForm form,Model model) {
		boolean status=userService.signUp(form);
		if(status) {
			model.addAttribute("Successmsg", "Check your mail");
		}
		else {
			model.addAttribute("Errmsg", "Give unique mailID");
		}
		
		
		return "signup";
	}
	
	@GetMapping("/signUp")
	public String signUp(Model model) {
		
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}
	
	
	@PostMapping("/unlock")
	public String unlockAcc(@ModelAttribute("unlockAcc")  UnlockForm unlock,Model model) {
		if(unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			String status = userService.unlock(unlock);
			
			if(status.equals("unlocked")) {
				model.addAttribute("success", "Account Unlocked");
			}
			else if(status.equals("tempPwderror")){
				model.addAttribute("error", "TempPwd is incorrect, Check email");
			}
			else {
				model.addAttribute("error", "Account already Unlocked");

				
			}
			
		}
		else {
			model.addAttribute("error", "New password and Confirm password should be same");
			
		}
		
		
		
		
		return "unlock";
	}
	
	@GetMapping("/unlock")
	public String unlock(@RequestParam String email,Model model) {
		
		UnlockForm unlockObj= new UnlockForm();
		unlockObj.setEmail(email);
		
		
		model.addAttribute("unlockAcc",unlockObj);
		
		
		return "unlock";
	}
	
	@PostMapping("/forgotPwd")
	public String forgotPassword(@RequestParam String email,Model model) {
		
		String status = userService.forgotPwd(email);
		if(status.contains("success")) {
			model.addAttribute("success", "Password is sent to mail");
			
		}
		else {
		model.addAttribute("error", status);}
		
		
		
		return "forgotPwd";
	}
	
	@GetMapping("/forgotPwd")
	
	public String forgotPwdPage() {
		
		
		
		
		return "forgotPwd";
	}

}
