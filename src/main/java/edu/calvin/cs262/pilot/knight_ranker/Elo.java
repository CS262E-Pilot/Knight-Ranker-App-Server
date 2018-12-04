package edu.calvin.cs262.pilot.knight_ranker;

/**
 * Calculates elo ranks
 * Source: https://www.geeksforgeeks.org/elo-rating-algorithm/
 */
public class Elo {
    public static final int StartingRank = 1500;
    private static final int K = 30;
    /**
     * Function to calculate the Probability
     * @param ratingA elo rating
     * @param ratingB elo rating
     * @return the probability of winning
     */
    static float Probability(int ratingA, int ratingB) {
        return 1.0f * 1.0f / (1 + 1.0f *
                (float)(Math.pow(10, 1.0f *
                        (ratingA - ratingB) / 400)));
    }

    /**
     *  Function to calculate Elo rating for player A
     * @param ratingA elo rating
     * @param ratingB elo rating
     * @param scoreA the score of a match
     * @param scoreB the opposing score for a match
     */
    static int EloRating(int ratingA, int ratingB, int scoreA, int scoreB)
    {
        // Normalize the score to 0-1 range
        int normalizedScore = scoreA/(scoreA + scoreB);
        // To calculate the Winning
        // Probability of Player A
        float Pa = Probability(ratingB, ratingA);

        return Math.round(ratingA + K * (normalizedScore - Pa));
    }
}
