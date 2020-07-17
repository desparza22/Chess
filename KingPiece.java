import structure5.*;

public class KingPiece extends Piece {

  public KingPiece(char color, int position) {
    this.color = color;
    this.isSpace = false;
    this.position = position;
    this.reinfeld = 100;
    this.pieceChar = 'K';
    this.moved = false;
  }


  // public Vector<Move> possibleMoves(int position, ChessBoard board) {
  //   return null;
  // };

  public String toString() {
    return (color == WHITE)? "T": "t";
  }

  public KingPiece clonePiece() {
    KingPiece clone = new KingPiece(this.color, this.position);
    clone.moved = this.moved; // once king has moved, information will pass on to it's children
    return clone;
  }


}
