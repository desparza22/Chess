public class Move {

  private int start;
  private int end;
  private char pawnPlay = 'n'; // 'n' = none, 'e' = enpassant, 'Q', 'R', 'N', 'B' = promotion, 'P' = push

  public Move(int start, int end) {
    this.start = start;
    this.end = end;
  }

  public Move(int start, int end, char pawnPlay) {
    this.start = start;
    this.end = end;
    this.pawnPlay = pawnPlay;
  }

  public Move(int colFrom, int rowFrom, int colTo, int rowTo, int cols) {
    this.start = coordToIndex(rowFrom, colFrom, cols); // -1 converts to index
    this.end = coordToIndex(rowTo, colTo, cols);
  }

  public Move(int colFrom, int rowFrom, int colTo, int rowTo, int cols, char pawnPlay) {
    this.start = coordToIndex(rowFrom, colFrom, cols); // -1 converts to index
    this.end = coordToIndex(rowTo, colTo, cols);
    this.pawnPlay = pawnPlay;
  }

  public Move(String input, int cols) {
    stringConstructor(input, cols);
  }

  public Move(String input, int cols, char pawnPlay) {
    stringConstructor(input, cols);
    this.pawnPlay = pawnPlay;
  }

  private void stringConstructor(String input, int cols) {
    int comma = input.indexOf(",");
    int colFrom = Integer.parseInt(input.substring(0, comma));
    int colon = input.indexOf(":");
    int rowFrom = Integer.parseInt(input.substring(comma + 1, colon));

    int comma2 = input.lastIndexOf(",");
    int colTo = Integer.parseInt(input.substring(colon + 1, comma2));
    int rowTo = Integer.parseInt(input.substring(comma2 + 1, input.length()));

    this.start = coordToIndex(rowFrom, colFrom, cols); // -1 converts to index
    this.end = coordToIndex(rowTo, colTo, cols);
  }

  private int coordToIndex(int row, int col, int cols) {
    return cols * (row - 1) + col - 1;
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }

  public String toString() {
    return "Move from " + start + ", to " + end;
  }

  public char getPawnPlay() {
    return pawnPlay;
  }

  public static void main(String[] args) {
    Move newMove = new Move("7,3:5,4", 8);
    System.out.println(newMove);

    Move newMove2 = new Move("6,4:7,2", 8);
    System.out.println(newMove2);

    Move newMove3 = new Move("1,1:8,8", 8);
    System.out.println(newMove3);
  }
}
