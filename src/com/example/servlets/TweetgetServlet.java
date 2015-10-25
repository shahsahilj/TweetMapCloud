package com.example.servlets;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Servlet implementation class TweetgetServlet
 */
@WebServlet("/TweetgetServlet")
public class TweetgetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static int dbcount=0;
    static double[] latitude=new double[10];
    static double[] longitude=new double[10];
    static String[] handle=new String[10];
    static String[] text=new String[10];
    static int[] news=new int[10];
    static int[] music=new int[10];
    static int[] sports=new int[10];
    static int[] personal=new int[10];
    static String[] newsHandles = {"mashable","narendramodi","barackobama","cnn_brk","big_picture","theonion","time","breakingnews","bbcbreaking","harvardbiz","dalailama","bbcworld","gizmodo","techcrunch","wired","wsj","rollingstonne","whitehouse","cnn","peoplemag","pmoindia","natgeosociety","nytimes","foxnews","lifehacker","slate","theeconomist","reuters","usatoday","empiremagazine","cbsnews","cnnmoney","washingtonpost","cnnlive","yahoonews","guardinanews","newyorkpost","timesnow","ndtv","etmarkets","japantimes","tokyotimes","chinadailyusa","brazilgovnews","brazilnewsnet","telegraph"};
	static String[] sportsHandles={"lsubaseball","diamondheels","mstateBB","fsu_baseball","aggie_baseball","texas_baseball","ou_baseball","ukbaseball","unc_baasketball","umichbball","kentuckymbb","illinihoops","iowahoops","alabamahoops","umichfootball","gataorzonefb","vt_football","badgerfootball","stanfordtennis","ugatennis","uclatennis","fsu_mtennis","illinitennis","gztrackfield","kutarck","run4okstate","uwtrack","yalecrew","uvarowing","texasrowing","espn","sachin_rt","sportscenter","kaka","christiano","neymarjr","msdhoni","realmadrid","nba","fcbarcelona","nfl","waynerooney","10ronaldinho","3gerardpique","mesutozil1088","twittersports","premierleague","championsleague","falcao","imvkohli","kobebryant","persie_official","fifacom","davidluiz_4"};
    static String[] musicHandles= {"katyperry","justinbieber","taylorswift13","youtube","rihanna","ladygaga","jtimberlake","britneyspears","shakira","selenagomez","","jlo","ddlovato","pink","harry_styles","brunomars","officialAdele","mileycyrus","aliciakeys","pitbull","nickiminajdavidguetta","coldplay","mariahcarey","zaynmalik","edsheeran","mtv","snoopdog","maroon5","priyankachopra","zacefron","carlyraejespen","rustyrockets","twittermusic","enrique305"};
	static String[] newswords={"just in","breaking","explosion","killing","died","dead"};
	static String[] sportswords={"game","match","cricket","soccer","football","tennis","rugby","chess"};
	static String[] musicwords={"song","songs","musical","singer","lyrics","melody"};
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TweetgetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("")
          .setOAuthConsumerSecret("")
          .setOAuthAccessToken("")
          .setOAuthAccessTokenSecret("");
        
       TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
       StatusListener listener = new StatusListener() {
           @Override
           public void onStatus(Status status) {
           	if(status.getGeoLocation()!=null){
           		text[dbcount]=status.getText().replaceAll("'", "").replaceAll("\n", " ");
           		handle[dbcount]=status.getUser().getScreenName().toLowerCase();
           		latitude[dbcount]=status.getGeoLocation().getLatitude();
           		longitude[dbcount]=status.getGeoLocation().getLongitude();
           		music[dbcount]=news[dbcount]=sports[dbcount]=personal[dbcount]=0;
           		String temp="";
           		temp=text[dbcount].toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");		
           		if(Arrays.asList(newsHandles).contains(handle[dbcount])||handle[dbcount].contains("weather")||handle[dbcount].contains("news")||handle[dbcount].contains("breaking")||handle[dbcount].contains("times")){
           			news[dbcount]=1;
           		}
           		for (int i = 0; i < newswords.length; i++) {
           			if(temp.contains(newswords[i])){
               			news[dbcount]=1;
               		}
					}
           		if(Arrays.asList(sportsHandles).contains(handle[dbcount])){
           			sports[dbcount]=1;
           		}
           		for (int i = 0; i < sportswords.length; i++) {
           			if(temp.contains(sportswords[i])){
               			sports[dbcount]=1;
               		}
					}
           		if(Arrays.asList(musicHandles).contains(handle[dbcount])){
           			music[dbcount]=1;
           		}
           		for (int i = 0; i < musicwords.length; i++) {
           			if(temp.contains(musicwords[i])){
               			music[dbcount]=1;
               		}
					}
           		if(news[dbcount]==0&&sports[dbcount]==0&&music[dbcount]==0)
           				personal[dbcount]=1;
           		dbcount++;
           		if(dbcount==5){
           			try {
							data_store(handle,text,latitude,longitude,news,music,sports,personal);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
           			dbcount=0;
           		}
           	}
           }

			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub
				
			}

            
        };
        twitterStream.addListener(listener);
        twitterStream.sample();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	  public static void data_store(String[] handle,String[] text, double[] latitude, double[] longitude, int[] news, int[] music, int[] sports, int[] personal) throws ClassNotFoundException, SQLException{
	    	
	    	Class.forName("com.mysql.jdbc.Driver");
	    	String pwd="";
	        Connection con = DriverManager.getConnection("jdbc:mysql://tweetdb.cfkl2huwwkyh.us-east-1.rds.amazonaws.com:3306/tweettable","sduser",pwd);
	        Statement stmt = con.createStatement();
	        System.out.println("Created DB Connection....");
	        String query="";
	        for(int i=0;i<5;i++){
	        	query="INSERT INTO `tweetmap`.`tweets` (`handle`, `text`, `latitude`, `longitude`, `news`, `music`, `sports`, `personal`) VALUES ";
	            query+="('"+handle[i]+"', '"+text[i]+"', '"+Double.toString(latitude[i])+"', '"+Double.toString(longitude[i])+"', '"+Integer.toString(news[i])+"', '"+Integer.toString(music[i])+"', '"+Integer.toString(sports[i])+"', '"+Integer.toString(personal[i])+"')";
	            query+=";";
	            System.out.println(query);
	            try {
					stmt.executeUpdate(query);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("error");
					continue;
				}
	        }
	        
	        
	        
	    }

}
