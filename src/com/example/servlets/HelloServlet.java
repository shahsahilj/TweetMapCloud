package com.example.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String pwd="";
	        Connection con = DriverManager.getConnection("jdbc:mysql://tweetdb.cfkl2huwwkyh.us-east-1.rds.amazonaws.com:3306/tweettable","sduser",pwd);
	        Statement stmt = con.createStatement();
	        String query="SELECT COUNT(*) FROM tweets";
	        ResultSet rs=stmt.executeQuery(query);
	        int number=0;
	        if(rs.next()){
	        	number=rs.getInt(1);
	        }
	        query="SELECT * FROM tweets";
	        rs=stmt.executeQuery(query);
	        double[] latitude=new double[number];
	        double[] longitude=new double[number];
	        String[] handle=new String[number];
	        String[] text=new String[number];
	        int[] news=new int[number];
	        int[] music=new int[number];
	        int[] sports=new int[number];
	        int[] personal=new int[number];
	        int i=0;
	        while(rs.next()){
	        	latitude[i]=rs.getDouble("latitude");
	        	longitude[i]=rs.getDouble("longitude");
	        	handle[i]=rs.getString("handle");
	        	text[i]=rs.getString("text");
	        	news[i]=rs.getInt("news");
	        	music[i]=rs.getInt("music");
	        	sports[i]=rs.getInt("sports");
	        	personal[i]=rs.getInt("personal");
	        	i++;
	        }
	        request.setAttribute("latitude",latitude);
	        request.setAttribute("longitude",longitude);
	        request.setAttribute("text", text);
	        request.setAttribute("handle",handle);
	        request.setAttribute("news",news);
	        request.setAttribute("sports",sports);
	        request.setAttribute("music",music);
	        request.setAttribute("personal",personal);
	        request.getRequestDispatcher("NewFile.jsp").forward(request, response);
		
	        
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
