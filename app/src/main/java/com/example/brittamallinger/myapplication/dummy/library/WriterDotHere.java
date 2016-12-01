package com.example.brittamallinger.myapplication.dummy.library;

import java.util.EventObject;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by brittamallinger on 01.12.16.
 */
public class WriterDotHere implements Module1.StateListener {
    private final Integer id;
    private final AtomicInteger counter = new AtomicInteger(0);

    public WriterDotHere(WriterDotHereInitializer writerDotHereInitializer) {
        this.id = writerDotHereInitializer.getInteger();
        writerDotHereInitializer.getModule1().addStateListener(this);
    }

    public int getCounter() {
        return counter.get();
    }

    @Override
    public Object stateChanged(EventObject event) {
        System.out.println(this + "" +
                "\n\twith id " + this.id +
                "\n\tstateChanged for the " + counter.getAndIncrement() + "'th time" +
                "\n\t\tsource: " + event.getSource());
        return null;
    }

    public static class WriterDotHereInitializer {
        private final Module1 module1;
        private final Integer integer;

        public WriterDotHereInitializer(Module1 module1, Integer integer) {
            this.module1 = module1;
            this.integer = integer;
        }

        public Module1 getModule1() {
            return module1;
        }

        public Integer getInteger() {
            return integer;
        }
    }
}
