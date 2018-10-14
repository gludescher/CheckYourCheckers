package guiludescher.checkyourcheckers;


public class GameManager {

    public static final int EMPTY = 0;
    public static final int PAWN = 1;
    public static final int QUEEN = 2;
    public static final int DARK = -1;
    public static final int LIGHT = 1;

    public static final int WAITING_ORIGIN_INPUT = 0;
    public static final int WAITING_END_INPUT = 1;

    public static final int SPACE_OK = 100;
    public static final int MOVE_OK = 101;
    public static final int CAPTURE_OK = 102;
    public static final int INVALID_SPACE = 200;
    public static final int MUST_CAPTURE = 201;
    public static final int EOG_NO_MOVES = 300;
    public static final int EOG_NO_CHECKERS = 301;

    public static final int CAN_MOVE = 400;
    public static final int CAN_CAPTURE = 401;
    public static final int NO_MOVES = 402;

    public static final int N_STARTING_PIECES = 12;
    public static final int BORDER = 8;

    public int[][] board;

    private int roundState;
    private int playerTurn;
    private boolean isCapturing;

    private int columnOrigin, lineOrigin, columnEnd, lineEnd;
    private boolean mustCapture, movesAvailable = true;

    private int nDarkCheckers, nLightCheckers, winner;

    public GameManager(){

        initializeBoard();
        nDarkCheckers = nLightCheckers = N_STARTING_PIECES;
        roundState = WAITING_ORIGIN_INPUT;
        playerTurn = DARK;
        isCapturing = false;
        movesAvailable = true;
    }

    private void initializeBoard(){

        board = new int[8][8];

        int i, j;
        for (i = 0; i < BORDER; i++){
            for (j = 0; j < BORDER; j++){
                if (j % 2 != i % 2)  {   //that is, one of them is odd and the other is even
                    if (i <= 2) board[i][j] = DARK * PAWN;
                    else if (i >= 5) board[i][j] = LIGHT * PAWN;
                    else board[i][j] = EMPTY;

                    BoardActivity.updateBoard(i, j, board[i][j]);
                }
                else
                    board[i][j] = EMPTY;
            }
        }
    }

    //called when a button is clicked during the game
    public int nextStepInRound(int line, int column){

        switch (this.roundState) {
            case WAITING_ORIGIN_INPUT:
                if (!movesAvailable) {
                    winner = -1*playerTurn;
                    return EOG_NO_MOVES;
                }

                if (selectedOriginOK(line, column) == SPACE_OK) {
                    this.roundState = WAITING_END_INPUT;
                    return SPACE_OK;
                }
                else return INVALID_SPACE;

            case WAITING_END_INPUT:
                switch (selectedEndOK(line, column)) {
                    case SPACE_OK:
                        this.roundState = WAITING_END_INPUT;
                        return SPACE_OK;
                    case MOVE_OK:
                        if (mustCapture)
                            return MUST_CAPTURE;
                        becameQueen();
                        moveChecker();
                        newRound();
                        return MOVE_OK;
                    case CAPTURE_OK:
                        if (becameQueen()) {
                            makeCapture();
                            newRound();
                            return CAPTURE_OK;
                        }
                        makeCapture();
                        if (zeroCheckers()) {
                            winner = (int) Math.signum(nLightCheckers - nDarkCheckers);
                            return EOG_NO_CHECKERS;
                        }
                        isCapturing = true;
                        if (isHunter(lineEnd, columnEnd)) {
                            lineOrigin = lineEnd;
                            columnOrigin = columnEnd;
                            this.roundState = WAITING_END_INPUT;
                            nextStepInRound(line, column);
                        }
                        else
                            newRound();
                        return CAPTURE_OK;
                    default:
                        return INVALID_SPACE;
                }
        }
        return 0;
    }

    private void newRound(){
        this.roundState = WAITING_ORIGIN_INPUT;
        playerTurn *= -1;
        isCapturing = false;
        //cantMove = lookForHunters();
        switch (canMoveOrCapture()){
            case CAN_CAPTURE:
                mustCapture = true;
                movesAvailable = true;
                break;
            case CAN_MOVE:
                mustCapture = false;
                movesAvailable = true;
                break;
            case NO_MOVES:
                mustCapture = false;
                movesAvailable = false;
                break;
        }

    }

