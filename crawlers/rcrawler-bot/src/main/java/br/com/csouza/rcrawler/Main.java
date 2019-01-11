package br.com.csouza.rcrawler;

import java.io.IOException;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.logging.BotLogger;

public class Main {
	
    public static void main( String[] args ) throws IOException
    {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        
        try {
            botsApi.registerBot(new RCrawlerBot());
        } catch (TelegramApiRequestException e) {
            BotLogger.error("Algo deu errado enquanto registrava o bot.", e);
        }
    }
}
