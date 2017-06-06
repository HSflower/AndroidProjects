package com.example.union_tab;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebView_ForTest extends Activity{

	WebView _webView = null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_test);
		
		//여기에 html을 넣어주세욤!
		String htmlString = "<b><font color=#ff0000> Html View using TextView" +
	            "</font></b><br><br><a href='http://m.naver.com'>naver.com</a>" +
	            "<br><br><a href='http://mainia.tistory.com'>mainia.tistory.com</a>";
		_webView = (WebView)findViewById(R.id.WebView_test);
		
		
		_webView.getSettings().setJavaScriptEnabled(true);
		_webView.loadData(htmlString, "text/html", "utf-8");
		_webView.getSettings().setUseWideViewPort(true);
		_webView.getSettings().setBuiltInZoomControls(true);
		_webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		_webView.setHorizontalScrollbarOverlay(false);

		//String imageName = getIntent().getStringExtra(getString(R.string.key_imageDetail)) + ".png";
	
		//init image Detail view.
		//initImageDetail(imageName);
	}
	/* 
	private void initImageDetail(String imageName) {
		
		String imageLocation = "file://" + Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/MamasPapas/" + getResources().getString(R.string.subpath) + imageName;
		
		final StringBuilder s = new StringBuilder();    
		s.append("<html>"); 
		s.append("<BODY style='margin:0; padding:20dp; text-align:center;'>"); 
		s.append("<meta name=\"viewport\" content=\"target-densitydpi=device-dpi, width=device-width\">");
		s.append("<body>");
		s.append("<img src=\"" + imageLocation+ "\"/>");
		s.append("</body>");                            
		s.append("</html>");

		_webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		_webView.loadDataWithBaseURL(null, s.toString(), "text/html", "UTF-8", null);
		_webView.getSettings().setUseWideViewPort(true);
		_webView.getSettings().setBuiltInZoomControls(true	);
		_webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		_webView.setHorizontalScrollbarOverlay(false);
		
	}
	*/
}


