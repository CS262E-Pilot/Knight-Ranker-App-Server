package edu.calvin.cs262.pilot.knight_ranker;

/**
 * Calculates elo ranks
 * Source: https://www.geeksforgeeks.org/elo-rating-algorithm/
 */
public class Elo {
    private static final int K = 30;
    /**
     * Function to calculate the Probability
     * @param rating1
     * @param rating2
     * @return
     */
    static float Probability(int rating1, int rating2) {
        return 1.0f * 1.0f / (1 + 1.0f *
                (float)(Math.pow(10, 1.0f *
                        (rating1 - rating2) / 400)));
    }

    /**
     * Function to calculate Elo rating
     * @param playerRank
     * @param opponentRank
     * @param playerScore
     * @param opponentScore
     */
    static void EloRating(int playerRank, int opponentRank, int playerScore, int opponentScore)
    {
        // Normalize playerScore and opponentScore to 0-1 range
        int maxScore = playerScore + opponentScore;
        int playerNormalizedScore = playerScore/maxScore;
        int opponentNormalizedScore = opponentScore/maxScore;
        // To calculate the Winning
        // Probability of Player B
        float Pb = Probability(playerRank, opponentRank);

        // To calculate the Winning
        // Probability of Player A
        float Pa = Probability(opponentRank, playerRank);

        int playerNewRank = Math.round(playerRank + K * (playerNormalizedScore - Pa));
        int opponentNewRank = Math.round(opponentRank + K * (opponentNormalizedScore - Pb));


    }
}
