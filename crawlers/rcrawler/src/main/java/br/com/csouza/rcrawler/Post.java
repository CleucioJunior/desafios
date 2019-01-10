package br.com.csouza.rcrawler;

public class Post {

	private String subReddit; 
	private Integer likes;
	private String title;
	private String comments;
	private String thread;
	
	
	public void setSubReddit(String subReddit) {
		this.subReddit = subReddit;
	}
	public void setLikes(Integer likes) {
		this.likes = likes;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	
	@Override
	public String toString() {
		return "Upvote: " + likes + System.lineSeparator() +
				"Subreddit: " + subReddit + System.lineSeparator() +
				"Título: " + title + System.lineSeparator() +
				"Link para comentários: " + comments + System.lineSeparator() +
				"Link para a thread: " + thread + System.lineSeparator() +
				"___________________________________________________________________________________________________________________________" + System.lineSeparator();
	}
}
