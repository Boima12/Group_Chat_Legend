import java.io.*;
import java.net.*;
import java.util.*;

public class Handler {
  private Socket socket;
  private DataInputStream datain;
  private DataOutputStream dataout;
  private BufferedReader reader;
  private BufferedWriter writer;
  private String name;

  public Handler(Socket socket){
    try {
      this.socket = socket;

      datain = new DataInputStream(socket.getInputStream());
      reader = new BufferedReader(new InputStreamReader(datain));

      dataout = new DataOutputStream(socket.getOutputStream());
      writer = new BufferedWriter(new OutputStreamWriter(dataout));

      name = reader.readLine();

      System.out.println(name + " đã tham gia!");


    } catch (Exception e) {
      
    }
  }

  public void receiveMsg(){
    Thread receive = new Thread((new Runnable() {
      @Override
      public void run(){
        try {
          String msg;
          while(socket.isConnected()) {
            msg = reader.readLine();
            ChatServer.Broadcast("[" + name + "]: " + msg);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }));
    receive.start();
  }

  public void removeSocket(){
    ChatServer.handlerList.remove(this);
  }

  public void closeEveryThing(){
    removeSocket();
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

      System.out.println(name + " đã ngắt kết nối!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public BufferedWriter getWriter(){
    return this.writer;
  }
}
