package com.alura.currency;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static final DecimalFormat df = new DecimalFormat("#,##0.00");

    public static void main(String[] args) {
        Properties env = loadEnv();
        String baseUrl = env.getProperty("EXCHANGE_API_BASE_URL", "https://api.exchangerate.host");
        String apiKey = env.getProperty("EXCHANGE_API_KEY", "");

        ExchangeClient client = new ExchangeClient(baseUrl, apiKey);
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Conversor de Moedas - CLI ===");
        System.out.println("API base: " + baseUrl);
        String[] options = {
                "USD -> BRL",
                "BRL -> USD",
                "EUR -> BRL",
                "BRL -> EUR",
                "USD -> EUR",
                "EUR -> USD"
        };

        for (int i=0;i<options.length;i++) {
            System.out.printf("%d) %s\n", i+1, options[i]);
        }
        System.out.println("0) Sair");

        while (true) {
            System.out.print("Escolha uma opção: ");
            String line = scanner.nextLine().trim();
            if (line.equals("0") || line.equalsIgnoreCase("sair")) break;
            try {
                int opt = Integer.parseInt(line);
                if (opt < 1 || opt > options.length) {
                    System.out.println("Opção inválida.");
                    continue;
                }
                String pair = options[opt-1];
                String[] currencies = pair.split("->");
                String from = currencies[0].trim();
                String to = currencies[1].trim();
                System.out.print("Digite o valor em " + from + ": ");
                String valStr = scanner.nextLine().trim();
                double val = Double.parseDouble(valStr.replace(",","."));
                try {
                    double converted = client.convert(from, to, val);
                    RateResponse rr = client.latest(from);
                    double rate = rr.getRates().get(to);
                    System.out.printf("%s %s = %s %s\n", df.format(val), from, df.format(converted), to);
                    System.out.printf("Taxa usada: 1 %s = %s %s (data: %s)\n", from, df.format(rate), to, rr.getDate());
                } catch (Exception ex) {
                    System.out.println("Erro ao obter taxa: " + ex.getMessage());
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Entrada inválida. Digite o número da opção.");
            }
        }

        System.out.println("Encerrando...");    
    }

    private static Properties loadEnv() {
        Properties p = new Properties();
        try (FileInputStream fis = new FileInputStream(".env")) {
            p.load(fis);
        } catch (IOException e) {
            // ignore, we'll use defaults and environment variables
        }
        String base = System.getenv("EXCHANGE_API_BASE_URL");
        String key = System.getenv("EXCHANGE_API_KEY");
        if (base != null) p.setProperty("EXCHANGE_API_BASE_URL", base);
        if (key != null) p.setProperty("EXCHANGE_API_KEY", key);
        return p;
    }
}
