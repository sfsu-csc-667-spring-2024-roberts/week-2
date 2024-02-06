import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class FakeServer {
  private ServerSocket serverSocket;
  private boolean isRunning;

  public FakeServer() throws IOException {
    this.serverSocket = new ServerSocket(9876);
    this.isRunning = true;
  }

  public void start() throws IOException {

    while (this.isRunning) {
      Socket socket = serverSocket.accept();

      String message = this.readFromInput(socket.getInputStream());
      socket.getOutputStream().write(
          String.format("> RECEIVED MESSAGE: %s", message).getBytes());
      socket.getOutputStream().flush();
      socket.getOutputStream().close();
    }
  }

  private String readFromInput(InputStream input) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    return reader.readLine();
  }

  public void stop() {
    this.isRunning = false;
  }

  public static void main(String[] args) throws IOException {
    FakeServer server = new FakeServer();
    server.start();
  }
}