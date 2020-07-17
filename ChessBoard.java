import structure5.*;
import java.util.Scanner;
import java.util.Random;

public class ChessBoard {

  private Vector<Move> whiteMoves;
  private Vector<Move> blackMoves;
  private Piece[] board;
  private char WHITE = 'W';
  private char BLACK = 'B';
  private int rows;
  private int cols;
  private int numMoves; // update with ChessBoard(ChessBoard, Move), then pushed pawns would store move they pushed at
  private int whiteKingPosition;
  private int blackKingPosition;

  public ChessBoard() {
    rows = 8;
    cols = 8;
    numMoves = 0;
    board = new Piece[rows*cols];
    fillBoard();
  }

  public ChessBoard(ChessBoard previous, Move newMove) {
    char pawnPlay = newMove.getPawnPlay(); // pawnPlay == 'n', 'e', 'Q', 'R', 'N', or 'B', or 'P'
    int start = newMove.getStart();
    int end = newMove.getEnd();
    this.rows = previous.getRows();
    this.cols = previous.getCols();
    this.board = previous.copyBoard();
    this.numMoves = previous.getMoves() + 1;
    this.whiteKingPosition = previous.getKingPosition(WHITE);
    this.blackKingPosition = previous.getKingPosition(BLACK);

    // universal
    Piece active = getPiece(start);
    char color = active.getColor();
    if(active.isPiece('K')) {
      updateKingPosition(color, end);
    }
    Piece space = new SpacePiece(start);
    setPiece(space, start);

    if(pawnPlay == 'n') { // no promotion or enPassant or double push
      active.setPosition(end);
      setPiece(active, end);
    } else if (pawnPlay == 'P') {
      active.setPosition(end);
      active.setPassant(getMoves());
      setPiece(active, end);
    } else if(pawnPlay == 'e') { // enPassant
      active.setPosition(end);
      int taken = (color ==  WHITE)? end + this.cols: end - this.cols;
      Piece space2 = new SpacePiece(taken);
      setPiece(active, end);
      setPiece(space2, taken);
    } else { // promotion
      Piece promoted;
      if(pawnPlay == 'Q') {promoted = new QueenPiece(color, end);
      } else if (pawnPlay == 'R'){promoted = new RookPiece(color, end);
      } else if (pawnPlay == 'N'){promoted = new KnightPiece(color, end);
      } else {promoted = new BishopPiece(color, end);}
      setPiece(promoted, end);
    }
  }

  private void fillBoard() {
    addPieces(BLACK, 0);
    addPawns(BLACK, 8);
    for(int i = 16; i < 48; i++) {
      Piece Space = new SpacePiece(i); // position = i
      board[i] = Space;
    }
    addPawns(WHITE, 48);
    addPieces(WHITE, 56);
  }

  private void addPieces(char color, int start) {
    int i = 0;
    Piece Rook1 = new RookPiece(color, start + i);
    setPiece(Rook1, start + i);
    i++;
    Piece Knight1 = new KnightPiece(color, start + i);
    setPiece(Knight1, start + i);
    i++;
    Piece Bishop1 = new BishopPiece(color, start + i);
    setPiece(Bishop1, start + i);
    i++;
    Piece Queen = new QueenPiece(color, start + i);
    setPiece(Queen, start + i);
    i++;
    Piece King = new KingPiece(color, start + i);
    setPiece(King, start + i);
    updateKingPosition(color, start + i);
    i++;
    Piece Bishop2 = new BishopPiece(color, start + i);
    setPiece(Bishop2, start + i);
    i++;
    Piece Knight2 = new KnightPiece(color, start + i);
    setPiece(Knight2, start + i);
    i++;
    Piece Rook2 = new RookPiece(color, start + i);
    setPiece(Rook2, start + i);
  }

  private void addPawns(char color, int start) {
    for(int i = 0; i < 8; i++) {
      Piece Pawn = new PawnPiece(color, start + i);
      setPiece(Pawn, start + i);
    }
  }

