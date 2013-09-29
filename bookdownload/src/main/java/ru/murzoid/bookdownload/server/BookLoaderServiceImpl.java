package ru.murzoid.bookdownload.server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ru.murzoid.bookdownload.client.GreetingLoaderService;
import ru.murzoid.bookdownload.server.exceptions.BooksNotFoundException;
import ru.murzoid.bookdownload.server.exceptions.FirstAnalyzeIsRunnigException;
import ru.murzoid.bookdownload.server.util.LookupAPI;
import ru.murzoid.bookdownload.server.util.LookupConstants;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class BookLoaderServiceImpl extends RemoteServiceServlet implements
		GreetingLoaderService {

	private static final String SECOND_PART_DOWNLOAD_IMAGE = "\" target=\"_blank\"><img src=\"images/download-now.jpg\" border=\"0\"></a></strong></p>";
	private static final String FIRST_PART_DOWNLOAD_IMAGE = "<p><strong><a href=\"";
	private static final String PLEASE_DOWNLOAD = "ваша книга доступна по следующей ссылке в течении часа:<br>";
	private static final String BOOK_NOT_FOUND_IMAGE = "<p><strong><img src=\"images/error.jpg\" border=\"0\"></a></strong></p>";
	private static final String SORRY_IMAGE = "<p><strong><img src=\"images/sorry.jpg\" border=\"0\"></a></strong></p>";
	private static final String SORRY_FIRST_RUNNING_NOT_FINISHED = "Приносим свои извенения.<br>Первый анализ архивов еще не завершился, пожалуйста повторите попытку через несколько минут.";
	private static final String BOOK_NOT_FOUND = "Такой книги в архивах не обнаружено.";
	public static Logger log = LogManager.getLogger(BookLoaderServiceImpl.class);
	
	public String greetServer(String input) throws IllegalArgumentException {
		log.debug("start method greet server");
//		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);
		final String ip = getThreadLocalRequest().getRemoteAddr();
		String response="<br><b>Ответ от сервера:</b><br>Здорово!<br><br>Похоже вы используете:<br>" + userAgent +
		"<br><br>Ваш ip:<br>" + ip;
		log.info("request from ip="+ip+"");
		response+=getBook(input);
		log.debug("end method greet server");
		return response;
	}

	private String getBook(String input) {
		log.info("get book with name "+input + " start...");
		String text="<br><br>";
		String url="извиняемся произошла ошибка";
		try {
			BookFinder bookHelper=(BookFinder) LookupAPI.lookup(LookupConstants.BOOK_HELPER_JNDI_NAME);
			url=bookHelper.findBook(input);
		} catch (FirstAnalyzeIsRunnigException e) {
			text+=SORRY_FIRST_RUNNING_NOT_FINISHED;
			text+=SORRY_IMAGE;
			return text;
		} catch (BooksNotFoundException e){
			text+=BOOK_NOT_FOUND;
			text+=BOOK_NOT_FOUND_IMAGE;
			return text;
		} catch (RuntimeException e) {
			log.error("Error occuring while we got BookHelper instance");
		}
		text+=PLEASE_DOWNLOAD +
		FIRST_PART_DOWNLOAD_IMAGE+url+SECOND_PART_DOWNLOAD_IMAGE;
		log.info("get book with name "+input +" end");
		return text;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
