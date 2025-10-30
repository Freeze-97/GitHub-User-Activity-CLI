import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class GitHubUserActivity {

	public static void main(String[] args) throws IOException, URISyntaxException {
		
		// Must contain one argument for username
		if(args.length != 1) {
			System.out.println("Usage: java GitHubUserActivity <username>");
			return;
		}
		
		// Get name
		String name = args[0];
		fetchGitHubActivity(name);
	}
	
	public static void fetchGitHubActivity(String name) {
		String fullURL = "https://api.github.com/users/" + name + "/events";
		
		try {
			URI uri = new URI(fullURL);
			URL url = uri.toURL();
			
			// Open connection
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Java-GitHubClient");
			
			// Check response code
			int responseCode = connection.getResponseCode();
			
			if(responseCode == 200) {
				// Read JSON respone
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line;
				
				// Read line by line
				while((line = reader.readLine()) != null) {
					response.append(line);
				}
				
				// print data
				printCleanActivity(response.toString());
				
			} else if(responseCode == 404) {
				System.out.println("User not found.");
			} else {
				System.out.println("Error: " + responseCode);
			}
			
		} catch (URISyntaxException e) {
			System.out.println("An URISyntax error occurred: " + e.getMessage());
			e.printStackTrace();
		} catch (MalformedURLException e) {
			System.out.println("An MalformedURL error occurred: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("An IO error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void printCleanActivity(String json) {
	    // Split by "}
	    String[] events = json.split("\\},\\s*\\{");

	    for (String event : events) {
	        // Extract "type"
	        int typeStart = event.indexOf("\"type\":");
	        
	        if (typeStart == -1) continue;
	        
	        typeStart = event.indexOf("\"", typeStart + 7) + 1;
	        int typeEnd = event.indexOf("\"", typeStart);
	        String type = event.substring(typeStart, typeEnd);

	        // Extract "repo" name
	        int repoStart = event.indexOf("\"name\":");
	        if (repoStart == -1) continue;
	        
	        repoStart = event.indexOf("\"", repoStart + 7) + 1;
	        int repoEnd = event.indexOf("\"", repoStart);
	        String repo = event.substring(repoStart, repoEnd);

	        // Make output cleaner based on event type
	        switch (type) {
	            case "PushEvent":
	                System.out.println("- Pushed to " + repo);
	                break;
	            case "WatchEvent":
	                System.out.println("- Starred " + repo);
	                break;
	            case "ForkEvent":
	                System.out.println("- Forked " + repo);
	                break;
	            case "CreateEvent":
	                System.out.println("- Created something in " + repo);
	                break;
	            case "IssuesEvent":
	                System.out.println("- Worked on an issue in " + repo);
	                break;
	            default:
	                System.out.println("- " + type + " in " + repo);
	        }
	    }
	}
}
