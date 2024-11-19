package com.unisinos;

public class R {

    private static final int REPETITIONS = 3;

    public static String encode(String input) {
        StringBuilder encoded = new StringBuilder();
        for (char character : input.toCharArray()) {
            String binary = String.format("%8s", Integer.toBinaryString(character)).replace(' ', '0');
            for (char bit : binary.toCharArray()) {
                encoded.append(String.valueOf(bit).repeat(REPETITIONS));
            }
        }
        return encoded.toString();
    }

    public static String decode(String input) {
        StringBuilder decodedBinary = new StringBuilder();
        for (int i = 0; i < input.length(); i += REPETITIONS) {
            String block = input.substring(i, Math.min(i + REPETITIONS, input.length()));
            long onesCount = block.chars().filter(ch -> ch == '1').count();
            decodedBinary.append(onesCount > REPETITIONS / 2 ? '1' : '0');
        }
        StringBuilder decodedText = new StringBuilder();
        for (int i = 0; i < decodedBinary.length(); i += 8) {
            String byteString = decodedBinary.substring(i, Math.min(i + 8, decodedBinary.length()));
            decodedText.append((char) Integer.parseInt(byteString, 2));
        }
        return decodedText.toString();
    }
}
