import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//La clase ConsultaInfoMoneda proporciona funcionalidad para obtener información actual sobre los tipos de cambio de moneda a través de una API externa.
public class ConsultaInfoMoneda {
    public Conversor buscaMoneda(String denominacionBase){
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/b78810a09c3fa67e166c3db4/latest/"+denominacionBase);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();
        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), Conversor.class);
        } catch (Exception e) {
            throw new RuntimeException("NO SE REALIZO LA CONVERSION");
        }
    }
}
