import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import structure5.*;

public class OpeningReader {

  private String fileName;
  private Scanner input;
  private int fileSize = -1;
  private char[] columns = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
  private char WHITE = 'W';
  private char BLACK = 'B';

  public OpeningReader(String fileName) {
    this.fileName = fileName;
    try {
      Scanner input = new Scanner(new File(fileName));
      this.input = input;
    } catch(IOException e) {
      System.out.println("File not found");
    }
  }

  // public Vector<Move> nextOpening() {
  //   String openingString = nextOpeningString();
  //   int nextPeriod = openingString.indexOf(".");
  //   int nextSpace = openingString.indexOf(" ");
  //
  //   return openingString;
  // }

  private String nextOpeningString() {
    String currentLine = input.nextLine();
    while(currentLine.length() == 0 || currentLine.charAt(0) == ';') {
      currentLine = input.nextLine();
    }
    while(currentLine.charAt(0) != '1') {
      currentLine = input.nextLine();
    }
    boolean anotherLine = true;
    while(anotherLine && input.hasNextLine()) {
      String nextLine = input.nextLine();
      if(nextLine.length() != 0 && nextLine.charAt(0) != ';') {
        currentLine += " " + nextLine;
      } else {
        anotherLine = false;
      }
    }
    return currentLine;
  }

  public boolean readerHasNextLine() {
    return input.hasNextLine();
  }

  public void practice(String practice) {
    while(practice.contains(".")) {
      int nextPeriod = practice.indexOf(".");
      int nextSpace = practice.indexOf(" ");
      String leftOver = practice.substring(nextSpace + 1, practice.length());
      System.out.println("WhiteMove: *" + practice.substring(nextPeriod + 1, nextSpace) + "*");
      if(leftOver.contains(" ")) {
        int nextSpace2 = leftOver.indexOf(" ");
        System.out.println("Black move: *" + leftOver.substring(0, nextSpace2) + "*");
        practice = leftOver.substring(nextSpace2 + 1, leftOver.length());
      } else {
        break;
      }
    }
  }

  private Move stringToMove(String moveString, ChessBoard currentBoard, char color) {
    int startPosition = -1;
    int endPosition = -1;
    if(moveString == "O-O-O" || moveString == "O-O") {} else {
      char piece = moveString.charAt(0); // lower case (pawn), N, R, Q, B, K

      if(Character.isUpperCase(piece)) { // not a pawn
        int[] positions = currentBoard.positionOf(moveString.charAt(0), color);
        // moveString = moveString.substring(1, moveString.length());
      }

      //last two letters are end position coordinates
      char endColumnChar = moveString.charAt(moveString.length() - 2);
      char endRowChar = moveString.charAt(moveString.length() - 1);
      int endColumnInt = columnInt(endColumnChar);
      int endRowInt = 9 - Character.getNumericValue(endRowChar); // "9 -..." because rows given in reverse
      if(endColumnInt <= 8 && endRowInt <= 8) { // for an 8x8 board
        endPosition = (endRowInt - 1) * 8 + (endColumnInt - 1);
      } else {
        System.out.print("Row or column was out of bounds");
      }

      if(!Character.isUpperCase(piece)) { // piece is a pawn
        if(moveString.contains("x")) { // capture
          int capturePosition = moveString.indexOf("x");
          char pawnColumnChar = moveString.charAt(capturePosition - 1);
          int pawnColumnInt = Character.getNumericValue(pawnColumnChar);
          if(pawnColumnint < endColumnInt) {
            startPosition = (color == WHITE)? endPosition + 7: endPosition - 9;
          } else {
            startPosition = (color == WHITE)? endPosition + 9: endPosition - 7;
          }
        } else { // not a capture. straight push
          int smallPushStart = (color == WHITE)? endPosition + 8: endPosition - 8;
          if(currentBoard.getPiece(smallPushStart).isPiece('P')) {
            startPosition = smallPushStart;
          } else {
            startPosition = (color == WHITE)? endPosition + 16: endPosition - 16;
          }
        }

      }
    }
  }

  private int columnInt(char column) {
    for(int i = 0; i < columns.length; i++) {
      if(columns[i] == column) {
        return i + 1;
      }
    }
    System.out.println("Column not found in OpeningReader.java/columnInt");
    return -1;
  }

  public static void main(String[] args) {
    OpeningReader reader = new OpeningReader("P3eco.txt");
    int i = 0;
    while(reader.readerHasNextLine()) {
      i++;
      String current = reader.nextOpeningString();
      if(current.contains("x")) {
        System.out.println("Practice " + i);
        reader.practice(current);
      }
    }


    // String practice1 = "1.b4 e5 2.Bb2 Bxb4 3.Bxe5 Nf6 4.c4 O-O 5.e3 1/2";
    // String practice2 = "1.b4 1/2";
    // String practice3 = "1.e3 e5 2.c4 d6 3.Nc3 Nc6 4.b3 Nf6 1/2";
    //
    // System.out.println("Practice 1");
    // reader.practice(practice1);
    // System.out.println("Practice 2");
    // reader.practice(practice2);
    // System.out.println("Practice 3");
    // reader.practice(practice3);

  }




}
