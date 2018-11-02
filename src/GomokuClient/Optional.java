package GomokuClient;

public class Optional {
    private int chessX;
    private int chessY;
    private int score;

    public Optional(int x, int y, int score) {
        this.chessX = x;
        this.chessY = y;
        this.score = score;
    }

    public int getChessX() {
        return chessX;
    }

    public int getChessY() {
        return chessY;
    }

    public void setChessX(int chessX) {
        this.chessX = chessX;
    }

    public void setChessY(int chessY) {
        this.chessY = chessY;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Optional) {
            Optional opt = (Optional) obj;
            if (opt.getChessX() == this.chessX && opt.getChessY() == this.chessY && opt.getScore() == this.score) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
