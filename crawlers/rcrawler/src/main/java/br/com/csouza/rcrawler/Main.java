package br.com.csouza.rcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	
	private static final String DEFAULT_SUBREDDITS = "askreddit;worldnews;cats";
	private static final Integer DEFAULT_MINIMUMLIKES = 5000;
	
    public static void main( String[] args ) throws IOException
    {
    	List<String> subredditsString = Arrays.asList(DEFAULT_SUBREDDITS.split(";"));
    	Integer minimumLikes = DEFAULT_MINIMUMLIKES;
    	
    	switch (args.length) {
	        case 1:
	        	subredditsString = Arrays.asList(args[0].split(";"));
	            break;
	        case 2:
	        	subredditsString = Arrays.asList(args[0].split(";"));
	        	minimumLikes = Integer.parseInt(args[1]);
                break;        
    	}
        
        List<Subreddit> subreddits = new ArrayList<Subreddit>();
        
        for (String subredditString : subredditsString) {
        	Subreddit subreddit = new Subreddit(subredditString);
        	subreddit.setPosts(minimumLikes);
        	subreddits.add(subreddit);       	
		}
        
        for (Subreddit subreddit : subreddits) {
			String trending = subreddit.trending();
			System.out.println(trending);
		}
    }
}
