package code;

import code.queue.*;

/*
 * An enum used to parse a search strategy string to.
 */
public enum SearchStrategy implements SearchProcedure {
    BF {
        @Override
        public String search(SearchProblem problem) {
            return problem.genericSearchProcedure(new BfsQueue<>());
        }
    },
    DF {
        @Override
        public String search(SearchProblem problem) {
            Comparator<MNode> comp = Comparator.comparingInt(node -> -node.depth);
            return problem.genericSearchProcedure(comp);
        }
    },
    ID {
        @Override
        public String search(SearchProblem problem) {
            int limit = 0;
            while (true) {
                String result = problem.DepthLimitedSearch(limit);
                if (result.equals("empty")) {
                    return "No Solution";
                } else if (result.equals("cutoff")) {
                    limit++;
                } else {
                    return result;
                }
            }
        }
    },
    UC {
        @Override
        public String search(SearchProblem problem) {
            return problem.genericSearchProcedure(new UcQueue());
        }
    },
    GR1 {
        @Override
        public String search(SearchProblem problem) {
            return problem.genericSearchProcedure(new Gr1Queue(problem));
        }
    },
    GR2 {
        @Override
        public String search(SearchProblem problem) {
            return problem.genericSearchProcedure(new Gr2Queue(problem));
        }
    },
    AS1 {
        @Override
        public String search(SearchProblem problem) {
            return problem.genericSearchProcedure(new As1Queue(problem));
        }
    },
    AS2 {
        @Override
        public String search(SearchProblem problem) {
            return problem.genericSearchProcedure(new As2Queue(problem));
        }
    };

    public static SearchStrategy parse(String searchStrategy) {
        return SearchStrategy.valueOf(searchStrategy);
    }
}
