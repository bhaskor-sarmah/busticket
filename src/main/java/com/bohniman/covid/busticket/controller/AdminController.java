package com.bohniman.covid.busticket.controller;

import javax.validation.Valid;

import com.bohniman.covid.busticket.model.Applicant;
import com.bohniman.covid.busticket.services.ApplicantService;
import com.bohniman.covid.busticket.services.CommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    CommonService commonService;

    @Autowired
    ApplicantService applicantService;

    @GetMapping(value = { "/page-home" })
    public ModelAndView noRole(ModelAndView mv) {
        mv = new ModelAndView("admin/index");
        mv.addObject("stateList", commonService.getAllState());
        mv.addObject("purposeList", commonService.getAllPurpose());
        mv.addObject("applicant", new Applicant());
        return mv;
    }

    @PostMapping(value = { "/saveApplicant" })
    public ModelAndView saveApplicant(ModelAndView mv, @Valid @ModelAttribute("applicant") Applicant applicant,
            BindingResult result) {

        if (applicantService.checkIfMobileExist(applicant.getMobile())) {
            result.rejectValue("mobile", "error.mobile", "Mobile Number already registered");
        }

        if (result.hasErrors()) {
            mv = new ModelAndView("admin/index");
            mv.addObject("stateList", commonService.getAllState());
            mv.addObject("purposeList", commonService.getAllPurpose());
            mv.addObject("applicant", applicant);
            return mv;
        } else {
            applicant.setType("OFFICIAL");
            if (applicantService.saveApplicant(applicant)) {
                mv = new ModelAndView("admin/index");
                mv.addObject("successmsg", "Application Saved successfully !");
                return mv;
            } else {
                mv = new ModelAndView("admin/index");
                mv.addObject("stateList", commonService.getAllState());
                mv.addObject("purposeList", commonService.getAllPurpose());
                mv.addObject("applicant", applicant);
                mv.addObject("msg", "Failed saving Application ! Please try again.");
                return mv;
            }
        }
    }

    @GetMapping(value = { "/report" })
    public ModelAndView report(ModelAndView mv) {
        mv = new ModelAndView("admin/report");
        mv.addObject("applicantList", applicantService.getAllApplicant());
        return mv;
    }

    @PostMapping(value = { "/updateDetails" })
    public ModelAndView updateDetails(ModelAndView mv, @RequestParam("applicantId") Long applicantId) {
        mv = new ModelAndView("admin/update");
        mv.addObject("stateList", commonService.getAllState());
        mv.addObject("purposeList", commonService.getAllPurpose());
        mv.addObject("applicant", applicantService.getApplicantById(applicantId));
        return mv;
    }

    @PostMapping(value = { "/updateApplicant" })
    public ModelAndView updateApplicant(ModelAndView mv, @Valid @ModelAttribute("applicant") Applicant applicant,
            BindingResult result) {
        if (result.hasErrors()) {
            mv = new ModelAndView("admin/update");
            mv.addObject("stateList", commonService.getAllState());
            mv.addObject("purposeList", commonService.getAllPurpose());
            mv.addObject("applicant", applicant);
            return mv;
        } else {
            applicant.setType("OFFICIAL");
            if (applicantService.saveApplicant(applicant)) {
                mv = new ModelAndView("admin/report");
                mv.addObject("applicantList", applicantService.getAllApplicant());
                mv.addObject("successmsg", "Applicant Updated Successfully");
                return mv;
            } else {
                mv = new ModelAndView("admin/update");
                mv.addObject("stateList", commonService.getAllState());
                mv.addObject("purposeList", commonService.getAllPurpose());
                mv.addObject("applicant", applicant);
                mv.addObject("msg", "Failed Updating Applicat ! Please try again.");
                return mv;
            }
        }

    }
}