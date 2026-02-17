package com.example.demo.payment;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;


//	 RazorpayClient client = new RazorpayClient("rzp_test_SGWwdWaVUtSKqC", "DlceDQk12EYvz1or14y0IXlZ");
	@Service
	public class PaymentService {

	    private static final String KEY = "rzp_test_SGWwdWaVUtSKqC";
	    private static final String SECRET = "DlceDQk12EYvz1or14y0IXlZ";

	    public Order createOrder(int amount) throws RazorpayException {

	        RazorpayClient client = new RazorpayClient(KEY, SECRET);

	        JSONObject orderRequest = new JSONObject();
	        orderRequest.put("amount", amount * 100); // convert ₹ to paisa
	        orderRequest.put("currency", "INR");
	        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

	        return client.orders.create(orderRequest);
	    }
}