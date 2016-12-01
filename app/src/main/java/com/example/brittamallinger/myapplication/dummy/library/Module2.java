package com.example.brittamallinger.myapplication.dummy.library;

import java.util.EventObject;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by brittamallinger on 01.12.16.
 */
public class Module2 implements Module1.StateListener {
    final ThreadLocal<AtomicInteger> actual = new ThreadLocal<AtomicInteger>() {
        @Override
        protected AtomicInteger initialValue() {
            return new AtomicInteger();
        }
    };

    public Module2(Module1 module1) {
        module1.addStateListener(this);
    }


    public int getActual() {
        return actual.get().get();
    }

    @Override
    public Object stateChanged(EventObject event) {
        actual.get().set(1);
        return null;
    }
}
