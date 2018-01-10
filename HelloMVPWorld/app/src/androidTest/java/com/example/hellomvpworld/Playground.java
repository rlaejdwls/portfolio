package com.example.hellomvpworld;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.Observable;

import static org.junit.Assert.assertEquals;

/**
 * Created by Hwang on 2018-01-09.
 *
 * Description :
 */
@RunWith(AndroidJUnit4.class)
public class Playground {
    private TextView text;

    @Before
    public void setup() {
        text = new TextView(InstrumentationRegistry.getContext());
        text.setText("Hello World");
    }

    @Test
    public void playground() {
        Observable.just(text)
                .map((data) -> data + ", Rx")
                .subscribe((data) -> text.setText(data));
        assertEquals("Hello World, Rx", text.getText().toString());
    }
}
