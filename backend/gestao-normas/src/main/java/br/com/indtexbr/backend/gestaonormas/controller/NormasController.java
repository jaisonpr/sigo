package br.com.indtexbr.backend.gestaonormas.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NormasController {

	private static final String PATH = "/v1/normas";

	@GetMapping(value = PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	List<String> all() {
		List<String> arrayList = new ArrayList<String>();
		arrayList.add("teste 1");
		arrayList.add("teste 2");
		arrayList.add("teste 3");
		return arrayList;
	}
}
