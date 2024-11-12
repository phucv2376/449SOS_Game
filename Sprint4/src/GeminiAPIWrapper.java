import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.nio.charset.StandardCharsets;
import java.net.URI;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;


public class GeminiAPIWrapper {
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key="; // Base API URL
    private static final String API_KEY = "AIzaSyCTpKvVKeEwUy_ozRYSwe_6r6eMITNY_7Q"; // Your actual API key
    private HttpURLConnection connection;

    // Constructor that initializes the API connection
    public GeminiAPIWrapper() {
        try {
            // Create a URL object and open a connection
            URI uri = new URI(API_URL + API_KEY);
            this.connection = (HttpURLConnection) uri.toURL().openConnection();
            this.connection.setRequestMethod("POST");
            this.connection.setRequestProperty("Content-Type", "application/json; utf-8");
            this.connection.setRequestProperty("Accept", "application/json");
            // Remove the Authorization header since API key is already in the URL
            // Uncomment the line below if you decide to use Bearer token authentication
            // this.connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            this.connection.setDoOutput(true); // Set to true to send request body
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to get response based on prompt
    private String getResponse(String prompt) {
        String jsonInputString = String.format("{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}]}", prompt);
        StringBuilder response = new StringBuilder();

        try {
            // Send the request payload to AI
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get the response code from call
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }

                // Parse the JSON response and extract the "text" value
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray candidates = jsonResponse.getJSONArray("candidates");
                if (!candidates.isEmpty()) {
                    JSONObject content = candidates.getJSONObject(0).getJSONObject("content");
                    JSONArray parts = content.getJSONArray("parts");
                    if (!parts.isEmpty()) {
                        return parts.getJSONObject(0).getString("text");
                    }
                }
            } else {
                System.err.println(STR."Request failed with HTTP code: \{responseCode}");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.toString();
    }
    private void disconnect() {
        if (connection != null) {
            connection.disconnect();
        }
    }
    public void getComputerMove(char[][] board) {
        String prompt = String.format("Act as a player in an SOS game. The board size is 3. index starts at 0 and ends at 2.The goal is to complete 1 SOS sequence on the board. The board is represented in a 2d array with char[row][col]. The current board is %s. Make the best move in a format of row, col, letter", Arrays.deepToString(board));
        System.out.println(prompt);
        String response = getResponse(prompt);
        System.out.println(response);
        char row = response.charAt(0);
        char letter = response.charAt(5);
        int col = response.charAt(3);
        System.out.println(row);
    }
    // Main method for testing (this would be in another class or test)
    public static void main(String[] args) {
        GeminiAPIWrapper LLMConn = new GeminiAPIWrapper();
        char[][] board = new char[][]{
                {'S', 'O', ' ' },
                {' ' , ' ', ' ' },
                {' ', ' ', ' '}
                };
        LLMConn.getComputerMove(board);
//        System.out.println(board[0][1]);
    }
}