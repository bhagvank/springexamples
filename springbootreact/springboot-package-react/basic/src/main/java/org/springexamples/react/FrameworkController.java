package org.springexamples.react;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller 
public class FrameworkController {

	@RequestMapping(value = "/")
	public String index() {
		return "index"; 
	}

}
