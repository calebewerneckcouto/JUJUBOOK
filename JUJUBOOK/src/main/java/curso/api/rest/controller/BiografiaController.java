package curso.api.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BiografiaController {
	
	
	@GetMapping("/biografia")
	public String biografia() {
		return "biografia";
	}
	

}
