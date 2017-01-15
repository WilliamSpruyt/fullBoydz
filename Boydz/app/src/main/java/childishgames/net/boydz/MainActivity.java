package childishgames.net.boydz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    static final int REQUEST_TAKE_PHOTO = 100;
    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter
    Bitmap bitImage;
    Sensor accelerometer;
    String preTrans = "black";
    BoydzSurfaceView boydzSurfaceView;
    boolean tipper=true;


    //Extract the data…
    private SensorManager sensorManager;
    private String paperMode = "black";
    private String swipe = "down";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so Lng
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.actionfile) {
            getGallPic();
            paperMode = "photo";
            boydzSurfaceView.setBackground(paperMode, bitImage);

            return true;
        }
        if (id == R.id.actiontrans) {
            if (!paperMode.equals("trans")) {
                preTrans = paperMode;
                paperMode = "trans";
                boydzSurfaceView.setBackground(paperMode);
            } else if (paperMode.equals("trans")) {
                paperMode = preTrans;
                if (preTrans.equals("black")) {
                    boydzSurfaceView.setBackground(paperMode);
                }
                if (preTrans.equals("photo")) {
                    boydzSurfaceView.setBackground(paperMode, bitImage);
                }
            }
            return true;
        }
        if (id == R.id.actionreset) {
            paperMode = "black";
            boydzSurfaceView.setBackground(paperMode);
            return true;
        }
        if (id == R.id.tilt) {
            tipper^=true;
            return true;
        }
        if (id == R.id.reset) {
            paperMode = "black";
            boydzSurfaceView.setBackground(paperMode);
            boydzSurfaceView.setReset(true);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bitImage = BitmapFactory.decodeResource(getResources(),
                R.drawable.logo);
        setContentView(R.layout.activity_main);


        boydzSurfaceView = (BoydzSurfaceView) findViewById(R.id.boydz_view);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);


        boydzSurfaceView.setBackground(paperMode);
        boydzSurfaceView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {


            public void onSwipeUp() {
                boydzSurfaceView.setBreedBounce();
                if (swipe.equals("up")) {
                    boydzSurfaceView.flickCircles();
                }
                swipe = "up";


            }

            public void onSwipeRight() {
                boydzSurfaceView.setBreedSingularity();
                if (swipe.equals("right")) {
                    boydzSurfaceView.flickCircles();
                }
                swipe = "right";


            }

            public void onSwipeLeft() {
                boydzSurfaceView.setBreedDisco();
                if (swipe.equals("left")) {
                    boydzSurfaceView.flickCircles();
                }
                swipe = "left";

            }

            public void onSwipeDown() {
                boydzSurfaceView.resetBreed();
                if (swipe.equals("down")) {
                    boydzSurfaceView.flickCircles();
                }
                swipe = "down";
            }

        });


    }

    @Override
    protected void onResume() {

        super.onResume();
    }


    private void getGallPic() {
        paperMode = "gallery";
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        switch (requestCode) {

            case PICK_IMAGE_ID:
                bitImage = ImagePicker.getImageFromResult(this, resultCode, data);
                //mCurrentPhotoPath=(ImagePicker.getImageFromResult(this, resultCode, data)).getPath();
                boydzSurfaceView.setBackground("photo", bitImage);
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }

    }

    public void displayInfo() {


        Intent i = new Intent(getApplicationContext(), SensorActivity.class);


        startActivity(i);

    }


    public void toaster(String message) {
        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && tipper) {

            float xVal = event.values[0];
            float yVal = event.values[1];
            float zVal = event.values[2];

            boydzSurfaceView.setSlipping(-xVal, yVal);


        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

