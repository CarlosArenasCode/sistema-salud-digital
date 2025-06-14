// SERVICIOS SIMPLIFICABLES
// 
// Actual: PacienteService, MedicoService, etc. tienen 30+ líneas
// Optimizado: Pueden reducirse a 15-20 líneas cada uno

// ANTES (30 líneas):
/*
@Service
public class PacienteService extends BaseService<PacienteEntity, Long> {
    private final PacienteJpaRepository pacienteRepository;
    
    @Autowired
    public PacienteService(PacienteJpaRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }
    
    @Override
    protected JpaRepository<PacienteEntity, Long> getRepository() {
        return pacienteRepository;
    }
    
    @Override
    protected String getEntityName() {
        return "Paciente";
    }
    // Comentarios innecesarios...
}
*/

// DESPUÉS (15 líneas) - con Lombok:
@Service
@RequiredArgsConstructor
public class PacienteService extends BaseService<PacienteEntity, Long> {
    private final PacienteJpaRepository repository;
    
    @Override
    protected JpaRepository<PacienteEntity, Long> getRepository() { return repository; }
    
    @Override
    protected String getEntityName() { return "Paciente"; }
}

// BENEFICIO: 50% menos código en servicios simples
