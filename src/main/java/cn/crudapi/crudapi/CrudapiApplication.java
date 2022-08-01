package cn.crudapi.crudapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crudapi")
@SpringBootApplication
public class CrudapiApplication {
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(CrudapiApplication.class, args);
	}

	@GetMapping(value="/blog")
    public List<Map<String, Object>> queryForList() {
        return namedParameterJdbcTemplate.queryForList("select * from blog", new HashMap<String, Object>());
    }
}
