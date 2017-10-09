import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.StringEntity;

public class NetClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hellow world");

		String strHash = "";
		
		if (args.length > 0)
			strHash = getHash(args[0]);
		else
			strHash = getHash("some values");
		
		useHttpURL(strHash);		
	}
	
	static String getHash(String strInput) {
		String strResult = "";
		//byte[] data = Files.readAllBytes(path);
		
		byte[] data = strInput.getBytes();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(data);
			strResult = Base64.getEncoder().encodeToString(md.digest());		
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		System.out.println("Hash Value: " + strResult);
		
		return strResult;
	}
	
	static void useHttpURL(String strInput) {
		try {
			String token = "biIr1ZS61vBzo2IrYcURRczQDjh-cGJJwvEf8qSz2sUFQ3YJUhU6CrwWFj9s6s14g7unTj7K3_mby11VDU21F9dIaGXCV48U1eEE3KjYqQjuoSjvE5pKW50ZhE1mrM-qZYBNvE3Zj-Gk5PXqzO58EFA9Vu5oRD6-_akVTuXfDrKlcLmYihsTzHAZUnRO6dBxh9ydjB1K8ZD_us0cw3Rmc0Zg58SD81V-IW8B0NBydzyV-_6IPLczh-_VBd_v4pp9FOKUa5K1t7WGrU6qbPCMI5y94gdDVrzhInZHMd9X6zgk_laUH9RJRvC1cWTZnRLuM3S0WK4pX_sDq0kFJGnzidYR6PEoKv1TUyhYvgZ2mEJoMvtWdZD6gChnode3smYu2oLW0Q77qjaYCC_DrDRw_OJ8s6plQiyA2w_KntoaR_jMGZUGVg516HaBcc1yLLySHX7bjlnnYeP0iHMVmbJ7OzYQal9tbYPE6KLheM9eoejws_6DQlp2bwtf6dIJQsWB";
			
			URL url = new URL("https://gt-sg-w2012-a.guardtime.com/api/Catena");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();			
			conn.setRequestProperty("Authorization", "Bearer " + token);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);

			//String input =  "{\"DataHash\":\"GHgonhozPdhQkdMPABufb57U1CjVfgSb0OvU28ib0h\",\"Metadata\":\"Test\"}";
			String input =  "{\"DataHash\":\""+ strInput +"\",\"Metadata\":\"Test\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			
			/*if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}*/

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		 }	
	}

	static void useApache() {
		try {

			String token = "biIr1ZS61vBzo2IrYcURRczQDjh-cGJJwvEf8qSz2sUFQ3YJUhU6CrwWFj9s6s14g7unTj7K3_mby11VDU21F9dIaGXCV48U1eEE3KjYqQjuoSjvE5pKW50ZhE1mrM-qZYBNvE3Zj-Gk5PXqzO58EFA9Vu5oRD6-_akVTuXfDrKlcLmYihsTzHAZUnRO6dBxh9ydjB1K8ZD_us0cw3Rmc0Zg58SD81V-IW8B0NBydzyV-_6IPLczh-_VBd_v4pp9FOKUa5K1t7WGrU6qbPCMI5y94gdDVrzhInZHMd9X6zgk_laUH9RJRvC1cWTZnRLuM3S0WK4pX_sDq0kFJGnzidYR6PEoKv1TUyhYvgZ2mEJoMvtWdZD6gChnode3smYu2oLW0Q77qjaYCC_DrDRw_OJ8s6plQiyA2w_KntoaR_jMGZUGVg516HaBcc1yLLySHX7bjlnnYeP0iHMVmbJ7OzYQal9tbYPE6KLheM9eoejws_6DQlp2bwtf6dIJQsWB";
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(
				"https://gt-sg-w2012-a.guardtime.com/api/Catena");

			StringEntity input = new StringEntity("{\"DataHash\":\"GHgonhozPdhQkdMPABufb57U1CjVfgSb0OvU28ib0hA=\",\"Metadata\":\"Test\"}");
			input.setContentType("application/json");
			postRequest.setEntity(input);
			postRequest.setHeader("Authorization", "Bearer " + token);

			HttpResponse response = httpClient.execute(postRequest);

			/*if (response.getStatusLine().getStatusCode() != 201) {
				//throw new RuntimeException("Failed : HTTP error code : "
				//	+ response.getStatusLine().getStatusCode());
				throw new RuntimeException(response.toString());
			}*/

			BufferedReader br = new BufferedReader(
	                        new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			httpClient.getConnectionManager().shutdown();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
	}
}