    /* The same way as it happens in the wild, here, the 'hunter' captures its prey for a nice feast.
     * In the game of Checkers, if one CAN capture an opponent's checker, one MUST capture it.
     * Also, if there are two or more possible captures, the player may choose between them.
     */
    private int canMoveOrCapture () {
        int i, j;
        boolean hasMoves = false;
        //this makes the verification start from the most forward checker for each color, that is
        //the ones with most chances of capture
        if (this.playerTurn == LIGHT) {
            for (i = 0; i < BORDER; i++) {
                for (j = 0; j < BORDER; j++) {
                    //only check for spaces that can be used and current player's pieces
                    if (i % 2 != j % 2 && colorOf(this.board[i][j]) == this.playerTurn) {
                        if (isHunter(i, j)) return CAN_CAPTURE;

                        //if a piece can move, no need to check for other pieces, only checking for hunters
                        else if (!hasMoves) hasMoves = isMover(i, j);
                    }
                }
            }
        }

        else if (this.playerTurn == DARK) {
            for (i = 7; i >= 0; i--) {
                for (j = 7; j >= 0; j--) {
                    //only check for spaces that can be used and current player's pieces
                    if (i % 2 != j % 2 && colorOf(this.board[i][j]) == this.playerTurn) {
                        if (isHunter(i, j)) return CAN_CAPTURE;
                        else if (!hasMoves) hasMoves = isMover(i, j);
                    }
                }
            }
        }
        if (hasMoves) return CAN_MOVE;
        return NO_MOVES;
    }

    private boolean isHunter (int line, int column){
        int linePrey, columnPrey, lineHunt, columnHunt;
        int i = 1, j = 1, n = 0;
        int hunter = board[line][column];
        int prey;

        while (n <= 3) {
            linePrey = line + i;
            columnPrey = column + j;
            lineHunt = line + 2*i;
            columnHunt = column + 2*j;

            if (isWithinBorders(lineHunt, columnHunt)){
                prey = board[linePrey][columnPrey];
                if (colorOf(prey) == -1*playerTurn && board[lineHunt][columnHunt] == EMPTY &&
                        (colorOf(hunter) == -1*i || valueOf(hunter) == QUEEN))
                    return true;
            }

            if (n % 2 == 0) i *= -1;
            else j *= -1;
            n++;
        }
        return false;
    }

    private boolean isMover (int line, int column) {
        int lineDestiny, columnDestiny;
        int i = 1, j = 1, n = 0;
        int spaceDestiny;
        int mover = board[line][column];

        while (n <= 3) {
            lineDestiny = line + i;
            columnDestiny = column + j;

            if (isWithinBorders(lineDestiny, columnDestiny)){
                spaceDestiny = board[lineDestiny][columnDestiny];
                if (spaceDestiny == EMPTY && (colorOf(mover) == -1*i || valueOf(mover) == QUEEN))
                    return true;
            }

            if (n % 2 == 0) i *= -1;
            else j *= -1;
            n++;
        }
        return false;
    }


    //verifies whether the selected origin is occupied by a Checker of the current player
    private int selectedOriginOK(int line, int column){
        if (colorOf(board[line][column]) == colorOf(playerTurn)){
            lineOrigin = line;
            columnOrigin = column;
            return SPACE_OK;
        }
        return INVALID_SPACE;
    }

