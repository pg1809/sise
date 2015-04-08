package sise.pietnastka.solver;

/**
 * Abstrakcyjna klasa reprezentująca algorytm przeszukiwania drzewa gry
 *
 * @author PiotrGrzelak
 */
public abstract class AbstractSearch {
    
    /**
     * Liczba stanów, które algorytm dodał do zbioru stanów do przetworzenia
     */
    protected int statesOpen;
    
    /**
     * Liczba stanów w zbiorze stanów przetowrzonych przez algorytm
     */
    protected int statesClosed;
    
    /**
     * Maksymalna głębokość w drzewie na jaką zszedł algorytm
     */
    protected int maximumDepth;
    
    protected AbstractSearch() {
        statesOpen = 0;
        statesClosed = 0;
        maximumDepth = 0;
    }
    
    /**
     * Metoda przeszukująca drzewo gry, zaczynając od podanego stan początkowego,
     * w celu znalezienia ścieżki do podanego stanu docelowego
     * 
     * @param initial początkowy stan układanki
     * @param target stan układanki, do którego należy znaleźć ścieżkę
     * @param movesOrder porządek w jakim wykonywane mają być ruchy na planszy 
     * (patrz treść zadania na wikampie)
     * @return obiekt klasy @Solution zawierający informacje o rozwiązaniu układanki,
     * jeśli rozwiązania nie udało się znaleźć zwracany jest null.
     */
    public abstract Solution search(PuzzleNode initial, PuzzleNode target, String movesOrder);

    public int getStatesOpen() {
        return statesOpen;
    }

    public int getStatesClosed() {
        return statesClosed;
    }

    public int getMaximumDepth() {
        return maximumDepth;
    }
}
