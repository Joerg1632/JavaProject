package org.nsu.service;

import org.nsu.gui.events.Event;

public interface Observer {
    void notify(Event event);
}
