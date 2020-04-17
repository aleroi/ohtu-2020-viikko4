package ohtu;

import java.util.HashMap;

public class TennisGame {
    
    private int score_player1 = 0;
    private int score_player2 = 0;
    private String player1Name;
    private String player2Name;
    
    private HashMap<Integer, String> scoreToString;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.scoreToString = new HashMap<>();
        
        scoreToString.put(0, "Love");
        scoreToString.put(1, "Fifteen");
        scoreToString.put(2, "Thirty");
        scoreToString.put(3, "Forty");
    }
    
    public void wonPoint(String playerName) {
        if (playerName == player1Name)
        	score_player1 += 1;
        else
        	score_player2 += 1;
    }
    
    public String getScore() {
        String score = "";
        if (score_player1==score_player2)
        {
            score += scoreToString.getOrDefault(score_player1, "Deuce");
            
            if (score_player1 < 4) {
            	score += "-All";
            }
        }
        else if (score_player1>=4 || score_player2>=4)
        {
            int minusResult = score_player1-score_player2;
            if (minusResult==1) score ="Advantage player1";
            else if (minusResult ==-1) score ="Advantage player2";
            else if (minusResult>=2) score = "Win for player1";
            else score ="Win for player2";
        }
        else
        {
        	score += scoreToString.getOrDefault(score_player1, "Deuce");
        	score += "-";
        	score += scoreToString.getOrDefault(score_player2, "Deuce");
        }
        return score;
    }

}