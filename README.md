# Conversor de Moedas - CLI (Java + Maven)

Conversor de moedas por terminal que consulta uma API de taxas de câmbio (por padrão `exchangerate.host`, que não necessita de chave).

## O que tem aqui
- CLI interativo com menu (mínimo 6 opções de conversão)
- Cliente HTTP que busca taxas em tempo real (configurável)
- Desserialização JSON com Gson
- `pom.xml` (Maven)
- `run.sh` / `run.bat`
- `sample.env` com configuração de API (opcional)

## Como rodar
1. Com Maven instalado:
```bash
mvn compile exec:java -Dexec.mainClass="com.alura.currency.Main"
```

2. Usando o script (Linux/macOS):
```bash
chmod +x run.sh
./run.sh
```

3. Windows:
```cmd
run.bat
```

## Configuração da API
Por padrão o projeto usa `https://api.exchangerate.host` (sem necessidade de API key).

## Funcionalidades
- Menu com opções como: USD -> BRL, BRL -> USD, EUR -> BRL, BRL -> EUR, USD -> EUR e EUR -> USD (6 opções).
- Pede o valor ao usuário e retorna o valor convertido com duas casas decimais.
- Exibe a taxa usada e a data da cotação.
- Modo offline (cache simples): se a API falhar, avisa ao usuário.
- Código organizado em `service`, `model` e `app`.

## Observações
- Endpoints e políticas de APIs públicas podem mudar — neste projeto escolhemos `exchangerate.host` por ser simples e sem chave.
