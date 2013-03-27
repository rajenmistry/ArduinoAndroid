package rbm.arduinoandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.widget.TextView;

public class CommunicateWithServer extends AsyncTask<Integer, Void, String> {

	private PrintWriter out;
	private BufferedReader inFromServer;
	private TextView statusView;

	/**
	 * Class to handle communication with the server
	 * 
	 * @param socket
	 *            Socket from MainActivity
	 * @param statusView
	 *            TextView from MainActivity
	 * @throws IOException
	 */
	public CommunicateWithServer(Socket socket, TextView statusView)
			throws IOException {
		this.statusView = statusView;
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.inFromServer = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

	}

	/**
	 * @param Integer
	 *            sent to Arduino
	 * @return String used to pass on to onPostExecute method
	 */
	@Override
	protected String doInBackground(Integer... params) {
		String message = "";
		int i = params[0];
		out.println(i);
		out.flush();
		try {
			message = inFromServer.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}

	/**
	 * updates the statusView with the response from server
	 */
	@Override
	protected void onPostExecute(String response) {

		statusView.setText("Connected");
	}

}
