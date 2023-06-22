package chucknorris;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    static StringBuilder output = new StringBuilder();
    static Scanner sc = new Scanner(System.in);

    static void binaryForm(char[] chArr) {
        String value;
        String sequence = "";

        for (char c : chArr) {
            int ch = (int) c;
            value = Integer.toBinaryString(ch);
            value = value.replace(' ', '0');
            if (value.length() < 7) {
                value = "0" + value;
            }
           /* System.out.printf("%c = %07d\n", c, Integer.parseInt(value));
            System.out.print(c + " = ");
            System.out.println(value);*/

            sequence += value;
        }
        chuckNorris(sequence);
    }

    static void chuckNorris(String sequence) {
        int len = sequence.length();
        int i = 0;

        while (i < len) {
            if (sequence.charAt(i) == '1') {
                output.append("0 ");
                while (i < len && sequence.charAt(i) == '1') {
                    output.append("0");
                    i++;
                }
                output.append(" ");
            } else if (sequence.charAt(i) == '0') {
                output.append("00 ");
                while (i < len && sequence.charAt(i) == '0') {
                    output.append("0");
                    i++;
                }
                output.append(" ");
            }
        }
        System.out.println("Encoded string:");
        System.out.println(output);
    }

    static String zerosToBinary(String[] strArr) {
        output.setLength(0);
        int len = strArr.length;
        int i = 0;

        while (i < len) {
            if (strArr[i].equals("0")) {
                String s = strArr[i + 1];
                String[] arr = s.split("");
                String ones = "1".repeat(arr.length);
                output.append(ones);
            }
            if (strArr[i].equals("00")) {
                String s = strArr[i + 1];
                String[] arr = s.split("");
                String zeros = "0".repeat(arr.length);
                output.append(zeros);
            }
            i += 2;
        }
        String decode = output.toString();
        output.setLength(0);
        return decode;
    }

    static void splitBinary(String decode) {
        StringBuilder o = new StringBuilder();
        int len = decode.length();
        int i = 0;

        while (i < len) {
            if (output.length() == 7) {
                o.append(output).append(" ");
                output.setLength(0);
            }
            output.append(decode.charAt(i));
            i++;
        }
        o.append(output);

        String code = o.toString();
        output.setLength(0);
        binaryToDecimal(code);
    }

    static void binaryToDecimal(String code) {
        String[] strArr = code.split(" ");
        int value = 0;
        int pow;

        for (String s : strArr) {
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == '1') {
                    pow = Math.abs(j - 6);
                    value += Math.pow(2, pow);
                }
            }
            output.append(value).append(" ");
            value = 0;
        }
        String values = output.toString();
        output.setLength(0);
        decimalToChar(values);
    }

    static void decimalToChar(String values) {
        String[] strArr = values.split(" ");
        int len = strArr.length;
        int[] charValues = new int[len];

        for (int i = 0; i < len; i++) {
            charValues[i] = Integer.parseInt(strArr[i]);
            char ch = (char) charValues[i];
            output.append(ch);
        }

        System.out.println("Decoded string:");
        System.out.println(output);
    }

    static boolean checkForLetters(String input) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(input);
        boolean containsLetters = matcher.find();

        return containsLetters;
    }

    static boolean checkInput(String input) {
        String[] strArr = input.split(" ");
        if (checkForLetters(input)) {
            System.out.println("Encoded string is not valid.\n");
            return false;
        }
        if (input.contains("1")) {
            System.out.println("Encoded string is not valid.\n");
            return false;
        }
        if (strArr.length % 2 != 0) {
            System.out.println("Encoded string is not valid.\n");
            return false;
        }
        return true;
    }

    static void operation() {
        boolean op = true;
        String choice;
        String input;

        while (op) {
            System.out.println("Please input operation (encode/decode/exit):");
            choice = sc.nextLine().trim();

            switch (choice) {
                case "encode":
                    System.out.println("Input string:");
                    input = sc.nextLine();
                    char[] chArr = input.toCharArray();
                    binaryForm(chArr);
                    System.out.println();
                    break;
                case "decode":
                    System.out.println("Input encoded string:");
                    input = sc.nextLine().trim();

                    if (checkInput(input)) {
                        String[] strArr = input.split(" ");
                        String decode = zerosToBinary(strArr);
                        if (strArr.length % 2 != 0) {
                            System.out.println("Encoded string is not valid.\n");
                            break;
                        }
                        if (decode.length() % 7 != 0) {
                            System.out.println("Encoded string is not valid.\n");
                            break;
                        }
                        splitBinary(decode);
                        System.out.println();
                    }
                    break;
                case "exit":
                    System.out.println("Bye!");
                    op = false;
                    break;
                default:
                    String wrong = "There is no '%s' operation\n".formatted(choice);
                    System.out.println(wrong);

            }
        }
    }

    public static void main(String[] args) {
        operation();
    }
}
