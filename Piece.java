import structure5.*;
import java.lang.Math;
import java.util.Random;
import java.util.Scanner;

abstract class Piece {

  protected char WHITE = 'W';
  protected char BLACK = 'B';
  protected boolean isSpace;
  protected char color;
  protected int position;
  protected boolean isPawn = false;
  protected int enPassant = -1;
  protected int reinfeld;
  protected char pieceChar;
  protected boolean moved;

  //abstract Vector<Move> possibleMoves(int position, ChessBoard board, int cols);
  public Vector<Move> possibleMoves(int position, ChessBoard board, Vector<Move> allMoves) {
    Assert.pre(!board.getPiece(position).isSpace(), "Cannot find moves for a space");
    return allMoves;
  };

  abstract Piece clonePiece();

  public boolean isPiece(char piece) {
    if(piece == this.pieceChar) {
      return true;
    }
    return false;
  }

  public boolean isSpace() {
    return this.isSpace;
  };

  public boolean isColor(char colorCompare) {
    return this.color == colorCompare;
  }

  public char getColor() {
    return this.color;
  }

  public int getPosition() {
    return this.position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public int isPassant() {
    return this.enPassant;
  }

  public void setPassant(int enPassant) {
    this.enPassant = enPassant;
  }

  public int getReinfeld() {
    return this.reinfeld;
  }

  public boolean hasMoved() {
    return this.moved;
  }

  public static void main(String[] args) {
    char WHITE = 'W';
    char BLACK = 'B';
    char current = WHITE;
    Random r = new Random();

    System.out.println("Hello girl");
    ChessBoard board = new ChessBoard();
    System.out.println(board);

    Vector<Move> allMoves;

    boolean moveAgain = true;
    Scanner s = new Scanner(System.in);
    while(moveAgain) {
      for(int i = 0; i < 10; i++) {
        allMoves = board.possibleMoves(current);
        Move move = allMoves.get(r.nextInt(allMoves.size()));
        board = new ChessBoard(board, move);
        //System.out.println(board);
        current = (current == WHITE)? BLACK: WHITE;
      }
      System.out.println(board);
      System.out.println("Make more moves? (true/false)");
      moveAgain = s.nextBoolean();
    }

    boolean tryAgain = true;
    while(tryAgain) {
      System.out.println(board);
      System.out.println("Enter the starting position: ");
      int start = s.nextInt();
      System.out.println("Enter the ending position: ");
      int end = s.nextInt();
      System.out.println("Enter the color to be checked: ");
      char color = s.next().charAt(0);
      System.out.println(canMove(start, end, board, color));
      System.out.println("Try another piece? (true/false)");
      tryAgain = s.nextBoolean();
    }



  }

}
