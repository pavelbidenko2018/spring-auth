package com.pbidenko.springauth.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pbidenko.springauth.entity.Mail;
import com.pbidenko.springauth.entity.PasswordForgotDTO;
import com.pbidenko.springauth.entity.PasswordResetDTO;
import com.pbidenko.springauth.entity.PasswordResetToken;
import com.pbidenko.springauth.entity.Usr;
import com.pbidenko.springauth.repository.ForgetPasswordRepo;
import com.pbidenko.springauth.service.EmailService;
import com.pbidenko.springauth.service.UsrStorageService;

@Controller
public class ForgetPasswordController {

	@Autowired
	private ForgetPasswordRepo forgetPasswordRepo;

	@Autowired
	private UsrStorageService usrStorageService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ForgetPasswordRepo tokenRepository;
	
	@Autowired private BCryptPasswordEncoder passwordEncoder;

	@ModelAttribute(name = "passwordResetForm")
	public PasswordResetDTO getPasswordResetDTO() {
		return new PasswordResetDTO();
	}

	@GetMapping("/forgetPassword")
	public String forgetPwd() {
		return "forget-password";
	}

	@PostMapping("/forgetPassword")
	@ResponseBody
	public String processForgetPasswordForm(
			@ModelAttribute("forgetPasswordEmailForm") @Valid PasswordForgotDTO entryForm, BindingResult result,
			HttpServletRequest request) {

		if (result.hasErrors()) {
			return "forgetPassword";
		}

		Usr user = usrStorageService.findByEmail(entryForm.getEmail());

		PasswordResetToken resetToken = new PasswordResetToken();

		resetToken.setToken(UUID.randomUUID().toString());
		resetToken.setExpireDate(60);
		resetToken.setUsr(user);

		forgetPasswordRepo.save(resetToken);

		Mail mail = new Mail();
		mail.setFrom("pavelbidenko2018@gmail.com");
		mail.setTo(user.getEmail());
		mail.setSubject("Password reset request");

		Map<String, Object> model = new HashMap<>();
		model.put("token", resetToken);
		model.put("user", user);
		model.put("signature", "https://communalka.com");

		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		model.put("resetUrl", url + "/reset-password?token=" + resetToken.getToken());
		mail.setModel(model);
		emailService.sendMailToUser(mail);

		return "redirect:/forgot-password?success";

	}

	@GetMapping("/reset-password")
	public String displayResetPasswordPage(@RequestParam(required = false) String token, Model model) {

		PasswordResetToken resetToken = tokenRepository.findByToken(token);
		if (resetToken == null) {
			model.addAttribute("error", "Could not find password reset token.");
		} else if (resetToken.isExpired()) {
			model.addAttribute("error", "Token has expired, please request a new password reset.");
		} else {
			model.addAttribute("token", resetToken.getToken());
		}

		return "reset-password";
	}

	@PostMapping("/reset-password")
	public String setNewPaswword(@ModelAttribute("passwordResetForm") @Valid PasswordResetDTO resetForm,
			BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute(BindingResult.class.getName() + ".passwordResetForm", result);
			redirectAttributes.addFlashAttribute("passwordResetForm", resetForm);
			return "redirect:/reset-password?token=" + resetForm.getToken();
		}

		PasswordResetToken token = tokenRepository.findByToken(resetForm.getToken());
		Usr usr = token.getUsr();
		if (usr == null) {
			return null;
		}

		String encodedPassword = passwordEncoder.encode(resetForm.getPassword());
		usr.setPwd(encodedPassword);

		usrStorageService.save(usr);

		tokenRepository.delete(token);

		return "redirect:/login";
	}

}
