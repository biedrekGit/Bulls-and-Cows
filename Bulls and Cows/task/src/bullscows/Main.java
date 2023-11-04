package bullscows;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("Input the length of the secret code:");
        Scanner scanner = new Scanner(System.in);
        StringBuilder code = new StringBuilder();
        StringBuilder hiddenCode = new StringBuilder();
        StringBuilder test = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyz");
        String inputLength = scanner.nextLine();
        try {
            Integer.parseInt(inputLength);
        } catch (Exception e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", inputLength);
            return;
        }
        int lengthOfSC = Integer.parseInt(inputLength);
        if (lengthOfSC < 1) {
            System.out.println("Error: the number should be bigger than '0'");
            return;
        }
        System.out.println("Input the number of possible symbols in the code:");
        String inputSymbols = scanner.nextLine();
        try {
            Integer.parseInt(inputSymbols);
        } catch (Exception e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", inputSymbols);
            return;
        }
        int numberOfSymbols = Integer.parseInt(inputSymbols);
        if (numberOfSymbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        } else if (numberOfSymbols < lengthOfSC) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", lengthOfSC, numberOfSymbols );
            return;
        }
        StringBuilder range = new StringBuilder("(0");
        if (numberOfSymbols <= 1) {
            range.append(")");
        } else if (numberOfSymbols > 0 && numberOfSymbols <= 10) {
            char tempC = test.charAt(numberOfSymbols - 1);
            range.append("-" + tempC + ")");
        } else {
            char tempC = test.charAt(numberOfSymbols - 1);
            range.append("-9, a-" + tempC + ")");
        }
        //scanner.nextLine(); // discard newline
        Random random = new Random();
        int lower = 48;
        int upper;
        if (numberOfSymbols < 11) {
            upper = lower + numberOfSymbols;
        } else {
            upper = 97 + numberOfSymbols - 11;
        }
        if (lengthOfSC > 36) {
            System.out.println("Error: can't generate a secret number with a length of 37 because there aren't enough unique digits.");
            return;
        } else {
            do {
                int pRN;
                do {
                    pRN = random.nextInt(upper - lower + 1) + lower;
                } while (57 < pRN && pRN < 97);
                // System.out.println(pRN);
                char tempChar = (char) pRN;
                // System.out.println(tempChar);
                String tempString = Character.toString(tempChar);
                if (code.length() == lengthOfSC) {
                    break;
                } else if (test.toString().contains(tempString)) {
                    code.append(tempString);
                    hiddenCode.append("*");
                    int tempInt = test.toString().indexOf(tempString);
                    test.deleteCharAt(tempInt);
                }
            } while (code.length() < lengthOfSC);
        }
        System.out.printf("The secret is prepared: %s %s.", hiddenCode, range);
        System.out.println("\nOkay, let's start a game!");
        int turnNumber = 1;
        boolean playContinue = true;
        do {
            System.out.printf("Turn %d:\n", turnNumber);
            String userInput = scanner.nextLine();
            String secretCode = code.toString();
            char[] codeArray = secretCode.toCharArray();
            char[] inputArray = userInput.toCharArray();
            int tempCows = 0;
            int countBulls = 0;
            for (int i = 0; i < codeArray.length; i++) {
                if (codeArray[i] == inputArray[i]) {
                    countBulls++;
                }
                for (char c : inputArray) {
                    if (codeArray[i] == c) {
                        tempCows++;
                    }
                }
            }
            int countCows = tempCows - countBulls;

            if (countBulls == codeArray.length) {
                System.out.printf("Grade: %d bull(s)\n", countBulls);
                System.out.println("Congratulations! You guessed the secret code.");
                playContinue = false;
            } else if (countCows == 0 && countBulls == 0) {
                System.out.println("Grade: None");
            } else if (countBulls == 0) {
                System.out.printf("Grade: %d cow(s)", countCows);
            } else if (countCows == 0) {
                System.out.printf("Grade: %d bull(s)", countBulls);
            } else {
                System.out.printf("Grade: %d bull(s) and %d cow(s)", countBulls, countCows);
            }

            turnNumber++;
            System.out.println();
        } while (playContinue);
    }
}
