package childishgames.net.boydz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Family on 22/12/2016.
 */

public class BoydzSurfaceView extends SurfaceView {


    Random r = new Random();
    int iconWidth;
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mOrbiterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mSingularityPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint mBouncePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int iconHeight;
    private float fingerX;
    private float fingerY;
    private SurfaceHolder surfaceHolder;
    private boolean reset = false;
    private float slippingX = 0;
    private float slippingY = 0;
    private MyThread myThread;
    private Boy testBoy;
    private ArrayList<Bitmap> georgeMapArrayList = new ArrayList<>(1);
    private ArrayList<Bitmap> harryMapArrayList = new ArrayList<>(1);
    private ArrayList<Bitmap> barryMapArrayList = new ArrayList<>(1);
    private ArrayList<Bitmap> bernie = new ArrayList<>(1);
    private boolean circles;
    private String background = "black";
    private Bitmap photo;
    private int WIDTH = 100;
    private int HEIGHT = 100;
    private ArrayList<Boy> boyArrayList = new ArrayList<>(1);

    public BoydzSurfaceView(Context context) {
        super(context);
        init();
    }

    public BoydzSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BoydzSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();


        if (event.getAction() != MotionEvent.ACTION_MOVE) {
            addBoy(x, y);

        }
       /* if (event.getAction() != MotionEvent.ACTION_DOWN) {
            fingerX=x;
            fingerY=y;
        }*/
        return false;
    }

    private void init() {
        bernie.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.bernie));

        georgeMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.george));
        georgeMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.george1));
        georgeMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.george2));
        georgeMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.george3));
        georgeMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.george6));
        georgeMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.george7));

        barryMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.harry));
        barryMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.harry1));
        barryMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.harry2));
        barryMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.harry3));
        barryMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.harry4));
        barryMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.harry6));
        barryMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.harry7));
        barryMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.harry8));
        barryMapArrayList.add(BitmapFactory.decodeResource(getResources(),
                R.drawable.harry9));
        for (int i = 0; i < barryMapArrayList.size(); i++) {
            harryMapArrayList.add(flip(barryMapArrayList.get(i)));
        }

        myThread = new MyThread(this);

        surfaceHolder = getHolder();


        iconWidth = georgeMapArrayList.get(0).getWidth();
        iconHeight = georgeMapArrayList.get(0).getHeight();

        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                myThread.setRunning(true);
                Log.i("bollox", "" + myThread.getState());
                if (myThread.getState() == Thread.State.NEW) myThread.start();
                if (myThread.getState() == Thread.State.TERMINATED) {
                    myThread = new MyThread(BoydzSurfaceView.this);
                    myThread.setRunning(true);
                    myThread.start();
                    Log.i("billox", "" + myThread.getState());
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder,
                                       int format, int width, int height) {
                // TODO Auto-generated method stub

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                myThread.setRunning(false);
                while (retry) {
                    try {
                        myThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
    }

    protected void drawSomething(Canvas canvas) {

        WIDTH = canvas.getWidth();
        HEIGHT = canvas.getHeight();

        if (background.equals("black")) canvas.drawColor(Color.BLACK);

        else if (background.equals("trans")) canvas.drawColor(Color.TRANSPARENT);
        else if (background.equals("photo")) {

            if (photo != null) canvas.drawBitmap(photo, 0, 0, mPaint);
        } else canvas.drawColor(Color.YELLOW);
        mOrbiterPaint.setColor(Color.GREEN);
        mSingularityPaint.setColor(Color.RED);
        mBouncePaint.setColor(Color.BLUE);
        mOrbiterPaint.setStyle(Paint.Style.FILL);
        mSingularityPaint.setStyle(Paint.Style.FILL);
        mBouncePaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(30);
        mPaint.setColor(Color.GREEN);
        //if (boyArrayList.size()>0){
        //canvas.drawText(boyArrayList.get(0).getIncrementCirc()+"",50,50,mPaint);}
        if (reset) reset();
        reset = false;
        for (int i = 0; i < boyArrayList.size(); i++) {

            boyArrayList.get(i).setCanvasDim(canvas.getWidth(), canvas.getHeight());
            //canvas.drawCircle((float) testBoy.getXpos(), (float) testBoy.getYpos(), 50, mPaint);
            //if (fingerX*fingerY!=0){boyArrayList.get(i).finger(fingerX,fingerY);}
            if (boyArrayList.get(i).getBreed().equals("bouncer")) {


                if (!circles) {
                    boyArrayList.get(i).bounce();
                    // boyArrayList.get(i).bounce();
                    boyArrayList.get(i).slipVel(slippingX, slippingY);

                    if (!boyArrayList.get(i).getFlips()) {
                        boyArrayList.get(i).display(canvas);
                    }
                    if (boyArrayList.get(i).getFlips()) {
                        boyArrayList.get(i).somersault(canvas);
                    }
                    boyArrayList.get(i).advanceFrame();

                }
                if (circles) {
                    boyArrayList.get(i).ballBounce();
                    canvas.drawCircle((int) boyArrayList.get(i).getXpos(),
                            (int) boyArrayList.get(i).getYpos(), boyArrayList.get(i).getBounceCirc(), mBouncePaint);
                    boyArrayList.get(i).slipVel(slippingX, slippingY);
                }

            }
            if (boyArrayList.get(i).getBreed().equals("orbiter")) {
                boyArrayList.get(i).disco_ball();
                boyArrayList.get(i).slipPos(slippingX, slippingY);
                if (!circles) {
                    boyArrayList.get(i).display4disco(canvas);
                    boyArrayList.get(i).advanceFrame();
                }
                if (circles) {
                    canvas.drawCircle((int) boyArrayList.get(i).getXpos(),
                            (int) boyArrayList.get(i).getYpos(), (int) boyArrayList.get(i).getIncrementCirc(), mOrbiterPaint);
                    boyArrayList.get(i).slipPos(slippingX, slippingY);
                }

            }

            if (boyArrayList.get(i).getBreed().equals("singularity")) {
                boyArrayList.get(i).singularity();
                boyArrayList.get(i).slipVel(slippingX, slippingY);
                boyArrayList.get(i).move();


                if (!circles) {
                    boyArrayList.get(i).display(canvas);
                    boyArrayList.get(i).advanceFrame();
                    boyArrayList.get(i).slipPos(slippingX, slippingY);
                }
                if (circles) {
                    canvas.drawCircle((int) boyArrayList.get(i).getXpos(),
                            (int) boyArrayList.get(i).getYpos(), boyArrayList.get(i).getVectorVel(), mSingularityPaint);
                    boyArrayList.get(i).slipPos(slippingX, slippingY);
                }
            }
            /*for (int j = 0; j < boyArrayList.size(); j++){
                if(i!=j){
                boyArrayList.get(i).collide(boyArrayList.get(j));}}*/

        }
    }

    protected void addBoy(int xPosition, int yPosition) {
        String[] breedArray = {"bouncer", "orbiter", "singularity"};
        String[] nameArray = {"george", "harry", "bernie"};
        Boy boy = null;
        String breed = breedArray[r.nextInt(breedArray.length)];
        String name = nameArray[r.nextInt(nameArray.length)];
        if (name.equals("george")) {
            boy = new Boy(name, georgeMapArrayList, (float) xPosition, (float) yPosition, ((r.nextInt(20)) - 10),
                    ((r.nextInt(20)) - 10), (r.nextDouble() * 2) - 1, (-30 - r.nextInt(70)) / 5, breed, r.nextBoolean());
            //boy.setBounceCirc(boy.getVectorVel());
        }
        if (name.equals("harry")) {
            boy = new Boy(name, harryMapArrayList, (float) xPosition, (float) yPosition, ((r.nextInt(20)) / -10),
                    ((r.nextInt(20)) / -10), (r.nextDouble() * 2) - 1, (-30 - r.nextInt(70)) / 5, breed, r.nextBoolean());
            //boy.setBounceCirc(boy.getVectorVel());
        }
        if (name.equals("bernie")) {
            boy = new Boy(name, bernie, (float) xPosition, (float) yPosition, ((r.nextInt(20)) / -10),
                    ((r.nextInt(20)) / -10), (r.nextDouble() * 2) - 1, (-30 - r.nextInt(70)) / 5, breed, true);
            //boy.setBounceCirc(boy.getVectorVel());
        }


        boyArrayList.add(boy);
    }

    protected void resetBreed() {

        for (int i = 0; i < boyArrayList.size(); i++) {
            boyArrayList.get(i).setBreed(boyArrayList.get(i).getOriginalBreed());
        }
    }

    protected void setBreedDisco() {
        for (int i = 0; i < boyArrayList.size(); i++) {
            boyArrayList.get(i).setBreed("orbiter");
        }
    }

    protected void setBreedSingularity() {
        for (int i = 0; i < boyArrayList.size(); i++) {
            boyArrayList.get(i).setBreed("singularity");
        }
    }

    protected void setBreedBounce() {
        for (int i = 0; i < boyArrayList.size(); i++) {
            boyArrayList.get(i).setBreed("bouncer");

        }
    }

    protected void flickCircles() {
        circles ^= true;
    }

    protected void setBackground(String back) {
        background = back;
    }

    protected void setBackground(String back, Bitmap backPhoto) {
        background = back;
        photo = Bitmap.createScaledBitmap(backPhoto, WIDTH, HEIGHT, true);
    }

    protected void setSlipping(float slipX, float slipY) {
        slippingX = slipX;
        slippingY = slipY;
    }

    public void reset() {
        boyArrayList.clear();
    }

    public void setReset(boolean b) {
        reset = b;
    }


    Bitmap flip(Bitmap d) {
        Matrix m = new Matrix();
        m.preScale(-1, 1);

        Bitmap dst = Bitmap.createBitmap(d, 0, 0, d.getWidth(), d.getHeight(), m, false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return dst;
    }
}