/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package brj979.hw1.tempconvert.servlet;

import brj979.hw1.tempconvert.model.TempConverter;
import brj979.hw1.tempconvert.model.TempUnit;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rfoy
 */
public class ConvertTempServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. get information from request
        
        String origTempVal = request.getParameter("origTemp");
        String origUnitStr = request.getParameter("origUnit");
        
        // 2. validate and convert data from request
        try{
        double origTemp = Double.parseDouble(origTempVal);
        TempUnit origUnit = TempUnit.valueOf(origUnitStr);
        
        // 3. "do it"
        TempConverter tc = new TempConverter();
        tc.setOrigTemp(origTemp);
        tc.setOrigUnit(origUnit);
        
        // 4. save values on request.
        request.setAttribute("tempConverter", tc);
        } catch (Exception ex){
            request.setAttribute("errMsg", "Invalid input value encountered. Please correct.");
            log(ex.toString());
        }


        // 5. forward control
        // can get RequestDispatcher from either request or the servlet context
        RequestDispatcher rd = request.getRequestDispatcher("/TempConvertResult.jsp");
        rd.forward(request, response);
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
