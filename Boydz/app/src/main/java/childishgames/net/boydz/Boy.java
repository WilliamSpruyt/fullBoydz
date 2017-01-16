package childishgames.net.boydz;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Family on 25/05/2016.
 */

public class Boy {
    int[] c;

    ArrayList<Bitmap> boyBitmapArrayList;
    float centrex;
    float centrey;
    double xpos;
    double ypos;
    double velx;
    double vely;
    float initvelx;
    float initvely;
    float initx;
    float inity;
    float rad;
    int mCanvasWidth;
    int mCanvasHeight;
    double orbit = 0;
    double angle = 0;
    double increment;
    float transparency = 128;
    float bounce;
    String breed = "none";
    float e = 0.8f;
    int CALM = 6;
    double tempxpos = 0;
    double deltaxpos = 0;
    int frames;
    String originalBreed;
    int bounceCirc = 2;
    int rotationAngle = 0;
    int spin = 0;
    boolean flips = false;
    String name = "";
    double incrementCirc;
    Random r = new Random();
    Bitmap bernieBit;
    int bitHeight;


    //The Constructor is defined with arguments.
    Boy(String Name, ArrayList<Bitmap> BoyBitmapArrayList, float Xpos, float Ypos, float Velx, float Vely,
        double Increment, float Bounce, String Breed, boolean Flips) {
        boyBitmapArrayList = BoyBitmapArrayList;
        xpos = Xpos;
        ypos = Ypos;
        velx = Velx;
        initvelx = Velx;
        initvely = Vely;
        initx = Xpos;
        inity = Ypos;
        vely = Vely;
        breed = Breed;
        originalBreed = breed;
        centrex = mCanvasWidth / 2;
        centrey = mCanvasHeight / 2;
        increment = Increment;
        bounce = Bounce;
        frames = 0;
        bounceCirc = setBounceCirc((int) Math.sqrt(Math.pow(velx, 2) + Math.pow(vely, 2))) * 2;
        flips = Flips;
        spin = (int) Velx;
        name = Name;
        incrementCirc = Math.abs(increment * 20);

        if (name.equals("bernie")) {
            int circ=r.nextInt(95)+5;

           bernieBit= Bitmap.createScaledBitmap(boyBitmapArrayList.get(0),circ , circ, true);
            bitHeight=circ/2;

        }
        else {bitHeight= (boyBitmapArrayList.get(0).getHeight())/2;}
    }
    public void display(Canvas canvas) {
        float expos = Float.valueOf(String.valueOf(xpos));
        float whypos = Float.valueOf(String.valueOf(ypos));
        if (name.equals("bernie")) {
            canvas.drawBitmap(bernieBit, expos - bernieBit.getWidth() / 2,
                    whypos - bernieBit.getHeight() / 2, null);
        }

            if (velx > 0&& !name.equals("bernie")) {
                canvas.drawBitmap(boyBitmapArrayList.get(frames), expos - boyBitmapArrayList.get(frames).getWidth() / 2,
                        whypos - boyBitmapArrayList.get(frames).getHeight() / 2, null);
            }
            if (velx < 0&& !name.equals("bernie")) {
                canvas.drawBitmap(flip(boyBitmapArrayList.get(frames)), expos - boyBitmapArrayList.get(frames).getWidth() / 2,
                        whypos - boyBitmapArrayList.get(frames).getHeight() / 2, null);
            }}


    public void display4disco(Canvas canvas) {
        float expos = Float.valueOf(String.valueOf(xpos));
        float whypos = Float.valueOf(String.valueOf(ypos));
        if (name.equals("bernie")) {
            canvas.drawBitmap(bernieBit, expos - bernieBit.getWidth() / 2,
                    whypos - bernieBit.getHeight() / 2, null);
        }

            if (deltaxpos < 0 && !name.equals("bernie")) {
                canvas.drawBitmap(boyBitmapArrayList.get(frames), expos - boyBitmapArrayList.get(frames).getWidth() / 2,
                        whypos - boyBitmapArrayList.get(frames).getHeight() / 2, null);
            }
            if (deltaxpos > 0&& !name.equals("bernie")) {
                canvas.drawBitmap(flip(boyBitmapArrayList.get(frames)), expos - boyBitmapArrayList.get(frames).getWidth() / 2,
                        whypos - boyBitmapArrayList.get(frames).getHeight() / 2, null);
            }

    }

