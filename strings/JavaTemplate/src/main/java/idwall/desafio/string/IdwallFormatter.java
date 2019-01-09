package idwall.desafio.string;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public class IdwallFormatter extends StringFormatter {

	/**
	 * Should format as described in the challenge
	 *
	 * @param text
	 * @return
	 */
	
	private String endOfLine = System.lineSeparator();

	@Override
	public String format(String text, Integer limit, boolean justify) {

		String newText = "";
		Integer lastIndex = 0;
		Integer beginIndex = 0;
		String lineBreaker = endOfLine;
		String line;

		while (text.length() != lastIndex) {

			lastIndex += limit;

			if (lastIndex > text.length()) {
				lastIndex = text.length();
			}
				
			if(lastIndex != text.length()) {					
				int hasEOL = text.substring(beginIndex, lastIndex).trim().lastIndexOf("\n");
				
				if (0 <= hasEOL) {
					lastIndex = text.lastIndexOf("\n", lastIndex);
					lineBreaker = endOfLine + endOfLine;
				} else {
					lastIndex = text.lastIndexOf(" ", lastIndex);
					lineBreaker = endOfLine;
				}
			}
			
			line = text.substring(beginIndex, lastIndex).trim();

			if(justify) {
				line = justifyLine(line, limit) + lineBreaker;
			}

			newText += line;
			beginIndex = lastIndex;
		}

		return newText;
	}
	
	private String justifyLine(String line, Integer limit) {
		
		String justifiedLine = "";
		
		List<String> lineArray = Arrays.asList(line.split(" "));
		int spacesToLimit = limit - line.length();		
		int gapsInLine = lineArray.size()-1;
		
		if(line.isEmpty() || spacesToLimit == 0 || gapsInLine == 0) {
			return line;
		}
		
		for(int i = 0; i < lineArray.size(); i++) {
			justifiedLine +=  lineArray.get(i);
			
			if(!lineArray.get(i).endsWith(endOfLine)) {
				justifiedLine += " "; 
				for(int j = 0; j < spacesToLimit/gapsInLine; j++) {
					justifiedLine += " "; 
				}
				if(i < spacesToLimit%gapsInLine) {
						justifiedLine += " "; 
				}
			}
		}
		
		return justifiedLine.trim();
	}
}
