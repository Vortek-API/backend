package vortek.sistponto.VortekPonto.Configs.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

public class BlobSerializer extends JsonSerializer<Blob> {

    @Override
    public void serialize(Blob blob, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            if (blob != null) {
                byte[] byteArray = blob.getBytes(1, (int) blob.length());
                String base64Encoded = Base64.getEncoder().encodeToString(byteArray);
                gen.writeString(base64Encoded);  // Escreve como uma string Base64
            } else {
                gen.writeNull();  // Se o Blob for nulo, escreva null no JSON
            }
        } catch (SQLException e) {
            throw new IOException("Erro ao serializar o Blob", e);
        }
    }
}
