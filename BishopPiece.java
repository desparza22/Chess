import structure5.*;

public class BishopPiece extends Piece {


  public BishopPiece(char color, int position) {
    this.color = color;
    this.isSpace = false;
    this.position = position;
    this.reinfeld = 3;
    this.pieceChar = 'B';
  }

  public Vector<Move> possibleMoves(int position, ChessBoard board, Vector<Move> allMoves) {
    allMoves = diagonalMoves(position, board, allMoves, this.color, "NE");
    allMoves = diagonalMoves(position, board, allMoves, this.color, "SE");
    allMoves = diagonalMoves(position, board, allMoves, this.color, "SW");
    allMoves = diagonalMoves(position, board, allMoves, this.color, "NW");
    return allMoves;
  };

  public String toString() {
    return (color == WHITE)? "B": "b";
  }

  public BishopPiece clonePiece() {
    BishopPiece clone = new BishopPiece(this.color, this.position);
    return clone;
  }




}
