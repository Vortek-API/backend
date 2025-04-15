package vortek.sistponto.VortekPonto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vortek.sistponto.VortekPonto.Repositories.ColaboradorRepository;
import vortek.sistponto.VortekPonto.Repositories.EmpresaRepository;

import java.util.HashMap;
import java.util.Map;

@Service

public class DashboardService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public Map<String, Object> getResumoDashboard() {
        Map<String, Object> resumo = new HashMap<>();

        resumo.put("totalEmpresas", empresaRepository.count());
        resumo.put("totalColaboradores", colaboradorRepository.count());

        // local para adicionar mais dados futuramente se for necessário

        return resumo;
    }
    
}
