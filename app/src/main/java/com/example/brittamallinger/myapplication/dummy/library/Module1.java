package com.example.brittamallinger.myapplication.dummy.library;

import java.util.EventObject;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by brittamallinger on 30.11.16.
 */
public class Module1 {
    // observer interface
    public interface StateListener {
        Object stateChanged(EventObject event);
    }

    private final Set<StateListener> stateListeners = new HashSet<StateListener>();

    public void addStateListener(StateListener listener) {
        stateListeners.add(listener);
    }

    public void removeStateListener(StateListener listener) {
        stateListeners.remove(listener);
    }

    public Module1(String config) {
        System.out.println(this.getClass() + "\n\t got config: >> " + config + " <<");
    }

    public void fnChange() {
        // raise change event..
        EventObject eventObject = new EventObject(this);

        // -- initial version
        //        stateListeners.forEach((el) -> {
        //            el.stateChanged(eventObject);
        //        });

        stateListeners.parallelStream().map(listener ->
                listener.stateChanged(new EventObject(this))).collect(Collectors.toList());
    }

}
