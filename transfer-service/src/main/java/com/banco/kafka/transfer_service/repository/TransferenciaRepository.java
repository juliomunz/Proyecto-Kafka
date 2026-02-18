package com.banco.kafka.transfer_service.repository;

import com.banco.kafka.transfer_service.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferenciaRepository extends  JpaRepository<Transferencia, String>{

}
