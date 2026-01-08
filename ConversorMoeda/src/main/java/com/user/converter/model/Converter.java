package com.user.converter.model;

import java.util.Map;

public class Converter {
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