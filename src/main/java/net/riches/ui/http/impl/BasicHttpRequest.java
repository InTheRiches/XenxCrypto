package net.riches.ui.http.impl;

import net.riches.ui.http.HttpMethod;
import net.riches.ui.http.HttpRequest;

public class BasicHttpRequest extends BasicHttpMessage implements HttpRequest
{
	HttpMethod method;
	String requestUri;

	@Override
	public HttpMethod getHttpMethod()
	{
		return method;
	}

	@Override
	public String getRequestUri()
	{
		return requestUri;
	}

	public HttpMethod getMethod()
	{
		return method;
	}

	public void setMethod(HttpMethod method)
	{
		this.method = method;
	}

	public void setRequestUri(String requestUri)
	{
		this.requestUri = requestUri;
	}
	
	
	
	

}
