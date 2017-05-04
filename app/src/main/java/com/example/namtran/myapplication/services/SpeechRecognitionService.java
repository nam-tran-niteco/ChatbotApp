package com.example.namtran.myapplication.services;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechRecognitionService extends Service implements RecognitionListener {

	private SpeechRecognizer speechRecognizer;
	private Intent recognizerIntent;

	ProgressDialog progressDialog;

	private boolean isCheck = true;

	private boolean isCreateService = true;

	private String saved_command;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		if (isCheck == true && isCreateService == false) {
//			speechRecognizer.startListening(recognizerIntent);
//			isCheck = false;
//
//		}
//		if (isCreateService == true)
//			isCreateService = false;
        Toast.makeText(getApplicationContext(), "Start Service", Toast.LENGTH_SHORT).show();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
		speechRecognizer.setRecognitionListener(this);
		recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
				this.getPackageName());
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        Toast.makeText(getApplicationContext(), "Listening service created", Toast.LENGTH_SHORT).show();
//		isCreateService = true;
//		progressDialog = new ProgressDialog(getApplicationContext());
//		progressDialog.getWindow().setType(
//				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//		progressDialog.setMessage("Đang xử lý ...");

	}

	@Override
	public IBinder onBind(Intent intent) {
		Toast.makeText(getApplicationContext(), "Bind", Toast.LENGTH_SHORT)
				.show();
		return null;
	}

	@Override
	public void onDestroy() {

		Toast.makeText(getApplicationContext(), "Destroy", Toast.LENGTH_SHORT)
				.show();
		super.onDestroy();
	}

	@Override
	public void onReadyForSpeech(Bundle params) {

		Toast.makeText(getApplicationContext(), "Ready for Listening",
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onBeginningOfSpeech() {
		//
		Toast.makeText(getApplicationContext(), "Start Recording",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRmsChanged(float rmsdB) {
	}

	@Override
	public void onBufferReceived(byte[] buffer) {
	}

	@Override
	public void onEndOfSpeech() {
		// Intent intent = new Intent(getApplicationContext(),
		// CustomProgressDialog.class);
		// startActivity(intent);
		Toast.makeText(getApplicationContext(), "end", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onError(int error) {
		String errorText = getErrorText(error);
		// txtError.setText(errorText);
		isCheck = true;
		Toast.makeText(getApplicationContext(), errorText, Toast.LENGTH_SHORT)
				.show();

	}

	@Override
	public void onResults(Bundle results) {
		ArrayList<String> matches = results
				.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		String command = matches.get(0);
		isCheck = true;
		Toast.makeText(getApplicationContext(), command, Toast.LENGTH_SHORT)
				.show();
		// analysis.implementCommand(command);
		// progressDialog.dismiss();
		// if (!(command.equals("không phải") || command.equals("không"))) {
		// if (!command.equals("")) {
		// if(command.contains(""))
		saved_command = command;
		new Progress().execute(saved_command);
		// }
		// }

	}

	@Override
	public void onPartialResults(Bundle partialResults) {
	}

	@Override
	public void onEvent(int eventType, Bundle params) {
	}

	private String getErrorText(int errorCode) {
		String message;
		switch (errorCode) {
		case SpeechRecognizer.ERROR_AUDIO:
			message = "Audio recording error";
			break;
		case SpeechRecognizer.ERROR_CLIENT:
			message = "Client side error";
			break;
		case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
			message = "Insufficient permissions";
			break;
		case SpeechRecognizer.ERROR_NETWORK:
			message = "Network error";
			break;
		case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
			message = "Network timeout";
			break;
		case SpeechRecognizer.ERROR_NO_MATCH:
			message = "No match";
			break;
		case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
			message = "RecognitionService busy";
			break;
		case SpeechRecognizer.ERROR_SERVER:
			message = "error from server";
			break;
		case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
			message = "No speech input";
			break;
		default:
			message = "Didn't understand, please try again.";
			break;
		}
		return message;
	}

	private class Progress extends AsyncTask<String, Void, Void> {

//		CommandObject commandObject;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (!progressDialog.isShowing()) {
				progressDialog.show();
			}
		}

		@Override
		protected Void doInBackground(String... params) {
//			if (analysis.implementCommand(params[0]) != null) {
//				commandObject = analysis.implementCommand(params[0]);
//			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
//			if (commandObject != null) {
//				implementFunction.implement(commandObject);
//			}
		}

	}

}
