package com.bolsasdeideas.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsasdeideas.springboot.app.model.dao.IClienteDao;
import com.bolsasdeideas.springboot.app.model.entity.Cliente;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	
	@Autowired
	@Qualifier("ClienteDaoJPA") // Se utiliza Qualifier para indicar la implementacion que queremos tomar, para este caso se puede omitir
                              	//porque solo hay una interface
	private IClienteDao clienteDao;
	
	@RequestMapping(value="/listar", method= RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteDao.findAll());
		return "listar";
	}
	
	@RequestMapping(value="/form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de cliente");
		return "form"; 
	}
	
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable long id, Map<String, Object> model) {
		
		Cliente cliente = null;
		
		if(id > 0) {
			 cliente = clienteDao.findOne(id); 
		}else {
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar cliente");
		return "form";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status) { // La anotacion Valid habilita la validacion en form, BindingResult valida que el formulario no tenga errores. 
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}
		this.clienteDao.save(cliente);
		status.setComplete();
		return "redirect:/listar";
	}

}
