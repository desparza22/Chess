

public Vector<Move> pawnMoves(int position, ChessBoard board, Vector<Move> allMoves, char colorCompare, String direction) {
  int cols = board.getCols();
  int rows = board.getRows();
  int newPosition;
  boolean stopper;

  if(colorCompare == WHITE) {
    stopper = (direction == "straight")? ((position - cols) >= 0):
    (direction == "right")? ((position % cols) != (cols - 1)):
    ((position % cols) != 0);
    newPosition = (direction == "straight")? (position - cols):
    (direction == "right"): (position - cols + 1):
    (position - cols - 1);
  } else if (colorCompare == BLACK) {
    stopper = (direction == "straight")? ((position + cols) <= (rows * cols - 1)):
    (direction == "right")? ((position % cols) != 0):
    ((position % cols) != (cols - 1));
    newPosition = (direction == "straight")? (position + cols):
    (direction == "right")? (position + cols - 1):
    (position + cols + 1);
  } else {
    System.out.println("Operating on a space or colorless piece?");
  }

  Piece occupying = board.getPiece(newPosition);
  if(direction == straight) {
    if(occupying.isSpace()) { // also necessary for nested case of two space push
      boolean unmoved = (colorCompare == WHITE)? (position % cols == 6): (position % cols == 1);
      if(unmoved) {
        int pushPosition = (colorCompare == WHITE)? (position - (2 * cols)): (position + (2 * cols));
        Piece occupyingPush = board.getPiece(pushPosition);
        if(occupyingPush.isSpace()) {
          Move addMove = new Move(position, pushPosition, 'P');
        }
        Move addMove = new Move(position, newPosition);
        allMoves.add(addMove);
      } else {
        boolean promotion = (colorCompare == WHITE)? (position < (2 * cols): (position + (2 * cols) >= (rows * cols - 1);
        if(promotion) {
          Move addMove1 = new Move(position, newPosition, 'Q');
          allMoves.add(addMove1);
          Move addMove2 = new Move(position, newPosition, 'R');
          allMoves.add(addMove2);
          Move addMove3 = new Move(position, newPosition, 'N');
          allMoves.add(addMove3);
          Move addMove4 = new Move(position, newPosition, 'B');
          allMoves.add(addMove4);
        } else {
          Move addMove = new Move(position, newPosition);
          allMoves.add(addMove);
        }
      }
    }
  } else {
    if(!occupying.isColor(colorCompare)) {
      Move addMove = new Move(position, newPosition);
      allMoves.add(addMove);
    } else if(occupying.isPassant() == board.getMoves()) {
      Move addMove = new Move(position, newPosition)
    }
    boolean middle = (colorCompare == WHITE)? (position / cols == 3): (position / cols == 4);
    if(middle) {
      int sidePosition = (colorCompare == WHITE)? (newPosition + cols): (newPosition - cols);
      Piece side = beard.getPiece(sidePosition);
      if(side.isPassant() == board.numMoves()) {
        Move addMove = new Move(position, newPosition, 'e');
        allMoves.add(addMove);
      }
    }
  }
}
