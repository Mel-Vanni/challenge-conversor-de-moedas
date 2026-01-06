import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Main {

    public static void main(String[] args) {
        Client api = new Client("DIGITE_SUA_API_KEY_AQUI");
        final String BASE = "USD";

        try (Scanner sc = new Scanner(System.in)) {
            Map<String, Double> taxas = buscarTaxas(api, BASE);

            while (true) {
                System.out.println("\n=== Conversor de Moedas ===");
                System.out.println("1 - USD -> BRL");
                System.out.println("2 - BRL -> USD");
                System.out.println("3 - USD -> EUR");
                System.out.println("4 - EUR -> USD");
                System.out.println("0 - Sair");
                System.out.print("Opção: ");

                int opcao = sc.nextInt();
                if (opcao == 0) {
                    System.out.println("Encerrando...");
                    break;
                }

                String origem = "", destino = "";
                switch (opcao) {
                    case 1: origem = "USD"; destino = "BRL"; break;
                    case 2: origem = "BRL"; destino = "USD"; break;
                    case 3: origem = "USD"; destino = "EUR"; break;
                    case 4: origem = "EUR"; destino = "USD"; break;
                    default:
                        System.out.println("Opção inválida!");
                        continue;
                }

                System.out.print("Digite o valor: ");
                double valor = sc.nextDouble();

                double convertido = Conversor.converter(valor, origem, destino, taxas);
                System.out.printf("Resultado: %.2f %s%n", convertido, destino);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static Map<String, Double> buscarTaxas(Client api, String base) throws Exception {
        String json = api.getRates(base);
        return Filter.filtrarMoedas(json);
    }
}

class Client {
    private final String apiKey;
    private final HttpClient client;

    public Client(String apiKey) {
        this.apiKey = apiKey;
        this.client = HttpClient.newHttpClient();
    }

    public String getRates(String baseCurrency) throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        } else {
            throw new IOException("Erro na requisição. Status: " + response.statusCode());
        }
    }
}

class Filter {
    public static Map<String, Double> filtrarMoedas(String json) {
        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(json, JsonObject.class);
        JsonObject rates = obj.getAsJsonObject("conversion_rates");

        Map<String, Double> mapa = new HashMap<>();
        for (String key : rates.keySet()) {
            mapa.put(key, rates.get(key).getAsDouble());
        }
        return mapa;
    }
}

class Conversor {
    public static double converter(double valor, String origem, String destino, Map<String, Double> taxas) {
        if (!taxas.containsKey(origem) || !taxas.containsKey(destino)) {
            throw new IllegalArgumentException("Moeda não encontrada nas taxas.");
        }
        double taxaOrigem = taxas.get(origem);
        double taxaDestino = taxas.get(destino);

        double valorEmUSD = valor / taxaOrigem;
        return valorEmUSD * taxaDestino;
    }
}