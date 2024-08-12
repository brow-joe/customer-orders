package br.com.customer.orders.infrastructure.repository;

import br.com.customer.orders.infrastructure.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Integer> { }