package GomokuClient;

import java.util.LinkedList;

public class FiveChessAI {
    private String[][] myScoreType = {
            {"CMMMM", "MCMMM", "MMCMM", "MMMCM", "MMMMC"},  // 成5
            {".CMMM.", ".MCMM.", ".MMCM.", ".MMMC."},  // 活四
            {"OMCMM.", "OMMCM.", "OMMMC.", "OMMM.C", ".CMMMO", ".MCMMO", ".MMCMO", "C.MMMO",
                    "OMM.MC", "OMM.CM", "OMC.MM", "CM.MMO", "MC.MMO", "MM.CMO", "OCM.MM", "MM.MCO",
                    "MM.CM", "MC.MM"},  // 死4
            {".MMC.", ".MCM.", ".CMM.", ".M.CM.", ".MC.M.", ".C.MM.", ".MM.C.", ".CM.M.", ".M.MC."}, //活三
            {"OCMM..", "OMCM..", "OMMC..", "..MMCO", "..MCMO", "..CMMO"},  // 死三
            {".MC..", "..CM.", ".CM..", "..MC."},  // 活2
            {"OMC...", "OCM...", "...MCO", "...CMO"} // 死2
    };
    private String[][] opScoreType = {
            {"COOOO", "OCOOO", "OOCOO", "OOOCO", "OOOOC"},  // 成5
            {".COOO.", ".OCOO.", ".OOCO.", ".OOOC."},  // 活四
            {"MOCOO.", "MOOCO.", "MOOOC.", "MOOO.C", ".COOOM", ".OCOOM", ".OOCOM", "C.OOOM",
                    "MOO.OC", "MOO.CO", "MOC.OO", "CO.OOM", "OC.OOM", "OO.COM", "MCO.OO", "OO.OCM",
                    "OO.CO", "OC.OO"},  // 死4
            {".OOC.", ".OCO.", ".COO.", ".O.CO.", ".OC.O.", ".C.OO.", ".OO.C.", ".CO.O.", ".O.OC."},  // 活三
            {"MCOO..", "MOCO..", "MOOC..", "..OOCM", "..OCOM", "..COOM"},  // 死三
            {".OC..", "..CO.", ".CO..", "..OC."},  // 活2
            {"MOC...", "MCO...", "...OCM", "...COM"}  // 死2
    };

