package br.com.csouza.rcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;

public class RCrawlerBot extends AbilityBot {
	
	private static final Integer DEFAULT_MINIMUMLIKES = 5000;
	
	protected RCrawlerBot() {
		super("718079616:AAGNmWXj-09szmsaahVnrSLk3tEDnK4Yj10", "RCrawlerBot");
	}

	@Override
	public int creatorId() {
		return 123456789;
	}

	public Ability startCommand() {
		return Ability.builder()
				.name("start")
				.info("Descrição do RCrawlerBot")
				.input(0)
				.locality(Locality.USER)
				.privacy(Privacy.PUBLIC)
				.action(ctx -> silent.sendMd( 
						"Use esse formato para buscar os Trendings do Reddit:" + System.lineSeparator() +
						"/nadaparafazer subreddits"+ System.lineSeparator() + System.lineSeparator() +
						"Subreddits pode ser de 1 à 3 subreddits juntos separados por ;"+ System.lineSeparator() +
						"Exemplo: /nadaparafazer askreddit;worldnews;cats"
						, 
						ctx.chatId()))
				.build();
	}
	
	public Ability redditCrawler() {
		return Ability.builder()
				.name("nadaparafazer")
				.info("Busca os trendings do subreddit informado.")
				.input(1)
				.locality(Locality.USER)
				.privacy(Privacy.PUBLIC)
				.action(ctx -> {
					
					try {
						
						silent.execute(new SendChatAction(ctx.chatId(),"typing"));
						
						List<Subreddit> subreddits = listSubreddit(Arrays.asList(ctx.firstArg().split(";")));
						for (Subreddit subreddit : subreddits) {
							List<Post> posts = subreddit.getPosts();
							if(posts.isEmpty()) {
								silent.sendMd("*/r/"+subreddit.getSubreddit() + "* está sem trending no momento. Tente mais tarde =)", ctx.chatId());
							}
							String msg = "";
							int i = 0;
							for (Post post : posts) {
								msg += post.toString();
								i++;
								// Envio de 3 em 3 posts para não ter erro de mensagem muito longa
								if(i==3) {							
									silent.sendMd(msg, ctx.chatId());
									msg = "";
									i = 0;
								}
							}
							if(!msg.isEmpty()) {
								silent.sendMd(msg, ctx.chatId());
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				})
				.build();
	}

	private List<Subreddit> listSubreddit(List<String> subredditsString) throws IOException {
		
		List<Subreddit> subreddits = new ArrayList<Subreddit>();;
		
		for (String subredditString : subredditsString) {
        	Subreddit subreddit = new Subreddit(subredditString);
        	subreddit.setPosts(DEFAULT_MINIMUMLIKES);
			subreddits.add(subreddit);       	
		}
		return subreddits;
	}
}
