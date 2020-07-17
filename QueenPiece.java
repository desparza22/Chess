import structure5.*;

public class QueenPiece extends Piece {

  public QueenPiece(char color, int position) {
    this.color = color;
    this.isSpace = false;
    this.position = position;
    this.reinfeld = 9;
    this.pieceChar = 'Q';
  }

  public Vector<Move> possibleMoves(int position, ChessBoard board, Vector<Move> allMoves) {
    allMoves = straightMoves(position, board, allMoves, this.color, "upwards");
    allMoves = straightMoves(position, board, allMoves, this.color, "downwards");
    allMoves = straightMoves(position, board, allMoves, this.color, "left");
    allMoves = straightMoves(position, board, allMoves, this.color, "right");
    allMoves = diagonalMoves(position, board, allMoves, this.color, "NE");
    allMoves = diagonalMoves(position, board, allMoves, this.color, "SE");
    allMoves = diagonalMoves(position, board, allMoves, this.color, "SW");
    allMoves = diagonalMoves(position, board, allMoves, this.color, "NW");
    return allMoves;
  };

  public String toString() {
    return (color == WHITE)? "Q": "q";
  }

  public QueenPiece clonePiece() {
    QueenPiece clone = new QueenPiece(this.color, this.position);
    return clone;
  }


}
