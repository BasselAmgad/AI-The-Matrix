package code;

import java.util.Comparator;

/*
 * An enum used to parse a search strategy string to.
 */
public enum SearchStrategy {
    BF,
    DF,
    ID,
    UC,
    GR1,
    GR2,
    AS1,
    AS2;

    public static SearchStrategy parse(String searchStrategy) {
        return SearchStrategy.valueOf(searchStrategy);
    }
}
