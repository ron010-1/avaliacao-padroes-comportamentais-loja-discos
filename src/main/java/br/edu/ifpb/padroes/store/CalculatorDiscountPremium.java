package br.edu.ifpb.padroes.store;

import br.edu.ifpb.padroes.music.Album;

public class CalculatorDiscountPremium extends CalculatorDiscount{

    public CalculatorDiscountPremium(Album album) {
        super(album);
    }

    public double calculateDiscount() {
        return super.getAlbum().getPrice() * 0.15;
    }
}
