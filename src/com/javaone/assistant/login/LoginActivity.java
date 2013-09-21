package com.javaone.assistant.login;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.javaone.assistant.R;
import com.javaone.assistant.model.JavaOneAppContext;

public class LoginActivity extends Activity {
	
	private static final String LOG_TAG = LoginActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		authenticate();
	}

	private void authenticate() {
		// Initiate the request to the auth service
		Button submitButton = (Button) findViewById(R.id.submit);
		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				setApplicationContext();
				try { 
					setSSLContext();
				} catch (Exception e) {
					Log.e(LOG_TAG, " Error creating SSLContext: ", e);
				}
				
				Log.d(LOG_TAG, "Executing Login background task");
				new LoginAsyncTask(LoginActivity.this).execute();
				Log.d(LOG_TAG, "Background task in excution");
			}

			private void setApplicationContext() {
				JavaOneAppContext context = JavaOneAppContext.getInstance();
				
				EditText editText = (EditText) findViewById(R.id.username);
				context.setUsername(editText.getText().toString());

				editText = (EditText) findViewById(R.id.password);
				context.setPassword(editText.getText().toString());

				context.setBaseUrl(getString(R.string.base_uri) + "/" + context.getUsername());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	private void setSSLContext() throws Exception {
		// Load self-signed cert from an InputStream
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		InputStream caInput = getAssets().open("s1as.cer");
		Certificate ca;
		try {
		    ca = cf.generateCertificate(caInput);
		    Log.d(LOG_TAG, "ca=" + ((X509Certificate) ca).getSubjectDN());
		} finally {
		    caInput.close();
		}

		// Create a KeyStore containing our trusted CAs
		String keyStoreType = KeyStore.getDefaultType();
		KeyStore keyStore = KeyStore.getInstance(keyStoreType);
		keyStore.load(null, null);
		keyStore.setCertificateEntry("ca", ca);

		// Create a TrustManager that trusts the CAs in our KeyStore
		String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
		tmf.init(keyStore);
		
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(keyStore, "changeit".toCharArray());

		// Create an SSLContext that uses our TrustManager
		SSLContext context = SSLContext.getInstance("TLS");
		SSLContext.setDefault(context);
		
		context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		
		HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
			public boolean verify(String string,SSLSession ssls) {
			return true;
			}
			});

		Log.d(LOG_TAG, "SSLContext set successfully");
	}

}
