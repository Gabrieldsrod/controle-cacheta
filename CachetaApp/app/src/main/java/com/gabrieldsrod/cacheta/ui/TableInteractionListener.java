package com.gabrieldsrod.cacheta.ui;

import com.gabrieldsrod.cacheta.entities.Table;

public interface TableInteractionListener {
    void onIniciarPartida(Table mesa);
    void onEncerrarPartida(Table mesa);
}

