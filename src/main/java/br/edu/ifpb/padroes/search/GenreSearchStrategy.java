package br.edu.ifpb.padroes.search;

import br.edu.ifpb.padroes.music.Album;

public class GenreSearchStrategy implements SearchStrategy {
    @Override
    public boolean matches(Album album, String searchTerm) {
        return album.getGenre().toLowerCase().contains(searchTerm.toLowerCase());
    }
}
