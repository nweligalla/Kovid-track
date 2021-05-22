package com.nweligalla.kovidTrack.controllers;

import com.nweligalla.kovidTrack.models.LocationStats;
import com.nweligalla.kovidTrack.services.CoronavirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeControllers {

    @Autowired
    CoronavirusDataService coronavirusDataService;



    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronavirusDataService.getAllStats();
        int totalCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDifferentFromPrevDays()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalCases", totalCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return "home";
    }
}
