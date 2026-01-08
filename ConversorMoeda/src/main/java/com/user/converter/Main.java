package com.user.converter;

import java.util.Map;
import java.util.Scanner;

import com.user.converter.api.Client;
import com.user.converter.model.Converter;
import com.user.converter.utility.Filter;

public class Main {
    public static void main(String[] args) {
        Client api = new Client("Digite_sua_key");
        final String BASE = "USD";

        try (Scanner sc = new Scanner(System.in)) {
            Map<String, Double> taxas = Filter.filtrarMoedas(api.getRates(BASE));

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

                String origem;
                String destino;

                switch (opcao) {
                    case 1 -> { origem = "USD"; destino = "BRL"; }
                    case 2 -> { origem = "BRL"; destino = "USD"; }
                    case 3 -> { origem = "USD"; destino = "EUR"; }
                    case 4 -> { origem = "EUR"; destino = "USD"; }
                    default -> {
                        System.out.println("Opção inválida!");
                        continue;
                    }
                }

                System.out.print("Digite o valor: ");
                double valor = sc.nextDouble();

                double convertido = Converter.converter(valor, origem, destino, taxas);
                System.out.printf("Resultado: %.2f %s%n", convertido, destino);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}