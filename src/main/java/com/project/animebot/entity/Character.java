package com.project.animebot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Character {
    @JsonProperty("mal_id")
    private int malId;

    @JsonProperty("url")
    private String url;

    @JsonProperty("images")
    private Images images;

    @JsonProperty("name")
    private String name;

    @JsonProperty("name_kanji")
    private String nameKanji;

    @JsonProperty("nicknames")
    private List<String> nicknames;

    @JsonProperty("favorites")
    private int favorites;

    @JsonProperty("about")
    private String about;

    @JsonProperty("animeography")
    private List<Animeography> animeography;

    @JsonProperty("mangaography")
    private List<Mangaography> mangaography;

    @JsonProperty("voice_actors")
    private List<VoiceActor> voiceActors;

    // Геттеры и сеттеры
    public int getMalId() { return malId; }
    public void setMalId(int malId) { this.malId = malId; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Images getImages() { return images; }
    public void setImages(Images images) { this.images = images; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNameKanji() { return nameKanji; }
    public void setNameKanji(String nameKanji) { this.nameKanji = nameKanji; }

    public List<String> getNicknames() { return nicknames; }
    public void setNicknames(List<String> nicknames) { this.nicknames = nicknames; }

    public int getFavorites() { return favorites; }
    public void setFavorites(int favorites) { this.favorites = favorites; }

    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }

    public List<Animeography> getAnimeography() { return animeography; }
    public void setAnimeography(List<Animeography> animeography) { this.animeography = animeography; }

    public List<Mangaography> getMangaography() { return mangaography; }
    public void setMangaography(List<Mangaography> mangaography) { this.mangaography = mangaography; }

    public List<VoiceActor> getVoiceActors() { return voiceActors; }
    public void setVoiceActors(List<VoiceActor> voiceActors) { this.voiceActors = voiceActors; }

    // Вложенные классы
    public static class Images {
        @JsonProperty("jpg")
        private ImageUrls jpg;

        @JsonProperty("webp")
        private ImageUrls webp;

        public ImageUrls getJpg() { return jpg; }
        public void setJpg(ImageUrls jpg) { this.jpg = jpg; }

        public ImageUrls getWebp() { return webp; }
        public void setWebp(ImageUrls webp) { this.webp = webp; }
    }

    public static class ImageUrls {
        @JsonProperty("image_url")
        private String imageUrl;

        @JsonProperty("small_image_url")
        private String smallImageUrl;

        @JsonProperty("large_image_url")
        private String largeImageUrl;

        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

        public String getSmallImageUrl() { return smallImageUrl; }
        public void setSmallImageUrl(String smallImageUrl) { this.smallImageUrl = smallImageUrl; }

        public String getLargeImageUrl() { return largeImageUrl; }
        public void setLargeImageUrl(String largeImageUrl) { this.largeImageUrl = largeImageUrl; }
    }

    public static class Animeography {
        @JsonProperty("mal_id")
        private int malId;

        @JsonProperty("name")
        private String name;

        @JsonProperty("url")
        private String url;

        public int getMalId() { return malId; }
        public void setMalId(int malId) { this.malId = malId; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    public static class Mangaography {
        @JsonProperty("mal_id")
        private int malId;

        @JsonProperty("name")
        private String name;

        @JsonProperty("url")
        private String url;

        public int getMalId() { return malId; }
        public void setMalId(int malId) { this.malId = malId; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    public static class VoiceActor {
        @JsonProperty("mal_id")
        private int malId;

        @JsonProperty("name")
        private String name;

        @JsonProperty("url")
        private String url;

        @JsonProperty("language")
        private String language;

        public int getMalId() { return malId; }
        public void setMalId(int malId) { this.malId = malId; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }

        public String getLanguage() { return language; }
        public void setLanguage(String language) { this.language = language; }
    }
}