import java.io.*;
import java.net.*;
import java.util.*;
public class ChatServer {
//  private static final int PORT = 5000;
  private ServerSocket serverSocket;
  public static ArrayList<Handler> handlerList = new ArrayList<>();

  public ChatServer(ServerSocket serverSocket){
    this.serverSocket = serverSocket;
  }

  public void startServer(){
    try {
      while(!serverSocket.isClosed()){
        Socket client = serverSocket.accept();

        Handler handler = new Handler(client);
        handlerList.add(handler);
        handler.receiveMsg();
      }
    } catch (Exception e) {
      e.printStackTrace();
      try {
        serverSocket.close();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }

  public static void Broadcast(String msg){
    for(Handler handler : handlerList){
      try {
        handler.getWriter().write(msg);
        handler.getWriter().newLine();
        handler.getWriter().flush();
      } catch (Exception e) {
        handler.closeEveryThing();
        break;
      }
    }
  }

  public static void main(String[] args){
    try {
      ServerSocket serverSocket = new ServerSocket(1610);
      ChatServer chatServer = new ChatServer(serverSocket);
      System.out.println("Server started! Waiting for connection..." );
      chatServer.startServer();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
