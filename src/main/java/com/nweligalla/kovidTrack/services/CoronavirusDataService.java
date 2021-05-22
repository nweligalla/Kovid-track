package com.nweligalla.kovidTrack.services;

import com.nweligalla.kovidTrack.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


@Service
public class CoronavirusDataService {

    private static String COVID_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct// <-------execute this method after construct
    @Scheduled(cron = "0 */1 * * * *")// <--------- run this method in a scheduled way
    public void fetchCoviddata() throws IOException, InterruptedException {

        List<LocationStats> newStats = new ArrayList<>();

        //send a http request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(COVID_DATA_URL)).build();

        //response
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());


        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);


        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevCases = Integer.parseInt(record.get(record.size() - 2));
            locationStats.setLatestTotalCases(latestCases);
            locationStats.setDifferentFromPrevDays(latestCases - prevCases);
            newStats.add(locationStats);
        }

        this.allStats = newStats;
    }
}
