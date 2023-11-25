
package jframegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JFrameGame extends JFrame{
    
    //Declaramos los Elemento de la Interfaz Grafica
    //guessField es el campo de texto para ingresar el numero a adivinar
    private JTextField guessField;
    //resultLabel es la etiqueta que muestra el resultado de la verificación (Si el numero es correcto, mayor o menor)
    //remainingLabel es la etiqueta que muestra la cantidad de intentos restantes
    private JLabel resultLabel, remainingLabel;
    //checkButton es el boton que se utiliza para verificar si el numero ingresado es correcto
    //newButton es el boton utilizado para generar un nuevo numero aleatorio
    private JButton checkButton, newButton;
    //randomNumber es el numero aleatorio que se debe adivinar
    //remainingNuber es la cantidad de intentos restantes para adivinar el numero
    private int randomNumber, remainingGuesses;
    
    
    //Se crea el cntructor
    public JFrameGame(){
        //En el contructor se configura la ventana
        //Establecer el titulo a la ventana ("Juego de adivinar numero")
        setTitle("Juego de adivinar numero");
        //Establecer el tamaño de la ventana
        setSize(400, 200);
        //Establecer la posición de la ventana en le centro de la pantalla
        setLocationRelativeTo(null);
        //Establecer la operación por defecto al cerrar la ventana como salir de la aplicación
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //CREACIÓN DE ELEMNTOS DE LA INTERFAZ
        
        //guessField campo de texto en donde el ususario ingresa el numero a aadivinar
        guessField = new JTextField(10);
        //checkButtones el boton que se utiliza para verficar el compo ingresado
        checkButton = new JButton("Verificar");
        //newButton es el boton que se utiliza para generar un nuevo numero aleatorio
        newButton = new JButton("Nuevo");
        //resultLabel es la etiqueta que muestra el resultado de la verificación
        resultLabel = new JLabel("Ingrese un numero y presione Verificar");
        //remainingLabel
        remainingLabel = new JLabel("Intentos restantes: ");
        
        //crear el contenedor de elementos
        JPanel content = new JPanel();
        
        //Se agregan los elementos al panel 
        content.add(guessField);
        content.add(checkButton);
        content.add(newButton);
        content.add(resultLabel);
        content.add(remainingLabel);
        
        //Añadoir contenedor a la ventana principal
        add(content);
        
        //Generar numero aleatorio y establecer intentos restantes
        randomNumber = new Random().nextInt(100)+1;
        remainingGuesses = 5;
        
        //Añdir acciones a los botones
        checkButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                //Obtener el numero ingresado en el campo de texto y convertirlo en entero
                int guess = Integer.parseInt(guessField.getText());
                //Verificar si el numero ingresado es igual al numero generado aleatoriamente
                if(guess == randomNumber){
                    
                    //Si el numero es correcto, mostrar mensaje de felicitaciones y deshabilitar el boton
                    resultLabel.setText("¡Felicidades, Adivinaste el numero!");
                    checkButton.setEnabled(false);
                }else{
                    //Si el numero es incorrecto, mostrar mensaje indicando si el numero es mayor o menor
                    if(guess < randomNumber){
                        resultLabel.setText("El numero es mayor");
                    }else{
                        resultLabel.setText("El numero es menor");
                    }
                    //Restar un intento y actualizar la etiqueta de Intentos restantes
                    remainingGuesses --;
                    remainingLabel.setText("Intentos restantes: " + remainingGuesses);
                    
                    //Si no quedan intentos mostar el mensaje de que se acabaron los intentos y deshabilitar el boton
                    if(remainingGuesses == 0){
                        resultLabel.setText("Se acabaron los intentos. El numero era: " + randomNumber);
                        checkButton.setEnabled(false);
                    }
                }
            }
        });
        
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Generar un nuevo numero aleatorio de 1 a 100
                randomNumber = new Random().nextInt(100)+1;
                //
                remainingGuesses = 5;
                //Establecer intentos restantes de nuevo a 5
                remainingLabel.setText("Intentos restantes: " + remainingGuesses);
                //Habilitar el boton de verificar
                checkButton.setEnabled(true);
                //Establecer el mensaje de resuelto a "Ingrese un numero y presione Verificar"
                resultLabel = new JLabel("Ingrese un numero y presione Verificar");
                //Limpiar el campo de texto para ingresar el texto
                guessField.setText("");
            }
        });
    }

    public static void main(String[] args) {
        
        //Crear una nueva instancia
        JFrameGame game = new JFrameGame();
        //Hacer visible la ventana de la aplicación
        game.setVisible(true);
        
    }
    
}
