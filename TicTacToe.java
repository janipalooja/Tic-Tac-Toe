import javafx.application.Application;
import javafx.application.Platform;

import java.util.TimerTask;
import java.util.Timer;
import java.util.Random;

import javafx.stage.Stage;
import javafx.stage.Modality;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.scene.Scene;

import javafx.scene.text.Text;
import javafx.scene.text.Font;

import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;

import javafx.scene.control.Menu;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class TicTacToe extends Application {

   private boolean isPlayersTurn = true;
   private Tile[][] board = new Tile[3][3];
   private int click = 0;

   @Override
   public void start(Stage primaryStage) {

      BorderPane root = new BorderPane();
      Pane grid = new Pane();

      Menu menu = new Menu("Peli");
      MenuItem menuItem1 = new MenuItem("Uusi peli");
      MenuItem menuItem2 = new MenuItem("Lopeta");

      menuItem1.setOnAction(e -> {
         Platform.runLater( () -> new TicTacToe().start( new Stage() ) );
         primaryStage.close();
      });

      menuItem2.setOnAction(e -> {
          Platform.exit();
      });

      menu.getItems().add(menuItem1);
      menu.getItems().add(menuItem2);

      MenuBar menuBar = new MenuBar();
      menuBar.getMenus().add(menu);

      root.setTop(menuBar);

      // Creating 3x3 grid tile layout...
      for(int i = 0; i < 3; i++) {
         for(int j = 0; j < 3; j++) {
            Tile tile = new Tile();
            tile.setTranslateX(j * 200);
            tile.setTranslateY(i * 200);
            grid.getChildren().add(tile);
            board[j][i] = tile;
         }
      }

      root.setCenter(grid);

      Scene scene = new Scene(root, 600, 625);
      primaryStage.setTitle("Ristinolla Peli");
      primaryStage.setScene(scene);
      primaryStage.show();
   }

   private void display(String title, String message){
      Stage window = new Stage();

      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle(title);
      window.setMinWidth(250);

      Label label = new Label();
      label.setText(message);

      Button closeButton = new Button("Sulje");
      closeButton.setOnAction(e -> window.close());

      VBox layout = new VBox(50);
      layout.getChildren().addAll(label, closeButton);
      layout.setAlignment(Pos.CENTER);
      layout.setPadding(new Insets(50));

      Scene scene = new Scene(layout);

      window.setScene(scene);
      window.showAndWait();
   }

   private boolean gameover(){
      // Tarkistaa onko jompikumpi voittanut vaakalinjalla
      for(int i = 0; i < 3; i++){
         if(!board[0][i].text.getText().isEmpty() && !board[1][i].text.getText().isEmpty() && !board[2][i].text.getText().isEmpty()){
            if(board[0][i].text.getText() == board[1][i].text.getText() && board[1][i].text.getText() == board[2][i].text.getText()){
               for(int k = 0; k < 3; k++){
                  board[k][i].winAnimation();
               }
               return true;
            }
         }
      }
      // Tarkistaa onko jompikumpi voittanut pystylinjalla
      for(int i = 0; i < 3; i++){
         if(!board[i][0].text.getText().isEmpty() && !board[i][1].text.getText().isEmpty() && !board[i][2].text.getText().isEmpty()){
            if(board[i][0].text.getText() == board[i][1].text.getText() && board[i][1].text.getText() == board[i][2].text.getText()){
               for(int k = 0; k < 3; k++){
                  board[i][k].winAnimation();
               }
               return true;
            }
         }
      }
      // Tarkistaa onko jompikumpi voittanut viistolinjalla
      if(!board[0][0].text.getText().isEmpty() && !board[1][1].text.getText().isEmpty() && !board[2][2].text.getText().isEmpty()){
         if(board[0][0].text.getText() == board[1][1].text.getText() && board[1][1].text.getText() == board[2][2].text.getText()){
            for(int k = 0; k < 3; k++){
               board[k][k].winAnimation();
            }
            return true;
         }
      }
      if(!board[2][0].text.getText().isEmpty() && !board[1][1].text.getText().isEmpty() && !board[0][2].text.getText().isEmpty()){
         if(board[2][0].text.getText() == board[1][1].text.getText() && board[1][1].text.getText() == board[0][2].text.getText()){
            int j = 2;
            for(int k = 0; k < 3; k++){
               board[j][k].winAnimation();
               j--;
            }
            return true;
         }
      }
	  
	  return false;
   }

   // 0,0 - 1,0 - 2,0
   // 0,1 - 1,1 - 2,1
   // 0,2 - 1,2 - 2,2

   private void checkIfPlayerIsWinning() {

      Random rand1 = new Random();
      Random rand2 = new Random();
      int random1 = rand1.nextInt(3);
      int random2 = rand2.nextInt(3);

        // Tarkista onko botti voittamassa jollain vaakalinjalla
        for(int i = 0; i < 3; i++){
           if(board[0][i].text.getText() == "O" && board[1][i].text.getText() == "O"){
              if(board[2][i].text.getText().isEmpty()){
                 board[2][i].draw("O");
                 click++;
                 return;
              }
           }
           if(board[1][i].text.getText() == "O" && board[2][i].text.getText() == "O"){
              if(board[0][i].text.getText().isEmpty()){
                 board[0][i].draw("O");
                 click++;
                 return;
              }
           }
           if(board[0][i].text.getText() == "O" && board[2][i].text.getText() == "O"){
              if(board[1][i].text.getText().isEmpty()){
                 board[1][i].draw("O");
                 click++;
                 return;
              }
           }
        }

        // Tarkista onko botti voittamassa jollain pystylinjalla
        for(int i = 0; i < 3; i++){
           if(board[i][0].text.getText() == "O" && board[i][1].text.getText() == "O"){
              if(board[i][2].text.getText().isEmpty()){
                 board[i][2].draw("O");
                 click++;
                 return;
              }
           }
           if(board[i][1].text.getText() == "O" && board[i][2].text.getText() == "O"){
              if(board[i][0].text.getText().isEmpty()){
                 board[i][0].draw("O");
                 click++;
                 return;
              }
           }
           if(board[i][0].text.getText() == "O" && board[i][2].text.getText() == "O"){
              if(board[i][1].text.getText().isEmpty()){
                 board[i][1].draw("O");
                 click++;
                 return;
              }
           }
        }

        // Tarkistaa, että onko botti voittamassa jollain viistolinjalla
        if(board[0][0].text.getText() == "O" && board[1][1].text.getText() == "O"){
           if(board[2][2].text.getText().isEmpty()){
              board[2][2].draw("O");
              click++;
              return;
           }
        }
        if(board[2][2].text.getText() == "O" && board[1][1].text.getText() == "O"){
           if(board[0][0].text.getText().isEmpty()){
              board[0][0].draw("O");
              click++;
              return;
           }
        }
        if(board[2][0].text.getText() == "O" && board[1][1].text.getText() == "O"){
           if(board[0][2].text.getText().isEmpty()){
              board[0][2].draw("O");
              click++;
              return;
           }
        }
        if(board[0][2].text.getText() == "O" && board[1][1].text.getText() == "O"){
           if(board[2][0].text.getText().isEmpty()){
              board[2][0].draw("O");
              click++;
              return;
           }
        }
        if(board[0][0].text.getText() == "O" && board[2][2].text.getText() == "O"){
           if(board[1][1].text.getText().isEmpty()){
              board[1][1].draw("O");
              click++;
              return;
           }
        }
        if(board[2][0].text.getText() == "O" && board[0][2].text.getText() == "O"){
           if(board[1][1].text.getText().isEmpty()){
              board[1][1].draw("O");
              click++;
              return;
           }
        }

        // Tarkistaa, että onko pelaaja voittamassa jollain vaakalinjalla
        for(int i = 0; i < 3; i++){
           if(board[0][i].text.getText() == "X" && board[1][i].text.getText() == "X"){
              if(board[2][i].text.getText().isEmpty()){
                 board[2][i].draw("O");
                 click++;
                 return;
              }
           }
           if(board[1][i].text.getText() == "X" && board[2][i].text.getText() == "X"){
              if(board[0][i].text.getText().isEmpty()){
                 board[0][i].draw("O");
                 click++;
                 return;
              }
           }
           if(board[0][i].text.getText() == "X" && board[2][i].text.getText() == "X"){
              if(board[1][i].text.getText().isEmpty()){
                 board[1][i].draw("O");
                 click++;
                 return;
              }
           }
        }

        // Tarkistaa, että onko pelaaja voittamassa jollain pystylinjalla
        for(int i = 0; i < 3; i++){
           if(board[i][0].text.getText() == "X" && board[i][1].text.getText() == "X"){
              if(board[i][2].text.getText().isEmpty()){
                 board[i][2].draw("O");
                 click++;
                 return;
              }
           }
           if(board[i][1].text.getText() == "X" && board[i][2].text.getText() == "X"){
              if(board[i][0].text.getText().isEmpty()){
                 board[i][0].draw("O");
                 click++;
                 return;
              }
           }
           if(board[i][0].text.getText() == "X" && board[i][2].text.getText() == "X"){
              if(board[i][1].text.getText().isEmpty()){
                 board[i][1].draw("O");
                 click++;
                 return;
              }
           }
        }

        // Tarkistaa, että onko pelaaja voittamassa jollain viistolinjalla
        if(board[0][0].text.getText() == "X" && board[1][1].text.getText() == "X"){
           if(board[2][2].text.getText().isEmpty()){
              board[2][2].draw("O");
              click++;
              return;
           }
        }
        if(board[2][2].text.getText() == "X" && board[1][1].text.getText() == "X"){
           if(board[0][0].text.getText().isEmpty()){
              board[0][0].draw("O");
              click++;
              return;
           }
        }
        if(board[2][0].text.getText() == "X" && board[1][1].text.getText() == "X"){
           if(board[0][2].text.getText().isEmpty()){
              board[0][2].draw("O");
              click++;
              return;
           }
        }
        if(board[0][2].text.getText() == "X" && board[1][1].text.getText() == "X"){
           if(board[2][0].text.getText().isEmpty()){
              board[2][0].draw("O");
              click++;
              return;
           }
        }
        if(board[0][0].text.getText() == "X" && board[2][2].text.getText() == "X"){
           if(board[1][1].text.getText().isEmpty()){
              board[1][1].draw("O");
              click++;
              return;
           }
        }
        if(board[2][0].text.getText() == "X" && board[0][2].text.getText() == "X"){
           if(board[1][1].text.getText().isEmpty()){
              board[1][1].draw("O");
              click++;
              return;
           }
        }
		// Jos botti tai pelaaja ei ole voitolla, niin botti laittaa nollan sattumanvaraisesti johonkin ruutuun
        do{
           random1 = rand1.nextInt(3);
           random2 = rand2.nextInt(3);
           if(board[random2][random1].text.getText().isEmpty()){
               board[random2][random1].draw("O");
               click++;
               return;
           }
        } while (!board[random2][random1].text.getText().isEmpty());

        // 0,0 - 1,0 - 2,0
        // 0,1 - 1,1 - 2,1
        // 0,2 - 1,2 - 2,2
  }

   private class Tile extends StackPane {

      private Text text = new Text();
      Rectangle border = new Rectangle(200, 200); // Leveys, korkeus

      public Tile() {

         border.setFill(Color.DARKGREY);
         border.setStroke(Color.GREY);

         text.setFont(Font.font(75));

         setAlignment(Pos.CENTER);
         getChildren().addAll(border, text);

         setOnMouseClicked( event -> {


                  if(!gameover() && isPlayersTurn && text.getText().isEmpty()) {
                     draw("X");
                     click++;
                     border.setFill(Color.WHITE);
                     text.setFill(Color.GREEN);
                     gameover();

                     if(click < 9){
                        Timer t = new Timer();
                        t.schedule(new TimerTask() {
                                    @Override
                                     public void run() {
                                     if(!gameover()){
                                        checkIfPlayerIsWinning();
                                        gameover();
                                        t.cancel();
                                     }
                                     }
                         }, 1000);
                     }
                     else if(click == 9 && !gameover()) {
                        display("Peli loppui!", "Tuli tasapeli...");
                     }

                  }


         });

      }

      public String getValue() {
         return text.getText();
      }

      private void draw(String value) {
         border.setFill(Color.WHITE);
         text.setFill(Color.RED);
         text.setText(value);
			if(isPlayersTurn) {
			isPlayersTurn = false;
			}
			else {
			isPlayersTurn = true;
			}
      }

      public void winAnimation() {
		 if(!isPlayersTurn) {
			border.setFill(Color.GREEN);
			text.setFill(Color.WHITE);
		 }
		 else { 
			border.setFill(Color.RED);
			text.setFill(Color.WHITE);
		 }
      }

   }

    public static void main(String[] args) {
        launch(args);
    }

}
