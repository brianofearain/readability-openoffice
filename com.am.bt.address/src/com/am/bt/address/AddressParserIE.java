package com.am.bt.address;

public class AddressParserIE {

	public Street Parse(String streetAddress)
	{
		if (!streetAddress.isEmpty())
			return new Street();

		Street result;
		String input = streetAddress.toUpperCase();

		String regex = buildPattern();
		if (regex.isMatch(input))
		{
			var m = re.Match(input);
			result = new StreetAddress
					{
				HouseNumber = m.Groups["HouseNumber"].Value,
						StreetPrefix = m.Groups["StreetPrefix"].Value,
						StreetName = m.Groups["StreetName"].Value,
						StreetType = m.Groups["StreetType"].Value,
						StreetSuffix = m.Groups["StreetSuffix"].Value,
						Apt = m.Groups["Apt"].Value,
					};
		}
		else
		{
			result = new StreetAddress
					{
				StreetName = input,
					};
		}
		return result;
	}

	public String[] parseChunks (String addressLine){

		// split based on white spaces
		String[] parsedChunks = addressLine.toUpperCase().split("\\s"); 

		// check country
		for (int i = 0; i < parsedChunks.length; i++)
			if (country.get(parseChunks[i])
					parseChunks[i] = countryl

					// check county , for Dublin check postal code
					if (countyNotDublin)
						nextPos = areaCode

						TODO...
						// street/road....usually number but not always....
						// c/o, fao, for attenction of, attention to, attn....

						return parsedChunks;
	}

	private String buildPattern() {
		String pattern = "^" + // beginning of string
				"(?<HouseNumber>\\d+)" + // 1 or more digits
				"(?:\\s+(?<StreetPrefix>" + getStreetPrefixes() + "))?" + // whitespace
				// +
				// valid
				// prefix
				// (optional)
				"(?:\\s+(?<StreetName>.*?))" + // whitespace + anything
				"(?:" + // group (optional) {
				"(?:\\s+(?<StreetType>" + getStreetTypes() + "))" + // whitespace
				// + valid
				// street
				// type
				"(?:\\s+(?<StreetSuffix>" + getStreetSuffixes() + "))?" + // whitespace
				// +
				// valid
				// street
				// suffix
				// (optional)
				"(?:\\s+(?<Apt>.*))?" + // whitespace + anything (optional)
				")?" + // }
				"$"; // end of string

		return pattern;
	}

	private String getStreetPrefixes() {
		return "TE|NW|HW|RD|E|MA|EI|NO|AU|SE|GR|OL|W|MM|OM|SW|ME|HA|JO|OV|S|OH|NE|K|N";
	}

	private String getStreetTypes() {
		return "TE|STCT|DR|SPGS|PARK|GRV|CRK|XING|BR|PINE|CTS|TRL|VI|RD|PIKE|MA|LO|TER|UN|CIR|WALK|CO|RUN|FRD|LDG|ML|AVE|NO|PA|SQ|BLVD|VLGS|VLY|GR|LN|HOUSE|VLG|OL|STA|CH|ROW|EXT|JC|BLDG|FLD|CT|HTS|MOTEL|PKWY|COOP|ACRES|ESTS|SCH|HL|CORD|ST|CLB|FLDS|PT|STPL|MDWS|APTS|ME|LOOP|SMT|RDG|UNIV|PLZ|MDW|EXPY|WALL|TR|FLS|HBR|TRFY|BCH|CRST|CI|PKY|OV|RNCH|CV|DIV|WA|S|WAY|I|CTR|VIS|PL|ANX|BL|ST TER|DM|STHY|RR|MNR";
	}

	private String getStreetSuffixes() {
		return "NW|E|SE|W|SW|S|NE|N";
	}

}