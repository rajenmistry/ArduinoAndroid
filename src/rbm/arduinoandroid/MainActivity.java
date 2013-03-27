package rbm.arduinoandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	/**
	 * http://thinkandroid.wordpress.com/2010/03/27/incorporating-socket-
	 * programming-into-your-applications/
	 */

	private CheckBox red;
	private CheckBox yellow;
	private CheckBox green;
	private Button temperature;
	private TextView text;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader inFromServer;
	private TextView statusView;
	private String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		red = (CheckBox) findViewById(R.id.red);
		yellow = (CheckBox) findViewById(R.id.yellow);
		green = (CheckBox) findViewById(R.id.green);
		temperature = (Button) findViewById(R.id.temperature);
		text = (TextView) findViewById(R.id.text);
		statusView = (TextView) findViewById(R.id.statusView);

		Runnable connect = new Runnable() {

			@Override
			public void run() {
				try {
					socket = new Socket("192.168.0.5", 6002);
					out = new PrintWriter(socket.getOutputStream(), true);
					inFromServer = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));
					out.println("Android");
					message = inFromServer.readLine();
					statusView.setText(message);

				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};

		Thread task = new Thread(connect);
		task.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onCheckboxClicked(View view) throws IOException {
		boolean checked = ((CheckBox) view).isChecked();

		switch (view.getId()) {
		case R.id.red:
			if (checked) {
				red.setText("Red On");
			} else {
				red.setText("Red Off");
			}
			new CommunicateWithServer(socket, statusView).execute(1);

			break;

		case R.id.green:
			if (checked) {
				green.setText("Green On");

			} else {
				green.setText("Green Off");
			}
			new CommunicateWithServer(socket, statusView).execute(2);

			break;

		case R.id.yellow:
			if (checked) {
				yellow.setText("Yellow On");
			} else {
				yellow.setText("Yellow Off");
			}
			new CommunicateWithServer(socket, statusView).execute(3);

			break;
		}

	}

	public void temperatureButtonClicked(View view) {
		new GetTemperatureTask(socket, text).execute();

	}
}