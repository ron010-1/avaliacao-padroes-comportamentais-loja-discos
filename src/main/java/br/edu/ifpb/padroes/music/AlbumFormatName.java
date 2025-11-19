package br.edu.ifpb.padroes.music;

public class AlbumFormatName {

    private Album album;

    private AlbumFormatName (Album album){
        this.album = album;
    }

    public String getFormattedName() {
        return String.format("%s - %s (%s)", this.album.getTitle(), this.album.getArtist(), this.album.getReleaseDate().getYear());
    }
}
