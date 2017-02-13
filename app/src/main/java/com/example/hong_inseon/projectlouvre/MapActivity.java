package com.example.hong_inseon.projectlouvre;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {

    static int inf = 99999999, position, make; // 무한대 값

    static int[][] weightMatrix = {
            //  0    1    2    3    4    5    6    7    8    9    10   11   12
        /* 0 */ {   0, 260, inf, 230, inf, inf, inf, inf, inf, inf, inf, inf, inf},
        /* 1 */ { 260,   0, 220, inf, inf, 570, inf, inf, inf, inf, inf, inf, inf},
        /* 2 */ { inf, 220,   0, inf, inf, inf, 570, inf, inf, inf, inf, inf, inf},
        /* 3 */ { 230, inf, inf,   0, 340, inf, inf, inf, inf, inf, inf, inf, inf},
        /* 4 */ { inf, inf, inf, 340,   0, 260, inf, 300, inf, inf, inf, inf, inf},
        /* 5 */ { inf, 570, inf, inf, 260,   0, 220, inf, inf, inf, inf, inf, inf},
        /* 6 */ { inf, inf, 570, inf, inf, 220,   0, inf, inf, 300, inf, inf, inf},
        /* 7 */ { inf, inf, inf, inf, 300, inf, inf,   0, 260, inf, 280, inf, inf},
        /* 8 */ { inf, inf, inf, inf, inf, inf, inf, 260,   0, 220, inf, 280, inf},
        /* 9 */ { inf, inf, inf, inf, inf, inf, 300, inf, 220,   0, inf, inf, 280},
        /*10 */ { inf, inf, inf, inf, inf, inf, inf, 280, inf, inf,   0, 260, inf},
        /*11 */ { inf, inf, inf, inf, inf, inf, inf, inf, 280, inf, 260,   0, 220},
        /*12 */ { inf, inf, inf, inf, inf, inf, inf, inf, inf, 280, inf, 220,   0},
    };

    float xf, yf, r;

    private Point now;
    private Point[] pa;
    int[] path;
    private Paint mPaint;
    private FrameLayout ll;

    private final static float diagonal = 1721.56818f;
    private final static int WM = 726, HM = 1561;
    private final static float WMd = 726.0f, HMd = 1561.0f;

    private double min1, min2, max1, max2;
    private int [][] temp = new int[3][4];
    private int [] sum = new int[3];

    private RelativeLayout map;
    private RelativeLayout path1;
    private RelativeLayout path2;
    private View lineP;

    private View vv;

    private Intent intent3, intent2, intent1;

    private static int count = 0;

    private static double dis[] = {0,0,0}, dis2[] = {0,0};
    private double [][]dis22 = new double[3][15];
    private int i=0, vw;
    private int Xc=0, Yc=0,Xp=0,Yp=0;
    private float z[] = {0.11f,0.11f,150f};//3번째는 높이
    private float  XX=0, YY=0;
    private ImageView view;
    private int rwidth, rheight;

    private double place[][] = {{300.0d,450.0d},{550.0d,880.0d},{700.0d,1380.0d}};//작품들의 위치를 표시 가로 50에 세로 100을 빼야 한다
    private double placeR[][] = {{place[0][0]/WMd, place[0][1]/HMd},{place[1][0]/WMd, place[1][1]/HMd},{place[2][0]/WMd, place[2][1]/HMd}};

    private BluetoothManager bm;    //기본적으로 존재
    private BluetoothAdapter mba;   //블루스트 탐색과 연결을 담당

    private FrameLayout.LayoutParams kkk;       //이미지 뷰를 받음
    private RelativeLayout rl;                   //레이아웃 받음//레이아웃으로 부터의 거리 변환을 위한 매개변수

    public class BleList extends BaseAdapter {//리스트뷰 어뎁터 선언
        private ArrayList<BluetoothDevice> devices;
        private ArrayList<Integer> RSSIs;
        private LayoutInflater inflater;

        public BleList(){
            super();
            devices = new ArrayList<BluetoothDevice>();
            RSSIs = new ArrayList<Integer>();
            inflater = ((Activity) MapActivity.this).getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device, int rssi){
            if(!devices.contains(device)){
                devices.add(device);
                RSSIs.add(rssi);
            }
            else{
                RSSIs.set(devices.indexOf(device),rssi);
            }
        }

        public void clear(){
            devices.clear();
            RSSIs.clear();
        }

        @Override
        public int getCount() {
            return devices.size();
        }

        @Override
        public Object getItem(int position) {
            return devices.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(android.R.layout.two_line_list_item,null);
                viewHolder.deviceName = (TextView) convertView.findViewById(android.R.id.text1);
                viewHolder.deviceRssi = (TextView) convertView.findViewById(android.R.id.text2);
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            String deviceName = devices.get(position).getName();
            int rssi = RSSIs.get(position);

            viewHolder.deviceName.setText(deviceName != null && deviceName.length() > 0 ?deviceName:"알 수 없는 장치");
            viewHolder.deviceRssi.setText(String.valueOf(rssi));
            Log.d("scan","rssi : "+rssi);
            try {
                if (position < 3)
                    dis[position] = rtd(Integer.parseInt(String.valueOf(rssi)));
            }
            catch(Exception e) { }

            return convertView;
        }
    }

    static class ViewHolder {
        TextView deviceName;
        TextView deviceRssi;
    }

    private ListView lv;
    private Button b;
    private BleList bleList = null;
    //boolean scanning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        position = 0;
        make = -1;

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(7);

        ll = (FrameLayout)findViewById(R.id.main);

        vv = new MyView(this);

        r=getStatusBarHeight()/2;

        intent1 = new Intent(this, popup1.class);
        intent2 = new Intent(this, popup2.class);
        intent3 = new Intent(this, popup3.class);

        map = (RelativeLayout)findViewById(R.id.map);
        path1 = (RelativeLayout)findViewById(R.id.path1);
        path2 = (RelativeLayout)findViewById(R.id.path2);

        /*lineP = (View)findViewById(R.id.lineP);
        <View
        class="com.ourincheon.blestart"
        android:id="@+id/lineP"
        android:visibility="visible"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
        */

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.BLUETOOTH},1);

        bm = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        mba = bm.getAdapter();

        if(mba == null || !mba.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent); //REQUEST_ENABLE_BT
            Toast.makeText(this, "블루투스를 켜주세요", Toast.LENGTH_SHORT).show();
            finish();
        }//블루투스가 켜져 있지 않을시 키도록 함

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "No LE Support.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }//BLE를 지원 않는 경우 종료

        mba.startDiscovery();

        bleList = new BleList();
        lv = (ListView) findViewById(R.id.locationE);
        lv.setAdapter(bleList);

        mba.startLeScan(leScanCallback);

        rl =  (RelativeLayout)findViewById(R.id.map);                   //레이아웃 받음//레이아웃으로 부터의 거리 변환을 위한 매개변수
        DisplayMetrics disp = getApplicationContext().getResources().getDisplayMetrics();        //각 기기의 가로, 세로 비율을 알기 위한 매개변수

        rwidth = disp.widthPixels;                   //가로의의 좌우 여백을 조금 없앤다
        rheight = disp.heightPixels;                 //세로의 상하 여백을 조금 없앤다.
        XX = WMd/HMd*(float)rheight;                  //가로세로비율을 가로에 적용
        YY = HMd/WMd*(float)rwidth;                   //가로세로비율을 세로에 적용

        view = (ImageView) findViewById(R.id.location1);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        vw = view.getMeasuredWidth();

        if(WMd/HMd < ((float)rwidth/rheight)) {      //만약 가로에 여백이 남으면 - 총 화면 크기 비교
            Xc = (int) ((rwidth - XX) / 2);                   //Xc를 정함
            xf = XX-vw;
            yf = (float)rheight-(float)getStatusBarHeight()-vw;
        } else if(WMd/HMd > ((float)rwidth/rheight)) {  //만약 세로에 여백이 남으면
            Yc = (int) (((float)rheight - YY - (float)getStatusBarHeight()) / 2);                //Yc를 정함
            xf = (float)rwidth-vw;
            yf = YY-vw;
        }
        //같은 비율일시에는 그대로 진행해도 된다. 여기까지가 핸드폰 크기에따른 설정

        now = new Point((float)Xc + xf*z[0]+vw/2, (float)Yc + yf*z[1]+vw/2);

        pa = new Point[13];
        pa[0] = new Point((float)Xc+220.0/726.0*xf+vw/2, (float)Yc +230.0/1561.0*yf+vw/2);
        pa[1] = new Point((float)Xc+480.0/726.0*xf+vw/2, (float)Yc +230.0/1561.0*yf+vw/2);
        pa[2] = new Point((float)Xc+700.0/726.0*xf+vw/2, (float)Yc +230.0/1561.0*yf+vw/2);
        pa[3] = new Point((float)Xc+220.0/726.0*xf+vw/2, (float)Yc +460.0/1561.0*yf+vw/2);
        pa[4] = new Point((float)Xc+220.0/726.0*xf+vw/2, (float)Yc +800.0/1561.0*yf+vw/2);
        pa[5] = new Point((float)Xc+480.0/726.0*xf+vw/2, (float)Yc +800.0/1561.0*yf+vw/2);
        pa[6] = new Point((float)Xc+700.0/726.0*xf+vw/2, (float)Yc +800.0/1561.0*yf+vw/2);
        pa[7] = new Point((float)Xc+220.0/726.0*xf+vw/2, (float)Yc +1100.0/1561.0*yf+vw/2);
        pa[8] = new Point((float)Xc+480.0/726.0*xf+vw/2, (float)Yc +1100.0/1561.0*yf+vw/2);
        pa[9] = new Point((float)Xc+700.0/726.0*xf+vw/2, (float)Yc +1100.0/1561.0*yf+vw/2);
        pa[10] = new Point((float)Xc+220.0/726.0*xf+vw/2, (float)Yc +1380.0/1561.0*yf+vw/2);
        pa[11] = new Point((float)Xc+480.0/726.0*xf+vw/2, (float)Yc +1380.0/1561.0*yf+vw/2);
        pa[12] = new Point((float)Xc+700.0/726.0*xf+vw/2, (float)Yc +1380.0/1561.0*yf+vw/2);

        ll.addView(vv);

        kkk = (FrameLayout.LayoutParams) view.getLayoutParams();
        Xp = (int) (xf * placeR[0][0]);
        Yp = (int) (yf * placeR[0][1]);
        kkk.setMargins(Xp + Xc, Yp + Yc, 0, 0);

        view = (ImageView) findViewById(R.id.location2);
        kkk = (FrameLayout.LayoutParams) view.getLayoutParams();
        Xp = (int) (xf * placeR[1][0]);
        Yp = (int) (yf * placeR[1][1]);
        kkk.setMargins(Xp + Xc, Yp + Yc, 0, 0);
        /*if (Xc != 0) {
            Xp = (int) (xf * placeR[1][0]);
            Yp = (int) (yf * placeR[1][1]);
        } else if (Yc != 0) {
            Yp = (int) ((YY-50-getStatusBarHeight()) * placeR[1][1]);
            Xp = (int) ((rwidth-50) * placeR[1][0]);
        }*/


        view = (ImageView) findViewById(R.id.location3);
        kkk = (FrameLayout.LayoutParams) view.getLayoutParams();
        Xp = (int) (xf * placeR[2][0]);
        Yp = (int) (yf * placeR[2][1]);
        kkk.setMargins(Xp + Xc, Yp + Yc, 0, 0);

        //thread사용으로 계속 반복적으로 표시를 해야하는 부분 - 밑으로

        view = (ImageView)findViewById(R.id.location);
        kkk = (FrameLayout.LayoutParams)view.getLayoutParams();
        Xp = (int) (xf * z[0]);
        Yp = (int) (yf * z[1]);                               //위의 조건문에서 바뀐 Xc나 Yc에 대해 Xp와 Yp를 정한다.
        kkk.setMargins(Xp + Xc, Yp + Yc, 0, 0);               //마진 설정을 한다. 즉 현재 자신의 위치 표시

        z = getCoordinate(z,dis);
        if (areaPopupPictureMusic(z, place) == 0) {
            startActivity(intent1);
        } else if (areaPopupPictureMusic(z, place) == 1) {
            startActivity(intent2);
        } else if (areaPopupPictureMusic(z, place) == 2) {
            startActivity(intent3);
        }

        /*else {
            Toast.makeText(this, "Not closed in work", Toast.LENGTH_SHORT).show();
        }

        /*
        //작품당 비콘 전용
        if (dis[0] < 3) {
            Intent intent3 = new Intent(this, popup1.class);
            startActivity(intent3);
        } else if (dis[1] < 3) {
            Intent intent3 = new Intent(this, popup2.class);
            startActivity(intent3);
        } else if (dis[2] < 3) {
            Intent intent3 = new Intent(this, popup3.class);
            startActivity(intent3);
        } else {
            Toast.makeText(this, "Not closed in work", Toast.LENGTH_SHORT).show();
        }*/
    }

    class MyView extends View {
        public MyView(Context c, AttributeSet a) {
            super(c, a);
        }

        public MyView(Context c) {
            super(c);
        }

        @Override
        protected void onDraw(Canvas c) {
            try {
                c.drawLine((float) now.getPx(), (float) now.getPy(), (float) pa[path[0]].getPx(), (float) pa[path[0]].getPy(), mPaint);
                for (int i = 0; i < path.length - 1; i++) {
                    c.drawLine((float) pa[path[i]].getPx(), (float) pa[path[i]].getPy(), (float) pa[path[i + 1]].getPx(), (float) pa[path[i + 1]].getPy(), mPaint);
                }
                //c.drawCircle((float) pa[path[path.length-1]].getPx(), (float) pa[path[path.length-1]].getPy(), r, mPaint);
            }
            catch(Exception e)
            {

            }
           //화살표 그리기 (나중에)
        }
    }

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Log.d("scan",device.getName() + " RSSI :" + rssi + " Record " + scanRecord);
            if(device.getAddress().equals("F0:9D:50:D7:49:6C")) {
                bleList.addDevice(device, rssi);
                bleList.notifyDataSetChanged();
            }
            if(device.getAddress().equals("E5:F1:43:74:F5:76") && bleList.getCount() == 1) {
                bleList.addDevice(device, rssi);
                bleList.notifyDataSetChanged();
            }
            if(device.getAddress().equals("DA:DF:3A:F3:52:38") && bleList.getCount() == 2) {
                bleList.addDevice(device, rssi);
                bleList.notifyDataSetChanged();
            }

            try
            {
                if(count<11)
                    count++;

                dis[0] = rtd(bleList.RSSIs.get(0));
                dis[1] = rtd(bleList.RSSIs.get(1));
                dis[2] = rtd(bleList.RSSIs.get(2));

                for(int j=0;j<3;j++)
                    for(int i=dis22[j].length-2;i>-1;i--)
                        dis22[j][i+1] = dis22[j][i];
                dis22[0][0] = rtd(bleList.RSSIs.get(0));
                dis22[1][0] = rtd(bleList.RSSIs.get(1));
                dis22[2][0] = rtd(bleList.RSSIs.get(2));

                if(count > 6) {
                    for (int i = 0; i < dis22.length; i++) {
                        min1 = dis22[i][0];
                        for (int j = 1; j < dis22[i].length; j++) {
                            if (min1 > dis22[i][j]) {
                                min1 = dis22[i][j];
                                temp[i][0] = j;
                            }
                        }
                        max1 = dis22[i][0];
                        for (int j = 1; j < dis22[i].length; j++) {
                            if (max1 < dis22[i][j]) {
                                max1 = dis22[i][j];
                                temp[i][1] = j;
                            }
                        }
                        min2 = dis22[i][0];
                        for (int j = 1; j < dis22[i].length; j++) {
                            if (j == temp[i][0])
                                continue;
                            if (min1 > dis22[i][j]) {
                                temp[i][2] = j;
                            }
                        }
                        max2 = dis22[i][2];
                        for (int j = 1; j < dis22[i].length; j++) {
                            if (j == temp[i][1])
                                continue;
                            if (max1 < dis22[i][j]) {
                                temp[i][3] = j;
                            }
                        }
                    }

                    for (int i = 0; i < dis22.length; i++)
                        for (int j = 0; j < dis22[i].length; i++) {
                            if (j == temp[i][0] || j == temp[i][1] || j == temp[i][2] || j == temp[i][3])
                                continue;
                            sum[i] += dis22[i][j];
                        }

                    for (int i = 0; i < 3; i++)
                        dis[i] = sum[i] / count;
                }

                Xp = (int) (xf * z[0]);
                Yp = (int) (yf * z[1]);                                  //위의 조건문에서 바뀐 Xc나 Yc에 대해 Xp와 Yp를 정한다.
                kkk.setMargins(Xp + Xc, Yp + Yc, 0, 0);               //마진 설정을 한다. 즉 현재 자신의 위치 표시

                now.setD((float)Xc + xf*z[0]+vw/2, (float)Yc + yf*z[1]+vw/2);

                z = getCoordinate(z,dis);
                if (areaPopupPictureMusic(z, place) == 0) {
                    startActivity(intent1);
                } else if (areaPopupPictureMusic(z, place) == 1) {
                    startActivity(intent2);
                } else if (areaPopupPictureMusic(z, place) == 2) {
                    startActivity(intent3);
                }
            }
            catch (Exception e)
            {

            }
        }
    };

    public float[] getCoordinate(float zz[],double a[]) {      //a배열은 크기가 3이상, 3비콘으로부터의 거리를 받는다.
        float [] zzz = new float[3];
        try{
            if(a[0] + a[1] < WM || a[1] + a[2] < diagonal || a[0] + a[2] < HM)
            {
                return zz;
            }
            else {
                zzz[0] = (float)(((WM * WM + a[0] * a[0] - a[1] * a[1]) / 2) / WMd);         //x좌표를 구한다.
                zzz[1] = (float)(((HM * HM + a[0] * a[0] - a[2] * a[2]) / 2) / HMd);      //y좌표를 구한다.
                zzz[2] = (float)a[0] * (float)a[0] - zzz[0] * zzz[0] - zzz[1] * zzz[1];               //높이 h^2를 구한다.
                if (zzz[2] >= 0)
                    Math.sqrt(zzz[2]);                                //만약 h구한것이 양수이면 제곱근을 구함
                else
                    zzz[2] = -1111;//잘못된 값임을 의미
                zzz[0]/=HMd;
                zzz[1]/=WMd;

                if(a[0]*a[0] - WM*WM > a[1]*a[1])
                    z[0] = 1;
                else if(a[1]*a[1] - WM*WM > a[0]*a[0])
                    z[0] = 0;
                //좌우 벽을 넘어가면 그 크기가 벽에 한계가 되게 만든다.

                if(a[0]*a[0] - HM*HM > a[2]*a[2])
                    z[0] = 0;
                else if(a[2]*a[2] - HM*HM > a[0]*a[0])
                    z[0] = 1;
                //위아래 벽을 넘어가면 그 크기가 벽에 한계가 되게 만든다.

                zzz = zz;
                return zzz; //zz[0]과zz[1]은 x,y비율, zz[2]는 높이 차이(cm)
            }
        }
        catch (Exception e)
        {
            return zz;
        }
    }

    public double[] getCoordinate2(double zz[],double a[]) {      //a배열은 크기가 2, 2비콘으로부터의 거리를 받는다.(단위cm)
        double [] zzz = new double[3];
        try{
            if(a[0] + a[1] < WM || a[0] > diagonal || a[1] > diagonal)
            {
                return zz;
            }
            else {
                if(a[0]*a[0] - 150*150 < 0 || a[1]*a[1] - 150*150 < 0)
                    return zz;
                a[0] = Math.sqrt(a[0]*a[0] - 150*150);                               //평면으로 내적
                a[1] = Math.sqrt(a[1]*a[1] - 150*150);                               //평면으로 내적
                zzz[0] = ((WM * WM + a[0] * a[0] - a[1] * a[1]) / 2) / HMd;      //x좌표를 구한다.
                if(a[0]*a[0] - zz[0]*zz[0] < 0)
                    return zz;
                zzz[1] = Math.sqrt(a[0]*a[0] - zzz[0]*zzz[0]) / WMd / WMd;     //y좌표를 구한다.(비율)
                zzz[0] /= HMd;                                                     //비율을 구한다.(비율)
                if(zzz[0] < 0 || zzz[0] > 1 || zzz[1] > 1 || zzz[1] < 0)
                    return zz;
                zzz[2] = 150;                                                        //높이는 150으로 가정(cm)
                zzz = zz;
                return zzz;                                                         //zz[0]과zz[1]은 x,y비율, zz[2]는 높이 차이(cm)
            }
        }
        catch (Exception e)
        {
            return zz;
        }
    }//deprecated됨 - 예외에 대한 자세한 처리가 힘들다

    public int areaPopupPictureMusic(float zz[], double place[][]) {     //x,y의 비율을 가진 double배열, place[][]로 작품위치를 가진 배열
        double xxx = zz[0]*HMd;                                             //맵의 크기에 맞게 변환
        double yyy = zz[1]*WMd;                                            //맵의 크기에 맞게 변환
        for(int i = 0;i<place.length;i++) {
            if ((place[i][0] - xxx) * (place[i][0] - xxx) + (place[i][1] - yyy) * (place[i][1] - yyy) <= 4900.0d)
                return i;
        }
        return -1;
    }                                                                       //위치 중심으로 원의 범위 안에 들어가면 return 0,1,2를 하는 함수\

    public double rtd(int a) {
        Log.d("scan", Math.pow(9.0,-a/22.0)+"cm");//원래 10의 -a/20.0 승
        return 3.0*10.0/(4.0*3.14*2.4)* Math.pow(10.0,-a/20.0);
    } //rssiToDistance(cm단위) a가0이면 약 0.995

    public void noChange(View v) {
        map.setVisibility(View.VISIBLE);
        path1.setVisibility(View.INVISIBLE);
        path2.setVisibility(View.INVISIBLE);
        vv.setVisibility(View.INVISIBLE);
    }

    public void pathChange1(View v) {
        map.setVisibility(View.INVISIBLE);
        path1.setVisibility(View.VISIBLE);
        path2.setVisibility(View.INVISIBLE);
        vv.setVisibility(View.INVISIBLE);
    }

    public void pathChange2(View v) {
        map.setVisibility(View.INVISIBLE);
        path1.setVisibility(View.INVISIBLE);
        path2.setVisibility(View.VISIBLE);
        vv.setVisibility(View.INVISIBLE);
    }

    public void pathChange3(View v) {
        map.setVisibility(View.VISIBLE);
        path1.setVisibility(View.INVISIBLE);
        path2.setVisibility(View.INVISIBLE);
        vv.setVisibility(View.VISIBLE);
        vv.invalidate();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void oneP(View v)
    {
        intent1.putExtra("value",make);
        startActivityForResult(intent1, 1);
    }

    public void twoP(View v)
    {
        intent2.putExtra("value",make);
        startActivityForResult(intent2,2);
    }

    public void threeP(View v)
    {
        intent3.putExtra("value",make);
        startActivityForResult(intent3,3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch(requestCode)
            {
                case 1:
                    make = data.getIntExtra("value", -1);
                    break;
                case 2:
                    make = data.getIntExtra("value", -1);
                    break;
                case 3:
                    make = data.getIntExtra("value", -1);
                    break;
            }
            vv.invalidate();

            map.setVisibility(View.VISIBLE);
            path1.setVisibility(View.INVISIBLE);
            path2.setVisibility(View.INVISIBLE);
            vv.setVisibility(View.VISIBLE);

            try {
                if (make != -1) {
                    double min = Point.getD(now, pa[0]);
                    for (int i = 1; i < 13; i++) {
                        if (min > Point.getD(now, pa[i])) {
                            min = Point.getD(now, pa[i]);
                            position = i;
                        }
                    }

                    path = Dikstra.dikstra(weightMatrix, position, make);

                    if(path != null) {
                        if (Point.getD(now, pa[path[1]]) < Point.getD(pa[path[0]], pa[path[1]])) {
                            for (int i = 0; i < path.length - 1; i++)
                                path[i] = path[i + 1];
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "No destination", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch(Exception e)
            {

            }
        }
        catch(Exception e)
        {
            make = -1;
        }
    }

    /*@Override
    public void onResume() {
        super.onResume();
        vv.invalidate();
    }*/

    public void onBackPressed() {
        mba.stopLeScan(leScanCallback);
        bleList.clear();
        bleList.notifyDataSetChanged();
        this.finish();
    }
}