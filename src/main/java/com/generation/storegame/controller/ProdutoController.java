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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.generation.storegame.model.Produto;
import com.generation.storegame.repository.CategoriaRepository;
import com.generation.storegame.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto criarProduto(@Valid @RequestBody Produto produto) {
		if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
			throw new RuntimeException("Categoria não pode ser nula");
		}

		categoriaRepository.findById(produto.getCategoria().getId())
				.orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

		return produtoRepository.save(produto);
	}

	@GetMapping
	@JsonIgnoreProperties("categoria")
	public List<Produto> listarProdutos() {
		return produtoRepository.findAll();
	}

	@GetMapping("/{id}")
	public Produto buscarProduto(@PathVariable Long id) {
		return produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
	}

	@PutMapping("/{id}")
	public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
		Produto produtoExistente = produtoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
		produtoExistente.setNome(produto.getNome());
		produtoExistente.setPreco(produto.getPreco());
		produtoExistente.setCategoria(produto.getCategoria());
		return produtoRepository.save(produtoExistente);
	}

	@DeleteMapping("/{id}")
	public void deletarProduto(@PathVariable Long id) {
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
		produtoRepository.delete(produto);
	}
}
