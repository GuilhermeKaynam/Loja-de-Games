package com.generation.storegame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.storegame.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
