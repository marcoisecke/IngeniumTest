package pt.com.ingenium.ingeniumtest.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import lombok.RequiredArgsConstructor;
import pt.com.ingenium.ingeniumtest.dto.ResponseQuote;
import pt.com.ingenium.ingeniumtest.service.interfaces.IQuoteService;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuotesController {
	
	private final IQuoteService quoteService;
	
	//FOR PURPOSE TEST
	//@PostConstruct
	//public void testInit() throws StreamReadException, DatabindException, IOException, Exception {
	//	this.processQuotes();
	//}
	
	//TODO API DOCUMENTATION
	//TODO RESPONSE DOCUMENTATION
	@GetMapping("/")
	public List<ResponseQuote> processQuotes() throws StreamReadException, DatabindException, IOException, Exception {
		return this.quoteService.processQuotes();
	}

}
