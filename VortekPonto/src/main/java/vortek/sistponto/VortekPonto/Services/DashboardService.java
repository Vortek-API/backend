package vortek.sistponto.vortekponto.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vortek.sistponto.vortekponto.repositories.ColaboradorRepository;
import vortek.sistponto.vortekponto.repositories.EmpresaRepository;

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
