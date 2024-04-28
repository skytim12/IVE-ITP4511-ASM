/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import ict.bean.EquipmentBean;
import java.util.List;

/**
 *
 * @author Soman
 */
public class EquipmentListTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            List<EquipmentBean> equipmentList = (List<EquipmentBean>) pageContext.getRequest().getAttribute("equipmentList");

            if (equipmentList != null && !equipmentList.isEmpty()) {
                for (EquipmentBean equipment : equipmentList) {
                    out.println("<li>" + equipment.getName() + " - " + equipment.getDescription() + "</li>");
                }
            } else {
                out.println("No equipment data available.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new JspException("Error in EquipmentListTag: " + e.getMessage(), e);
        }

        return SKIP_BODY;
    }
}


