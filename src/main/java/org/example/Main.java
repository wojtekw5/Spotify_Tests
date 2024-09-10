package org.example;

public class Main {
    public static void main(String[] args) {
        User user = new User(1, "John Doe", "john.doe@example.com", "password123");
        Song song1 = new Song(1, "Song A", "Artist A", "Album A", 210, "Pop");
        Song song2 = new Song(2, "Song B", "Artist B", "Album B", 180, "Rock");

        user.addFavoriteSong(song1);
        user.addFavoriteSong(song2);

        System.out.println("Ulubione utwory użytkownika: " + user.getName());
        for (Song song : user.getFavoriteSongs()) {
            System.out.println(song.getSongInfo());
        }

        Playlist playlist = user.createPlaylist("My Favorite Songs");
        playlist.addSong(song1);
        playlist.addSong(song2);

        System.out.println("Playlisty użytkownika: " + user.getName());
        for (Playlist pl : user.getPlaylists()) {
            System.out.println("Playlist: " + pl.getName());
            for (Song song : pl.getSongs()) {
                System.out.println(" - " + song.getSongInfo());
            }
        }
    }
}