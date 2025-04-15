package vortek.sistponto.vortekponto.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vortek.sistponto.vortekponto.services.DashboardService;


@RestController
@RequestMapping("/api/dashboard")

public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/resumo")
    public Map<String, Object> getResumoDashboard(){
        return dashboardService.getResumoDashboard();
    }
}
