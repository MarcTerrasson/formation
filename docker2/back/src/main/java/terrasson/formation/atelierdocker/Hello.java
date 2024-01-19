package terrasson.formation.atelierdocker;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8181")
@RestController
@RequestMapping("api/v2")
public class Hello {

	@GetMapping("/devs/names")
	public List<String> getDevsNames() {
		return List.of("Marc");
	}
}
