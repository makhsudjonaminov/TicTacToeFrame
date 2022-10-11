import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TicTacToeFrame extends JFrame{
    JFrame frame = new JFrame();

    JPanel mainPnl;
    JPanel topPnl;
    JPanel midPnl;
    JPanel botPnl;

    JLabel titleLbl;

    JButton quitButton;


    String player = "X";


    TicTacToeTile[][] board = new TicTacToeTile[3][3];

    boolean dontSwitch;

    final int MOVES_FOR_WIN = 5;
    final int MOVES_FOR_TIE = 7;
    int moveCnt = 0;

    public TicTacToeFrame()
    {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        createTopPanel();
        mainPnl.add(topPnl, BorderLayout.NORTH);
        createMiddlePanel();
        mainPnl.add(midPnl, BorderLayout.CENTER);
        createBottomPanel();
        mainPnl.add(botPnl, BorderLayout.SOUTH);

        add(mainPnl);

        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createTopPanel()
    {
        topPnl = new JPanel();
        titleLbl = new JLabel("TicTacToe",JLabel.CENTER);
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 38));



        topPnl.add(titleLbl);

    }

    private void createMiddlePanel()
    {
        midPnl = new JPanel();
        midPnl.setLayout(new GridLayout(3,3));

        for( int row = 0; row < 3; row++)
            for(int col= 0; col < 3; col++)
            {
                board[row][col] = new TicTacToeTile(row, col);
                board[row][col].setText(" ");
                midPnl.add(board[row][col]);
                board[row][col].addActionListener(
                        (ActionEvent ae) -> {
                            Object source = ae.getSource();
                            if(source instanceof JButton)
                            {
                                JButton buttonSource = (JButton)source;

                                if(buttonSource.getText().equals(" "))
                                {
                                    buttonSource.setText(player);
                                    moveCnt++;
                                    if(moveCnt >= MOVES_FOR_WIN)
                                    {
                                        if(isWin(player))
                                        {
                                            int a = JOptionPane.showConfirmDialog(frame, player + " wins! Play Again?");
                                            if(a == JOptionPane.YES_OPTION)
                                            {
                                                clearBoard();
                                            }
                                            else if (a == JOptionPane.NO_OPTION)
                                            {
                                                System.exit(0);
                                            }
                                        }
                                    }
                                    if(moveCnt >= MOVES_FOR_TIE)
                                    {
                                        if(isTie())
                                        {
                                            int a = JOptionPane.showConfirmDialog(frame, "It's a tie! Play again?");
                                            if(a == JOptionPane.YES_OPTION)
                                            {
                                                clearBoard();
                                            }
                                            else if (a == JOptionPane.NO_OPTION)
                                            {
                                                System.exit(0);
                                            }
                                        }
                                    }


                                    if(player.equals("X") && !dontSwitch)
                                    {
                                        player = "O";
                                    }
                                    else
                                    {
                                        player = "X";
                                    }
                                    dontSwitch = false; //keeps player from switching after a game is over



                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(frame, "That is an invalid move: spot already taken");
                                }
                            }
                        });
            }
    }


    private void createBottomPanel()
    {
        botPnl = new JPanel();
        botPnl.setLayout(new GridLayout(1,2));
        quitButton = new JButton("Quit");

        quitButton.addActionListener((ActionEvent ae) -> System.exit(0));
        botPnl.add(quitButton);
    }

    private boolean isWin(String player)
    {
        if(isColWin(player) || isRowWin(player) || isDiagnalWin(player))
        {
            return true;
        }

        return false;
    }

    private boolean isColWin(String player)
    {
        // checks for a col win for specified player
        for(int col=0; col < 3; col++)
        {
            if(board[0][col].getText().equals(player) &&
                    board[1][col].getText().equals(player) &&
                    board[2][col].getText().equals(player))
            {
                return true;
            }
        }
        return false; // no col win
    }
    private boolean isRowWin(String player)
    {
        // checks for a row win for the specified player
        for(int row=0; row < 3; row++)
        {
            if(board[row][0].getText().equals(player) &&
                    board[row][1].getText().equals(player) &&
                    board[row][2].getText().equals(player))
            {
                return true;
            }
        }
        return false; // no row win
    }

    private boolean isDiagnalWin(String player)
    {
        // checks for a diagonal win for the specified player

        if(board[0][0].getText().equals(player) &&
                board[1][1].getText().equals(player) &&
                board[2][2].getText().equals(player) )
        {
            return true;
        }
        if(board[0][2].getText().equals(player) &&
                board[1][1].getText().equals(player) &&
                board[2][0].getText().equals(player) )
        {
            return true;
        }
        return false;
    }

    private void clearBoard()
    {
        for( int row = 0; row < 3; row++)
            for(int col= 0; col < 3; col++) {
                board[row][col].setText(" ");
            }
        dontSwitch = true;
    }

    private boolean isTie()
    {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so
        // no win is possible
        // Check for row ties
        for(int row=0; row < 3; row++)
        {
            if(board[row][0].getText().equals("X") ||
                    board[row][1].getText().equals("X") ||
                    board[row][2].getText().equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(board[row][0].getText().equals("O") ||
                    board[row][1].getText().equals("O") ||
                    board[row][2].getText().equals("O"))
            {
                oFlag = true; // there is an O in this row
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < 3; col++)
        {
            if(board[0][col].getText().equals("X") ||
                    board[1][col].getText().equals("X") ||
                    board[2][col].getText().equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(board[0][col].getText().equals("O") ||
                    board[1][col].getText().equals("O") ||
                    board[2][col].getText().equals("O"))
            {
                oFlag = true; // there is an O in this col
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = oFlag = false;

        if(board[0][0].getText().equals("X") ||
                board[1][1].getText().equals("X") ||
                board[2][2].getText().equals("X") )
        {
            xFlag = true;
        }
        if(board[0][0].getText().equals("O") ||
                board[1][1].getText().equals("O") ||
                board[2][2].getText().equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;

        if(board[0][2].getText().equals("X") ||
                board[1][1].getText().equals("X") ||
                board[2][0].getText().equals("X") )
        {
            xFlag =  true;
        }
        if(board[0][2].getText().equals("O") ||
                board[1][1].getText().equals("O") ||
                board[2][0].getText().equals("O") )
        {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }

        // Checked every vector so I know I have a tie
        return true;
    }
}
