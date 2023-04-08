package pt.com.ingenium.ingeniumtest.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseQuote {

	private LocalDateTime init;
	private LocalDateTime end;
	private Double maxBid;
	private Double minBid;
	private Double averageBid;
	private Double medianBid;
	private Double maxAsk;
	private Double minAsk;
	private Double averageAsk;
	private Double medianAsk;
	private long totalBidVolume;
	private long totalAskVolume;

}
