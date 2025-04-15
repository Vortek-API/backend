package vortek.sistponto.VortekPonto.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vortek.sistponto.VortekPonto.Services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;


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
