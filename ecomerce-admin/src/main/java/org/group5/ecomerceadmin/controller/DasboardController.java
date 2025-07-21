package org.group5.ecomerceadmin.controller;

import org.group5.ecomerceadmin.dto.DashboardStats;
import org.group5.ecomerceadmin.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DasboardController {
    
    @Autowired
    private DashboardService dashboardService;
    
    @GetMapping({"/index", "/dashboard"})
    public String index(Model model) {
        try {
            DashboardStats stats = dashboardService.getDashboardStats();
            model.addAttribute("stats", stats);
        } catch (Exception e) {
            // In case of error, provide empty stats
            model.addAttribute("stats", new DashboardStats());
            model.addAttribute("error", "Unable to load dashboard data: " + e.getMessage());
        }
        return "index";
    }
}
