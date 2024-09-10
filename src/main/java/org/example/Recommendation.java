package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Recommendation {
    private User user;
    private List<Song> recommendedSongs;

    public Recommendation(User user) {
        this.user = user;
        this.recommendedSongs = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public List<Song> getRecommendedSongs() {
        return recommendedSongs;
    }

    public void generateRecommendations(List<Song> allSongs) {
        // pobranie ulubionych utworów usera
        List<Song> favoriteSongs = user.getFavoriteSongs();

        // tworzenie listy z ulubionymi gatunkami
        List<String> favoriteGenres = new ArrayList<>();
        for (Song song : favoriteSongs) {
            if (!favoriteGenres.contains(song.getGenre())) { //sprawdzenie czy gatunek już nie znajduje się w liście
                favoriteGenres.add(song.getGenre());
            }
        }

        // filtrowanie piosenek, żeby były w tym samym gatunku
        List<Song> similarGenreSongs = new ArrayList<>();
        for (Song song : allSongs) {
            if (favoriteGenres.contains(song.getGenre())) {
                similarGenreSongs.add(song);
            }
        }

        // sortowanie według liczby odtworzeń
        Collections.sort(similarGenreSongs, new Comparator<Song>() {
            public int compare(Song s1, Song s2) {
                return s2.getPlayCount() - s1.getPlayCount();
            }
        });

        // pierwsze 10
        List<Song> topSongs = new ArrayList<>();
        for (int i = 0; i < Math.min(10, similarGenreSongs.size()); i++) {
            topSongs.add(similarGenreSongs.get(i));
        }

        recommendedSongs.clear();
        recommendedSongs.addAll(topSongs);
    }


}
