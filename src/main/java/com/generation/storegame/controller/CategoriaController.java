package com.generation.storegame.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.generation.storegame.model.Categoria;
import com.generation.storegame.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Categoria criarCategoria(@RequestBody Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	@GetMapping
	public List<Categoria> listarCategorias() {
		return categoriaRepository.findAll();
	}

	@GetMapping("/{id}")
	public Categoria buscarCategoria(@PathVariable Long id) {
		return categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
	}

	@PutMapping("/{id}")
	public Categoria atualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
		Categoria categoriaExistente = categoriaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
		categoriaExistente.setNome(categoria.getNome());
		return categoriaRepository.save(categoriaExistente);
	}

	@DeleteMapping("/{id}")
	public void deletarCategoria(@PathVariable Long id) {
		Categoria categoria = categoriaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
		categoriaRepository.delete(categoria);
	}
}
