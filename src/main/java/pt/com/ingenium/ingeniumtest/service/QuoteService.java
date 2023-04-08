package pt.com.ingenium.ingeniumtest.service;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import lombok.RequiredArgsConstructor;
import mathUtils.MathUtils;
import pt.com.ingenium.ingeniumtest.dto.Quote;
import pt.com.ingenium.ingeniumtest.dto.ResponseQuote;
import pt.com.ingenium.ingeniumtest.service.interfaces.IQuoteService;

@Service(value = "quoteService")
@RequiredArgsConstructor
public class QuoteService implements IQuoteService {
	private static final int DIVISOR_N_SEC = 1_000_000_000;
	private static final int WHOLE_TIMEFRAME = 30;

	@Override
	public List<ResponseQuote> processQuotes() {
		List<ResponseQuote> responseQuotes = null;
		try {
			List<Quote> quotes = getAllQuotesFromDataBase();
			TreeMap<LocalDateTime, List<Quote>> groupedQuotes = groupByWholeTimeframe(quotes, WHOLE_TIMEFRAME);
			responseQuotes = calculateQuotesBy30(groupedQuotes);
		} catch (Exception e) {
			// TODO WIP
			e.printStackTrace();
		}
		return responseQuotes;
	}

	/**
	 * 
	 * @param groupedQuotes
	 * @return
	 */
	private List<ResponseQuote> calculateQuotesBy30(TreeMap<LocalDateTime, List<Quote>> groupedQuotes) {
		List<ResponseQuote> responseList = new ArrayList<ResponseQuote>();
		for (Map.Entry<LocalDateTime, List<Quote>> entry : groupedQuotes.entrySet()) {
			List<Quote> intervalQuotes = entry.getValue();

			ResponseQuote response = new ResponseQuote(entry.getKey(), entry.getKey().plusMinutes(WHOLE_TIMEFRAME),
					MathUtils.getMax(intervalQuotes, Quote::getBidValue),
					MathUtils.getMin(intervalQuotes, Quote::getBidValue),
					MathUtils.getAverage(intervalQuotes, Quote::getBidValue),
					MathUtils.getMedian(intervalQuotes, Quote::getBidValue),
					MathUtils.getMax(intervalQuotes, Quote::getAskValue),
					MathUtils.getMin(intervalQuotes, Quote::getAskValue),
					MathUtils.getAverage(intervalQuotes, Quote::getAskValue),
					MathUtils.getMedian(intervalQuotes, Quote::getAskValue),
					MathUtils.getTotal(intervalQuotes, Quote::getBidVolumeValue),
					MathUtils.getTotal(intervalQuotes, Quote::getAskVolumeValue));
			responseList.add(response);

		}
		return responseList;
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public List<Quote> getAllQuotesFromDataBase() throws Exception {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new StringReader(new String(Files.readAllBytes(Paths.get("euraud.json")))));
		reader.setLenient(true);
		List<Quote> quotes = new ArrayList<Quote>();

		try {
			quotes = Arrays.asList(gson.fromJson(reader, Quote[].class));
		} catch (JsonSyntaxException e) {
			// TODO WIP
			e.printStackTrace();
		}

		return quotes;
	}

	/**
	 * 
	 * @param quotes
	 * @param timeIntervalMinutes
	 * @return
	 */
	private TreeMap<LocalDateTime, List<Quote>> groupByWholeTimeframe(List<Quote> quotes, int timeIntervalMinutes) {
		TreeMap<LocalDateTime, List<Quote>> groupedQuotes = new TreeMap<>();

		Collections.sort(quotes, Comparator.comparingLong(Quote::getTimestamp));

		for (Quote quote : quotes) {
			long timestamp = quote.getTimestamp();
			LocalDateTime dateTime = Instant.ofEpochSecond(timestamp / DIVISOR_N_SEC, timestamp % DIVISOR_N_SEC)
					.atZone(ZoneId.systemDefault()).toLocalDateTime();

			LocalDateTime intervalStart = getIntervalStart(dateTime, timeIntervalMinutes);
			groupedQuotes.computeIfAbsent(intervalStart, k -> new ArrayList<>()).add(quote);
		}

		return groupedQuotes;
	}

	/**
	 * 
	 * @param dateTime
	 * @param timeIntervalMinutes
	 * @return
	 */
	private static LocalDateTime getIntervalStart(LocalDateTime dateTime, int timeIntervalMinutes) {
		return dateTime.minusMinutes(dateTime.getMinute() % timeIntervalMinutes).withSecond(0).withNano(0);
	}

}
