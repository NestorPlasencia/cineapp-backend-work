package com.mitocode.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.Rol;
import com.mitocode.repo.IRolRepo;
import com.mitocode.service.IRolService;

@Service
public class RepoServiceImpl implements IRolService{

	@Autowired
	private IRolRepo repo;
	
	@Override
	public Rol registrar(Rol rol) {
		return repo.save(rol);
	}

	@Override
	public Rol modificar(Rol rol) {
		return repo.save(rol);
	}

	@Override
	public List<Rol> listar() {
		return repo.findAll();
	}

	@Override
	public Rol listarPorId(Integer id) { 
		Optional<Rol> op = repo.findById(id);
		return op.isPresent() ? op.get() : new Rol();
	}

	@Override
	public void eliminar(Integer id) {
		repo.deleteById(id);
	}

}
