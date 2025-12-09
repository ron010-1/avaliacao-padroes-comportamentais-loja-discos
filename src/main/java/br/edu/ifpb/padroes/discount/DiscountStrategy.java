package br.edu.ifpb.padroes.discount;

import br.edu.ifpb.padroes.music.Album;

public interface DiscountStrategy {
    double calculate(Album album);
}
