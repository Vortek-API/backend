package vortek.sistponto.VortekPonto.Dto;

import java.sql.Blob;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import vortek.sistponto.VortekPonto.Models.Empresa;

public class EmpresaDto {

    @NotBlank
    @Column(name = "id")
    private Integer id_emp;

    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank(message = "O cnpj é obrigatório")
    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;
    @Column(name = "telefone", nullable = false, unique = true)
    private String telefone;
    @Column(name = "logo", nullable = true)
    private Blob logo;


    public EmpresaDto() {
    }

    public EmpresaDto( String nome, String cnpj, String telefone, Blob logo) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.logo = logo;
    }

    public EmpresaDto(Empresa empresa) {
        this.id_emp = empresa.getId();
        this.nome = empresa.getNome();
        this.cnpj = empresa.getCnpj();
        this.telefone = empresa.getTelefone();
        this.logo = empresa.getLogo();
    }

    @NotBlank
    public Integer getId() {
        return id_emp;
    }

    public void setId(@NotBlank Integer id_emp) {
        this.id_emp = id_emp;
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

    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Blob getLogo() {
        return logo;
    }

    public void setLogo(Blob logo) {
        this.logo = logo;
    }
}
