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
            // Read the request (optional)
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            line = reader.readLine();
            System.out.println("Line: " + line + " read from client");
            if(line.startsWith("GET")){
                int firstSpace = line.indexOf(" ");
                int secondSpace = line.indexOf(" ", firstSpace + 1);
                String request = line.substring(firstSpace + 2, secondSpace);
                if(request.equals("")){
                    System.out.print("No request; Homepage ");
                    File index = new File("Portfolio.html");
                    byte[] idxBits = readFileInBytes(index);
                    String httpResponse = "HTTP/1.1 200 OK\n"
                        + "Content-Type: text/html\n"
                        + "\n";
                    out.write(httpResponse.getBytes());
                    out.write(idxBits);
                }else if(request.equals("styles.css")){
                    System.out.println("Style sheet request");
                    File style = new File("styles.css");
                    byte[] styleBits = readFileInBytes(style);
                    String cssResponse = "HTTP/1.1 200 OK\n"
                        + "Content-Type: text/css\n"
                        + "\n";
                    out.write(cssResponse.getBytes());
                    out.write(styleBits);
                }else if(request.equals("profile.jpeg")){
                    System.out.println("Profile picture request");
                    File profile = new File("profile.jpeg");
                    byte[] profileBits = readFileInBytes(profile);
                    String profileResponse = "HTTP/1.1 200 OK\n"
                        + "Content-Type: text/jpeg\n"
                        + "\n";
                    out.write(profileResponse.getBytes());
                    out.write(profileBits);
                }
            }
            // Send HTTP response

        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }