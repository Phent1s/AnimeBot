package com.project.animebot.bot;

import com.project.animebot.entity.Anime;
import com.project.animebot.entity.Manga;
import com.project.animebot.entity.Character;
import com.project.animebot.service.JikanService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeBot extends TelegramLongPollingBot {
    private final JikanService jikanService;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    private enum UserState {
        MAIN_MENU,
        SEARCH_CATEGORY,
        SEARCH_QUERY,
        TOP_CATEGORY,
        TOP_ANIME_TYPE,
        TOP_ANIME_FILTER,
        TOP_ANIME_RATING,
        TOP_ANIME_SFW,
        TOP_ANIME_LIMIT,
        TOP_MANGA_TYPE,
        TOP_MANGA_FILTER,
        TOP_MANGA_LIMIT,
        TOP_CHARACTERS_LIMIT,
        RANDOM_CATEGORY
    }

    private UserState currentState = UserState.MAIN_MENU;
    private String currentCategory = "";
    private String currentTopCategory = "";
    private String currentType = "";
    private String currentFilter = "";
    private String currentRating = "";
    private boolean currentSfw = true;
    private int currentLimit = 10;

    public AnimeBot(JikanService jikanService) {
        this.jikanService = jikanService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String text = message.getText();
            long chatId = message.getChatId();

            switch (currentState) {
                case MAIN_MENU:
                    handleMainMenu(chatId, text);
                    break;
                case SEARCH_CATEGORY:
                    handleSearchCategory(chatId, text);
                    break;
                case SEARCH_QUERY:
                    handleSearchQuery(chatId, text);
                    break;
                case TOP_CATEGORY:
                    handleTopCategory(chatId, text);
                    break;
                case TOP_ANIME_TYPE:
                    handleTopAnimeType(chatId, text);
                    break;
                case TOP_ANIME_FILTER:
                    handleTopAnimeFilter(chatId, text);
                    break;
                case TOP_ANIME_RATING:
                    handleTopAnimeRating(chatId, text);
                    break;
                case TOP_ANIME_SFW:
                    handleTopAnimeSfw(chatId, text);
                    break;
                case TOP_ANIME_LIMIT:
                    handleTopAnimeLimit(chatId, text);
                    break;
                case TOP_MANGA_TYPE:
                    handleTopMangaType(chatId, text);
                    break;
                case TOP_MANGA_FILTER:
                    handleTopMangaFilter(chatId, text);
                    break;
                case TOP_MANGA_LIMIT:
                    handleTopMangaLimit(chatId, text);
                    break;
                case TOP_CHARACTERS_LIMIT:
                    handleTopCharactersLimit(chatId, text);
                    break;
                case RANDOM_CATEGORY:
                    handleRandomCategory(chatId, text);
                    break;
            }
        }
    }

    private void handleMainMenu(long chatId, String text) {
        if (text.equals("/start") || text.equals("Главное меню")) {
            showMainMenu(chatId);
        } else if (text.equals("Поиск")) {
            currentState = UserState.SEARCH_CATEGORY;
            showSearchCategoryMenu(chatId);
        } else if (text.equals("Топ")) {
            currentState = UserState.TOP_CATEGORY;
            showTopCategoryMenu(chatId);
        } else if (text.equals("Случайное")) {
            // Временное сообщение
            sendMessage(chatId, "Функция в разработке. Следите за обновлениями!");
        } else {
            sendMessage(chatId, "Используйте кнопки для навигации.");
        }
    }

    private void handleSearchCategory(long chatId, String text) {
        if (text.equals("Аниме") || text.equals("Манга") || text.equals("Персонаж")) {
            currentCategory = text;
            currentState = UserState.SEARCH_QUERY;
            sendMessage(chatId, "Введите название для поиска:");
        } else if (text.equals("Назад")) {
            currentState = UserState.MAIN_MENU;
            showMainMenu(chatId);
        } else {
            sendMessage(chatId, "Пожалуйста, выберите категорию из меню.");
        }
    }

    private void handleSearchQuery(long chatId, String text) {
        switch (currentCategory) {
            case "Аниме":
                Anime anime = jikanService.searchAnime(text);
                if (anime != null) {
                    sendMessage(chatId, formatAnimeResponse(anime));
                } else {
                    sendMessage(chatId, "Аниме не найдено. Попробуйте другой запрос.");
                }
                break;
            case "Манга":
                Manga manga = jikanService.searchManga(text);
                if (manga != null) {
                    sendMessage(chatId, formatMangaResponse(manga));
                } else {
                    sendMessage(chatId, "Манга не найдена. Попробуйте другой запрос.");
                }
                break;
            case "Персонаж":
                Character character = jikanService.searchCharacter(text);
                if (character != null) {
                    sendMessage(chatId, formatCharacterResponse(character));
                } else {
                    sendMessage(chatId, "Персонаж не найден. Попробуйте другой запрос.");
                }
                break;
        }
        currentState = UserState.MAIN_MENU;
        showMainMenu(chatId);
    }

    private void handleTopCategory(long chatId, String text) {
        if (text.equals("Аниме") || text.equals("Манга") || text.equals("Персонаж")) {
            currentTopCategory = text;
            switch (text) {
                case "Аниме":
                    currentState = UserState.TOP_ANIME_TYPE;
                    showTopAnimeTypeMenu(chatId);
                    break;
                case "Манга":
                    currentState = UserState.TOP_MANGA_TYPE;
                    showTopMangaTypeMenu(chatId);
                    break;
                case "Персонаж":
                    currentState = UserState.TOP_CHARACTERS_LIMIT;
                    sendMessage(chatId, "Введите количество персонажей (например, 10):");
                    break;
            }
        } else if (text.equals("Назад")) {
            currentState = UserState.MAIN_MENU;
            showMainMenu(chatId);
        } else {
            sendMessage(chatId, "Пожалуйста, выберите категорию из меню.");
        }
    }

    private void handleTopAnimeType(long chatId, String text) {
        currentType = text;
        currentState = UserState.TOP_ANIME_FILTER;
        showTopAnimeFilterMenu(chatId);
    }

    private void handleTopAnimeFilter(long chatId, String text) {
        currentFilter = text;
        currentState = UserState.TOP_ANIME_RATING;
        showTopAnimeRatingMenu(chatId);
    }

    private void handleTopAnimeRating(long chatId, String text) {
        currentRating = text;
        currentState = UserState.TOP_ANIME_SFW;
        showTopAnimeSfwMenu(chatId);
    }

    private void handleTopAnimeSfw(long chatId, String text) {
        currentSfw = text.equalsIgnoreCase("Да");
        currentState = UserState.TOP_ANIME_LIMIT;
        sendMessage(chatId, "Введите количество аниме (например, 10):");
    }

    private void handleTopAnimeLimit(long chatId, String text) {
        try {
            currentLimit = Integer.parseInt(text);
            List<Anime> animeList = jikanService.getTopAnime(currentType, currentFilter, currentRating, currentSfw, 1, currentLimit);

            if (animeList != null && !animeList.isEmpty()) {
                StringBuilder response = new StringBuilder("Топ аниме:\n");
                for (Anime anime : animeList) {
                    response.append(formatAnimeResponse(anime)).append("\n\n");
                }
                sendMessage(chatId, response.toString());
            } else {
                sendMessage(chatId, "Аниме не найдены.");
            }
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Пожалуйста, введите число.");
        }
        currentState = UserState.MAIN_MENU;
        showMainMenu(chatId);
    }

    private void handleTopMangaType(long chatId, String text) {
        currentType = text;
        currentState = UserState.TOP_MANGA_FILTER;
        showTopMangaFilterMenu(chatId);
    }

    private void handleTopMangaFilter(long chatId, String text) {
        currentFilter = text;
        currentState = UserState.TOP_MANGA_LIMIT;
        sendMessage(chatId, "Введите количество манги (например, 10):");
    }

    private void handleTopMangaLimit(long chatId, String text) {
        try {
            currentLimit = Integer.parseInt(text);
            List<Manga> mangaList = jikanService.getTopManga(currentType, currentFilter, 1, currentLimit);

            if (mangaList != null && !mangaList.isEmpty()) {
                StringBuilder response = new StringBuilder("Топ манги:\n");
                for (Manga manga : mangaList) {
                    response.append(formatMangaResponse(manga)).append("\n\n");
                }
                sendMessage(chatId, response.toString());
            } else {
                sendMessage(chatId, "Манга не найдена.");
            }
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Пожалуйста, введите число.");
        }
        currentState = UserState.MAIN_MENU;
        showMainMenu(chatId);
    }

    private void handleTopCharactersLimit(long chatId, String text) {
        try {
            currentLimit = Integer.parseInt(text);
            List<Character> characters = jikanService.getTopCharacters(1, currentLimit);

            if (characters != null && !characters.isEmpty()) {
                StringBuilder response = new StringBuilder("Топ персонажей:\n");
                for (Character character : characters) {
                    response.append(formatCharacterResponse(character)).append("\n\n");
                }
                sendMessage(chatId, response.toString());
            } else {
                sendMessage(chatId, "Персонажи не найдены.");
            }
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Пожалуйста, введите число.");
        }
        currentState = UserState.MAIN_MENU;
        showMainMenu(chatId);
    }

    private void handleRandomCategory(long chatId, String text) {
        if (text.equals("Аниме") || text.equals("Манга") || text.equals("Персонаж")) {
            switch (text) {
                case "Аниме":
                    Anime anime = jikanService.getRandomAnime();
                    if (anime != null) {
                        sendMessage(chatId, formatAnimeResponse(anime));
                    } else {
                        sendMessage(chatId, "Случайное аниме не найдено.");
                    }
                    break;
                case "Манга":
                    Manga manga = jikanService.getRandomManga();
                    if (manga != null) {
                        sendMessage(chatId, formatMangaResponse(manga));
                    } else {
                        sendMessage(chatId, "Случайная манга не найдена.");
                    }
                    break;
                case "Персонаж":
                    Character character = jikanService.getRandomCharacter();
                    if (character != null) {
                        sendMessage(chatId, formatCharacterResponse(character));
                    } else {
                        sendMessage(chatId, "Случайный персонаж не найден.");
                    }
                    break;
            }
            currentState = UserState.MAIN_MENU;
            showMainMenu(chatId);
        } else if (text.equals("Назад")) {
            currentState = UserState.MAIN_MENU;
            showMainMenu(chatId);
        } else {
            sendMessage(chatId, "Пожалуйста, выберите категорию из меню.");
        }
    }

    private String formatAnimeResponse(Anime anime) {
        return String.format(
                "🎬 *Название:* %s\n" +
                "🌐 *Английское название:* %s\n" +
                "📺 *Тип:* %s\n" +
                "📊 *Эпизоды:* %d\n" +
                "⭐ *Рейтинг:* %.2f\n" +
                "📅 *Статус:* %s\n" +
                "📖 *Описание:* %s\n" +
                "🔗 *Ссылка:* [Перейти](" + anime.getUrl() + ")",
                anime.getTitle(),
                anime.getTitleEnglish(),
                anime.getType(),
                anime.getEpisodes(),
                anime.getScore(),
                anime.getStatus(),
                anime.getSynopsis()
        );
    }

    private String formatMangaResponse(Manga manga) {
        return String.format(
                "📚 *Название:* %s\n" +
                "🌐 *Английское название:* %s\n" +
                "📖 *Тип:* %s\n" +
                "📊 *Главы:* %d\n" +
                "⭐ *Рейтинг:* %.2f\n" +
                "📅 *Статус:* %s\n" +
                "📖 *Описание:* %s\n" +
                "🔗 *Ссылка:* [Перейти](" + manga.getUrl() + ")",
                manga.getTitle(),
                manga.getTitleEnglish(),
                manga.getType(),
                manga.getChapters(),
                manga.getScore(),
                manga.getStatus(),
                manga.getSynopsis()
        );
    }

    private String formatCharacterResponse(Character character) {
        return String.format(
                "👤 *Имя:* %s\n" +
                "🎭 *Альтернативные имена:* %s\n" +
                "📖 *Описание:* %s\n" +
                "🔗 *Ссылка:* [Перейти](" + character.getUrl() + ")",
                character.getName(),
                String.join(", ", character.getNicknames()),
                character.getAbout()
        );
    }

    private void showMainMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите действие:");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Поиск");
        row.add("Топ");
        row.add("Случайное");
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void showSearchCategoryMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите категорию для поиска:");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Аниме");
        row.add("Манга");
        row.add("Персонаж");
        keyboard.add(row);

        KeyboardRow backRow = new KeyboardRow();
        backRow.add("Назад");
        keyboard.add(backRow);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void showTopCategoryMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите категорию для топа:");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Аниме");
        row.add("Манга");
        row.add("Персонаж");
        keyboard.add(row);

        KeyboardRow backRow = new KeyboardRow();
        backRow.add("Назад");
        keyboard.add(backRow);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void showTopAnimeTypeMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите тип аниме:");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add("tv");
        row1.add("movie");
        row1.add("ova");
        keyboard.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add("special");
        row2.add("ona");
        row2.add("music");
        keyboard.add(row2);

        KeyboardRow row3 = new KeyboardRow();
        row3.add("cm");
        row3.add("pv");
        row3.add("tv_special");
        keyboard.add(row3);

        KeyboardRow backRow = new KeyboardRow();
        backRow.add("Назад");
        keyboard.add(backRow);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void showTopAnimeFilterMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите фильтр:");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("airing");
        row.add("upcoming");
        row.add("bypopularity");
        row.add("favorite");
        keyboard.add(row);

        KeyboardRow backRow = new KeyboardRow();
        backRow.add("Назад");
        keyboard.add(backRow);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void showTopAnimeRatingMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите рейтинг:");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add("g");
        row1.add("pg");
        row1.add("pg13");
        keyboard.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add("r17");
        row2.add("r");
        row2.add("rx");
        keyboard.add(row2);

        KeyboardRow backRow = new KeyboardRow();
        backRow.add("Назад");
        keyboard.add(backRow);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void showTopAnimeSfwMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Исключить взрослый контент?");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Да");
        row.add("Нет");
        keyboard.add(row);

        KeyboardRow backRow = new KeyboardRow();
        backRow.add("Назад");
        keyboard.add(backRow);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void showTopMangaTypeMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите тип манги:");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add("manga");
        row1.add("novel");
        row1.add("lightnovel");
        keyboard.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add("oneshot");
        row2.add("doujin");
        row2.add("manhwa");
        row2.add("manhua");
        keyboard.add(row2);

        KeyboardRow backRow = new KeyboardRow();
        backRow.add("Назад");
        keyboard.add(backRow);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void showTopMangaFilterMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите фильтр:");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("publishing");
        row.add("upcoming");
        row.add("bypopularity");
        row.add("favorite");
        keyboard.add(row);

        KeyboardRow backRow = new KeyboardRow();
        backRow.add("Назад");
        keyboard.add(backRow);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void showRandomCategoryMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите категорию для случайного:");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Аниме");
        row.add("Манга");
        row.add("Персонаж");
        keyboard.add(row);

        KeyboardRow backRow = new KeyboardRow();
        backRow.add("Назад");
        keyboard.add(backRow);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.enableMarkdown(true); // Включаем Markdown для форматирования
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}