package com.company.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@Resource(name="jdbc/mydb", type=javax.sql.DataSource.class, authenticationType=AuthenticationType.CONTAINER)
@WebServlet("/jndi/*")
@SuppressWarnings("serial")
public class JndiDumpServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        
        PrintWriter out = resp.getWriter();

        try
        {
            InitialContext ctx = new InitialContext();
            out.printf("InitialContext: %s%n",ctx);

            Integer age = (Integer)ctx.lookup("cfg/age");
            out.printf("Integer(age): %s%n",age);

            DateFormat fmt = (DateFormat)ctx.lookup("fmt/date");
            if (fmt != null)
            {
                out.printf("DateFormat: %s%n",fmt.getClass().getName());
                out.printf("DateFormat.format(now) = %s%n",fmt.format(new Date()));
            } else {
                out.printf("DateFormat: <null>%n");
            }

            DataSource db = (DataSource)ctx.lookup("java:comp/env/jdbc/mydb");
            out.printf("DataSource: %s%n",db);
            if (db != null)
            {
                try (Connection conn = db.getConnection())
                {
                    out.printf("DataSource.Connection = %s%n",conn);
                    out.printf("Connection.MetaData = %s%n",conn.getMetaData());
                }
                catch (SQLException e)
                {
                    out.println();
                    e.printStackTrace(out);
                }
            }
        }
        catch (NamingException e)
        {
            out.println();
            e.printStackTrace(out);
        }
    }
}
