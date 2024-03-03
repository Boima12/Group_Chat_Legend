import java.io.*;
import java.net.*;
import javax.swing.*;

public class ChatClient {
  private Socket socket;
  private DataInputStream datain;
  private DataOutputStream dataout;
  private BufferedReader reader;
  private BufferedWriter writer;
  private String name;

  public ChatClient(Socket socket, String name, JTextArea textArea){
    try {
      this.socket = socket;
      this.name = name;

      datain = new DataInputStream(socket.getInputStream());
      reader = new BufferedReader(new InputStreamReader(datain));

      dataout = new DataOutputStream(socket.getOutputStream());
      writer = new BufferedWriter(new OutputStreamWriter(dataout));

      ClientSignUp();

			System.out.println("Client connected succesfully!");
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  public void ReceiveMsg(JTextArea textArea){
    Thread receive = new Thread(new Runnable(){
      @Override
      public void run() {
        try {
          String msg;
          while(socket.isConnected()){
            msg = reader.readLine();

            textArea.append(msg);
            textArea.append("\n");
          }
        } catch (Exception e) {
          System.out.println("Client disconnected succesfully!");
          closeEveryThing();
        }
      }
    });
    receive.start();
  }

  public void SendMsg(JTextArea textArea){
    try {
      writer.write(textArea.getText());
      writer.newLine();
      writer.flush();
    } catch (Exception e) {
      closeEveryThing();
    }
  }

  public void ClientSignUp(){
    try {
      writer.write(name);
      writer.newLine();
      writer.flush();
    } catch (Exception e) {
      System.out.println("Client signUp failed!");
      closeEveryThing();
    }
  }

  public void closeEveryThing(){
    try {
      if(socket != null){
        socket.close();
      }
      if(datain != null){
        datain.close();
      }
      if(dataout != null){
        dataout.close();
      }
      if(reader != null){
        reader.close();
      }
      if(writer != null){
        writer.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
