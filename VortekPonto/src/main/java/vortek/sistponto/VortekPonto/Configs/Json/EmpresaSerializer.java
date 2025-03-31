package vortek.sistponto.VortekPonto.Configs.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import vortek.sistponto.VortekPonto.Models.Colaborador;

import java.io.IOException;
import java.util.List;

public class EmpresaSerializer extends JsonSerializer<List<Colaborador>> {
    @Override
    public void serialize(List<Colaborador> colaboradores, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(colaboradores.size()); // Apenas o número de colaboradores
    }
}


