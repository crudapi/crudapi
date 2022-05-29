package cn.crudapi.api.config;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HeadRequestWrapper extends HttpServletRequestWrapper {
    private Map<String, String> headers;
    
    HeadRequestWrapper(HttpServletRequest request) {
        super(request);
        this.headers = new HashMap<>();
    }

    @Override
    public String getHeader(String name) {
        String headervalue = super.getHeader(name);
        if (headers.containsKey(name)) {
            headervalue = headers.get(name);
        }
        return headervalue;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> values = Collections.list(super.getHeaderNames());
        for (String value : headers.keySet()) {
            values.add(value);
        }
        return Collections.enumeration(values);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = Collections.list(super.getHeaders(name));
        if (headers.containsKey(name)) {
            values = Arrays.asList(headers.get(name));
        }
        return Collections.enumeration(values);
    }

    public void addHead(String name, String value) {
        this.headers.put(name, value);
    }
}