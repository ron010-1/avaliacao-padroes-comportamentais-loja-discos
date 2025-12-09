package br.edu.ifpb.padroes.discount;

import br.edu.ifpb.padroes.music.Album;

public class PremiumDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculate(Album album) {
        return album.getPrice() * 0.15;
    }
}
