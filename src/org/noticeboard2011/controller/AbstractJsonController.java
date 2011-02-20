package org.noticeboard2011.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import net.arnx.jsonic.JSON;

import org.slim3.controller.Controller;
import org.slim3.util.ThrowableUtil;

abstract public class AbstractJsonController extends Controller {
    
    protected void json(Object data) {
        String encoding = request.getCharacterEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        response.setContentType("application/json; charset=" + encoding);
        try {
            PrintWriter out = null;
            try {
                out =
                    new PrintWriter(new OutputStreamWriter(response
                        .getOutputStream(), encoding));
                out.print(JSON.encode(data));

            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } catch (IOException e) {
            ThrowableUtil.wrapAndThrow(e);
        }
    }
}
