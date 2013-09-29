package ru.murzoid.bookdownload.server.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

public class LookupAPI
{
    private static final Logger Log = Logger.getLogger(LookupAPI.class);

    public static Object lookup(String name)
    {
        try
        {
            Context ctx = new InitialContext();
            return ctx.lookup(name);
        }
        catch (NamingException e)
        {
            Log.error(e.getMessage() + " : " + name, e);
            throw new RuntimeException("NamingException : " + e.getMessage() + " : " + name);
        }
    }
}