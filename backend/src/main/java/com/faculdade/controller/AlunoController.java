package com.faculdade.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faculdade.model.Aluno;
import com.faculdade.model.Turma;
import com.faculdade.repository.AlunoRepository;
import com.faculdade.repository.TurmaRepository;
import com.faculdade.service.AlunoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private TurmaRepository turmaRepository;

	@GetMapping
	public List<Aluno> listarTodosAlunos() {
		return alunoRepository.findAll();
	}

	@GetMapping("/{matricula}")
	public ResponseEntity<Aluno> buscarPorMatricula(@PathVariable Integer matricula) {
		Aluno aluno = alunoRepository.findByMatricula(matricula);

		if (aluno != null) {
			return ResponseEntity.ok(aluno);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<Aluno> criarAluno(@Valid @RequestBody Aluno novoAluno) {
		Aluno alunoSalvo = alunoService.criarNovoAluno(novoAluno);
		return ResponseEntity.status(HttpStatus.CREATED).body(alunoSalvo);
	}

	@PutMapping("/{matricula}")
	public ResponseEntity<Aluno> atualizarAluno(@PathVariable Integer matricula,
			@Valid @RequestBody Aluno dadosAtualizados) {
		Aluno alunoExistente = alunoRepository.findByMatricula(matricula);

		if (alunoExistente == null) {
			return ResponseEntity.notFound().build();
		}

		dadosAtualizados.setId(alunoExistente.getId());

		Aluno alunoSalvo = alunoRepository.save(dadosAtualizados);

		return ResponseEntity.ok(alunoSalvo);
	}

	@DeleteMapping("/{matricula}")
	public ResponseEntity<?> deletarAluno(@PathVariable Integer matricula) {
		// 1 Busca o aluno que sera deletado
		Aluno alunoParaDeletar = alunoRepository.findByMatricula(matricula);
		if (alunoParaDeletar == null) {
			return ResponseEntity.notFound().build();
		}

		// 2 Busca todas as turmas em que o aluno esta
		String stringDeBusca = alunoParaDeletar.getMatricula() + ":" + alunoParaDeletar.getNome();
		List<Turma> turmasDoAluno = turmaRepository.findByAlunosStrContaining(stringDeBusca);

		// 3 Remove o aluno de cada uma dessas turmas
		for (Turma turma : turmasDoAluno) {
			List<String> alunosList = Arrays.asList(turma.getAlunosStr().split(","));

			List<String> novaListaDeAlunos = alunosList.stream().filter(alunoStr -> !alunoStr.equals(stringDeBusca))
					.collect(Collectors.toList());

			String novaStringDeAlunos = String.join(",", novaListaDeAlunos);
			turma.setAlunosStr(novaStringDeAlunos);

			// Salva a turma atualizada
			turmaRepository.save(turma);
		}

		// 4 Finalmente, deleta o aluno
		alunoRepository.delete(alunoParaDeletar);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/buscar")
	public List<Aluno> buscarAlunosPorNome(@RequestParam("nome") String nome) {
		return alunoRepository.findByNomeContainingIgnoreCase(nome);
	}

}