  public Vector<Move> possibleMoves(char color) {
    System.out.println("Don't forget, if king is in check, make a board for each move and scan to make sure king not still in check");

    Vector<Move> allMoves = new Vector<Move>();
    for(int i = 0; i < getSize(); i++) {
      int position = i;
      Piece current = getPiece(position);
      if(current.isColor(color)) {
        allMoves = current.possibleMoves(position, this, allMoves);
      }
    }
    return allMoves;
  }

  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }

  public Piece getPiece(int location) {
    return board[location];
  }

  public void setPiece(Piece newPiece, int location) {
    board[location] = newPiece;
  }

  public int getSize() {
    return board.length;
  }

  public Piece[] getBoard() {
    return board;
  }

  private Piece[] copyBoard() {
    Piece[] newBoard = new Piece[rows*cols];
    for(int i = 0; i < getSize(); i++) {
      Piece current = getPiece(i);
      Piece copy = current.clonePiece();
      newBoard[i] = copy;
    }
    return newBoard;
  }

  public int getMoves() {
    return numMoves;
  }

  private void updateKingPosition(char color, int position) {
    if(color == WHITE) {
      this.whiteKingPosition = position;
    } else {
      this.blackKingPosition = position;
    }
  }

  public int getKingPosition(char color) {
    if(color == WHITE) {
      return this.whiteKingPosition;
    } else {
      return this.blackKingPosition;
    }
  }

  public int[] positionOf(char piece, char color) {
    int found = 0;
    int[] positions = new int[2];
    for(int i = 0; i < getSize(); i++) {
      Piece current = getPiece(i);
      if(current.isPiece(piece) && current.isColor(color)) {
        if(found == 0) {
          positions[0] = i;
        } else {
          positions[1] = i;
          break;
        }
      }
    }
    if(found == 1) {
      positions[1] = -1;
    } else if(found == 0) {
      System.out.println("No pieces found of given type error in ChessBoard.java/positionOf");
    }
    return positions;
  }










  // possible moves helper
  // moves below//
  // TODO: get rid of print statement when piece is pinned
  public Vector<Move> straightMoves(int position, ChessBoard board, Vector<Move> allMoves, char colorCompare, String direction) {
    // int cols = board.getCols();
    // int rows = board.getRows();
    int cols = getCols();
    int rows = getRows();
    int newPosition = position;
    boolean stopper = (direction == "upwards")? (newPosition >= cols):
    (direction == "downwards")? ((newPosition + cols) <= (cols * rows - 1)):
    (direction == "left")? (newPosition % cols != 0): (newPosition % cols != (cols - 1));

    while(stopper) {
      if(direction == "upwards") { newPosition -= cols;} else if(direction == "downwards") {
        newPosition += cols;} else if(direction == "left") { newPosition--;
      } else if(direction == "right") { newPosition++;} else {
        System.out.println("Direction misspelled?");}
      Move addMove = new Move(position, newPosition);
      Piece occupying = board.getPiece(newPosition);
      if(!occupying.isSpace()) {
        if(!occupying.isColor(colorCompare)) {
          addMove(addMove, allMoves, position, newPosition, board, colorCompare);
        }
        break;
      }
      addMove(addMove, allMoves, position, newPosition, board, colorCompare);
      stopper = (direction == "upwards")? (newPosition >= cols):
      (direction == "downwards")? (newPosition + cols <= (cols * rows - 1)):
      (direction == "left")? (newPosition % cols != 0): (newPosition % cols != (cols - 1));
    }
    return allMoves;
  }

  public Vector<Move> diagonalMoves(int position, ChessBoard board, Vector<Move> allMoves, char colorCompare, String direction) {
    int cols = board.getCols();
    int rows = board.getRows();
    int size = board.getSize();
    int newPosition = position;
    boolean stopper = (direction == "NE")? ((newPosition >= cols) && ((newPosition % cols) != (cols - 1))):
    (direction == "SE")? (((newPosition + cols) <= (size - 1)) && ((newPosition % cols) != (cols - 1))):
    (direction == "SW")? (((newPosition + cols) <= (size - 1)) && ((newPosition % cols) != 0)):
    ((newPosition >= cols) && ((newPosition % cols) != 0));

    while(stopper) {
      newPosition = (direction == "NE")? (newPosition - cols + 1):
      (direction == "SE")? (newPosition + cols + 1):
      (direction == "SW")? (newPosition + cols - 1): (newPosition - cols - 1);
      Move addMove = new Move(position, newPosition);
      Piece occupying = board.getPiece(newPosition);
      if(!occupying.isSpace()) {
        if(!occupying.isColor(colorCompare)) {
          addMove(addMove, allMoves, position, newPosition, board, colorCompare);
        }
        break;
      }
      addMove(addMove, allMoves, position, newPosition, board, colorCompare);
      stopper = (direction == "NE")? ((newPosition >= cols) && ((newPosition % cols) != (cols - 1))):
      (direction == "SE")? (((newPosition + cols) <= (size - 1)) && ((newPosition % cols) != (cols - 1))):
      (direction == "SW")? (((newPosition + cols) <= (size - 1)) && ((newPosition % cols) != 0)):
      ((newPosition >= cols) && ((newPosition % cols) != 0));
    }
    return allMoves;
  }

  public Vector<Move> lMoves(int position, ChessBoard board, Vector<Move> allMoves, char colorCompare, String direction) {
    int cols = board.getCols();
    int rows = board.getRows();
    int size = board.getSize();
    int newPosition;
    boolean stopper = (direction == "NE")? (((position) >= (2 * cols)) && (position % cols != (cols - 1))):
    (direction == "EN")? (((position % cols) != (cols - 1)) && ((position % cols) != (cols - 2)) && position >= cols):
    (direction == "ES")? (((position % cols) != (cols - 1)) && ((position % cols) != (cols - 2)) && ((position + cols) <= (size - 1))):
    (direction == "SE")? (((position + (2 * cols)) <= (size - 1)) && ((position % cols) != (cols - 1))):
    (direction == "SW")? ((position + (2 * cols)) <= (size - 1) && ((position % cols) != 0)):
    (direction == "WS")? (((position % cols) != 0) && ((position % cols) != 1) && ((position + cols) <= (size - 1))):
    (direction == "WN")? (((position % cols) != 0) && ((position % cols) != 1) && (position >= cols)): ((position >= (2 * cols)) && ((position % cols) != 0));

    if(stopper) {
      newPosition = (direction == "NE")? position - (2 * cols) + 1:
      (direction == "EN")? position - cols + 2:
      (direction == "ES")? position + cols + 2:
      (direction == "SE")? position + (2 * cols) + 1:
      (direction == "SW")? position + (2 * cols) - 1:
      (direction == "WS")? position + cols - 2:
      (direction == "WN")? position - cols - 2: position - (2 * cols) - 1;
      Piece occupying = board.getPiece(newPosition);
      if(occupying.isSpace() || !occupying.isColor(colorCompare)) {
        Move addMove = new Move(position, newPosition);
        addMove(addMove, allMoves, position, newPosition, board, colorCompare);
      }
    }
    return allMoves;
  }

  public Vector<Move> pawnMoves(int position, ChessBoard board, Vector<Move> allMoves, char colorCompare, String direction) {
    int cols = board.getCols();
    int rows = board.getRows();
    int size = board.getSize();
    int newPosition = -1;
    boolean stopper = false;
    boolean promotion = false;

    if(colorCompare == WHITE) {
      stopper = (direction == "straight")? ((position - cols) >= 0):
      (direction == "right")? ((position % cols) != (cols - 1)):
      ((position % cols) != 0);
      newPosition = (direction == "straight")? (position - cols):
      (direction == "right")? (position - cols + 1):
      (position - cols - 1);
    } else if (colorCompare == BLACK) {
      stopper = (direction == "straight")? ((position + cols) <= (size - 1)):
      (direction == "right")? ((position % cols) != 0):
      ((position % cols) != (cols - 1));
      newPosition = (direction == "straight")? (position + cols):
      (direction == "right")? (position + cols - 1):
      (position + cols + 1);
    } else {
      System.out.println("Operating on a space or colorless piece?");
    }

    if(stopper) {
      Piece occupying = board.getPiece(newPosition);
      if(direction == "straight") {
        if(occupying.isSpace()) { // also necessary for nested case of two space push
          boolean unmoved = (colorCompare == WHITE)? (position / cols == 6): (position / cols == 1);
          if(unmoved) {
            int pushPosition = (colorCompare == WHITE)? (position - (2 * cols)): (position + (2 * cols));
            Piece occupyingPush = board.getPiece(pushPosition);
            if(occupyingPush.isSpace()) {
              Move addMove = new Move(position, pushPosition, 'P');
              addMove(addMove, allMoves, position, newPosition, board, colorCompare);
            }
            Move addMove = new Move(position, newPosition);
            addMove(addMove, allMoves, position, newPosition, board, colorCompare);
          } else {
            promotion = (colorCompare == WHITE)? (position < (2 * cols)): (position + (2 * cols) >= (size - 1));
            if(promotion) {
              Move addMove1 = new Move(position, newPosition, 'Q');
              addMove(addMove1, allMoves, position, newPosition, board, colorCompare);
              Move addMove2 = new Move(position, newPosition, 'R');
              addMove(addMove2, allMoves, position, newPosition, board, colorCompare);
              Move addMove3 = new Move(position, newPosition, 'N');
              addMove(addMove3, allMoves, position, newPosition, board, colorCompare);
              Move addMove4 = new Move(position, newPosition, 'B');
              addMove(addMove4, allMoves, position, newPosition, board, colorCompare);
            } else {
              Move addMove = new Move(position, newPosition);
              addMove(addMove, allMoves, position, newPosition, board, colorCompare);
            }
          }
        }
      } else { // diagonal pawn moves
        if(!occupying.isColor(colorCompare) && !occupying.isSpace()) {
          promotion = (colorCompare == WHITE)? (position < (2 * cols)): (position + (2 * cols) > (size - 1));
          if(promotion) {
            Move addMove1 = new Move(position, newPosition, 'Q');
            addMove(addMove1, allMoves, position, newPosition, board, colorCompare);
            Move addMove2 = new Move(position, newPosition, 'R');
            addMove(addMove2, allMoves, position, newPosition, board, colorCompare);
            Move addMove3 = new Move(position, newPosition, 'N');
            addMove(addMove3, allMoves, position, newPosition, board, colorCompare);
            Move addMove4 = new Move(position, newPosition, 'B');
            addMove(addMove4, allMoves, position, newPosition, board, colorCompare);
          } else {
            Move addMove = new Move(position, newPosition);
            addMove(addMove, allMoves, position, newPosition, board, colorCompare);
          }
        }
        boolean middle = (colorCompare == WHITE)? (position / cols == 3): (position / cols == 4);
        if(middle) {
          int sidePosition = (colorCompare == WHITE)? (newPosition + cols): (newPosition - cols);
          Piece side = board.getPiece(sidePosition);
          if(side.isPassant() == board.getMoves()) {
            Move addMove = new Move(position, newPosition, 'e');
            addMove(addMove, allMoves, position, newPosition, board, colorCompare);
          }
        }
      }
    }
    return allMoves;
  }

  private void addMove(Move move, Vector<Move> allMoves, int position, int newPosition, ChessBoard board, char color) {
    if(board.canReach(position, newPosition, color)) {
      allMoves.add(move);
    } else {
      System.out.println("Piece was pinned");
    }
  }












  // can reach// (piece should be N, R, or B)
  public static boolean canReach(char piece, int positionStart, int positionEnd) {
    if(positionStart == positionEnd) {
      System.out.println("Error? start and end positions are the same");
      return false;
    }
    if(piece == 'N') {
      return canReachKnight(positionStart, positionEnd, board);
    } else if(piece == 'B') {
      return canReachBishop(positionStart, positionEnd, board);
    } else if(piece == 'R') {
      return canReachRook(positionStart, positionEnd, board);
    } else {
      System.out.println("Error? piece was not one of the three expected: Knight, bishop or rook.");
      return false;
    }
  }

  private static boolean canReachKnight(int positionStart, int positionEnd) {
    //int cols = board.getCols();
    int cols = getCols();
    int smallerPosition = (positionStart < positionEnd)? positionStart: positionEnd;
    int largerPosition = (positionStart > positionEnd)? positionStart: positionEnd;

    int largerColumn = largerPosition % cols;
    int largerRow = largerPosition / cols;

    int eastBoundaryLarger = largerColumn; // number of empty spaces between position and east side
    int westBoundaryLarger = (cols - 1) - largerColumn;

    int upTwoRows = smallerPosition + (2 * cols);
    int upOneRow = smallerPosition + (cols);

    if(largerPosition == upTwoRows + 1 && eastBoundaryLarger >= 1) {
      return true;
    } else if(largerPosition == upTwoRows - 1 && westBoundaryLarger >= 1) {
      return true;
    } else if(largerPosition == upOneRow + 2 && eastBoundaryLarger >= 2) {
      return true;
    } else if(largerPosition == upOneRow - 2 && westBoundaryLarger >= 2) {
      return true;
    } else {
      return false;
    }

  }

  private static boolean canReachBishop(int positionStart, int positionEnd) {
    // int cols = board.getCols();
    int cols = getCols();
    int startColumn = positionStart % cols;
    int endColumn = positionEnd % cols;
    int startRow = positionStart / cols;
    int endRow = positionEnd / cols;
    int increment; // if for some reason increment is not updated, funcition will return true
    int boardSideStart;
    int boardSideEnd;

    if(Math.abs(positionStart - positionEnd) % (cols - 1) == 0) { // entire statement checks whether start and end are on the same diagonal
      boardSideStart = ((startColumn + startRow) >= (cols - 1))? 1: 0;
      boardSideEnd = ((endColumn + endRow) >= (cols - 1))? 1: 0;
      if(boardSideStart == boardSideEnd) {
        increment = cols - 1;
      } else {
        return false;
      }
    } else if(Math.abs(positionStart - positionEnd) % (cols + 1) == 0) {
      boardSideStart = (startColumn >= startRow)? 1: 0;
      boardSideEnd = (endColumn >= endRow)? 1: 0;
      if(boardSideStart == boardSideEnd) {
        increment = cols + 1;
      } else {
        return false;
      }
    } else {
      return false;
    }

    int smallerPosition = (positionStart < positionEnd)? positionStart: positionEnd;
    int largerPosition = (positionStart > positionEnd)? positionStart: positionEnd;

    boolean canReach = true;

    for(int check = smallerPosition + increment; check < largerPosition; check += increment) {
      if(!getPiece(check).isSpace()) {
        canReach = false;
        break;
      }
    }
    return canReach;
  }

  private static boolean canReachRook(int positionStart, int positionEnd) {
    boolean canReach = true;
    // int cols = board.getCols();
    int cols = getCols();
    boolean sameRow = (positionStart / cols == positionEnd / cols);
    boolean sameColumn = (Math.abs(positionStart - positionEnd) % cols == 0);
    int increment;
    int smallerPosition;
    int largerPosition;

    if(sameRow) {increment = 1;} else if (sameColumn) {increment = cols;
    } else {
      return false;}

    if(positionStart < positionEnd) {
      smallerPosition = positionStart;
      largerPosition = positionEnd;
    } else {
      smallerPosition = positionEnd;
      largerPosition = positionStart;
    }

    for(int check = smallerPosition + increment; check < largerPosition; check += increment) {
          if(!getPiece(check).isSpace()) {
            canReach = false;
            break;
          }
    }
    return canReach;
  }














  //can move
  // TODO: make nonstatic after testing. put functions wherever a piece moves (except king)
  // can move // testing whether a piece can move from A to B without putting the king into check
  public boolean canMove(int positionStart, int positionEnd, char color) {
    // int cols = board.getCols();
    int cols = getCols();
    // int kingPosition = board.getKingPosition(color);
    int kingPosition = getKingPosition(color);
    int kingColumn = kingPosition % cols;
    int startColumn = positionStart % cols;
    // System.out.println("Called canMove. " + "kingPosition = " + kingPosition + "\nkingColumn = " + kingColumn +
    // ". startPosition = " + positionStart + ". startColumn = " + startColumn);
    // System.out.println("kingPosition - positionStart  %  cols + 1 = " + Math.abs(kingPosition - positionStart) % (cols + 1) +
    // "\n kingPosition - positionStart  %  cols - 1 = " + Math.abs(kingPosition - positionStart) % (cols - 1));
    if(kingColumn == startColumn) {
      int endColumn = positionEnd % cols;
      if(kingColumn != endColumn) {
        return verticalCheck(kingPosition, positionStart, board, color);
      } else {
        //System.out.println("Returned true from kingColumn == startColumn");
        return true;
      }
    }
    int kingRow = kingPosition / cols;
    int startRow = positionStart / cols;
    if(kingRow == startRow) {
      int endRow = positionEnd / cols;
      if(kingRow != endRow) {
        return horizontalCheck(kingPosition, positionStart, board, color);
      } else {
        //System.out.println("Returned true from kingRow == startRow");
        return true;
      }
    }
    if(Math.abs(kingPosition - positionStart) % (cols + 1) == 0) {
      // System.out.println("Got to downright/upleft. \nkingPosition = " + kingPosition +
      // ". \npositionStart = " + positionStart + ".");
      int kingBoardSide = (kingRow >= kingColumn)? 1: 0;
      int startBoardSide = (startRow >= startColumn)? 1: 0;
      if(kingBoardSide == startBoardSide) {
        if(kingPosition < positionStart) {
          return diagonalCheck(kingPosition, positionStart, positionEnd, board, color, "downright");
        } else {
          return diagonalCheck(kingPosition, positionStart, positionEnd, board, color, "upleft");
        }
      } else {
        return true;
      }
    }
    if(Math.abs(kingPosition - positionStart) % (cols - 1) == 0) {
      // System.out.println("Got to upright/downleft. \nkingPosition = " + kingPosition +
      // ". \npositionStart = " + positionStart + ".");
      int kingBoardSide = (kingRow + kingColumn >= (cols - 1))? 1: 0;
      int startBoardSide = (startRow + startColumn >= (cols - 1))? 1: 0;
      if(kingBoardSide == startBoardSide) {
        if(kingPosition < positionStart) {
          return diagonalCheck(kingPosition, positionStart, positionEnd, board, color, "downleft");
        } else {
          return diagonalCheck(kingPosition, positionStart, positionEnd, board, color, "upright");
        }
      }
    }
    return true;
  }

  private boolean diagonalCheck(int kingPosition, int positionStart, int positionEnd, char color, String direction) {
    // int cols = board.getCols();
    int cols = getCols();
    // int size = board.getSize();
    int size = getSize();
    //System.out.println("Got to here: " + direction);
    int increment = (direction == "downright")? cols + 1: (direction == "upleft")? -1 * (cols + 1):
    (direction == "downleft")? cols - 1: -1 * (cols - 1);

    int check = kingPosition + increment;
    //System.out.println("First check is: " + check);

    boolean sideBoundary = (direction == "downright" || direction == "upright")? check % cols != 0:
    check % cols != (cols - 1); // != 0 because that is the index where it has looped around
    //System.out.println("Boolean sideBoundary is: " + sideBoundary);

    while(sideBoundary && check <= size && check >= 0) {
      if(check == positionEnd) {
        System.out.println("Check equaled positionEnd. positionEnd = " + positionEnd);
        return true;
      }
      // Piece occupying = board.getPiece(check);
      Piece occupying = getPiece(check);
      // System.out.println("Occupying piece is at position check. \npiece is: " + occupying +
      // ". \noccupying color is: " + occupying.getColor());
      if(!occupying.isSpace() && check != positionStart) {
        //System.out.println("!isSpace and check!=positionStart");
        if(occupying.isColor(color)) {
          //System.out.println("occupying.isColor" + color);
          return true;
        } else if(occupying.isPiece('Q') || occupying.isPiece('B')) {
          //System.out.println("occupying is Q or B");
          return false;
        }
      }
      check += increment;
      sideBoundary = (direction == "downright" || direction == "upright")? check % cols != 0:
      check % cols != (cols - 1); // != 0 because that is the index where it has looped around
      //System.out.println("For the next iteration: \nsideBoundary = " + sideBoundary + "\ncheck = " + check);
    }
    return true;
  }

  private boolean verticalCheck(int kingPosition, int positionStart, char color) {
    // int cols = board.getCols();
    int cols = getCols();
    int increment = (kingPosition > positionStart)? -1 * cols: cols;
    int boundaryBlack = 0;
    // int boundaryWhite = board.getSize() - 1;
    int booundaryWhite = getSize() - 1;

    for(int check = kingPosition + increment; check <= boundaryWhite && check >= boundaryBlack; check += increment) {
      // Piece current = board.getPiece(check);
      Piece current = getPiece(check);
      if(!current.isSpace() && check != positionStart) {
        if(current.isColor(color)) {
          return true;
        } else if(current.isPiece('Q') || current.isPiece('R')) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean horizontalCheck(int kingPosition, int positionStart, char color) {
    // int cols = board.getCols();
    int cols = getCols();
    int kingRow = kingPosition / cols;
    int increment = (kingPosition > positionStart)? -1: 1;
    int boundaryRight = kingRow * cols + (cols - 1);
    int boundaryLeft = kingRow * cols;

    for(int check = kingPosition + increment; check <= boundaryRight && check >= boundaryLeft; check += increment) {
      // Piece current = board.getPiece(check);
      Piece current = getPiece(check);
      if(!current.isSpace() && check != positionStart) {
        if(current.isColor(color)) {
          return true;
        } else if(current.isPiece('Q') || current.isPiece('R')) {
          return false;
        }
      }
    }
    return true;
  }









  public String toString() {
    String boardString = " ";
    for(int i = 0; i < cols; i++) {
      boardString += i + 1;
    }
    boardString += "\n";
    for(int i = 0; i < rows; i++) {
      boardString += i + 1;
      for(int j = 0; j < cols; j++) {
        int position = cols * i + j;
        boardString += getPiece(position).toString();
      }
      boardString += "\n";
    }
    return boardString;
  }

  public static void main(String[] args) {
    // char WHITE = 'W';
    // char BLACK = 'B';
    //
    //
    // ChessBoard parentBoard = new ChessBoard();
    //
    // for(int i = 0; i < parentBoard.getSize(); i++) {
    //   Piece current = parentBoard.getPiece(i);
    //   if(!current.isSpace()) {
    //     System.out.print(current + ": " + current.getReinfeld() + ", ");
    //   }
    // }
    //
    // System.out.print("\n");
    //
    // Move move1 = new Move(3, 35);
    //
    // ChessBoard childBoard = new ChessBoard(parentBoard, move1);
    //
    // for(int i = 0; i < childBoard.getSize(); i++) {
    //   Piece current = childBoard.getPiece(i);
    //   current.setPosition(15);
    //   if(!current.isSpace()) {
    //     System.out.print(current.getPosition() + ", ");
    //   }
    // }
    //
    // System.out.print("\n");
    //
    // for(int i = 0; i < parentBoard.getSize(); i++) {
    //   Piece current = parentBoard.getPiece(i);
    //   if(!current.isSpace()) {
    //     System.out.print(current.getPosition() + ", ");
    //   }
    // }
    Random r = new Random();
    long startTime = System.nanoTime();
    char WHITE = 'W';
    char BLACK = 'B';
    char current = WHITE;
    Scanner s = new Scanner(System.in);

    ChessBoard theBoard = new ChessBoard();
    System.out.println(theBoard);

    CalculatorTest c = new CalculatorTest();
    double bestScore = 0;



    for(int i = 0; i < 10; i++) {
      current = (current == WHITE)? BLACK: WHITE;
      bestScore = (current == WHITE)? -5000: 5000;
      Vector<Move> allMoves = theBoard.possibleMoves(current);
      ChessBoard bestBoard = new ChessBoard();
      for(int j = 0; j < allMoves.size(); j++) {
        ChessBoard board1 = new ChessBoard(theBoard, allMoves.get(j));
        double score = c.boardScore(board1, current);
        if(current == WHITE) {
          if(score > bestScore) {
            bestBoard = board1;
            bestScore = score;
          }
        } else {
          if(score < bestScore) {
            bestBoard = board1;
            bestScore = score;
          }
        }
      }
      theBoard = bestBoard;
      System.out.println(theBoard + "\nScore: " + bestScore);
      for(int j = 0; j < theBoard.getSize(); j++) {
        Piece currentPiece = theBoard.getPiece(j);
        System.out.print(currentPiece.getPosition() + ", ");
      }
      System.out.print("\n");
    }

    long totalTime = System.nanoTime() - startTime;
    System.out.println(totalTime / 1000000000.1);
  }
}
