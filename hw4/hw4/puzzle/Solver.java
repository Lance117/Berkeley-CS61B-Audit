package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.HashSet;

public class Solver {

    /* Solver class attributes */
    private ArrayList<WorldState> solutionSeq = new ArrayList<>();

    /**
     * SearchNode - represents one move sequence
     * @state - state of the puzzle
     * @cost - optimization #2 - cache cost (priority) of the state
     * @numOfMoves - num of moves to reach this state from initial state
     * @prev - reference to previous search node
     */
    private class SearchNode implements Comparable<SearchNode>{
        private WorldState state;
        private int cost;
        private int numOfMoves;
        private SearchNode prev;

        public SearchNode(WorldState state, int numOfMoves, SearchNode prev) {
            this.state = state;
            this.numOfMoves = numOfMoves;
            this.prev = prev;
            this.cost = state.estimatedDistanceToGoal() + numOfMoves;
        }

        public int moves() {
            return numOfMoves;
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.cost - o.cost;
        }
    }

    /**
     * Constructor takes initial state and solves the puzzle.
     * @param initial - initial WorldState
     */
    public Solver(WorldState initial) {
        SearchNode BMS = null;
        SearchNode initNode = new SearchNode(initial, 0, null);
        MinPQ<SearchNode> pq = new MinPQ<>();
        pq.insert(initNode);

        /* Implement A* algorithm */
        while (!pq.isEmpty()) {
            BMS = pq.delMin();
            SearchNode prev = BMS.prev;
            WorldState current = BMS.state;
            if (current.isGoal()) {
                break;
            }
            for (WorldState neighbor : current.neighbors()) {
                /* Critical optimization */
                if (prev == null || !prev.state.equals(neighbor)) {
                    SearchNode newNode = new SearchNode(neighbor, BMS.moves() + 1, BMS);
                    pq.insert(newNode);
                }
            }
        }

        /* Add best move sequence to iterable */
        while (BMS != null) {
            solutionSeq.add(0, BMS.state);
            BMS = BMS.prev;
        }
    }

    /* Returns number of moves needed to solve the puzzle. */
    public int moves() {
        return solutionSeq.size() - 1;
    }

    /* Returns a sequence of WorldStates from initial to solution. */
    public Iterable<WorldState> solution() {
        return solutionSeq;
    }
}
