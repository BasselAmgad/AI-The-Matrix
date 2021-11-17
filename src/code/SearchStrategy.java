package code;

import java.util.Comparator;

/*
 * An enum used to parse a search strategy string to.
 */
public enum SearchStrategy implements SearchProcedure{
    BF{
        @Override
        public String search(SearchProblem problem) {
            Comparator<Node> comp = Comparator.comparingInt(node -> node.depth);
            return problem.genericSearchProcedure(comp);
        }
    },
    DF{
        @Override
        public String search(SearchProblem problem) {
            Comparator<Node> comp = Comparator.comparingInt(node -> -node.depth);
            return problem.genericSearchProcedure(comp);
        }
    },
    ID{
        @Override
        public String search(SearchProblem problem) {
            int limit = 0;
            while(true){//TODO: check that the maximum depth of an actually expanded node is l
//                String result = problem.DepthLimitedSearch(limit);
                String result = problem.DepthLimitedSearch(limit);
                if (result.equals("empty")){
                    return "No Solution";
                }
                else if (result.equals("cutoff")){
                    limit++;
                }
                else{
                    return result;
                }
            }
        }

    },
    UC{
        @Override
        public String search(SearchProblem problem) {
            Comparator<Node> comp = Comparator.comparingInt(node -> node.pathCost);
            return problem.genericSearchProcedure(comp);
        }
    },
    GR1{
        @Override
        public String search(SearchProblem problem) {
            Comparator<Node> comp = Comparator.comparingInt(node -> problem.heuristic_1(node));
            return problem.genericSearchProcedure(comp);
        }
    },
    GR2{
        @Override
        public String search(SearchProblem problem) {
            Comparator<Node> comp = Comparator.comparingInt(node -> problem.heuristic_2(node));
            return problem.genericSearchProcedure(comp);
        }
    },
    AS1{
        @Override
        public String search(SearchProblem problem) {
            Comparator<Node> comp = Comparator.comparingInt(node -> node.pathCost + problem.heuristic_1(node));
            return problem.genericSearchProcedure(comp);
        }
    },
    AS2{
        @Override
        public String search(SearchProblem problem) {
            Comparator<Node> comp = Comparator.comparingInt(node -> node.pathCost + problem.heuristic_2(node));
            return problem.genericSearchProcedure(comp);
        }
    };

    public static SearchStrategy parse(String searchStrategy) {
        return SearchStrategy.valueOf(searchStrategy);
    }
}
