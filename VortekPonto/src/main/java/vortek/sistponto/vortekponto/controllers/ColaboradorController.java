package vortek.sistponto.vortekponto.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import vortek.sistponto.vortekponto.dto.ColaboradorComEmpresasDto;
import vortek.sistponto.vortekponto.dto.ColaboradorDto;
import vortek.sistponto.vortekponto.dto.ColaboradorRequest;
import vortek.sistponto.vortekponto.exceptions.ObjectNotFoundException;
import vortek.sistponto.vortekponto.services.AzureBlobService;
import vortek.sistponto.vortekponto.services.ColaboradorService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/colaborador")
public class ColaboradorController {

    @Autowired
    private ColaboradorService colaboradorService;

    @Autowired
    private AzureBlobService azureBlobService;

    private final String containerName = "colab-foto";

    @GetMapping()
    public List<ColaboradorComEmpresasDto> listarTodos() {
        List<ColaboradorComEmpresasDto> colaboradores = colaboradorService.listarTodos();
        return (colaboradores != null) ? colaboradores : Collections.emptyList();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ColaboradorRequest request) {
        try {
            ColaboradorDto colaborador = colaboradorService.salvar(request.colaborador(), request.empresasId());
            return ResponseEntity.status(HttpStatus.CREATED).body(colaborador);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Erro ao criar colaborador: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            ColaboradorComEmpresasDto colaborador = colaboradorService.buscarPorId(id);
            if (colaborador == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(colaborador);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> excluirFunc(@PathVariable Integer id) {
        boolean isDeleted = colaboradorService.excluirFunc(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody ColaboradorRequest colaboradorAtualizado) throws IOException {
        try {
            ColaboradorDto colaborador = colaboradorService.atualizar(id, colaboradorAtualizado.colaborador(), colaboradorAtualizado.empresasId());
            return ResponseEntity.ok(colaborador);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @PostMapping("/{id}/foto")
    public ResponseEntity<String> salvarFoto(@PathVariable Integer id, @RequestParam("foto") MultipartFile foto)
            throws IOException {
        try {
            String imageUrl = azureBlobService.salvarFoto(foto, containerName);
            colaboradorService.atualizarFoto(id, imageUrl); // Atualiza a URL da foto no banco de dados
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao fazer upload da foto: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> baixarFoto(@PathVariable Integer id) {
        try {
            // Obtenha a URL da foto do banco de dados usando o ID do colaborador
            String imageUrl = colaboradorService.buscarFotoUrl(id);

            // Extraia o nome do arquivo da URL
            String fileName = colaboradorService.extrairNomeArquivoDaUrl(imageUrl);

            byte[] imageBytes = azureBlobService.baixarFoto(fileName, containerName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Fotos do tipo JPEG
            headers.setContentLength(imageBytes.length);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}