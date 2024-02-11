package org.example;
import java.util.*;
import java.lang.*;
public class Game {
    private final int secret;

    public Game() {
        this.secret = createSecretNumber();
    }

    public int createSecretNumber(){
        return 2345;
    }


    public void gameplay() {
        System.out.println(secret);
    }

}
