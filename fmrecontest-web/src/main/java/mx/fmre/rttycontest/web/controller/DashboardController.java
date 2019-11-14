package mx.fmre.rttycontest.web.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/dashboard")
public class DashboardController {

	@GetMapping
	public String findAll(@RequestParam Map<String, Object> params, Model model) {
		return "dashboard/dashboard";
	}

//	@GetMapping(value = "/test")
//	public ModelAndView getTestData(Model model) {
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("/dashboard/dashboard");
//		mv.getModel().put("data", "Welcome home man");
//		return mv;
//	}
}