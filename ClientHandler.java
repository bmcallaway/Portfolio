import java.io.*;
import java.net.*;

    public class ClientHandler implements Runnable{
        Socket socket;
        InputStream in = null;
        OutputStream out = null;
        public ClientHandler(Socket socket){
            this.socket = socket;
            try{
                in = socket.getInputStream();
                out = socket.getOutputStream();
            } catch(Exception e){
    
            }
    
        }
        public static byte[] readFileInBytes(File f)
            throws IOException {

                byte[] result = new byte[(int) f.length()];

                try {
                    new FileInputStream(f).read(result);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return result;
            }
        @Override
        public void run(){
        try (
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
        ) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                System.out.println(line);
            }

            // Send HTTP response
            File file = new File("Portfolio.html");
            if (file.exists()) {
                byte[] content = readFileInBytes(file);
                String httpResponse = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: " + content.length + "\r\n" +
                        "\r\n";
                out.write(httpResponse.getBytes());
                out.write(content);
            } else {
                String errorResponse = "HTTP/1.1 404 Not Found\r\n\r\n";
                out.write(errorResponse.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }