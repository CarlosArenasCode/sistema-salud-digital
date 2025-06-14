// CONTROLADORES SIMPLIFICABLES
//
// La mayoría de tus controladores solo extienden BaseController
// sin funcionalidad adicional

// ANTES (25+ líneas):
/*
@Tag(name = "Pacientes", description = "Operaciones relacionadas con pacientes")
@RestController
@RequestMapping("/pacientes")
@Validated
public class PacienteController extends BaseController<PacienteEntity, Long> {
    
    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Override
    protected BaseService<PacienteEntity, Long> getService() {
        return pacienteService;
    }
    // Comentarios redundantes...
}
*/

// DESPUÉS (12 líneas) - con Lombok:
@RestController
@RequestMapping("/pacientes")
@Tag(name = "Pacientes")
@RequiredArgsConstructor
public class PacienteController extends BaseController<PacienteEntity, Long> {
    private final PacienteService service;
    
    @Override
    protected BaseService<PacienteEntity, Long> getService() { return service; }
}

// BENEFICIO: 50% menos código en controladores simples
