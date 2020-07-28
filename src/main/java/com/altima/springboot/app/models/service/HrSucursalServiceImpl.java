package com.altima.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.altima.springboot.app.models.entity.HrSucursal;
import com.altima.springboot.app.repository.HrSucursalRepository;


@Service
public class HrSucursalServiceImpl implements IHrSucursalService {
	@Autowired
	private HrSucursalRepository repository;
	@Override
	@Transactional(readOnly=true)
	public List<HrSucursal> findAll() {
		// TODO Auto-generated method stub
		return (List<HrSucursal>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(HrSucursal hrsucursal) {
		// TODO Auto-generated method stub
		repository.save(hrsucursal);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public HrSucursal findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

}
