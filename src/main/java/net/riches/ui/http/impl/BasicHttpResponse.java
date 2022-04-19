package net.riches.ui.http.impl;

import net.riches.ui.http.HttpResponse;
import net.riches.ui.http.HttpStatusCode;

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
