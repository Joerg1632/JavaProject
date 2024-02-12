package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testCorrectNumber() {
        Game game = new Game();
        assertTrue(game.CorrectNumber("1234"));
        assertFalse(game.CorrectNumber("12ab"));
        assertFalse(game.CorrectNumber("12345"));
    }

    @Test
    public void testUniqueNumbers() {
        Game game = new Game();
        assertTrue(game.UniqueNumbers("1234"));
        assertFalse(game.UniqueNumbers("1123"));
        assertFalse(game.UniqueNumbers("abcd"));
    }

    @Test
    public void testNumberAnimals() {
        Game game = new Game(new int[]{1, 2, 3, 4});

        assertEquals(1, game.NumberAnimals("1524").get("cows"));
        assertEquals(2, game.NumberAnimals("1524").get("bulls"));

        assertEquals(0, game.NumberAnimals("5678").get("cows"));
        assertEquals(0, game.NumberAnimals("5678").get("bulls"));
    }
}
