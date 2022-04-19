package net.riches.web.http.impl;

import net.riches.web.http.HttpResponse;
import net.riches.web.http.HttpStatusCode;

public class BasicHttpResponse extends BasicHttpMessage implements HttpResponse
{
	HttpStatusCode statusCode;

	@Override
	public HttpStatusCode getStatusCode()
	{
		return statusCode;
	}

	public void setStatusCode(HttpStatusCode statusCode)
	{
		this.statusCode = statusCode;
	}
	
	
}
