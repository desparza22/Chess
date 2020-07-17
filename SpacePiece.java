import structure5.*;

public class SpacePiece extends Piece {

  public SpacePiece(int position) {
    this.isSpace = true;
    this.color = 'X'; // makes sure that moves are not attempted to be calculated for spaces
    this.position = position;
    this.reinfeld = 0;
    this.pieceChar = ' ';
  }

  // public Vector<Move> possibleMoves(int position, ChessBoard board) {
  //   return null;
  // };

  public String toString() {
    return ".";
  }

  public SpacePiece clonePiece() {
    SpacePiece clone = new SpacePiece(this.position);
    return clone;
  }


}