    void move() {
        xpos = xpos + velx;
        ypos = ypos + vely;
        if (xpos > mCanvasWidth + (2 * rad)) {
            xpos = 0 - (2 * rad);
        }
        if (ypos > mCanvasHeight + (2 * rad)) {
            /*ypos = 0 - (2 * rad);*/
        }
        if (xpos < 0 - (2 * rad)) {
            xpos = mCanvasWidth + (2 * rad);
        }
        if (ypos < 0 - (2 * rad)) {
           /* ypos = height + (2 * rad);*/
        }
    }

    void singularity() {
        double dis = Math.sqrt(Math.pow(xpos - mCanvasWidth / 2, 2) + Math.pow(ypos - mCanvasHeight / 2, 2));
        double pull = 1 / (dis);
        velx = velx + ((mCanvasWidth / 2) - (xpos)) * pull;
        vely = vely + ((mCanvasHeight / 2) - (ypos)) * pull;
    }

    /*void crowding(){
        for(int i = 0; i < numBalls; ++i)

            if (this!= body[i] &&dist(this.xpos,this.ypos,body[i].xpos,body[i].ypos )<
            ((this.rad+body[i].rad)/2)-1){xpos=(mCanvasWidth*1.5)*random(-1,1);ypos=mCanvasHeight*1.5*random(1,-1);}
 */
    void collide(Boy thebody) {


        double dis = Math.sqrt(Math.pow(xpos - thebody.getXpos(), 2) + Math.pow(ypos - thebody.getYpos(), 2));

        if (this != thebody) {
            if (dis <= (50)) {
                double tempx = this.getVelx();
                double tempy = this.getVely();
                this.setVelx(thebody.getVelx());
                this.setVely(thebody.getVely());

                thebody.setVelx(tempx);
                thebody.setVely(tempy);
            }
        }
    }


    void disco_ball() {

        orbit = Math.sqrt(Math.pow(xpos - mCanvasWidth / 2, 2) + Math.pow(ypos - mCanvasHeight / 2, 2));
        double xlength = xpos - mCanvasWidth / 2;
        double ylength = ypos - mCanvasHeight / 2;
        if (xpos < mCanvasWidth / 2) {
            angle = (Math.acos((ylength) / orbit)) * -1;
        }
        if (xpos > mCanvasWidth / 2) {
            angle = Math.acos((ylength) / orbit);
        }

        angle += (increment / 10);
        double ffs = mCanvasWidth / 2 + (orbit * Math.sin(angle));
        double fFs = mCanvasHeight / 2 + (orbit * Math.cos(angle));
        xpos = Float.valueOf(String.valueOf(ffs));

        ypos = Float.valueOf(String.valueOf(fFs));
        if (xpos < mCanvasWidth / 2 && ypos < mCanvasHeight / 2) {
            deltaxpos = -1;
        }
        if (xpos > mCanvasWidth / 2 && ypos > mCanvasHeight / 2) {
            deltaxpos = 1;
        }
        if (xpos > mCanvasWidth / 2 && ypos < mCanvasHeight / 2) {
            deltaxpos = -1;
        }
        if (xpos < mCanvasWidth / 2 && ypos > mCanvasHeight / 2) {
            deltaxpos = 1;
        }
        deltaxpos *= increment * -1;


    }


    void bounce() {
        float grav = 0.5f;
        Random r = new Random();
        xpos = xpos + velx;
        ypos = ypos + vely;

        vely = vely + grav;
        if (xpos + velx > mCanvasWidth) {
            velx = velx * -1;
            spin = (int) velx;
        }
        if (ypos + vely > mCanvasHeight -bitHeight){
            vely = bounce;
            bounce = bounce * e;
        }
        if (xpos + velx < 0) {
            velx = velx * -1;
            spin = (int) velx;
        }
        if (bounce > -0.1) {
            bounce = r.nextFloat() * -50;
        }
    }

    void ballBounce() {
        float grav = 0.5f;
        Random r = new Random();
        xpos = xpos + velx;
        ypos = ypos + vely;

        vely = vely + grav;
        if (xpos + velx > mCanvasWidth) {
            velx = velx * -1;
        }
        if (ypos > (mCanvasHeight - bounceCirc)) {
            vely = bounce;
            bounce = bounce * e;
        }
        if (xpos + velx < 0) {
            velx = velx * -1;
        }
        if (bounce > -0.1) {
            bounce = r.nextFloat() * -25;
        }
    }


