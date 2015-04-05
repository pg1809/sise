package sise.pietnastka;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class AStarSearch extends AbstractSearch {

    private final Evaluator scoringFunction;

    public AStarSearch(Evaluator scoringFunction) {
        this.scoringFunction = scoringFunction;
    }

    @Override
    public Solution search(PuzzleNode initial, PuzzleNode goal, String movesOrder) {

        TreeSet<PuzzleNode> open = new TreeSet<>(new Comparator<PuzzleNode>() {
            @Override
            public int compare(PuzzleNode o1, PuzzleNode o2) {
                return o1.getScore() - o2.getScore();
            }       
        }); 
        
        PuzzleNode copy = new PuzzleNode(initial);
        scoringFunction.giveScoreToState(copy);
        open.add(copy);

        List<PuzzleNode> closed = new LinkedList<>(); 
        while (!open.isEmpty()) {

            PuzzleNode node = open.pollFirst();
            closed.add(node);

            if (node.equals(goal)) {
                numOpen = open.size();
                numClosed = closed.size(); 

                return new Solution(initial, node);
            }

            Transition trans = node.getTransition();
            int depth = 1;
            if (trans != null) {
                depth = trans.getDepth() + 1;
            }

            List<Move> moves = node.getValidMoves(movesOrder);
            for (Move move : moves) {

                PuzzleNode successor = new PuzzleNode(node);
                move.execute(successor);

                successor.setTransition(new Transition(move, node, depth));
                scoringFunction.giveScoreToState(successor);

                numMoves++; 

                int idx = closed.indexOf(successor);
                PuzzleNode past = null;
                if (idx >= 0) {
                    past = closed.get(idx);
                }
                if (past != null) {
                    if (successor.getScore() >= past.getScore()) {
                        continue;
                    }

                    closed.remove(past);
                }

                open.add(successor);
            }
        }

        numOpen = open.size();
        numClosed = closed.size(); 

        return new Solution(initial, goal, false);
    }

}
