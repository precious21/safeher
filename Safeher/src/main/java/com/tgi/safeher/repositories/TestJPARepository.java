package com.tgi.safeher.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tgi.safeher.entity.ActiveDriverLocationEntity;
@Transactional
public interface TestJPARepository extends JpaRepository<ActiveDriverLocationEntity, Integer>{
	
}
