package sise.pietnastka.solver;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Klasa reprezentujÄ…ca algorytm A*.
 *
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
        // gdzie priorytet stanowi wartoĹ›Ä‡ funkcji oceny stanu
        PriorityQueue<PuzzleNode> open = new PriorityQueue<>((o1, o2) -> o1.getScore() - o2.getScore());

        PuzzleNode copy = new PuzzleNode(initial);
        scoringFunction.giveScoreToState(copy);
        open.add(copy);
        statesOpen = 1;

        // zbiĂłr stanĂłw odwiedzonych, dla szybkoĹ›ci znajdowania elementĂłw wybraĹ‚em zbiĂłr hashujÄ…cy,
        // niestety HashSet, nie ma metod pozwalajÄ…cych na wyciÄ…ganie elementĂłw ze zbioru :(
        // wiÄ™c trzeba byĹ‚o uĹĽyÄ‡ hashmapy, i tu ciekawostka, biblioteczny HashSet sam pod spodem ma HashMap :D
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

                // Sprawdzamy czy stan jaki uzyskaliĹ›my w wyniku wykonania ruchu nie zostaĹ‚ wczeĹ›niej przetworzony
                PuzzleNode past = closed.get(successor);
                if (past != null) {
                    // JeĹ›li byĹ‚ to sprawdzamy jakÄ… dostaĹ‚ wczeĹ›niej ocenÄ™
                    if (successor.getScore() >= past.getScore()) {
                        continue;
                    }

                    // JeĹ›li jego poprzednia ocena byĹ‚a gorsza (wartoĹ›Ä‡ funkcji oceny miaĹ‚a wiÄ™kszÄ… wartoĹ›Ä‡) od obecnej
                    // to usuwamy go ze zbioru stanĂłw przetworzonych i pĂłĹşniej dodajemy do zbioru stanĂłw do przetworzenia,
                    // ale juĹĽ z nowÄ…, lepszÄ… ocenÄ…
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
