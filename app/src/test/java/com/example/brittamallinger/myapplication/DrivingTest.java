package com.example.brittamallinger.myapplication;

import com.example.brittamallinger.myapplication.dummy.library.Module1;
import com.example.brittamallinger.myapplication.dummy.library.Module2;
import com.example.brittamallinger.myapplication.dummy.library.WriterDotHere;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by brittamallinger on 30.11.16.
 */

public class DrivingTest {
    @Test
    public void DrivingModularity_isIdent() throws Exception {
        assertEquals(1, 1);

    }

    @Test
    public void DrivingModularity_initModules() throws Exception {
        // 'controller' .. w/o config
        String config = "config parm";

        // expected change by sideeffect (candMock)
        int actual = 0;
        int expected = 1;

        // controller
        Module1 module1;
        module1 = new Module1(config);

        // have some 'observer's
        Module2 module2 = new Module2(module1);
        Set<WriterDotHere> sampleLoggingObservers;
        sampleLoggingObservers = new HashSet<>();
        Set<Integer> writersGeneratorSet = new HashSet<Integer>() {
            {
                add(1);
                add(12);
                add(13);
                add(14);
            }
        };
        sampleLoggingObservers.addAll(
                writersGeneratorSet.stream().map(integer ->
                        new WriterDotHere(new WriterDotHere.WriterDotHereInitializer(module1, integer)))
                        .collect(Collectors.toList()));

        // a change in module1 raises EV
        // which gets handled by the 'observer'
        // .. updating the view/viewmodel
        final AtomicIntegerArray calls = new AtomicIntegerArray(1);
        calls.set(0, 0);
        Set<String> h = new HashSet<String>() {{
            add("a");
            add("b");
            add("b");
            add("b");
            add("c");
            add("d");
            add("b");
        }};
        h.forEach((o) -> {
            synchronized (o) {
                module1.fnChange();
                calls.getAndIncrement(0);
            }
        });

        // on my way out.. process changed state
        actual = module2.getActual();
        // check that!
        assertEquals(expected, actual);
        assertEquals(calls.get(0), getOneWriterDotHere(sampleLoggingObservers).getCounter());
        assertEquals(h.size(), calls.get(0));
    }

    private WriterDotHere getOneWriterDotHere(Set<WriterDotHere> oWriterDotHeres) {
        return (WriterDotHere) oWriterDotHeres.toArray()[0];
    }

}
