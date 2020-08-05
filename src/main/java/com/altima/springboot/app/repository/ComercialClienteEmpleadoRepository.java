package com.altima.springboot.app.repository;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import com.altima.springboot.app.models.entity.ComercialClienteEmpleado;



@Repository

public interface ComercialClienteEmpleadoRepository extends CrudRepository<ComercialClienteEmpleado,Long> {
	

	
	
}




