package br.edu.ifpb.padroes.store;

import br.edu.ifpb.padroes.music.Album;

public class CalculatorDiscountRegular extends CalculatorDiscount {

    public CalculatorDiscountRegular(Album album) {
        super(album);
    }

    public double calculateDiscount() {
        return super.getAlbum().getPrice() * 0.05;
    }
}
