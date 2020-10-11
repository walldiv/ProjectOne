package com.ex.model;

/**
 * This class is for the holds data for when a game was played, the score of the game
 * and the win status
 *
 * @param score - the score of the game
 * @param win - the win status of the game
 * @param dayPlayed - day of the week the game was played
 */
public class GameScore {

    private int score;
    private boolean win;
    private String dayPlayed;

    /** no arg constructor sets score to 0, win to false, and dayPlayed to an empty string
     *
     */
    public GameScore(){
        this.score = 0;
        this.win = false;
        this.dayPlayed = "";
    }

    /** arged constructor sets all fields according to data passed
     *
     * @param score
     * @param win
     * @param dayPlayed
     */
    public GameScore(int score, boolean win, String dayPlayed) {
        this.score = score;
        this.win = win;
        this.dayPlayed = dayPlayed;
    }

    /* =================    GET & SET   ======================= */
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public boolean isWin() { return win; }
    public void setWin(boolean win) { this.win = win; }
    public String getDayPlayed() { return dayPlayed; }
    public void setDayPlayed(String dayPlayed) { this.dayPlayed = dayPlayed; }

    @Override
    public String toString() {
        return "GameScore{" +
                "score=" + score +
                ", win=" + win +
                ", dayPlayed='" + dayPlayed + '\'' +
                '}';
    }

}
