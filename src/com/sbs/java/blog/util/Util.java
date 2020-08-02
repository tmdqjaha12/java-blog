package com.sbs.java.blog.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.Map;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Util {
	public static boolean empty(HttpServletRequest req, String paramName) {
		String paramValue = req.getParameter(paramName);

		return empty(paramValue);
	}

	public static boolean empty(Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj instanceof String) {
			return ((String) obj).trim().length() == 0;
		}

		return true;
	}

	public static boolean isNum(HttpServletRequest req, String paramName) {
		String paramValue = req.getParameter(paramName);

		return isNum(paramValue);
	}

	public static boolean isNum(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof Long) {
			return true;
		} else if (obj instanceof Integer) {
			return true;
		} else if (obj instanceof String) {
			try {
				Integer.parseInt((String) obj);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		return false;
	}

	public static int getInt(HttpServletRequest req, String paramName) {
		return Integer.parseInt(req.getParameter(paramName));
	}

	public static void printEx(String errName, HttpServletResponse resp, Exception e) {
		try {
			resp.getWriter()
					.append("<h1 style='color:red; font-weight:bold; text-align:left;'>[에러 : " + errName + "]</h1>");

			resp.getWriter().append("<pre style='text-align:left; font-weight:bold; font-size:1.3rem;'>");
			e.printStackTrace(resp.getWriter());
			resp.getWriter().append("</pre>");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static String getString(HttpServletRequest req, String paramName) {
		return req.getParameter(paramName);
	}

	public static int sendMail(String smtpServerId, String smtpServerPw, String from, String fromName, String to,
			String title, String body) {
		System.out.println(smtpServerId);
		System.out.println(smtpServerPw);
		System.out.println(from);
		System.out.println(fromName);
		System.out.println(to);
		System.out.println(title);
		System.out.println(body);


		Properties prop = System.getProperties();
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", "587");

		Authenticator auth = new MailAuth(smtpServerId, smtpServerPw);

		Session session = Session.getDefaultInstance(prop, auth);

		MimeMessage msg = new MimeMessage(session);

		try {
			msg.setSentDate(new Date());

			msg.setFrom(new InternetAddress(from, fromName));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(title, "UTF-8");
			msg.setContent(body, "text/html; charset=UTF-8");

			Transport.send(msg);

		} catch (AddressException ae) {
			System.out.println("AddressException : " + ae.getMessage());
			return -1;
		} catch (MessagingException me) {
			System.out.println("MessagingException : " + me.getMessage());
			return -2;
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException : " + e.getMessage());
			return -3;
		}

		return 1;
	}

	public static String getRandomPassword(int length) {
		char[] charaters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuilder sb = new StringBuilder("");
		Random rn = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(charaters[rn.nextInt(charaters.length)]);
		}
		return sb.toString();
	}

	public static String encryptSHA256(String str) {
		String sha = "";
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			sha = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("Encrypt Error - NoSuchAlgorithmException");
			sha = null;
		}
		return sha;
	}
	
//	public static String getHtmlFormEmail(String msg1, String msg2) {
//		StringBuilder sb = new StringBuilder();
//		
//		sb.append("<html>");
//		sb.append("<head>");
//		sb.append("<script>");
//		sb.append("history.replaceState({}, null, location.pathname);");
//		sb.append("</script>");
//		sb.append("</head>");
//		sb.append("<body>");
//		sb.append("<table border=\"1\">");
//		sb.append("<tbody>");
//		sb.append("<tr>");
//		sb.append("<th>");
//		sb.append("<h3>인증번호 : ");
//		sb.append(msg2);
//		sb.append("</h3>");
//		sb.append("</th>");
//		sb.append("<td>");
//		sb.append(msg1);
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("</tbody>");
//		sb.append("</table>");
//		sb.append("</body>");
//		sb.append("</html>");
//
//		return sb.toString();
//	}
	

//		로그인 주소관련
	public static String getUrlEncoded(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	public static String getString(HttpServletRequest req, String paramName, String elseValue) {
		if (req.getParameter(paramName) == null) {
			return elseValue;
		}

		if (req.getParameter(paramName).trim().length() == 0) {
			return elseValue;
		}

		return getString(req, paramName);
//		로그인 주소관련
	}
	
	public static boolean isSuccess(Map<String, Object> rs) {
		return ((String) rs.get("resultCode")).startsWith("S-");
	}

	public static String getNewUrlRemoved(String url, String paramName) {
		String deleteStrStarts = paramName + "=";
		int delStartPos = url.indexOf(deleteStrStarts);

		if (delStartPos != -1) {
			int delEndPos = url.indexOf("&", delStartPos);

			if (delEndPos != -1) {
				delEndPos++;
				url = url.substring(0, delStartPos) + url.substring(delEndPos, url.length());
			} else {
				url = url.substring(0, delStartPos);
			}
		}

		if (url.charAt(url.length() - 1) == '?') {
			url = url.substring(0, url.length() - 1);
		}

		if (url.charAt(url.length() - 1) == '&') {
			url = url.substring(0, url.length() - 1);
		}

		return url;
	}

	public static String getNewUrl(String url, String paramName, String paramValue) {
		url = getNewUrlRemoved(url, paramName);

		if (url.contains("?")) {
			url += "&" + paramName + "=" + paramValue;
		} else {
			url += "?" + paramName + "=" + paramValue;
		}

		url = url.replace("?&", "?");

		return url;
	}

	public static String getNewUrlAndEncoded(String url, String paramName, String pramValue) {
		return getUrlEncoded(getNewUrl(url, paramName, pramValue));
	}
	
	public static String getRegeDateNow() {
		long systemTime = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		String currentTime = formatter.format(systemTime);
		return currentTime;
	}

	public static long getCalRegDate(String regDate, String nowRegDate) {
		String date1 = regDate;
	    String date2 = nowRegDate;
	    long calDateDays = 0;
	 
	    try{ // String Type을 Date Type으로 캐스팅하면서 생기는 예외로 인해 여기서 예외처리 해주지 않으면 컴파일러에서 에러가 발생해서 컴파일을 할 수 없다.
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        // date1, date2 두 날짜를 parse()를 통해 Date형으로 변환.
	        Date FirstDate = format.parse(date1);
	        Date SecondDate = format.parse(date2);
	        
	        // Date로 변환된 두 날짜를 계산한 뒤 그 리턴값으로 long type 변수를 초기화 하고 있다.
	        // 연산결과 -950400000. long type 으로 return 된다.
	        long calDate = FirstDate.getTime() - SecondDate.getTime(); 
	        
	        // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다. 
	        // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
	        calDateDays = calDate / ( 24*60*60*1000); 
	 
	        calDateDays = Math.abs(calDateDays);
	        
//	        System.out.println("두 날짜의 날짜 차이: "+calDateDays);
	        }
	        catch(ParseException e)
	        {
	            // 예외 처리
	        }

		return calDateDays;
	}
}