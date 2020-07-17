import structure5.*;

public class PawnPiece extends Piece {

  public PawnPiece(char color, int position) {
    this.color = color;
    this.isSpace = false;
    this.isPawn = true;
    this.position = position;
    this.reinfeld = 1;
    this.pieceChar = 'P';
  }

  public Vector<Move> possibleMoves(int position, ChessBoard board, Vector<Move> allMoves) {
    allMoves = pawnMoves(position, board, allMoves, this.color, "straight");
    allMoves = pawnMoves(position, board, allMoves, this.color, "right");
    allMoves = pawnMoves(position, board, allMoves, this.color, "left");
    return allMoves;
  };

  public String toString() {
    return (color == WHITE)? "P": "p";
  }

  public PawnPiece clonePiece() {
    PawnPiece clone = new PawnPiece(this.color, this.position);
    clone.setPassant(this.isPassant());
    return clone;
  }
}