    private char[][] drawBoard(int[][] board) {
        char[][] newBoard = new char[board.length][board[0].length];
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++) {
                if (board[i][j] == 0) newBoard[i][j] = '.';
                else if (board[i][j] == 1) newBoard[i][j] = 'x';
                else if (board[i][j] == 2) newBoard[i][j] = 'o';
            }
        return newBoard;
    }

    private char[] judgingPieces(char[][] board) {
        int count = 0;
        char myChess, opChess;
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
                if (board[i][j] == 'x' || board[i][j] == 'o') count++;
        if (count % 2 == 1) {
            myChess = 'o';
            opChess = 'x';
        } else {
            myChess = 'x';
            opChess = 'o';
        }
        return new char[]{myChess, opChess};
    }

    private LinkedList<Optional> getOptional(char[][] board, char myChess, char opChess) {
        LinkedList<Optional> optional = new LinkedList<>();
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
                if (board[i][j] == '.')
                    optional.add(new Optional(i, j, 0));
        for (int i = -3; i < 4; i++)
            for (int j = -3; j < 4; j++) {
                Optional o = new Optional(7 + i, 7 + j, 0);
                for (int k = 0; k < optional.size(); k++) {
                    if (o.equals(optional.get(k)))
                        optional.get(k).setScore(150 / (i * i + j * j + 1));
                }
            }
//        LinkedList<Optional> optionals = new LinkedList<>();
//        for (int k = 0; k < optional.size(); k++) {
//            if (optional.get(k).getScore() > 0)
//                optionals.add(optional.get(k));
//        }
        return optional;
//        LinkedList<Optional> optional1 = new LinkedList<>();
//        for (int i = 0; i < optional.size(); i++) {
//            if (optional.get(i).getScore()>0)
//                optional1.add(optional.get(i));
//        }
//        return optional1;
    }

    private String[] getType(char[][] board, Optional each, char myChess, char opChess) {
        String[] myType = new String[4];

        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++) {
                if (board[i][j] == myChess)
                    board[i][j] = 'M';
                else if (board[i][j] == opChess)
                    board[i][j] = 'O';
            }
        board[each.getChessX()][each.getChessY()] = 'C';
        int a = each.getChessX();
        int b = each.getChessY();

        // my→
        int starta = a;
        int startb = b - 1;
        myType[0] = String.valueOf(board[a][b]);
        for (int i = 0; i < 4; i++) {
            if (startb < 0)
                break;
            myType[0] = String.valueOf(board[starta][startb]) + myType[0];
            startb -= 1;
        }
        starta = a;
        startb = b + 1;
        for (int i = 0; i < 4; i++) {
            if (startb > 14)
                break;
            myType[0] = myType[0] + String.valueOf(board[starta][startb]);
            startb += 1;
        }
        //my↘
        starta = a - 1;
        startb = b - 1;
        myType[1] = String.valueOf(board[a][b]);
        for (int i = 0; i < 4; i++) {
            if (starta < 0 || startb < 0)
                break;
            myType[1] = String.valueOf(board[starta][startb]) + myType[1];
            starta -= 1;
            startb -= 1;
        }
        starta = a + 1;
        startb = b + 1;
        for (int i = 0; i < 4; i++) {
            if (starta > 14 || startb > 14)
                break;
            myType[1] = myType[1] + String.valueOf(board[starta][startb]);
            starta += 1;
            startb += 1;
        }
        //my↓
        starta = a - 1;
        startb = b;
        myType[2] = String.valueOf(board[a][b]);
        for (int i = 0; i < 4; i++) {
            if (starta < 0)
                break;
            myType[2] = String.valueOf(board[starta][startb]) + myType[2];
            starta -= 1;
        }
        starta = a + 1;
        startb = b;
        for (int i = 0; i < 4; i++) {
            if (starta > 14)
                break;
            myType[2] = myType[2] + String.valueOf(board[starta][startb]);
            starta += 1;
        }
        //my↙
        starta = a - 1;
        startb = b + 1;
        myType[3] = String.valueOf(board[a][b]);
        for (int i = 0; i < 4; i++) {
            if (starta < 0 || startb > 14)
                break;
            myType[3] = String.valueOf(board[starta][startb]) + myType[3];
            starta -= 1;
            startb += 1;
        }
        starta = a + 1;
        startb = b - 1;
        for (int i = 0; i < 4; i++) {
            if (starta > 14 || startb < 0)
                break;
            myType[3] = myType[3] + String.valueOf(board[starta][startb]);
            starta += 1;
            startb -= 1;
        }
        board[a][b] = '.';
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++) {
                if (board[i][j] == 'M')
                    board[i][j] = myChess;
                else if (board[i][j] == 'O')
                    board[i][j] = opChess;
            }
        return myType;
    }

    private int typeScore(String[] myType, String[] opType) {
        int myScore = 0;
        int opScore = 0;
        int flag_be5 = 0;
        int flag_alive4 = 0;
        int flag_die4 = 0;
        int flag_alive3 = 0;
        int flag_die3 = 0;
        int flag_alive2 = 0;
        int flag_die2 = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < myScoreType[0].length; j++)
                if ((myType[i].indexOf(myScoreType[0][j])) != -1) {
                    flag_be5 += 1;
                    break;
                }
            for (int j = 0; j < myScoreType[1].length; j++)
                if ((myType[i].indexOf(myScoreType[1][j])) != -1) {
                    flag_alive4 += 1;
                    break;
                }
            for (int j = 0; j < myScoreType[2].length; j++)
                if ((myType[i].indexOf(myScoreType[2][j])) != -1) {
                    flag_die4 += 1;
                    break;
                }
            for (int j = 0; j < myScoreType[3].length; j++)
                if ((myType[i].indexOf(myScoreType[3][j])) != -1) {
                    flag_alive3 += 1;
                    break;
                }
            for (int j = 0; j < myScoreType[4].length; j++)
                if ((myType[i].indexOf(myScoreType[4][j])) != -1) {
                    flag_die3 += 1;
                    break;
                }
            for (int j = 0; j < myScoreType[5].length; j++)
                if ((myType[i].indexOf(myScoreType[5][j])) != -1) {
                    flag_alive2 += 1;
                    break;
                }
            for (int j = 0; j < myScoreType[6].length; j++)
                if ((myType[i].indexOf(myScoreType[6][j])) != -1) {
                    flag_die2 += 1;
                    break;
                }
        }
        if (flag_be5 >= 1) // 成5
            myScore = 10000;
        else if ((flag_alive4 >= 1) || (flag_die4 >= 2) || (flag_die4 >= 1 && flag_alive3 >= 1))  // 活4、双死4、死4活3
            myScore = 9000;
        else if (flag_alive3 >= 2)  // 双活3
            myScore = 8000;
        else if (flag_alive3 >= 1 && flag_die3 >= 1)  // 死3活3
            myScore = 7000;
        else if (flag_die4 >= 1)  // 死4
            myScore = 6000;
        else if (flag_alive3 != 0 && flag_alive2 != 0)  // 活3活2
            myScore = 5500;
        else if (flag_alive3 != 0)  // 活3
            myScore = 5000;
        else if (flag_alive2 >= 2)  // 双活2
            myScore = 4000;
        else if (flag_die3 >= 2)  // 双死3
            myScore = 3000;
        else if (flag_alive2 >= 1)  // 活2
            myScore = 2000;
        else if (flag_die2 >= 1)  // 死2
            myScore = 1000;

        flag_be5 = 0;
        flag_alive4 = 0;
        flag_die4 = 0;
        flag_alive3 = 0;
        flag_die3 = 0;
        flag_alive2 = 0;
        flag_die2 = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < opScoreType[0].length; j++)
                if ((opType[i].indexOf(opScoreType[0][j])) != -1) {
                    flag_be5 += 1;
                    break;
                }
            for (int j = 0; j < opScoreType[1].length; j++)
                if ((opType[i].indexOf(opScoreType[1][j])) != -1) {
                    flag_alive4 += 1;
                    break;
                }
            for (int j = 0; j < opScoreType[2].length; j++)
                if ((opType[i].indexOf(opScoreType[2][j])) != -1) {
                    flag_die4 += 1;
                    break;
                }
            for (int j = 0; j < opScoreType[3].length; j++)
                if ((opType[i].indexOf(opScoreType[3][j])) != -1) {
                    flag_alive3 += 1;
                    break;
                }
            for (int j = 0; j < opScoreType[4].length; j++)
                if ((opType[i].indexOf(opScoreType[4][j])) != -1) {
                    flag_die3 += 1;
                    break;
                }
            for (int j = 0; j < opScoreType[5].length; j++)
                if ((opType[i].indexOf(opScoreType[5][j])) != -1) {
                    flag_alive2 += 1;
                    break;
                }
            for (int j = 0; j < opScoreType[6].length; j++)
                if ((opType[i].indexOf(opScoreType[6][j])) != -1) {
                    flag_die2 += 1;
                    break;
                }
        }
        if (flag_be5 >= 1) // 成5
            opScore = 10000;
        else if ((flag_alive4 >= 1) || (flag_die4 >= 2) || (flag_die4 >= 1 && flag_alive3 >= 1))  // 活4、双死4、死4活3
            opScore = 9000;
        else if (flag_alive3 >= 2)  // 双活3
            opScore = 8000;
        else if (flag_alive3 >= 1 && flag_die3 >= 1)  // 死3活3
            opScore = 7000;
        else if (flag_die4 >= 1)  // 死4
            opScore = 6000;
        else if (flag_alive3 != 0 && flag_alive2 != 0)  // 活3活2
            opScore = 5500;
        else if (flag_alive3 != 0)  // 活3
            opScore = 5000;
        else if (flag_alive2 >= 2)  // 双活2
            opScore = 4000;
        else if (flag_die3 >= 2)  // 双死3
            opScore = 3000;
        else if (flag_alive2 >= 1)  // 活2
            opScore = 2000;
        else if (flag_die2 >= 1)  // 死2
            opScore = 1000;
        return (myScore <= opScore) ? opScore : myScore;

    }

    private boolean beFive(char[][] board, int a, int b, char myChess, char opChess) {
        // my→
        int starta = a;
        int startb = b - 1;
        int[] count = {1, 1, 1, 1, 1, 1, 1, 1};
        for (int i = 0; i < 4; i++) {
            if (startb < 0 || board[starta][startb] == opChess)
                break;
            if (board[starta][startb] == myChess)
                count[0]++;
            startb -= 1;
        }

        starta = a;
        startb = b + 1;
        for (int i = 0; i < 4; i++) {
            if (startb > 14 || board[starta][startb] == opChess)
                break;
            if (board[starta][startb] == myChess)
                count[1]++;
            startb += 1;
        }

        //my↘
        starta = a - 1;
        startb = b - 1;
        for (int i = 0; i < 4; i++) {
            if (starta < 0 || startb < 0 || board[starta][startb] == opChess)
                break;
            if (board[starta][startb] == myChess)
                count[2]++;
            starta -= 1;
            startb -= 1;
        }
        starta = a + 1;
        startb = b + 1;
        for (int i = 0; i < 4; i++) {
            if (starta > 14 || startb > 14 || board[starta][startb] == opChess)
                break;
            if (board[starta][startb] == myChess)
                count[3]++;
            starta += 1;
            startb += 1;
        }
        //my↓
        starta = a - 1;
        startb = b;
        for (int i = 0; i < 4; i++) {
            if (starta < 0 || board[starta][startb] == opChess)
                break;
            if (board[starta][startb] == myChess)
                count[4]++;
            starta -= 1;
        }
        starta = a + 1;
        startb = b;
        for (int i = 0; i < 4; i++) {
            if (starta > 14 || board[starta][startb] == opChess)
                break;
            if (board[starta][startb] == myChess)
                count[5]++;
            starta += 1;
        }
        //my↙
        starta = a - 1;
        startb = b + 1;
        for (int i = 0; i < 4; i++) {
            if (starta < 0 || startb > 14 || board[starta][startb] == opChess)
                break;
            if (board[starta][startb] == myChess)
                count[6]++;
            starta -= 1;
            startb += 1;
        }
        starta = a + 1;
        startb = b - 1;
        for (int i = 0; i < 4; i++) {
            if (starta > 14 || startb < 0 || board[starta][startb] == opChess)
                break;
            if (board[starta][startb] == myChess)
                count[7]++;
            starta += 1;
            startb -= 1;
        }
        for (int i = 0; i < 8; i++)
            if (count[i] >= 5)
                return true;
        return false;
    }

    public boolean win(int[][] b) {
        char[][] board = drawBoard(b);
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++) {
                if (board[i][j] == 'x' && beFive(board, i, j, 'x', 'o'))
                    return true;
                else if (board[i][j] == 'o' && beFive(board, i, j, 'o', 'x'))
                    return true;
            }
        return false;
    }

    public Optional testAI(int[][] board) {
        char[][] newBoard = drawBoard(board);
        char[] chess = judgingPieces(newBoard);
        char myChess = chess[0];
        char opChess = chess[1];
        LinkedList<Optional> optional = getOptional(newBoard, myChess, opChess);
        int max = -1, score = 0, k = 0;
        for (int i = 0; i < optional.size(); i++) {

            String[] myType = getType(newBoard, optional.get(i), myChess, opChess);
            score = optional.get(i).getScore() + typeScore(myType, myType);
            if (score > max) {
                max = score;
                k = i;
            }
            optional.get(i).setScore(score);
        }
        return optional.get(k);
        //return optional.get(k).getScore();
    }
}
