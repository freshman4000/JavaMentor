package com.freshman4000.servlets;

import com.freshman4000.models.BankClient;
import com.freshman4000.services.*;
import com.freshman4000.utility.DBException;
import com.freshman4000.utility.PageGenerator;
import com.freshman4000.utility.ResponseComposer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MoneyTransactionServlet extends HttpServlet {

    BankClientService bankClientService = new BankClientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       resp.getWriter().println(PageGenerator.getInstance().getPage("moneyTransactionPage.html", new HashMap<>()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVars = Collections.synchronizedMap(new HashMap<>());
        String senderName = req.getParameter("senderName");
        String senderPassword = req.getParameter("senderPass");
        String count = req.getParameter("count");
        String receiverName = req.getParameter("nameTo");
        BankClient sender;
        resp.setContentType("text/html");
            try {
                //checking if sender exists in database
                if ((sender = bankClientService.getClientByName(senderName)) != null) {
                    //checking if provided password matches sender password from database
                    if (sender.getPassword().equals(senderPassword)) {
                        //checking if receiver exists in database
                        if (bankClientService.getClientByName(receiverName) != null) {
                            //amount validation block
                            long amount = 0L;
                            try {
                                amount = Long.parseLong(count);
                            } catch (NumberFormatException e) {
                                //tell user that wrong amount input has taken place - not a number
                                ResponseComposer.formResponse(resp, 200, pageVars,
                                        PageGenerator.getInstance(), "Wrong amount type! Please input numeric value!");
                            }
                            //in case transaction amount is positive and less that sender funds - WE DO transaction here
                            if (amount > 0 && amount <= sender.getMoney()) {
                                //transaction logic if everything is ok
                                try {
                                    bankClientService.sendMoneyToClient(sender, receiverName, amount);
                                    ResponseComposer.formResponse(resp, 200, pageVars,
                                            PageGenerator.getInstance(), "The transaction was successful");
                                    //inform user about DB error
                                } catch (DBException e) {
                                    ResponseComposer.formResponse(resp, 200, pageVars,
                                            PageGenerator.getInstance(), "transaction rejected");
                                }
                            } else {
                                //in case funds are insufficient
                                if (amount > 0) {
                                    ResponseComposer.formResponse(resp, 200, pageVars,
                                            PageGenerator.getInstance(), "transaction rejected");
                                    //in case amount is less or equals zero
                                } else {
                                    ResponseComposer.formResponse(resp, 200, pageVars,
                                            PageGenerator.getInstance(), "Amount should be a positive numeric value! Re-input please!");
                                }
                            }
                            //if receiver doesn't exist in database
                        } else {
                            ResponseComposer.formResponse(resp, 200, pageVars,
                                    PageGenerator.getInstance(), "Receiver does not exist in database!");
                        }
                        //if passwords don't match
                    } else {
                        ResponseComposer.formResponse(resp, 200, pageVars,
                                PageGenerator.getInstance(), "transaction rejected");
                    }
                    //if sender name is not in base
                } else {
                    ResponseComposer.formResponse(resp, 200, pageVars,
                            PageGenerator.getInstance(), "Can't find registered user with such name!");
                }
                //if some problem on server side
            } catch (DBException e) {
                ResponseComposer.formResponse(resp, 500, pageVars,
                    PageGenerator.getInstance(), "Database error! PLease try one more time!");
            }
    }
}
