package rbm.arduinoandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.os.AsyncTask;
import android.widget.TextView;

/**
 * Async Task that reads and writes to server
 * 
 * @author rajen
 * 
 */
public class GetTemperatureTask extends AsyncTask<Void, Void, String> {

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	private TextView textView;

	/**
	 * Constructor
	 * 
	 * @param socket
	 *            Socket from MainActivity that is used for reading and writing
	 * @param textView
	 *            TextView from MainActivity that is updated
	 */
	public GetTemperatureTask(Socket socket, TextView textView) {
		this.socket = socket;
		this.textView = textView;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Method to write to socket in the background
	 * 
	 * @return String response from the server that updates the status box
	 */
	@Override
	protected String doInBackground(Void... params) {
		String response = "";

		out.println('5');
		out.flush();
		try {
			response = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		return response;
	}

	/**
	 * @param String
	 *            from doInBackground that is used to update status box
	 */
	@Override
	protected void onPostExecute(String response) {
		textView.setText(response);

	}
}
