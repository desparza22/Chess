import structure5.*;

public class KnightPiece extends Piece {

  public KnightPiece(char color, int position) {
    this.color = color;
    this.isSpace = false;
    this.position = position;
    this.reinfeld = 3;
    this.pieceChar = 'N';
  }

  public Vector<Move> possibleMoves(int position, ChessBoard board, Vector<Move> allMoves) {
    allMoves = lMoves(position, board, allMoves, this.color, "NE");
    allMoves = lMoves(position, board, allMoves, this.color, "EN");
    allMoves = lMoves(position, board, allMoves, this.color, "ES");
    allMoves = lMoves(position, board, allMoves, this.color, "SE");
    allMoves = lMoves(position, board, allMoves, this.color, "SW");
    allMoves = lMoves(position, board, allMoves, this.color, "WS");
    allMoves = lMoves(position, board, allMoves, this.color, "WN");
    allMoves = lMoves(position, board, allMoves, this.color, "NW");
    return allMoves;
  };

  public String toString() {
    return (color == WHITE)? "K": "k";
  }

  public KnightPiece clonePiece() {
    KnightPiece clone = new KnightPiece(this.color, this.position);
    return clone;
  }




}
