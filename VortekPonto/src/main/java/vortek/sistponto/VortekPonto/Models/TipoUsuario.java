package vortek.sistponto.VortekPonto.Models;

public enum TipoUsuario {
    ADMIN("ADMIN"),
    EMPRESA("EMPRESA"),
    COLABORADOR("COLABORADOR");

    private final String tipo;

    private TipoUsuario(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
