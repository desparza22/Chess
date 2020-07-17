import structure5.*;

public class RookPiece extends Piece {

  public RookPiece(char color, int position) {
    this.color = color;
    this.isSpace = false;
    this.position = position;
    this.reinfeld = 5;
    this.pieceChar = 'R';
  }

  public Vector<Move> possibleMoves(int position, ChessBoard board, Vector<Move> allMoves) {
    allMoves = straightMoves(position, board, allMoves, this.color, "upwards");
    allMoves = straightMoves(position, board, allMoves, this.color, "downwards");
    allMoves = straightMoves(position, board, allMoves, this.color, "left");
    allMoves = straightMoves(position, board, allMoves, this.color, "right");
    return allMoves;
  };

  public String toString() {
    return (color == WHITE)? "R": "r";
  }

  public RookPiece clonePiece() {
    RookPiece clone = new RookPiece(this.color, this.position);
    return clone;
  }


}
