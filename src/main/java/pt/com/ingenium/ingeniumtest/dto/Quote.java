package pt.com.ingenium.ingeniumtest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * For time optimization, i will leave the correspondent json name 
 * @author marcovicandido
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
	
	private Id _id;
	private String entryID;
	private Key key;
	private QuoteType quoteType;
	private Time time;
	private Bid bid;
	private Ask ask;
	private BidVolume bidVolume;
	private AskVolume askVolume;
	private Depth depth;
	private PositionNumber positionNumber;
	private String compID;
	private ValidTime validTime;

	public long getTimestamp() {
		return this.getTime() != null ? this.getTime().get$numberLong() : 0;
	}

	public double getBidValue() {
		return this.getBid() != null ? this.getBid().get$numberInt() : 0;
	}

	public long getBidVolumeValue() {
		return this.getBidVolume() != null ? this.getBidVolume().get$numberInt() : 0;
	}

	public long getAskVolumeValue() {
		return this.getAskVolume() != null ? this.getAskVolume().get$numberInt() : 0;
	}

	public double getAskValue() {
		return this.getAsk() != null ? this.getAsk().get$numberInt() : 0;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Ask {

	@Getter
	@Setter
	private double $numberInt;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class AskVolume {

	@Getter
	@Setter
	private long $numberInt;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Bid {

	@Getter
	@Setter
	private double $numberInt;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class BidVolume {

	@Getter
	@Setter
	private long $numberInt;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Depth {

	@Getter
	@Setter
	private String $numberInt;
	
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Id {

	@Getter
	@Setter
	private String $oid;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Key {

	@Getter
	@Setter
	private String $numberLong;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class PositionNumber {

	@Getter
	@Setter
	private String $numberInt;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class QuoteType {
	
	@Getter
	@Setter
	private String numberInt;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Time {

	@Getter
	@Setter
	private long $numberLong;
	
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ValidTime {

	@Getter
	@Setter
	private String $numberInt;
}
