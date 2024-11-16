package com.unisinos;

public class Hamming74 {

    // Método para codificar um texto usando Hamming (7,4)
    public static String encode(String input) {
        // Converte o texto para binário
        String binaryInput = textToBinary(input);

        StringBuilder encoded = new StringBuilder();

        // Processar blocos de 4 bits
        for (int i = 0; i < binaryInput.length(); i += 4) {
            String dataBits = binaryInput.substring(i, Math.min(i + 4, binaryInput.length()));
            if (dataBits.length() < 4) {
                // Preencher com 0 se o bloco for incompleto
                dataBits = String.format("%-4s", dataBits).replace(' ', '0');
            }

            int d1 = Character.getNumericValue(dataBits.charAt(0));
            int d2 = Character.getNumericValue(dataBits.charAt(1));
            int d3 = Character.getNumericValue(dataBits.charAt(2));
            int d4 = Character.getNumericValue(dataBits.charAt(3));

            // Calculando os bits de paridade
            int p1 = d1 ^ d2 ^ d4;
            int p2 = d1 ^ d3 ^ d4;
            int p3 = d2 ^ d3 ^ d4;

            // Montando o código (p1, p2, d1, p3, d2, d3, d4)
            encoded.append(p1).append(p2).append(d1).append(p3).append(d2).append(d3).append(d4);
        }

        return encoded.toString();
    }

    // Método para decodificar binário para texto usando Hamming (7,4)
    public static String decode(String input) {
        StringBuilder decodedBinary = new StringBuilder();

        // Processar blocos de 7 bits
        for (int i = 0; i < input.length(); i += 7) {
            String codeWord = input.substring(i, Math.min(i + 7, input.length()));
            if (codeWord.length() < 7) {
                // Preencher com 0 se o bloco for incompleto
                codeWord = String.format("%-7s", codeWord).replace(' ', '0');
            }

            int p1 = Character.getNumericValue(codeWord.charAt(0));
            int p2 = Character.getNumericValue(codeWord.charAt(1));
            int d1 = Character.getNumericValue(codeWord.charAt(2));
            int p3 = Character.getNumericValue(codeWord.charAt(3));
            int d2 = Character.getNumericValue(codeWord.charAt(4));
            int d3 = Character.getNumericValue(codeWord.charAt(5));
            int d4 = Character.getNumericValue(codeWord.charAt(6));

            // Calculando o síndrome para verificar erros
            int s1 = p1 ^ d1 ^ d2 ^ d4;
            int s2 = p2 ^ d1 ^ d3 ^ d4;
            int s3 = p3 ^ d2 ^ d3 ^ d4;

            // Localizando o erro
            int errorPosition = s1 * 1 + s2 * 2 + s3 * 4;

            // Corrigir o erro se necessário
            if (errorPosition > 0) {
                int errorIndex = errorPosition - 1;
                char[] correctedCode = codeWord.toCharArray();
                correctedCode[errorIndex] = (correctedCode[errorIndex] == '0') ? '1' : '0';
                codeWord = new String(correctedCode);
            }

            // Extrair os 4 bits de dados
            decodedBinary.append(codeWord.charAt(2)).append(codeWord.charAt(4))
                         .append(codeWord.charAt(5)).append(codeWord.charAt(6));
        }

        // Converter o binário decodificado de volta para texto
        return binaryToText(decodedBinary.toString());
    }

    // Converte texto para binário
    private static String textToBinary(String text) {
        StringBuilder binary = new StringBuilder();
        for (char c : text.toCharArray()) {
            binary.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
        return binary.toString();
    }

    // Converte binário para texto
    private static String binaryToText(String binary) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 8) {
            String byteString = binary.substring(i, Math.min(i + 8, binary.length()));
            int charCode = Integer.parseInt(byteString, 2);
            text.append((char) charCode);
        }
        return text.toString();
    }
}
