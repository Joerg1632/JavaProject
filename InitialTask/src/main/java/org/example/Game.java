package org.example;
import java.util.*;
import java.lang.*;
public class Game {
    private final int[] secretNumber;
    private final Scanner in = new Scanner(System.in);

    public Game() {
        this.secretNumber = CreateSecretNumber();
    }

    public void Gameplay() {
        int attempts = 0;

        while (true) {
            String num = in.next();
            if (CorrectNumber(num)) {
                attempts++;

                if (attempts % 10 == 0) {
                    System.out.println("Friend, aren't you tired? Yes or No");
                    String answer = in.next();
                    if (answer.equals("Yes")) {
                        System.out.println("It was the number " + Arrays.toString(secretNumber).replaceAll("[\\[\\], ]", ""));
                        break;
                    }
                    else if(answer.equals(("No"))){
                        System.out.println("Let's! I believe in you");
                    }
                    else{
                        System.out.println("I didn't understand you, let's continue)))");
                    }
                }

                Map<String, Integer> Animals = new HashMap<>();
                Animals = NumberAnimals(num);

                System.out.println(attempts + " attempt result: " + Animals.get("cows") + " cows and " + Animals.get("bulls") + " bulls");

                if (Animals.get("bulls") == num.length()) {
                    System.out.println("Congratulations!!! You passed it on the " + attempts + "th attempt!");
                    break;
                }
            } else {
                System.out.println("Incorrect number entered, please try again");
            }

        }
    }

    private boolean CorrectNumber(String num) {
        return num.length() == 4 && num.matches("\\d{4}") && UniqueNumbers(num);
    }

    private boolean UniqueNumbers(String num) {
        HashSet CountUnuqueNum = new HashSet();
        for (char c : num.toCharArray()) {
            if (c >= '0' && c <= '9') {
                CountUnuqueNum.add(c);
            }
        }
        return CountUnuqueNum.toArray().length == num.length();
    }

    private int[] CreateSecretNumber() {
        Random r = new Random();
        int[] localSecretNum = new int[4];
        while(true){
            int randomValue = 1000 + r.nextInt(9000);

            if (UniqueNumbers(String.valueOf(randomValue))){
                localSecretNum = String.valueOf(randomValue).chars().map(Character::getNumericValue).toArray();
                break;
            }
        }
        return localSecretNum;
    }

    private Map<String, Integer> NumberAnimals(String num) {
        int[] intEnteredNum = convertStringToInt(num);
        Map<String, Integer> Animals = new HashMap<>();
        Animals.put("cows", 0);
        Animals.put("bulls", 0);
        for (int i = 0; i < intEnteredNum.length; i++){
            if(intEnteredNum[i] == secretNumber[i]){
                FillContainer(Animals, "bulls");
            }
            else if(digitInSecretNumber(intEnteredNum[i])){
                FillContainer(Animals, "cows");
            }
        }
        return Animals;
    }

    private void FillContainer(Map<String, Integer> Animals, String animal){
        int key = Animals.get(animal);
        ++key;
        Animals.put(animal,key);
    }

    private boolean digitInSecretNumber(int digit){
        for(int num: secretNumber){
            if(num == digit) {
                return true;
            }
        }
        return false;
    }

    private int[] convertStringToInt(String num) {
        int[] arr = new int[4];
        for(int i = 0; i < num.length(); i++){
            arr[i] = (int)num.toCharArray()[i] - '0';
        }
        return arr;
    }
}
