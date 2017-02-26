package net.mazatlab.dev;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Socket Server
 *
 */
public class App extends Application implements Runnable{
	private String title;
	private int clientMessagesCoodrsX;
	private int clientMessagesCoodrsY;
	private Point clientMessagesCoords;
	private boolean clientMessagesTxtEditable;
	private TextArea clientMessagesTxt;
	private ServerSocket serverSocket;
	private Socket socket;
	private GridPane grid;
	private Pane root;
	
	public App() {
		this.title = "Socket Server";
		
		this.clientMessagesCoodrsX = 10;
		this.clientMessagesCoodrsY = 10;
		this.clientMessagesCoords = new Point(
				this.clientMessagesCoodrsX
				, this.clientMessagesCoodrsY);
		this.clientMessagesTxt = new TextArea();
		this.clientMessagesTxtEditable = false;
		this.clientMessagesTxt.setEditable(
				this.clientMessagesTxtEditable);
		
		this.grid = new GridPane();
		this.root = new Pane();
	}
	
	@Override
	public void start(Stage primaryStage) 
			throws Exception {
		primaryStage.setTitle(this.title);
		primaryStage.setScene(new Scene(this.appWidgets()));
		primaryStage.show();
		
		new Thread(this).start();
	}
	
	public Pane appWidgets() {
		this.grid.add(this.clientMessagesTxt
				, (int) this.clientMessagesCoords.getX()
				, (int) this.clientMessagesCoords.getY());
		this.root.getChildren().addAll(this.grid);
		return this.root;
	}
	
	public void receiveClientMessage() {
		System.out.println("Server socket started");
		try {
			this.serverSocket = new ServerSocket(5050);
			
			while(true) {
				this.socket = serverSocket.accept();
				DataInputStream dis = new DataInputStream(this.socket.getInputStream());
				this.clientMessagesTxt.appendText(dis.readUTF() + "\n");
				dis.close();
				this.socket.close();
				this.serverSocket.close();
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	 public static void main( String[] args ) {
	 	launch(args);
	 }

	public void run() {
		this.receiveClientMessage();
	}
}