package br.com.csouza.rcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Subreddit {
	
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36";
	
	private String subreddit;
	private Document doc;
	private List<Post> posts;
	
	public Subreddit(String subreddit) throws IOException {
		this.subreddit = subreddit;
		setDoc();
	}

	public String getSubreddit() {
		return this.subreddit;
	}
	
	public void setDoc() throws IOException{
			this.doc = Jsoup.connect("https://old.reddit.com/r/" + this.subreddit)
					.userAgent(USER_AGENT)
					.timeout(12000) 
			        .get();
	}

	public List<Post> getPosts() {
		return this.posts;
	}
	
	public void setPosts(Integer minimumLikes) {
		this.posts = getPostsWithMinimumLikes(minimumLikes);		
	}
	
	private List<Post> getPostsWithMinimumLikes(Integer minimumLikes){
		
		List<Post> posts = new ArrayList<Post>();
		Elements postsElements = this.doc.select("div.thing");
				
		for (Element postElement : postsElements) {
        	String likesString = postElement.select("div.score.unvoted").attr("title");
        	if(!likesString.isEmpty()) {
        		Integer likes = Integer.parseInt(likesString);
        		if(likes > minimumLikes) {
        			Post post = new Post();
        			post.setTitle(postElement.select("a.title.may-blank").text());
        			post.setLikes(likes);
        			post.setSubReddit(postElement.attr("data-subreddit-prefixed"));
        			post.setComments(postElement.select("a.comments.bylink.may-blank").attr("href"));
        			post.setThread(postElement.select("a.title.may-blank").first().absUrl("href"));
        			
        			posts.add(post);
        		}        		
        	}
        }
		
		return posts;
	}	
}
