package sise.pietnastka.solver;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Klasa odpowiadająca za przeszukiwanie przestrzeni stanów przy pomocy
 * algorytmu A*.
 */
public class AStarSearch extends AbstractSearch {

    private Evaluator scoringFunction;

    public AStarSearch(Evaluator scoringFunction) {
        super();
        this.scoringFunction = scoringFunction;
    }

    public AStarSearch() {
        super();
        scoringFunction = null;
    }

    @Override
    public Solution search(PuzzleNode initial, PuzzleNode goal, String movesOrder) {

        // Kolejka priorytetowa na stany do przetworzenia (tzw. "otwarte"), 
        // gdzie priorytet stanowi wartość funkcji oceny stanu
        PriorityQueue<PuzzleNode> open = new PriorityQueue<>((o1, o2) -> o1.getScore() - o2.getScore());

        PuzzleNode copy = new PuzzleNode(initial);
        scoringFunction.giveScoreToState(copy);
        open.add(copy);
        statesOpen = 1;

        // zbiór stanów odwiedzonych, dla szybkości znajdowania elementów wybrałem zbiór hashujący,
        // niestety HashSet, nie ma metod pozwalających na wyciąganie elementów ze zbioru :(
        // więc trzeba było użyć hashmapy, i tu ciekawostka, biblioteczny HashSet sam pod spodem ma HashMap :D
        HashMap<PuzzleNode, PuzzleNode> closed = new HashMap<>();
        while (!open.isEmpty()) {

            PuzzleNode node = open.poll();
            closed.put(node, node);
            statesClosed++;

            if (node.equals(goal)) {
                return new Solution(initial, node);
            }

            Transition trans = node.getTransition();
            int depth = 1;
            if (trans != null) {
                depth = trans.getDepth() + 1;
            }
            maximumDepth = Math.max(maximumDepth, depth);

            List<Move> moves = node.getValidMoves(movesOrder);
            for (Move move : moves) {

                PuzzleNode successor = new PuzzleNode(node);
                move.execute(successor);

                successor.setTransition(new Transition(move, node, depth));
                scoringFunction.giveScoreToState(successor);

                // Sprawdzamy czy stan jaki uzyskaliśmy w wyniku wykonania ruchu nie został wcześniej przetworzony
                PuzzleNode past = closed.get(successor);
                if (past != null) {
                    // Jeśli był, to sprawdzamy, jaką dostał wcześniej ocenę
                    if (successor.getScore() >= past.getScore()) {
                        continue;
                    }

                    // Jeśli jego poprzednia ocena była gorsza (wartość funkcji oceny miała większą wartość od obecnej)
                    // to usuwamy go ze zbioru stanów przetworzonych i później dodajemy do zbioru stanów do przetworzenia,
                    // ale już z nową, lepszą oceną
                    closed.remove(past);
                    statesClosed--;
                }

                open.add(successor);
                statesOpen++;
            }
        }

        return null;
    }

    public void setScoringFunction(Evaluator scoringFunction) {
        this.scoringFunction = scoringFunction;
    }
}
