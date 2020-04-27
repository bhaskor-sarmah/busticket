package com.bohniman.covid.busticket.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.bohniman.covid.busticket.model.Applicant;
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

	@GetMapping(value = { "/" })
	public ModelAndView index(ModelAndView mv) {
		mv = new ModelAndView("unlogged/index");
		return mv;
	}

	@PostMapping(value = { "/fireOtp" })
	public ModelAndView otp(ModelAndView mv, HttpSession session, @RequestParam("mobile") String mobile) {

		if (mobile.length() != 10 || !Validation.isNumeric(mobile)) {
			mv = new ModelAndView("unlogged/index");
			mv.addObject("msg", "Invalid Mobile Number ! Please try again.");
			return mv;
		}

		String otp = RandomString.randomNumber(4);
		System.out.println(otp);
		session.setAttribute("otp", otp);
		if (FireSms.doFireSMS(mobile, otp)) {
			mv = new ModelAndView("unlogged/otp");
			mv.addObject("mobile", mobile);
			return mv;
		} else {
			mv = new ModelAndView("unlogged/index");
			mv.addObject("msg", "Failed Sending OTP ! Please try again.");
			return mv;
		}
	}

	@PostMapping(value = { "/validateOtp" })
	public ModelAndView validateOtp(ModelAndView mv, HttpSession session, @RequestParam("otp") String otp) {

		// if (otp.length() != 4 || !Validation.isNumeric(otp)) {
		// mv = new ModelAndView("unlogged/otp");
		// mv.addObject("msg", "Invalid OTP ! Please try again.");
		// return mv;
		// }

		if (session.getAttribute("otp").toString().equals(otp)) {
			mv = new ModelAndView("unlogged/form");
			mv.addObject("districtList", commonService.getAllDistrict());
			mv.addObject("purposeList", commonService.getAllPurpose());
			mv.addObject("applicant", new Applicant());
			return mv;
		} else {
			mv = new ModelAndView("unlogged/otp");
			mv.addObject("msg", "Invalid OTP ! Please try again.");
			return mv;
		}
	}

	@PostMapping(value = { "/saveApplicant" })
	public ModelAndView saveApplicant(ModelAndView mv, @Valid @ModelAttribute("applicant") Applicant applicant,
			BindingResult result) {

		// if (otp.length() != 4 || !Validation.isNumeric(otp)) {
		// mv = new ModelAndView("unlogged/otp");
		// mv.addObject("msg", "Invalid OTP ! Please try again.");
		// return mv;
		// }
		if (result.hasErrors()) {
			mv = new ModelAndView("unlogged/form");
			mv.addObject("districtList", commonService.getAllDistrict());
			mv.addObject("purposeList", commonService.getAllPurpose());
			mv.addObject("applicant", applicant);
			return mv;
		} else if (applicantService.saveApplicant(applicant)) {
			mv = new ModelAndView("unlogged/index");
			mv.addObject("successmsg", "Application Submitted successfully !");
			return mv;
		} else {
			mv = new ModelAndView("unlogged/form");
			mv.addObject("districtList", commonService.getAllDistrict());
			mv.addObject("purposeList", commonService.getAllPurpose());
			mv.addObject("applicant", applicant);
			mv.addObject("msg", "Failed saving Application ! Please try again.");
			return mv;
		}
	}

	@GetMapping(value = { "/access-denied" })
	public ModelAndView accessDenied(ModelAndView mv) {
		mv = new ModelAndView("error/403");
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