    void reset_vels() {

        velx = initvelx;
        vely = initvely;
    }

    void reset_pos() {
        xpos = initx;
        ypos = inity;
    }


    public double getVelx() {
        return velx;
    }

    public void setVelx(double vel) {
        velx = vel;
    }

    public double getVely() {
        return vely;
    }

    public void setVely(double vel) {
        vely = vel;
    }

    public double getXdeltafordisco() {
        return deltaxpos;
    }

    public int getBitmapArrayLength() {
        return boyBitmapArrayList.size();
    }

    public String getBreed() {
        if (Math.abs(vely) > 30) {
            vely *= 0.8;
        }
        if (Math.abs(velx) > 30) {
            velx *= 0.8;
        }
        return breed;
    }

    public void setBreed(String newBreed) {
        breed = newBreed;
    }

    public String getOriginalBreed() {
        return originalBreed;
    }

    public double getXpos() {
        return xpos;
    }

    public int getVectorVel() {
        return (int) Math.sqrt(Math.pow(velx, 2) + Math.pow(vely, 2));
    }

    public double getYpos() {
        return ypos;
    }

    public int getFrames() {
        return frames;
    }

    public void advanceFrame() {
        frames++;

        if (frames >= boyBitmapArrayList.size()) {
            frames = 0;
        }
    }

    public void finger(float x, float y) {
        velx = -((x - xpos) / 50);
        if (y != -1) {
            vely = -((y - ypos) / 50);
        }

    }

    public void setCanvasDim(int x, int y) {
        mCanvasWidth = x;
        mCanvasHeight = y;
    }

    public void slipVel(float slipX, float slipY) {
        velx += slipX / 10;
        vely += slipY / 10;
    }

    public void slipPos(float slipX, float slipY) {
        xpos += slipX;
        ypos += slipY;
    }

    Bitmap flip(Bitmap d) {
        Matrix m = new Matrix();
        m.preScale(-1, 1);

        Bitmap dst = Bitmap.createBitmap(d, 0, 0, d.getWidth(), d.getHeight(), m, false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return dst;
    }

    public int getBounceCirc() {


        return bounceCirc;
    }



    public int setBounceCirc(int newCirc) {
        int res;

        res = Math.min(newCirc,50);
        res = Math.max(res,5);
        return  res;
    }

    public void somersault(Canvas canvas) {
        if (ypos > canvas.getHeight() - (boyBitmapArrayList.get(frames).getHeight() * 2))

        {
            if (rotationAngle % 10 != 0) {
                rotationAngle -= (rotationAngle % 10);
            }
            if (rotationAngle != 0) {
                if (rotationAngle >= 180) {
                    rotationAngle += 10;
                }
                if (rotationAngle < 180) {
                    rotationAngle -= 10;
                }
            }
        } else
            rotationAngle += spin;
        canvas.save(); //Save the position of the canvas.

        canvas.rotate(rotationAngle, (float) xpos + (boyBitmapArrayList.get(frames).getWidth() / 2),
                (float) ypos + (boyBitmapArrayList.get(frames).getHeight() / 2)); //Rotate the canvas.
        //canvas.drawBitmap(bit,(float) xpos,(float) ypos,null); //Draw the ball on the rotated canvas.
        if (name.equals("bernie")) {
            canvas.drawBitmap(bernieBit, (float)xpos - bernieBit.getWidth() / 2,
                    (float)ypos - bernieBit.getHeight() / 2, null);
        }
        if (velx > 0&& !name.equals("bernie")) {
            canvas.drawBitmap(boyBitmapArrayList.get(frames), (float) xpos - boyBitmapArrayList.get(frames).getWidth() / 2,
                    (float) ypos - boyBitmapArrayList.get(frames).getHeight() / 2, null);
        }
        if (velx < 0&& !name.equals("bernie")) {
            canvas.drawBitmap(flip(boyBitmapArrayList.get(frames)), (float) xpos - boyBitmapArrayList.get(frames).getWidth() / 2,
                    (float) ypos - boyBitmapArrayList.get(frames).getHeight() / 2, null);
        }
        canvas.restore(); //Rotate the canvas back so that it looks like ball has rotated.
        if (rotationAngle > 360 || rotationAngle < 0)
            rotationAngle = 0;


    }

    public boolean getFlips() {
        return flips;
    }
    public double getIncrementCirc(){return  incrementCirc;}
}
