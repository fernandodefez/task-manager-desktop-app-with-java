package com.fernandodefez.app.controllers;

import java.awt.event.ActionListener;

public interface Controller extends ActionListener {

    void index();

    void create();

    void save();

    void edit();

    void cancel();

    void remove();

}
