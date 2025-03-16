package vortek.sistponto.VortekPonto.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import vortek.sistponto.VortekPonto.Models.Empresa;
import vortek.sistponto.VortekPonto.Models.Usuario;

public class EmpresaDto {

    @NotBlank
    @Column(name = "id_emp", nullable = false)
    private long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "nome_emp", nullable = false)
    private String nome;

    @NotBlank(message = "O cnpj é obrigatório")
    @Column(name = "cnpj_emp", nullable = false, unique = true)
    private String cnpj;

    @NotBlank(message = "O tipo de usuario é obrigatório")
    @Column(name = "Usuario_id_usuar", nullable = false)
    private Usuario idUsuario;

    public EmpresaDto() {}

    public EmpresaDto(long id, String nome, String cnpj, Usuario idUsuario) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.idUsuario = idUsuario;
    }

    public EmpresaDto(Empresa empresa) {
        this.id = empresa.getId();
        this.nome = empresa.getNome();
        this.cnpj = empresa.getCnpj();
        this.idUsuario = empresa.getIdUsuario();
    }

    @NotBlank
    public long getId() {
        return id;
    }

    public void setId(@NotBlank long id) {
        this.id = id;
    }

    public @NotBlank(message = "O nome é obrigatório") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome é obrigatório") String nome) {
        this.nome = nome;
    }

    public @NotBlank(message = "O cnpj é obrigatório") String getCnpj() {
        return cnpj;
    }

    public void setCnpj(@NotBlank(message = "O cnpj é obrigatório") String cnpj) {
        this.cnpj = cnpj;
    }

    public @NotBlank(message = "O tipo de usuario é obrigatório") Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(@NotBlank(message = "O tipo de usuario é obrigatório") Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

}
