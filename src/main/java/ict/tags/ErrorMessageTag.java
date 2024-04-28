/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author Soman
 */
public class ErrorMessageTag extends TagSupport {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            if (message != null && !message.isEmpty()) {
                out.print("<p style='color:red;'>" + message + "</p>");
            }
        } catch (Exception e) {
            throw new JspException("Error in ErrorMessageTag", e);
        }
        return SKIP_BODY;
    }
}
