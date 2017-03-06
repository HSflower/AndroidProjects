package dao;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExhiAdapter extends BaseAdapter {

	Context context;
	int layout;
	MuseumData data;

	public ExhiAdapter(Context context, int layout, MuseumData data){
		this.context = context;
		this.layout = layout;
		this.data = data;
	}
	public void setData(MuseumData data){
		this.data = data;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.getSongs().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View cView, ViewGroup parent) {
		if(cView == null){
			cView = View.inflate(context, layout, null);
		}
		Workbook workbook = data.getWorkbooks().get(position);
		
		TextView tv = (TextView)cView.findViewById(R.id.rankId);
		tv.setText(workbook.getCurrentRank() + "");
		
		tv = (TextView)cView.findViewById(R.id.pastRank);
		tv.setText(workbook.getPastRank() + "");
		
		tv = (TextView)cView.findViewById(R.id.title);
		tv.setText(workbook.getSongName() + "");
		
		tv = (TextView)cView.findViewById(R.id.time);
		int time = workbook.getPlayTime();
		String tTime =  time / 60 + " : " + time % 60;
		tv.setText(tTime);
		tv = (TextView)cView.findViewById(R.id.album);
		tv.setText(workbook.getAlbumName() );
		String artist = "";
		
		for(Artist aritsta : workbook.artists){
			artist += aritsta.getArtistName() + " ";
		}
		
		tv = (TextView)cView.findViewById(R.id.songName);
		tv.setText( artist );
		return cView;
	}

}
