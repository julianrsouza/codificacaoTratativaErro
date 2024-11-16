package com.unisinos;

public class EncoderDecoder {
    
    public String encode(String input, String method) {
        switch (method) {
            case "Hamming":
                return Hamming74.encode(input);
            case "R":
                return R.encode(input);
            default:
                return "Método desconhecido";
        }
    }

    public String decode(String input, String method) {
        switch (method) {
            case "Hamming":
                return Hamming74.decode(input);
            case "R":
                return R.decode(input);
            default:
                return "Método desconhecido";
        }
    }
}
