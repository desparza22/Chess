import structure5.*;

public class CalculatorTest implements Calculable {

  private char WHITE = 'W';
  private char BLACK = 'B';

  public CalculatorTest() {

  }

  public double boardScore(ChessBoard board, char colorToPlay) {
    double boardScore = piecesScore(board);
    boardScore += movesScore(board);
    return boardScore;
  }

  private double piecesScore(ChessBoard board) {
    int size = board.getSize();
    double total = 0;
    for(int i = 0; i < size; i++) {
      Piece current = board.getPiece(i);
      double reinfeld = current.getReinfeld();
      if(current.isColor(BLACK)) {
        reinfeld = reinfeld * -1;
      }
      total += reinfeld;
    }
    return total;
  }

  private double movesScore(ChessBoard board) {
    double whiteMoves = board.possibleMoves(WHITE).size();
    double blackMoves = board.possibleMoves(BLACK).size();
    if(whiteMoves == 0 || blackMoves == 0) {
      return 0.0;
    }
    double ratio = whiteMoves / blackMoves;
    if(ratio < 1) {
      ratio = 1 / ratio * -1;
    }
    return ratio;
  }

}
