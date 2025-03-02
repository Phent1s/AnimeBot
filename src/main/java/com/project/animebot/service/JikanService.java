package com.project.animebot.service;

import com.project.animebot.entity.Anime;
import com.project.animebot.entity.Manga;
import com.project.animebot.entity.Character;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class JikanService {
    @Value("${jikan.api.url}")
    private String jikanApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // Поиск аниме
    public Anime searchAnime(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            String url = jikanApiUrl + "/anime?q=" + encodedQuery + "&limit=1";
            AnimeResponse response = restTemplate.getForObject(url, AnimeResponse.class);

            if (response != null && response.getData() != null && !response.getData().isEmpty()) {
                return response.getData().get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Поиск манги
    public Manga searchManga(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            String url = jikanApiUrl + "/manga?q=" + encodedQuery + "&limit=1";
            MangaResponse response = restTemplate.getForObject(url, MangaResponse.class);

            if (response != null && response.getData() != null && !response.getData().isEmpty()) {
                return response.getData().get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Поиск персонажа
    public Character searchCharacter(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            String url = jikanApiUrl + "/characters?q=" + encodedQuery + "&limit=1";
            CharacterResponse response = restTemplate.getForObject(url, CharacterResponse.class);

            if (response != null && response.getData() != null && !response.getData().isEmpty()) {
                return response.getData().get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Получение топовых аниме
    public List<Anime> getTopAnime(String type, String filter, String rating, boolean sfw, int page, int limit) {
        try {
            String url = jikanApiUrl + "/top/anime?type=" + type + "&filter=" + filter + "&rating=" + rating + "&sfw=" + sfw + "&page=" + page + "&limit=" + limit;
            AnimeResponse response = restTemplate.getForObject(url, AnimeResponse.class);

            if (response != null && response.getData() != null) {
                return response.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Получение топовой манги
    public List<Manga> getTopManga(String type, String filter, int page, int limit) {
        try {
            String url = jikanApiUrl + "/top/manga?type=" + type + "&filter=" + filter + "&page=" + page + "&limit=" + limit;
            MangaResponse response = restTemplate.getForObject(url, MangaResponse.class);

            if (response != null && response.getData() != null) {
                return response.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Получение топовых персонажей
    public List<Character> getTopCharacters(int page, int limit) {
        try {
            String url = jikanApiUrl + "/top/characters?page=" + page + "&limit=" + limit;
            CharacterResponse response = restTemplate.getForObject(url, CharacterResponse.class);

            if (response != null && response.getData() != null) {
                return response.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Получение случайного аниме
    public Anime getRandomAnime() {
        try {
            String url = jikanApiUrl + "/random/anime";
            Anime anime = restTemplate.getForObject(url, Anime.class);
            return anime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Получение случайной манги
    public Manga getRandomManga() {
        try {
            String url = jikanApiUrl + "/random/manga";
            Manga manga = restTemplate.getForObject(url, Manga.class);
            return manga;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Получение случайного персонажа
    public Character getRandomCharacter() {
        try {
            String url = jikanApiUrl + "/random/characters";
            Character character = restTemplate.getForObject(url, Character.class);
            return character;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Внутренние классы для парсинга ответов
    private static class AnimeResponse {
        private List<Anime> data;

        public List<Anime> getData() { return data; }
        public void setData(List<Anime> data) { this.data = data; }
    }

    private static class MangaResponse {
        private List<Manga> data;

        public List<Manga> getData() { return data; }
        public void setData(List<Manga> data) { this.data = data; }
    }

    private static class CharacterResponse {
        private List<Character> data;

        public List<Character> getData() { return data; }
        public void setData(List<Character> data) { this.data = data; }
    }
}