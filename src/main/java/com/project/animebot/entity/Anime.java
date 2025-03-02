package com.project.animebot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Anime {
    @JsonProperty("mal_id")
    private int malId;

    @JsonProperty("url")
    private String url;

    @JsonProperty("images")
    private Images images;

    @JsonProperty("title")
    private String title;

    @JsonProperty("title_english")
    private String titleEnglish;

    @JsonProperty("title_japanese")
    private String titleJapanese;

    @JsonProperty("type")
    private String type;

    @JsonProperty("episodes")
    private int episodes;

    @JsonProperty("status")
    private String status;

    @JsonProperty("aired")
    private Aired aired;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("rating")
    private String rating;

    @JsonProperty("score")
    private double score;

    @JsonProperty("scored_by")
    private int scoredBy;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("popularity")
    private int popularity;

    @JsonProperty("members")
    private int members;

    @JsonProperty("favorites")
    private int favorites;

    @JsonProperty("synopsis")
    private String synopsis;

    @JsonProperty("background")
    private String background;

    @JsonProperty("season")
    private String season;

    @JsonProperty("year")
    private int year;

    @JsonProperty("broadcast")
    private Broadcast broadcast;

    @JsonProperty("producers")
    private List<Producer> producers;

    @JsonProperty("licensors")
    private List<Licensor> licensors;

    @JsonProperty("studios")
    private List<Studio> studios;

    @JsonProperty("genres")
    private List<Genre> genres;

    @JsonProperty("themes")
    private List<Theme> themes;

    @JsonProperty("demographics")
    private List<Demographic> demographics;

    // Геттеры и сеттеры
    public int getMalId() { return malId; }
    public void setMalId(int malId) { this.malId = malId; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Images getImages() { return images; }
    public void setImages(Images images) { this.images = images; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getTitleEnglish() { return titleEnglish; }
    public void setTitleEnglish(String titleEnglish) { this.titleEnglish = titleEnglish; }

    public String getTitleJapanese() { return titleJapanese; }
    public void setTitleJapanese(String titleJapanese) { this.titleJapanese = titleJapanese; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getEpisodes() { return episodes; }
    public void setEpisodes(int episodes) { this.episodes = episodes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Aired getAired() { return aired; }
    public void setAired(Aired aired) { this.aired = aired; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public int getScoredBy() { return scoredBy; }
    public void setScoredBy(int scoredBy) { this.scoredBy = scoredBy; }

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public int getPopularity() { return popularity; }
    public void setPopularity(int popularity) { this.popularity = popularity; }

    public int getMembers() { return members; }
    public void setMembers(int members) { this.members = members; }

    public int getFavorites() { return favorites; }
    public void setFavorites(int favorites) { this.favorites = favorites; }

    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }

    public String getBackground() { return background; }
    public void setBackground(String background) { this.background = background; }

    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public Broadcast getBroadcast() { return broadcast; }
    public void setBroadcast(Broadcast broadcast) { this.broadcast = broadcast; }

    public List<Producer> getProducers() { return producers; }
    public void setProducers(List<Producer> producers) { this.producers = producers; }

    public List<Licensor> getLicensors() { return licensors; }
    public void setLicensors(List<Licensor> licensors) { this.licensors = licensors; }

    public List<Studio> getStudios() { return studios; }
    public void setStudios(List<Studio> studios) { this.studios = studios; }

    public List<Genre> getGenres() { return genres; }
    public void setGenres(List<Genre> genres) { this.genres = genres; }

    public List<Theme> getThemes() { return themes; }
    public void setThemes(List<Theme> themes) { this.themes = themes; }

    public List<Demographic> getDemographics() { return demographics; }
    public void setDemographics(List<Demographic> demographics) { this.demographics = demographics; }

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

    public static class Aired {
        @JsonProperty("from")
        private String from;

        @JsonProperty("to")
        private String to;

        @JsonProperty("string")
        private String string;

        public String getFrom() { return from; }
        public void setFrom(String from) { this.from = from; }

        public String getTo() { return to; }
        public void setTo(String to) { this.to = to; }

        public String getString() { return string; }
        public void setString(String string) { this.string = string; }
    }

    public static class Broadcast {
        @JsonProperty("day")
        private String day;

        @JsonProperty("time")
        private String time;

        @JsonProperty("timezone")
        private String timezone;

        @JsonProperty("string")
        private String string;

        public String getDay() { return day; }
        public void setDay(String day) { this.day = day; }

        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }

        public String getTimezone() { return timezone; }
        public void setTimezone(String timezone) { this.timezone = timezone; }

        public String getString() { return string; }
        public void setString(String string) { this.string = string; }
    }

    public static class Producer {
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

    public static class Licensor {
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

    public static class Studio {
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

    public static class Genre {
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

    public static class Theme {
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

    public static class Demographic {
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
}