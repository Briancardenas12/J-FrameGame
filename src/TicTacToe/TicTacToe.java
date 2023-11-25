package TicTacToe;

//Se importan todos los elemntos que se usaran
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Random;

public class TicTacToe extends JFrame implements ChangeListener, ActionListener{
    //El objeto JSlider slider, es utilizado para cambia el grosor de las lineas en el juego
    private JSlider slider;
    //Los objetos JButton (oButton, xButton) son utilizados para cambair los colores de las fichas
    private JButton oButton, xButton;
    //El objeto Board board es el panel donde se dibuja el juego
    private Board board;
    // La variable lineThickness es utilizada para almacenar el grososr de la linea
    private int lineThickness = 4;
    //Las variables de oColor y xColor almacenan los colores de las fichas
    private Color oColor = Color.BLUE, xColor = Color.RED;
    //
    static final char BLANK = ' ', O = 'O', X= 'X';
    
    private char position[] = {
        BLANK, BLANK, BLANK,
        BLANK, BLANK, BLANK,
        BLANK, BLANK, BLANK};
    
    //Almacen los numeros de victorias, derrotas y empates
    private int wins = 0, losses = 0, draws = 0; 
    
    //Se Inicializa la clase
    
    public TicTacToe(){
        //Esta es la inicialización de la ventana principal del juego. Se establece el titulo del juego
        super("Tic Tac Toe");
        
        //Se crea un panel llamado topPanel, se establece el Layout como FlowLayout y se añaden los elemento grafico necesarios
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        
        //El primero es un Jlabel con el texto "Grosor de la Linea". Seguido del slaider para cambiar el grosor
        topPanel.add(new JLabel("Grosor de la Linea: "));
        //El JSlider es configurado con un rango de 1 a 20 y un valor inicial de 4
        topPanel.add(slider = new JSlider(SwingConstants.HORIZONTAL, 1, 20, 4));
        
        //Se activa la funcion de marcas en Jslider
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.addChangeListener(this);
        
        //Se añaden los dos botones para el cambio de color de la fichas, y el listener para detectar los cambios
        topPanel.add(oButton = new JButton("O Color"));
        topPanel.add(xButton = new JButton("X Color"));
        oButton.addActionListener(this);
        xButton.addActionListener(this);
        
        //Se agregan el TopPanel y Layout NORTH de la ventana principal y se agregar el panel Board
        add(topPanel, BorderLayout.NORTH);
        add(board = new Board(), BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //
        
        setSize(500, 500);
        setVisible(true);
    }
    
    //
    public void stateChanged(ChangeEvent e){
        lineThickness = slider.getValue();
        board.repaint();
    }
 
    public void actionPerformed(ActionEvent e){
        
        if(e.getSource() == oButton){
            Color newColor = JColorChooser.showDialog(this, "Elija un color para O", oColor);
            if(newColor != null){
                oColor = newColor;
            }
        }else if(e.getSource() == xButton){
            Color newColor = JColorChooser.showDialog(this, "Elija un color para X", xColor);
            if(newColor != null){
                xColor = newColor;
            }
        }
        board.repaint();
        
    }
    
    //Creamos el tablero en donde se va aejecutar la aplicación
    private class Board extends JPanel implements MouseListener{
        
        private Random random = new Random();
        private int rows[][] = {{0,2}, {3,5}, {6,8}, {0,6}, {1,7}, {2,8}, {0,8}, {2,6}};
        
        
        //Hacemos le llamado del tablero
        public Board(){
            addMouseListener(this);
        }
        
        //Redibujar el tablero
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            int w = getWidth();
            int h = getHeight();
            Graphics2D g2d = (Graphics2D) g;
            
            //Dibujar la Cuadricula
            g2d.setPaint(Color.WHITE);
            g2d.fill(new Rectangle2D.Double(0, 0, w, h));
            g2d.setPaint(Color.BLACK);
            g2d.setStroke(new BasicStroke(lineThickness));
            g2d.draw(new Line2D.Double(0, h/3, w, h/3));
            g2d.draw(new Line2D.Double(0, h*2/3, w, h*2/3));
            g2d.draw(new Line2D.Double(w/3, 0, w/3, h));
            g2d.draw(new Line2D.Double(w*2/3, 0, w*2/3, h));
            
            
            //Dibujar las O's y las X's
            
            for(int i = 0; i<9; i++){
                double xpos = (i % 3 + 0.5) * w / 3.0;
                double ypos = (i / 3 + 0.5) * h / 3.0;
                double xr = w / 8.0;
                double yr = h / 8.0;
                
                if(position[i] == O){
                    g2d.setPaint(oColor);
                    g2d.draw(new Ellipse2D.Double(xpos - xr, ypos - yr, xr * 2, yr * 2));
                }else if(position[i] == X){
                    g2d.setPaint(xColor);
                    g2d.draw(new Line2D.Double(xpos - xr, ypos - yr, xpos + xr, ypos + yr ));
                    g2d.draw(new Line2D.Double(xpos - xr, ypos + yr, xpos + xr, ypos - yr ));
                    
                }
            }
        }
        
        public void mouseClicked(MouseEvent e){
            int xpos = e.getX() * 3 /getWidth();
            int ypos = e.getY() * 3 /getHeight();
            int pos = xpos + 3 * ypos;
            if(pos >= 0 && pos < 9 && position[pos] == BLANK){
                position[pos] = O;
                repaint();
                putX();
                repaint();
            }
            
        }
        
        //Ignorar otros eventos del mouse
        public void mousePressed(MouseEvent e){
        }
        
        public void mouseReleased(MouseEvent e){
        }
        
        public void mouseEntered(MouseEvent e){
        }
        
        public void mouseExited(MouseEvent e){
        }
        
        //La computadora Juga X
        
        void putX() {
            
            if(won(O)){
                newGame(O);
            }else if(isDraw()){
                newGame(BLANK);
            }
            else{
                nextMove();
                if(won(X)){
                    newGame(X);
                }else if(isDraw()){
                    newGame(BLANK);
                }
            }
        }
        

        
        boolean won(char player){
            for(int i = 0; i < 8; ++i){ 
                if(testRow(player, rows[i][0], rows[i][1])){
                    return true;
                }
            }
            return false;
        }
        
        boolean testRow(char player, int a, int b){
            return position[a] == player && position[b] == player && position[(a + b) / 2] == player;
        }
        
        void nextMove() {
            int r = findRow(X);
            if (r < 0) {
                r = findRow(O);
            }
            if (r < 0) {
                do {
                    r = random.nextInt(9);
                } while (position[r] != BLANK);
            }
            position[r] = X;
        }
        
        
        
        
        //
        int findRow(char player) {
            for (int i = 0; i < 8; ++i) {
                int result = find1Row(player, rows[i][0], rows[i][1]);
                if (result >= 0) {
                    return result;
                }
            }
            return -1;
        }
        
        
        
        int find1Row(char player, int a, int b){
            int c = (a + b) / 2;
            if(position[a] == player && position[b] == player && position[c] == BLANK){
                return c;
            }
            if(position[a] == player && position[c] == player && position[b] == BLANK){
                return b;
            }
            if(position[b] == player && position[c] == player && position[a] == BLANK){
                return a;
            }
            return -1;
        }
        
        boolean isDraw(){
            for(int i = 0; i < 9; ++i){
                if(position[i] == BLANK){
                    return false;
                }
            }
            return true;
        }
        
        //Inicamos el juego
        
        void newGame(char winner) {
            repaint();
            String result;
            if (winner == O) {
                ++wins;
                result = "Ganaste!";
            } else if (winner == X) {
                ++losses;
                result = "Yo Gano!";
            } else {
                result = "Empate";
                ++draws;
            }
            if (JOptionPane.showConfirmDialog(null, "Tú tienes " + wins + " ganas, " + losses + " Perdidas, " + draws + " empates \n"
                    + "¿Jugar de nuevo?", result, JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                System.exit(0);
            }

            // Despejamos el tablero para comenzar el juego nuevo
            for (int j = 0; j < 9; ++j) {
                position[j] = BLANK;
            }

            // La computadora comienza primero cada dos juegos
            if ((wins + losses + draws) % 2 == 1) {
                nextMove();
            }
        } 
    }  
    
    public static void main(String[] args) {
        new TicTacToe();
    }
}
