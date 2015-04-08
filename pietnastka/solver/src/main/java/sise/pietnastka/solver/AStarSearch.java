package sise.pietnastka.solver;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Klasa reprezentująca algorytm A*.
 *
 * @author PiotrGrzelak
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

        // Kolejka priorytetowa na stany do przetworzenia (tzw. "otwarte"), gdzie kluczem jest wartość funkcji oceny stanu
        PriorityQueue<PuzzleNode> open = new PriorityQueue<>((o1, o2) -> o1.getScore() - o2.getScore());

        PuzzleNode copy = new PuzzleNode(initial);
        scoringFunction.giveScoreToState(copy);
        open.add(copy);

        // zbiór stanów odwiedzonych, dla szybkości znajdowania elementów wybrałem zbiór hashujący,
        // niestety HashSet, nie ma metod pozwalających na wyciąganie elementów ze zbioru :(
        // więc trzeba było użyć hashmapy, i tu ciekawostka, biblioteczny HashSet sam pod spodem ma HashMap :D
        HashMap<PuzzleNode, PuzzleNode> closed = new HashMap<>();
        while (!open.isEmpty()) {

            PuzzleNode node = open.poll();
            closed.put(node, node);

            if (node.equals(goal)) {
                statesOpen = open.size();
                statesClosed = closed.size();
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

                // Sprawdzamy czy stan jaki uzyskaliśmy w wyniku uzyskania ruchu nie został wcześniej przetworzony
                PuzzleNode past = closed.get(successor);
                if (past != null) {
                    // Jeśli był to sprawdzamy jaką dostał wcześniej ocenę
                    if (successor.getScore() >= past.getScore()) {
                        continue;
                    }

                    // Jeśli jego poprzednia ocena była gorsza (wartość funkcji oceny miała większą wartość) od obecnej
                    // to usuwamy go ze zbioru stanów przetowrzonych i później dodajemy do zbioru stanów do przetworzenia,
                    // ale już z nową, lepszą oceną
                    closed.remove(past);
                }

                open.add(successor);
            }
        }

        statesOpen = open.size();
        statesClosed = closed.size();
        return null;
    }

    public void setScoringFunction(Evaluator scoringFunction) {
        this.scoringFunction = scoringFunction;
    }
}