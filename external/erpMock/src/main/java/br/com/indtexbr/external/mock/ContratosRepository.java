package br.com.indtexbr.external.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import erp_mock.web_service.Areas;
import erp_mock.web_service.Contrato;


@Component
public class ContratosRepository {
	
	private static final Map<Integer, Contrato> contratos = new HashMap<>();

	@PostConstruct
	public void initData() {
		
		Contrato contrato1 = new Contrato();
		contrato1.setId(1);
		contrato1.setTexto("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed imperdiet placerat diam, at molestie dolor facilisis sed. Vestibulum pellentesque semper eros nec lacinia. Nam congue ante eget turpis rutrum, at facilisis dolor ultricies. Ut sit amet feugiat nulla, id facilisis elit. Vestibulum vehicula sapien nisi, vel finibus ante porttitor gravida. Etiam sit amet aliquet est. In ornare nisi et tristique laoreet. Morbi convallis libero finibus tristique tempus. Maecenas ullamcorper mauris in egestas scelerisque. Fusce eu tincidunt orci. Proin posuere volutpat tristique. Duis placerat in purus ac euismod. Vestibulum et arcu at eros tempor euismod. Aenean ligula lorem, vehicula in congue a, lacinia ac nisi. Integer mattis vestibulum est sit amet eleifend. Praesent eleifend gravida sapien a congue.\n"
				+ "\n"
				+ "Integer consectetur ipsum porta, porta lectus non, elementum lectus. Donec vehicula auctor ante nec aliquet. Ut ut leo ac eros efficitur vulputate quis at neque. Mauris lobortis nibh eget ante ornare dapibus. Sed id leo in dui sodales consectetur. Aenean congue tristique ligula, at venenatis ligula aliquam vestibulum. Vivamus id est et nulla dignissim lobortis. Integer placerat mauris in elementum maximus. Nam mauris tellus, elementum vitae aliquet ut, sodales vitae eros. Donec in facilisis felis.\n"
				+ "\n"
				+ "Praesent porttitor mauris mattis tempor eleifend. Etiam hendrerit orci in lectus suscipit, eu ultrices lacus tempus. Nullam id gravida ligula, volutpat eleifend quam. Pellentesque facilisis lacus nisl. Nam viverra, tellus sit amet auctor volutpat, lectus dui iaculis tortor, at molestie nulla erat quis tellus. Pellentesque rhoncus enim ut nulla consectetur, ut tempor ipsum aliquam. Duis consectetur auctor ex non tempor. Aenean dui purus, venenatis et enim at, congue tempor lectus. Maecenas id finibus urna, vel accumsan ante. Suspendisse ultricies nibh non erat molestie, sed dignissim metus finibus.\n"
				+ "\n"
				+ "Nunc sapien sapien, cursus eget arcu at, dictum vestibulum ipsum. Aenean scelerisque turpis quis laoreet convallis. Morbi posuere vestibulum blandit. Nullam a nunc eget eros tempus suscipit. Phasellus sed eleifend dui. Nullam ornare sapien metus, eu tempus lorem consequat a. Aenean auctor in ligula sed malesuada. Suspendisse molestie, ligula et fermentum congue, ex justo rutrum tellus, id semper lectus purus id neque. Aenean sit amet sodales odio, ut placerat enim. Fusce nec lobortis libero. Etiam feugiat tellus id pellentesque varius. Aenean porttitor ipsum risus, ut semper libero tempus vitae. Donec facilisis sapien nec orci molestie tempus. Sed porta laoreet metus.\n"
				+ "\n"
				+ "Etiam eu molestie orci. Suspendisse orci purus, viverra sed tempor id, condimentum a metus. Mauris lacinia risus vel diam mattis suscipit. Donec ut ligula ultricies, aliquam nisi sed, mollis neque. Proin nec vulputate tellus, sed maximus nunc. Vivamus sollicitudin libero vel dolor bibendum, quis ultricies diam laoreet. Ut sit amet nisl turpis. Nullam mattis aliquet nibh a mattis. Integer imperdiet orci a facilisis tempus. Fusce eget tempus libero. Etiam a elementum nulla. Vestibulum et tortor sit amet nunc rutrum feugiat sit amet in tellus. Nunc tristique ultrices tellus quis pharetra. Ut vitae turpis sed ligula bibendum interdum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Nullam quis diam mollis, venenatis tortor non, vehicula metus.");
		contrato1.setArea(Areas.TI);	
		
		Contrato contrato2 = new Contrato();
		contrato2.setId(2);
		contrato2.setTexto("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed imperdiet placerat diam, at molestie dolor facilisis sed. Vestibulum pellentesque semper eros nec lacinia. Nam congue ante eget turpis rutrum, at facilisis dolor ultricies. Ut sit amet feugiat nulla, id facilisis elit. Vestibulum vehicula sapien nisi, vel finibus ante porttitor gravida. Etiam sit amet aliquet est. In ornare nisi et tristique laoreet. Morbi convallis libero finibus tristique tempus. Maecenas ullamcorper mauris in egestas scelerisque. Fusce eu tincidunt orci. Proin posuere volutpat tristique. Duis placerat in purus ac euismod. Vestibulum et arcu at eros tempor euismod. Aenean ligula lorem, vehicula in congue a, lacinia ac nisi. Integer mattis vestibulum est sit amet eleifend. Praesent eleifend gravida sapien a congue.\n"
				+ "\n"
				+ "Integer consectetur ipsum porta, porta lectus non, elementum lectus. Donec vehicula auctor ante nec aliquet. Ut ut leo ac eros efficitur vulputate quis at neque. Mauris lobortis nibh eget ante ornare dapibus. Sed id leo in dui sodales consectetur. Aenean congue tristique ligula, at venenatis ligula aliquam vestibulum. Vivamus id est et nulla dignissim lobortis. Integer placerat mauris in elementum maximus. Nam mauris tellus, elementum vitae aliquet ut, sodales vitae eros. Donec in facilisis felis.\n"
				+ "\n"
				+ "Praesent porttitor mauris mattis tempor eleifend. Etiam hendrerit orci in lectus suscipit, eu ultrices lacus tempus. Nullam id gravida ligula, volutpat eleifend quam. Pellentesque facilisis lacus nisl. Nam viverra, tellus sit amet auctor volutpat, lectus dui iaculis tortor, at molestie nulla erat quis tellus. Pellentesque rhoncus enim ut nulla consectetur, ut tempor ipsum aliquam. Duis consectetur auctor ex non tempor. Aenean dui purus, venenatis et enim at, congue tempor lectus. Maecenas id finibus urna, vel accumsan ante. Suspendisse ultricies nibh non erat molestie, sed dignissim metus finibus.\n"
				+ "\n"
				+ "Nunc sapien sapien, cursus eget arcu at, dictum vestibulum ipsum. Aenean scelerisque turpis quis laoreet convallis. Morbi posuere vestibulum blandit. Nullam a nunc eget eros tempus suscipit. Phasellus sed eleifend dui. Nullam ornare sapien metus, eu tempus lorem consequat a. Aenean auctor in ligula sed malesuada. Suspendisse molestie, ligula et fermentum congue, ex justo rutrum tellus, id semper lectus purus id neque. Aenean sit amet sodales odio, ut placerat enim. Fusce nec lobortis libero. Etiam feugiat tellus id pellentesque varius. Aenean porttitor ipsum risus, ut semper libero tempus vitae. Donec facilisis sapien nec orci molestie tempus. Sed porta laoreet metus.\n"
				+ "\n"
				+ "Etiam eu molestie orci. Suspendisse orci purus, viverra sed tempor id, condimentum a metus. Mauris lacinia risus vel diam mattis suscipit. Donec ut ligula ultricies, aliquam nisi sed, mollis neque. Proin nec vulputate tellus, sed maximus nunc. Vivamus sollicitudin libero vel dolor bibendum, quis ultricies diam laoreet. Ut sit amet nisl turpis. Nullam mattis aliquet nibh a mattis. Integer imperdiet orci a facilisis tempus. Fusce eget tempus libero. Etiam a elementum nulla. Vestibulum et tortor sit amet nunc rutrum feugiat sit amet in tellus. Nunc tristique ultrices tellus quis pharetra. Ut vitae turpis sed ligula bibendum interdum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Nullam quis diam mollis, venenatis tortor non, vehicula metus.");
		contrato2.setArea(Areas.INSUMOS);		
		
		contratos.put(contrato1.getId(), contrato1);
		contratos.put(contrato2.getId(), contrato2);
	}

	public Contrato findContrato(Integer id) {
		Assert.notNull(id, "O id do Contrato não pode ser nulo");
		return contratos.get(id);
	}
	
	public List<Contrato> findAll() {
		return new ArrayList<Contrato>(contratos.values());
	}
}