    //verifies whether the selected origin is empty and adjacent to the origin or configures a capture
    private int selectedEndOK(int lineSelected, int columnSelected){

        int columnDelta = columnSelected - columnOrigin;
        int lineDelta = lineSelected - lineOrigin;
        int checkerMoving = board[lineOrigin][columnOrigin];

        int linePrey = (int)(lineOrigin + (lineDelta)/2);
        int columnPrey = (int)(columnOrigin + (columnDelta)/2);
        int prey = board[linePrey][columnPrey];

        if (colorOf(board[lineSelected][columnSelected]) == playerTurn && !isCapturing){
            BoardActivity.updateBoard(lineOrigin, columnOrigin, board[lineOrigin][columnOrigin]);
            lineOrigin = lineSelected;
            columnOrigin = columnSelected;
            return SPACE_OK;
        }

        if (board[lineSelected][columnSelected] == EMPTY){
            //only queens may move backwards
            //light pawns always move from higher to lower lines(lineDelta < 0)
            if (colorOf(checkerMoving) == LIGHT && valueOf(columnDelta) == 1 &&
                lineDelta == -1 || (lineDelta == 1 && valueOf(checkerMoving) == QUEEN)){

                lineEnd = lineSelected;
                columnEnd = columnSelected;
                return MOVE_OK;
            }

            //dark pieces always move from lower to higher numbers(lines)
            else if (colorOf(checkerMoving) == DARK && valueOf(columnDelta) == 1 &&
                     lineDelta == 1 || (lineDelta == -1 && valueOf(checkerMoving) == QUEEN)) {

                lineEnd = lineSelected;
                columnEnd = columnSelected;
                return MOVE_OK;
            }

            //to make a capture, pieces move two lines and two columns, always forwards for pawns
            else if ((colorOf(checkerMoving) == LIGHT && valueOf(columnDelta) == 2 &&
                     lineDelta == -2 || (lineDelta == 2 && valueOf(checkerMoving) == QUEEN)) &&
                     colorOf(prey) == DARK){

                lineEnd = lineSelected;
                columnEnd = columnSelected;
                return CAPTURE_OK;
            }

            //to make a capture, pieces move two lines and two columns, always forwards for pawns
            else if ((colorOf(checkerMoving) == DARK && valueOf(columnDelta) == 2 &&
                     lineDelta == 2 || (lineDelta == -2 && valueOf(checkerMoving) == QUEEN)) &&
                     colorOf(prey) == LIGHT){

                lineEnd = lineSelected;
                columnEnd = columnSelected;
                return CAPTURE_OK;
            }
            else return INVALID_SPACE;
        }
        else return INVALID_SPACE;
    }

    private void moveChecker() {
        board[lineEnd][columnEnd] = board[lineOrigin][columnOrigin];
        board[lineOrigin][columnOrigin] = EMPTY;

        BoardActivity.updateBoard(lineOrigin, columnOrigin, board[lineOrigin][columnOrigin]);
        BoardActivity.updateBoard(lineEnd, columnEnd, board[lineEnd][columnEnd]);
    }

    private void makeCapture() {
        int deltaLine = lineEnd - lineOrigin;
        int deltaColumn = columnEnd - columnOrigin;

        int linePrey = lineOrigin + deltaLine/2;
        int columnPrey = columnOrigin + deltaColumn/2;

        if (colorOf(board[linePrey][columnPrey]) == LIGHT)
            nLightCheckers--;
        else
            nDarkCheckers--;

        board[lineEnd][columnEnd] = board[lineOrigin][columnOrigin];
        board[lineOrigin][columnOrigin] = EMPTY;
        board[linePrey][columnPrey] = EMPTY;

        BoardActivity.updateBoard(lineOrigin, columnOrigin, board[lineOrigin][columnOrigin]);
        BoardActivity.updateBoard(lineEnd, columnEnd, board[lineEnd][columnEnd]);
        BoardActivity.updateBoard(linePrey, columnPrey, board[linePrey][columnPrey]);
    }


    private boolean isWithinBorders (int line, int column){
        return (line >= 0 && line < BORDER && column >= 0 && column < BORDER);
    }

    private boolean becameQueen(){
        if (valueOf(board[lineOrigin][columnOrigin]) != QUEEN && (lineEnd == 7 || lineEnd == 0)) {
            board[lineOrigin][columnOrigin] *= 2;
            return true;
        }
        return false;
    }

    private boolean zeroCheckers (){
        return (nDarkCheckers == 0 || nLightCheckers == 0);
    }

   public int getWinner() {
        return winner;
    }

    public int getPlayerTurn(){
        return playerTurn;
    }


    /*The function Math.signum() returns -1 when
    *  the input is negative, 1 when it's positive and 0 when it's (duh) zero. Since the constants
    *  DARK, EMPTY and LIGHT are -1, 0 and 1, respectively, we can determine the color of the piece
    *  in the selected space or if it's empty.*/
    private static int colorOf (int valueInBoard) {
        return (int) Math.signum(valueInBoard);
    }

    private static int valueOf (int valueInBoard) {
        return Math.abs(valueInBoard);
    }

}
