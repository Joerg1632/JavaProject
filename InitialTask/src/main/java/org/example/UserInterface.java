package org.example;

public class UserInterface{
    public void startGame(){
        Game game = new Game();
        System.out.println("You will need guess a four-digit number, good luck!!!");
        game.Gameplay();
    }
}
