package vortek.sistponto.VortekPonto.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vortek.sistponto.VortekPonto.Dto.ColaboradorDto;
import vortek.sistponto.VortekPonto.Services.AzureBlobService;
import vortek.sistponto.VortekPonto.Services.ColaboradorService;
import vortek.sistponto.VortekPonto.Services.Exceptions.ObjectNotFoundException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/colaborador")
public class ColaboradorController {

    @Autowired
    private ColaboradorService colaboradorService;

    @Autowired
    private AzureBlobService azureBlobService;

    @GetMapping()
    public List<ColaboradorDto> listarTodos() {
        List<ColaboradorDto> colaboradores = colaboradorService.listarTodos();
        return (colaboradores != null) ? colaboradores : Collections.emptyList();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ColaboradorDto colaborador) {
        try {
            colaborador = colaboradorService.salvar(colaborador);
            return ResponseEntity.status(HttpStatus.CREATED).body(colaborador);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Erro ao criar colaborador: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            ColaboradorDto colaborador = colaboradorService.buscarPorId(id);
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
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody ColaboradorDto colaboradorAtualizado) {
        try {
            ColaboradorDto colaborador = colaboradorService.atualizar(id, colaboradorAtualizado);
            return ResponseEntity.ok(colaborador);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @PostMapping("/{id}/foto")
    public ResponseEntity<String> salvarFoto(@PathVariable Integer id, @RequestParam("foto") MultipartFile foto) throws IOException {
        try {
            String imageUrl = azureBlobService.salvarFoto(foto);
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

            byte[] imageBytes = azureBlobService.baixarFoto(fileName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Fotos do tipo JPEG
            headers.setContentLength(imageBytes.length);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}