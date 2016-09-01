package kr.ac.gachon.game.pingpong.model;

import android.graphics.Paint;

import com.google.gson.annotations.Expose;


/**
 * Created by kjs on 2015-06-03.
 */
public class TestModel extends TestBaseModel {
	@Expose private String testid;
	@Expose private float speed;
    
    @Expose(serialize = false, deserialize = false)
    private Paint paint;
    
    //@Expose(serialize = false, deserialize = false)
    //private Moving moving;

    public TestModel(float position, String testid, float speed, Paint paint) {
    	super(position);
        this.testid = testid;
        this.speed = speed;
        this.paint = paint;
        
        //moving = new Moving(this);
    }

    public String getTestid() {
        return testid;
    }
    public void setTestid(String testid) {
        this.testid = testid;
    }
    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "testid='" + testid + '\'' +
                ", speed=" + speed +
                '}';
    }
}
