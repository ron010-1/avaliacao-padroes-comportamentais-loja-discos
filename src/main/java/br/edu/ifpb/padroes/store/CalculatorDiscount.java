package br.edu.ifpb.padroes.store;

import br.edu.ifpb.padroes.customer.CustomerType;
import br.edu.ifpb.padroes.music.Album;

public abstract class CalculatorDiscount {

    private double discount;
    private Album album;

    public CalculatorDiscount(Album album) {
        this.album = album;
    }

    public Album getAlbum() {
        return album;
    }

    public double getTotalDiscount() {
        return this.calculateDiscount();
    }

    public abstract double calculateDiscount();
}
