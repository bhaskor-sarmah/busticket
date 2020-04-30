package com.bohniman.covid.busticket.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.bohniman.covid.busticket.model.Applicant;
import com.bohniman.covid.busticket.model.MasterDistrict;
import com.bohniman.covid.busticket.model.MasterSubDistrict;
import com.bohniman.covid.busticket.services.ApplicantService;
import com.bohniman.covid.busticket.services.CommonService;
import com.bohniman.covid.busticket.utils.FireSms;
import com.bohniman.covid.busticket.utils.RandomString;
import com.bohniman.covid.busticket.utils.Validation;

// import com.bohniman.covid.busticket.captcha.CaptchaGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

// import nl.captcha.Captcha;

/**
 * UnloggedController
 */
@RestController
public class UnloggedController {

	private static Logger log = LoggerFactory.getLogger(UnloggedController.class);

	// @Autowired
	// CaptchaGenerator captchaGenerator;

	@Autowired
	ApplicantService applicantService;

	@Autowired
	CommonService commonService;

	@GetMapping(value = { "/", "/login" })
	public ModelAndView index(ServletRequest request, ModelAndView mv) {
		mv = new ModelAndView("unlogged/index");

		Map<String, String[]> paramMap = request.getParameterMap();

		if (paramMap.containsKey("error")) {
			mv.addObject("loginError", "Invalid Username or Password");
		}
		return mv;
	}

	@PostMapping(value = { "/fireOtp" })
	public ModelAndView otp(ModelAndView mv, HttpSession session, @RequestParam("mobile") String mobile) {

		if (mobile.length() != 10 || !Validation.isNumeric(mobile)) {
			mv = new ModelAndView("unlogged/index");
			mv.addObject("msg", "Invalid Mobile Number ! Please try again.");
			return mv;
		} else if (applicantService.checkIfMobileExist(mobile)) {
			mv = new ModelAndView("unlogged/index");
			mv.addObject("msg", "Mobile Number already registered. Please use another number.");
			return mv;
		}

		String otp = RandomString.randomNumber(4);
		System.out.println(otp);
		session.setAttribute("otp", otp);
		session.setAttribute("mobile", mobile);
		if (FireSms.doFireSMS(mobile, otp)) {
			mv = new ModelAndView("unlogged/otp");
			return mv;
		} else {
			mv = new ModelAndView("unlogged/index");
			mv.addObject("msg", "Failed Sending OTP ! Please try again.");
			return mv;
		}
	}

	@PostMapping(value = { "/validateOtp" })
	public ModelAndView validateOtp(ModelAndView mv, HttpSession session, @RequestParam("otp") String otp,
			@RequestParam("mobile") String mobile) {

		// if (session.getAttribute("otp") != null &&
		// session.getAttribute("otp").toString().equals(otp)
		// && session.getAttribute("mobile") != null &&
		// session.getAttribute("mobile").toString().equals(mobile)) {
		mv = new ModelAndView("unlogged/form");
		mv.addObject("stateList", commonService.getAllState());
		mv.addObject("purposeList", commonService.getAllPurpose());
		mv.addObject("applicant", new Applicant());
		return mv;
		// } else {
		// mv = new ModelAndView("unlogged/otp");
		// mv.addObject("msg", "Invalid OTP ! Please try again.");
		// return mv;
		// }
	}

	@PostMapping(value = { "/saveApplicant" })
	public ModelAndView saveApplicant(HttpSession session, ModelAndView mv,
			@Valid @ModelAttribute("applicant") Applicant applicant, BindingResult result) {

		if (result.hasErrors()) {
			mv = new ModelAndView("unlogged/form");
			mv.addObject("stateList", commonService.getAllState());
			mv.addObject("purposeList", commonService.getAllPurpose());
			mv.addObject("applicant", applicant);
			return mv;
		} else {
			applicant.setMobile(session.getAttribute("mobile").toString());
			applicant.setType("PUBLIC");
			if (applicantService.saveApplicant(applicant)) {
				mv = new ModelAndView("unlogged/index");
				mv.addObject("successmsg", "Application Submitted successfully !");
				return mv;
			} else {
				mv = new ModelAndView("unlogged/form");
				mv.addObject("stateList", commonService.getAllState());
				mv.addObject("purposeList", commonService.getAllPurpose());
				mv.addObject("applicant", applicant);
				mv.addObject("msg", "Failed saving Application ! Please try again.");
				return mv;
			}
		}
	}

	@PostMapping(value = { "/GetDistrict" })
	@ResponseBody
	public List<MasterDistrict> getDistrict(@RequestParam("id") String stateCode) {
		return commonService.getDistrictByState(stateCode);
	}

	@PostMapping(value = { "/GetSubDistrict" })
	@ResponseBody
	public List<MasterSubDistrict> getSubDistrict(@RequestParam("id") String districtCode) {
		return commonService.getSubDistrictByDistrict(districtCode);
	}

	@GetMapping(value = { "/access-denied" })
	public ModelAndView accessDenied(ModelAndView mv) {
		mv = new ModelAndView("error/403");
		return mv;
	}

	@GetMapping(value = { "/no-role" })
	public ModelAndView noRole(ModelAndView mv) {
		mv = new ModelAndView("unlogged/index");
		mv.addObject("loginError", "Unauthorised ! Please try again.");
		return mv;
	}

	// @GetMapping(path = "/genCaptcha.png", produces = "image/png") // Map ONLY GET
	// Requests
	// @ResponseBody
	// public byte[] genCaptcha(HttpServletRequest request, HttpServletResponse
	// response, HttpSession httpSession) {
	// // List<Color> colors = new ArrayList<Color>();
	// // colors.add(Color.black);
	// // colors.add(Color.red);

	// // List<Font> fonts = new ArrayList<Font>();
	// // fonts.add(new Font("Comic Sans MS", Font.PLAIN, 40));

	// // Captcha captcha = new Captcha.Builder(170, 50).addText(new
	// // DefaultWordRenderer(colors, fonts))
	// // .addNoise(new StraightLineNoiseProducer()).addNoise().addBackground(new
	// // GradiatedBackgroundProducer())
	// // .addBorder().build();

	// Captcha captcha = captchaGenerator.createCaptcha(170, 50);
	// httpSession.setAttribute("captcha", captcha);

	// // request.setAttribute("answer", captcha.getAnswer());
	// // log.trace("Captcha answer : " + captcha.getAnswer());

	// response.setContentType("image/png");

	// ByteArrayOutputStream bao = new ByteArrayOutputStream();

	// try {

	// ImageIO.write(captcha.getImage(), "png", bao);
	// return bao.toByteArray();

	// } catch (IOException e) {

	// }

	// return null;
	// }
}